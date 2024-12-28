package com.biblio.dao.impl;

import com.biblio.dao.IBookTemplateDAO;
import com.biblio.dto.request.LoadRelatedBooksRequest;
import com.biblio.dto.request.SearchBookRequest;
import com.biblio.entity.Book;
import com.biblio.entity.BookTemplate;
import com.biblio.enumeration.EBookCondition;
import com.biblio.enumeration.EBookFormat;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EOrderStatus;

import java.time.LocalDateTime;
import java.util.*;

public class BookTemplateDAOImpl extends GenericDAOImpl<BookTemplate> implements IBookTemplateDAO {

    private final BookDAOImpl bookDAO = new BookDAOImpl();
    private final AuthorDAOImpl authorDAO = new AuthorDAOImpl();
    private final TranslatorDAOImpl translatorDAO = new TranslatorDAOImpl();
    private final ReviewDAOImpl reviewDAO = new ReviewDAOImpl();

    public BookTemplateDAOImpl() {
        super(BookTemplate.class);
    }

    @Override
    public BookTemplate findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<BookTemplate> findAll() {
        return super.findAll();
    }

    @Override
    public List<BookTemplate> findAllForManagement() {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT bt ")
                .append("FROM BookTemplate bt ")
                .append("LEFT JOIN FETCH bt.mediaFiles m");

        List<BookTemplate> bookTemplates = super.findAll(jpql.toString());

        for (BookTemplate bookTemplate : bookTemplates) {
            Set<Book> books = new HashSet<>(bookDAO.findByBookTemplate(bookTemplate));
            bookTemplate.setBooks(books);
        }
        return bookTemplates;
    }

    @Override
    public List<BookTemplate> findAllForHome() {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT bt ")
                .append("FROM BookTemplate bt ")
                .append("LEFT JOIN FETCH bt.reviews r")
                .append("LEFT JOIN FETCH bt.mediaFiles m");

        List<BookTemplate> bookTemplates = super.findAll(jpql.toString());

        for (BookTemplate bookTemplate : bookTemplates) {
            Set<Book> books = new HashSet<>(bookDAO.findByBookTemplate(bookTemplate));
            bookTemplate.setBooks(books);
        }
        return bookTemplates;
    }

    @Override
    public BookTemplate findOneForDetails(Long id) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT DISTINCT bt ")
                .append("FROM BookTemplate bt ")
                .append("LEFT JOIN FETCH bt.mediaFiles m")
                .append("LEFT JOIN FETCH bt.publisher p ")
                .append("WHERE bt.id = :id");

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        BookTemplate bookTemplate = super.findSingleByJPQL(jpql.toString(), params);
        bookTemplate.setAuthors(new HashSet<>(authorDAO.findByBookTemplate(bookTemplate)));
        bookTemplate.setTranslators(new HashSet<>(translatorDAO.findByBookTemplate(bookTemplate)));
        bookTemplate.setBooks(new HashSet<>(bookDAO.findByBookTemplate(bookTemplate)));
        bookTemplate.setReviews(new HashSet<>(reviewDAO.findByBookTemplate(bookTemplate)));
        return bookTemplate;
    }

    @Override
    public List<BookTemplate> findByCriteria(SearchBookRequest request) {
        StringBuilder jpql = new StringBuilder("SELECT bt FROM BookTemplate bt "
                + "JOIN FETCH bt.books b "
                + "LEFT JOIN FETCH bt.mediaFiles "
                + "JOIN FETCH b.bookMetadata bmd "
                + "WHERE b.id = (SELECT MIN(b2.id) FROM Book b2 WHERE b2.bookTemplate.id = bt.id) AND bt.status = 'ON_SALE' "
                + "AND b.sellingPrice >= :minPrice AND b.sellingPrice <= :maxPrice "
                + "AND (SELECT COALESCE(AVG(r2.rate), 0) FROM bt.reviews r2 WHERE r2.bookTemplate.id = bt.id) >= :reviewRate ");

        Map<String, Object> params = new HashMap<>();
        params.put("minPrice", Double.valueOf(request.getMinPrice()));
        params.put("maxPrice", Double.valueOf(request.getMaxPrice()));
        params.put("reviewRate", Double.valueOf(request.getReviewRate()));

        jpql.append(" AND (");
        String[] searchTerms = request.getTitle().split("\\s+");
        for (int i = 0; i < searchTerms.length; i++) {
            jpql.append("b.title LIKE :title").append(i);
            params.put("title" + i, "%" + searchTerms[i] + "%");
            if (i < searchTerms.length - 1) {
                jpql.append(" OR ");
            }
        }
        jpql.append(")");

        if (request.getCategoryId() != null) {
            jpql.append(" AND b.subCategory.category.id = :categoryId");
            params.put("categoryId", request.getCategoryId());
        }

        if (request.getCondition() != null) {
            jpql.append(" AND b.condition = :condition");
            params.put("condition", EBookCondition.valueOf(request.getCondition()));
        }

        if (request.getFormat() != null) {
            jpql.append(" AND b.format = :format");
            params.put("format", EBookFormat.valueOf(request.getFormat()));
        }

        String orderByClause = null;
        String sortBy = request.getSortBy();
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy) {
                case "3":
                    orderByClause = " ORDER BY b.sellingPrice ASC";
                    break;
                case "4":
                    orderByClause = " ORDER BY b.sellingPrice DESC";
                    break;
                case "5":
                    orderByClause = " ORDER BY b.publicationDate DESC";
                    break;
                case "bestseller":
                    orderByClause = " ORDER BY b.sales DESC";
                    break;
                default:
                    break;
            }
        }

        if (orderByClause != null) {
            jpql.append(orderByClause);
        }

        return super.findByJPQLPaginated(jpql.toString(), request.getPageNumber(), request.getPerPage(), params);
    }

    @Override
    public Long countByCriteria(SearchBookRequest request) {

        StringBuilder jpql = new StringBuilder("SELECT count(DISTINCT bt.id) FROM BookTemplate bt "
                + "JOIN bt.books b "
                + "WHERE b.id = (SELECT MIN(b2.id) FROM Book b2 WHERE b2.bookTemplate.id = bt.id) AND bt.status = 'ON_SALE' "
                + "AND b.sellingPrice >= :minPrice AND b.sellingPrice <= :maxPrice "
                + "AND (SELECT COALESCE(AVG(r2.rate), 0) FROM bt.reviews r2 WHERE r2.bookTemplate.id = bt.id) >= :reviewRate ");

        Map<String, Object> params = new HashMap<>();
        params.put("minPrice", Double.valueOf(request.getMinPrice()));
        params.put("maxPrice", Double.valueOf(request.getMaxPrice()));
        params.put("reviewRate", Double.valueOf(request.getReviewRate()));

        if (request.getTitle() != null) {
            String[] searchTerms = request.getTitle().split("\\s+");
            jpql.append(" AND (");
            for (int i = 0; i < searchTerms.length; i++) {
                jpql.append("b.title LIKE :title").append(i);
                params.put("title" + i, "%" + searchTerms[i] + "%");
                if (i < searchTerms.length - 1) {
                    jpql.append(" OR ");
                }
            }
            jpql.append(")");
        }

        if (request.getCategoryId() != null) {
            jpql.append(" AND b.subCategory.category.id = :categoryId");
            params.put("categoryId", request.getCategoryId());
        }

        if (request.getCondition() != null) {
            jpql.append(" AND b.condition = :condition");
            params.put("condition", EBookCondition.valueOf(request.getCondition()));
        }

        if (request.getFormat() != null) {
            jpql.append(" AND b.format = :format");
            params.put("format", EBookFormat.valueOf(request.getFormat()));
        }

        return super.countByJPQL(jpql.toString(), params);
    }

    @Override
    public Long countSoldById(Long bookTemplateId) {
        String jpql = "SELECT COUNT(b) FROM Book b " +
                "JOIN b.bookTemplate bt " +
                "WHERE bt.id = :bookTemplateId " +
                "AND b.bookMetadata.status = :status";

        Map<String, Object> params = new HashMap<>();
        params.put("bookTemplateId", bookTemplateId);
        params.put("status", EBookMetadataStatus.SOLD);

        return super.countByJPQL(jpql, params);
    }

    @Override
    public Long countInstockById(Long bookTemplateId) {
        String jpql = "SELECT COUNT(b) FROM Book b " +
                "JOIN b.bookTemplate bt " +
                "WHERE bt.id = :bookTemplateId " +
                "AND b.bookMetadata.status = :status";

        Map<String, Object> params = new HashMap<>();
        params.put("bookTemplateId", bookTemplateId);
        params.put("status", EBookMetadataStatus.IN_STOCK);

        return super.countByJPQL(jpql, params);
    }

    @Override
    public Long countAll() {
        return super.countByJPQL("SELECT count(DISTINCT bt.id) FROM BookTemplate bt WHERE bt.status != 'OUT_OF_STOCK'");
    }

    @Override
    public List<BookTemplate> findTop20() {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT bt ")
                .append("FROM BookTemplate bt ")
                .append("JOIN FETCH bt.books b ")
                .append("LEFT JOIN FETCH bt.reviews r ")
                .append("LEFT JOIN FETCH bt.mediaFiles m ")
                .append("WHERE b.id = (SELECT MIN(b2.id) FROM Book b2 WHERE b2.bookTemplate.id = bt.id) ")
                .append("AND bt.status = 'ON_SALE' ")
                .append("AND b.bookMetadata.status = 'IN_STOCK' ");

        List<BookTemplate> bookTemplates = super.findAll(jpql.toString(), 20);

        for (BookTemplate bookTemplate : bookTemplates) {
            Set<Book> books = new HashSet<>(bookDAO.findByBookTemplate(bookTemplate));
            bookTemplate.setBooks(books);
        }
        return bookTemplates;
    }

    @Override
    public List<BookTemplate> findRelatedBooks(LoadRelatedBooksRequest request) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT bt ")
                .append("FROM BookTemplate bt ")
                .append("JOIN FETCH bt.books b ")
                .append("LEFT JOIN FETCH bt.reviews r ")
                .append("LEFT JOIN FETCH bt.mediaFiles m ")
                .append("WHERE bt.id != :bookTemplateId ")
                .append("AND b.subCategory.category.id = (SELECT DISTINCT b1.subCategory.category.id FROM Book b1 WHERE b1.bookTemplate.id = :bookTemplateId) ")
                .append("AND bt.status = 'ON_SALE' ")
                .append("AND b.bookMetadata.status = 'IN_STOCK' ");

        Map<String, Object> params = new HashMap<>();
        params.put("bookTemplateId", request.getBookTemplateId());

        List<BookTemplate> bookTemplates = super.findByJPQL(jpql.toString(), params);

        for (BookTemplate bookTemplate : bookTemplates) {
            Set<Book> books = new HashSet<>(bookDAO.findByBookTemplate(bookTemplate));
            bookTemplate.setBooks(books);
        }
        return bookTemplates;
    }

    @Override
    public BookTemplate update(BookTemplate bookTemplate) {
        return super.update(bookTemplate);
    }

    @Override
    public Integer countOrdersByStatus(Long id, EOrderStatus status) {
        String sql = """
                    SELECT SUM(oi_books) amount
                    FROM (
                        SELECT oi.order_id, SUM(i_books) oi_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(book_id) i_books
                            FROM (
                                SELECT b.id
                                FROM book_template bt
                                LEFT JOIN book b
                                ON bt.id = b.book_template_id
                                WHERE bt.id = :bookTemplateId
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_bi
                        JOIN order_item oi
                        ON order_bi.order_item_id = oi.id
                        GROUP BY oi.order_id
                    ) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("bookTemplateId", id);
        params.put("orderStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersInRangeByStatus(Long id, LocalDateTime from, LocalDateTime to, EOrderStatus status) {
        String sql = """
                    SELECT SUM(oi_books) amount
                    FROM (
                        SELECT oi.order_id, SUM(i_books) oi_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(book_id) i_books
                            FROM (
                                SELECT b.id
                                FROM book_template bt
                                LEFT JOIN book b
                                ON bt.id = b.book_template_id
                                WHERE bt.id = :bookTemplateId
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_bi
                        JOIN order_item oi
                        ON order_bi.order_item_id = oi.id
                        GROUP BY oi.order_id
                    ) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus AND (o.order_date BETWEEN :startDate AND :endDate)
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("bookTemplateId", id);
        params.put("orderStatus", status.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksInOrderByStatus(Long id, EBookMetadataStatus bookStatus, EOrderStatus orderStatus) {
        String sql = """
                    SELECT SUM(oi_books) amount
                    FROM (
                        SELECT oi.order_id, SUM(i_books) oi_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(book_id) i_books
                            FROM (
                                SELECT b.id
                                FROM (
                                    SELECT b.id
                                    FROM book_template bt
                                    LEFT JOIN book b
                                    ON bt.id = b.book_template_id
                                    WHERE bt.id = :bookTemplateId
                                ) b
                                JOIN book_metadata bm
                                ON b.id = bm.id AND bm.status = :bookStatus
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_bi
                        JOIN order_item oi
                        ON order_bi.order_item_id = oi.id
                        GROUP BY oi.order_id
                    ) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("bookTemplateId", id);
        params.put("bookStatus", bookStatus.name());
        params.put("orderStatus", orderStatus.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksInRangeByStatus(Long id, LocalDateTime from, LocalDateTime to, EBookMetadataStatus bookStatus, EOrderStatus orderStatus) {
        String sql = """
                    SELECT SUM(oi_books) amount
                    FROM (
                        SELECT oi.order_id, SUM(i_books) oi_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(book_id) i_books
                            FROM (
                                SELECT b.id
                                FROM (
                                    SELECT b.id
                                    FROM book_template bt
                                    LEFT JOIN book b
                                    ON bt.id = b.book_template_id
                                    WHERE bt.id = :bookTemplateId
                                ) b
                                JOIN book_metadata bm
                                ON b.id = bm.id AND bm.status = :bookStatus
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_bi
                        JOIN order_item oi
                        ON order_bi.order_item_id = oi.id
                        GROUP BY oi.order_id
                    ) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus AND (o.order_date BETWEEN :startDate AND :endDate)
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("bookTemplateId", id);
        params.put("bookStatus", bookStatus.name());
        params.put("orderStatus", orderStatus.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueBooksSoldInRange(Long id, LocalDateTime from, LocalDateTime to, EOrderStatus status) {
        String sql = """
                    SELECT SUM(value_books) revenue
                    FROM (
                        SELECT oi.order_id, SUM(value_order_item) value_books
                        FROM (
                            SELECT oib.order_item_id, SUM(books.selling_price) value_order_item
                            FROM (
                                SELECT b.id, b.selling_price
                                FROM book_template bt
                                LEFT JOIN book b
                                ON bt.id = b.book_template_id
                                WHERE bt.id = :bookTemplateId
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_bi
                        JOIN order_item oi
                        ON order_bi.order_item_id = oi.id
                        GROUP BY oi.order_id
                    ) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus AND (o.order_date BETWEEN :startDate AND :endDate)
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("bookTemplateId", id);
        params.put("orderStatus", status.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueBooksSold(Long id, EOrderStatus status) {
        String sql = """
                    SELECT SUM(value_books) revenue
                    FROM (
                        SELECT oi.order_id, SUM(value_order_item) value_books
                        FROM (
                            SELECT oib.order_item_id, SUM(books.selling_price) value_order_item
                            FROM (
                                SELECT b.id, b.selling_price
                                FROM book_template bt
                                LEFT JOIN book b
                                ON bt.id = b.book_template_id
                                WHERE bt.id = :bookTemplateId
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_bi
                        JOIN order_item oi
                        ON order_bi.order_item_id = oi.id
                        GROUP BY oi.order_id
                    ) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("bookTemplateId", id);
        params.put("orderStatus", status.name());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public BookTemplate createBookTemplate(BookTemplate bookTemplate) {
        return super.insert(bookTemplate);
    }

    @Override
    public void updateBookTemplate(BookTemplate bookTemplate) {
        super.update(bookTemplate);
    }

    public static void main(String[] args) {
        BookTemplateDAOImpl dao = new BookTemplateDAOImpl();
        System.out.println(dao.findTop20());
    }


}

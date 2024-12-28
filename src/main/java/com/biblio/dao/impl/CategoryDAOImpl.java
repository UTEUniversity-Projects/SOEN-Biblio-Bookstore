package com.biblio.dao.impl;

import com.biblio.dao.ICategoryDAO;
import com.biblio.dto.request.CategoryCreateRequest;
import com.biblio.dto.request.CategoryDeleteRequest;
import com.biblio.dto.request.CategoryUpdateRequest;
import com.biblio.dto.response.CategoryBookCountResponse;
import com.biblio.dto.response.CategoryTotalBookResponse;
import com.biblio.entity.Category;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.dto.request.SearchBookRequest;
import com.biblio.enumeration.EBookCondition;
import com.biblio.enumeration.EBookFormat;
import com.biblio.jpaconfig.JpaConfig;
import com.biblio.mapper.CategoryMapper;

import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDAOImpl extends GenericDAOImpl<Category> implements ICategoryDAO {

    public CategoryDAOImpl() {
        super(Category.class);
    }

    @Override
    public Category getEntityById(Long categoryId) {
        return super.findById(categoryId);
    }

    @Override
    public List<Category> getEntityAll() {
        return super.findAll();
    }

    @Override
    public List<String> getTopSubCategory(Long categoryId) {
        String sql = """
                    SELECT res.name
                    FROM (
                        SELECT s_cate.name, b.sub_category_id, COUNT(b.id) unique_books
                        FROM (
                            SELECT sc.id, sc.name
                            FROM sub_category sc
                            WHERE sc.category_id = :categoryId
                        ) s_cate
                        JOIN book b
                        ON s_cate.id = b.sub_category_id
                        GROUP BY b.sub_category_id
                        ORDER BY unique_books DESC
                    ) res
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);

        return super.findByNativeQuery(sql, params, String.class);
    }

    @Override
    public Category create(CategoryCreateRequest categoryCreateRequest) {
        return super.save(CategoryMapper.toCategory(categoryCreateRequest));
    }

    @Override
    public void update(CategoryUpdateRequest categoryUpdateRequest) {
        super.update(CategoryMapper.toCategory(categoryUpdateRequest));
    }

    @Override
    public void delete(CategoryDeleteRequest categoryDeleteRequest) {

    }

    @Override
    public Integer countBooksTemplateAll(Long categoryId) {
        String sql = """
                    SELECT SUM(res.unique_book_template) amount
                    FROM (
                        SELECT b.sub_category_id, s_cate.name, COUNT(DISTINCT b.book_template_id) unique_book_template
                        FROM (
                            SELECT sc.id, sc.name
                            FROM sub_category sc
                            WHERE sc.category_id = :categoryId
                        ) s_cate
                        JOIN book b
                        ON s_cate.id = b.sub_category_id
                        GROUP BY b.sub_category_id
                        ORDER BY unique_book_template DESC
                    ) res
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksTemplateByStatus(Long categoryId, EBookTemplateStatus status) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM (
                        SELECT b.sub_category_id, b.book_template_id
                        FROM (
                            SELECT sc.id, sc.name
                            FROM sub_category sc
                            WHERE sc.category_id = :categoryId
                        ) s_cate
                        JOIN book b
                        ON s_cate.id = b.sub_category_id
                        GROUP BY b.sub_category_id, b.book_template_id
                    ) s_bt
                    JOIN book_template bt
                    ON s_bt.book_template_id = bt.id
                    WHERE bt.status = :bookTemplateStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("bookTemplateStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksInRangeByStatus(Long categoryId, LocalDateTime from, LocalDateTime to, EBookMetadataStatus bookStatus, EOrderStatus orderStatus) {
        String sql = """
                    SELECT SUM(books_count) amount
                    FROM (
                        SELECT order_id, SUM(total_books) books_count
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT books.id book_id
                                FROM (
                                    SELECT b.sub_category_id, b.id, b.book_metadata_id
                                    FROM (
                                        SELECT sc.id, sc.name
                                        FROM sub_category sc
                                        WHERE sc.category_id = :categoryId
                                    ) s_cate
                                    JOIN book b
                                    ON s_cate.id = b.sub_category_id
                                ) books
                                JOIN book_metadata bm
                                ON books.book_metadata_id = bm.id
                                WHERE bm.status = :bookStatus
                            ) bm_books
                            JOIN order_item_books oib
                            ON bm_books.book_id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_tb
                        JOIN order_item oi
                        ON order_tb.order_item_id = oi.id
                        GROUP BY oi.order_id) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus AND (o.order_date BETWEEN :startDate AND :endDate)
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("bookStatus", bookStatus.name());
        params.put("orderStatus", orderStatus.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksByStatus(Long categoryId, EBookMetadataStatus status) {
        String sql = """
                    SELECT COUNT(books.id) amount
                    FROM (
                        SELECT b.sub_category_id, b.id, b.book_metadata_id
                        FROM (
                            SELECT sc.id, sc.name
                            FROM sub_category sc
                            WHERE sc.category_id = :categoryId
                        ) s_cate
                        JOIN book b
                        ON s_cate.id = b.sub_category_id
                    ) books
                    JOIN book_metadata bm
                    ON books.book_metadata_id = bm.id
                    WHERE bm.status = :bookStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("bookStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksByOrderStatus(Long categoryId, EOrderStatus status) {
        String sql = """
                    SELECT SUM(books_count) amount
                    FROM (
                        SELECT order_id, SUM(total_books) books_count
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT books.id book_id
                                FROM (
                                    SELECT b.sub_category_id, b.id, b.book_metadata_id
                                    FROM (
                                        SELECT sc.id, sc.name
                                        FROM sub_category sc
                                        WHERE sc.category_id = :categoryId
                                    ) s_cate
                                    JOIN book b
                                    ON s_cate.id = b.sub_category_id
                                ) books
                                JOIN book_metadata bm
                                ON books.book_metadata_id = bm.id
                            ) bm_books
                            JOIN order_item_books oib
                            ON bm_books.book_id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_tb
                        JOIN order_item oi
                        ON order_tb.order_item_id = oi.id
                        GROUP BY oi.order_id) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("orderStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersAll(Long categoryId) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM (
                        SELECT order_id, total_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT b.id, b.sub_category_id, b.book_template_id
                                FROM (
                                    SELECT sc.id, sc.name
                                    FROM sub_category sc
                                    WHERE sc.category_id = :categoryId
                                ) s_cate
                                JOIN book b
                                ON s_cate.id = b.sub_category_id
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_tb
                        JOIN order_item oi
                        ON order_tb.order_item_id = oi.id) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersInRangeByStatus(Long categoryId, LocalDateTime from, LocalDateTime to, EOrderStatus status) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM (
                        SELECT order_id, total_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT b.id, b.sub_category_id, b.book_template_id
                                FROM (
                                    SELECT sc.id, sc.name
                                    FROM sub_category sc
                                    WHERE sc.category_id = :categoryId
                                ) s_cate
                                JOIN book b
                                ON s_cate.id = b.sub_category_id
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_tb
                        JOIN order_item oi
                        ON order_tb.order_item_id = oi.id) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus AND (o.order_date BETWEEN :startDate AND :endDate)
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("orderStatus", status.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersByStatus(Long categoryId, EOrderStatus status) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM (
                        SELECT order_id, total_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT b.id, b.sub_category_id, b.book_template_id
                                FROM (
                                    SELECT sc.id, sc.name
                                    FROM sub_category sc
                                    WHERE sc.category_id = :categoryId
                                ) s_cate
                                JOIN book b
                                ON s_cate.id = b.sub_category_id
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_tb
                        JOIN order_item oi
                        ON order_tb.order_item_id = oi.id) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("orderStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueBooksSold(Long categoryId) {
        String sql = """
                    SELECT SUM(books.selling_price) amount
                    FROM (
                        SELECT b.book_metadata_id, b.selling_price
                        FROM (
                            SELECT sc.id, sc.name
                            FROM sub_category sc
                            WHERE sc.category_id = :categoryId
                        ) s_cate
                        JOIN book b
                        ON s_cate.id = b.sub_category_id
                    ) books
                    JOIN book_metadata bm
                    ON books.book_metadata_id = bm.id
                    WHERE bm.status = :bookStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("bookStatus", EBookMetadataStatus.SOLD.name());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueBooksSoldInRange(Long categoryId, LocalDateTime from, LocalDateTime to) {
        String sql = """
                    SELECT SUM(sum_books) amount
                    FROM (
                        SELECT oi.order_id, SUM(sum_order_item) sum_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books, SUM(selling_price) sum_order_item
                            FROM (
                                SELECT b.id, b.selling_price
                                FROM (
                                    SELECT sc.id, sc.name
                                    FROM sub_category sc
                                    WHERE sc.category_id = :categoryId
                                ) s_cate
                                JOIN book b
                                ON s_cate.id = b.sub_category_id
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_tb
                        JOIN order_item oi
                        ON order_tb.order_item_id = oi.id
                        GROUP BY oi.order_id
                    ) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus AND (o.order_date BETWEEN :startDate AND :endDate)
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("orderStatus", EOrderStatus.COMPLETE_DELIVERY.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueOrdersSoldByStatus(Long categoryId, EOrderStatus status) {
        String sql = """
                    SELECT SUM(sum_books) amount
                    FROM (
                        SELECT oi.order_id, SUM(sum_order_item) sum_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books, SUM(selling_price) sum_order_item
                            FROM (
                                SELECT b.id, b.selling_price
                                FROM (
                                    SELECT sc.id, sc.name
                                    FROM sub_category sc
                                    WHERE sc.category_id = :categoryId
                                ) s_cate
                                JOIN book b
                                ON s_cate.id = b.sub_category_id
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_tb
                        JOIN order_item oi
                        ON order_tb.order_item_id = oi.id
                        GROUP BY oi.order_id
                    ) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("orderStatus", status.name());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Double calculateAverageRating(Long categoryId) {
        String sql = """
                    SELECT COALESCE(AVG(review.rate), 0) AS average_amount
                    FROM (
                        SELECT b.sub_category_id, b.book_template_id
                        FROM (
                            SELECT sc.id, sc.name
                            FROM sub_category sc
                            WHERE sc.category_id = :categoryId
                        ) s_cate
                        JOIN book b
                        ON s_cate.id = b.sub_category_id
                    ) ab_template
                    JOIN review
                    ON ab_template.book_template_id = review.book_template_id;
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);

        return (double) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public List<CategoryBookCountResponse> countBookPerCategory(SearchBookRequest request) {
        StringBuilder jpql = new StringBuilder("SELECT new com.biblio.dto.response.CategoryBookCountResponse(c.id, c.name, COUNT(DISTINCT bt.id)) "
                + "FROM Category c "
                + "LEFT JOIN c.subCategories sc "
                + "LEFT JOIN sc.books b "
                + "LEFT JOIN b.bookTemplate bt "
                + "ON b.id = (SELECT MIN(b2.id) FROM Book b2 WHERE b2.bookTemplate.id = bt.id AND bt.status = 'ON_SALE' "
                + "AND (b2.sellingPrice >= :minPrice AND b2.sellingPrice <= :maxPrice)) "
                + "WHERE (b.sellingPrice >= :minPrice AND b.sellingPrice <= :maxPrice) "
                + "AND (SELECT COALESCE(AVG(r2.rate), 0) FROM bt.reviews r2 WHERE r2.bookTemplate.id = bt.id) >= :reviewRate ");

        Map<String, Object> params = new HashMap<>();
        params.put("minPrice", Double.valueOf(request.getMinPrice()));
        params.put("maxPrice", Double.valueOf(request.getMaxPrice()));
        params.put("reviewRate", Double.valueOf(request.getReviewRate()));

        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            String[] searchTerms = request.getTitle().split("\\s+");
            jpql.append("AND (");
            for (int i = 0; i < searchTerms.length; i++) {
                jpql.append("b.title LIKE :title").append(i);
                params.put("title" + i, "%" + searchTerms[i] + "%");
                if (i < searchTerms.length - 1) {
                    jpql.append(" OR ");
                }
            }
            jpql.append(") ");
        }

        if (request.getCondition() != null) {
            jpql.append("AND (b.condition is NULL OR b.condition = :condition) ");
            params.put("condition", EBookCondition.valueOf(request.getCondition()));
        }

        if (request.getFormat() != null) {
            jpql.append(" AND b.format = :format ");
            params.put("format", EBookFormat.valueOf(request.getFormat()));
        }

        jpql.append("GROUP BY c.id, c.name ");
        jpql.append("HAVING COUNT(DISTINCT bt.id) > 0 ");
        jpql.append("ORDER BY COUNT(DISTINCT bt.id) DESC");

        TypedQuery<CategoryBookCountResponse> query = JpaConfig.getEntityManager().createQuery(jpql.toString(), CategoryBookCountResponse.class);
        params.forEach(query::setParameter);

        return query.getResultList();
    }

    public static void main(String[] args) {
        CategoryDAOImpl dao = new CategoryDAOImpl();
        SearchBookRequest request = new SearchBookRequest();

        for (CategoryBookCountResponse r : dao.countBookPerCategory(request)) {
            System.out.println(r);
        }
    }

}

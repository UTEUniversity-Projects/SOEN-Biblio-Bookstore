package com.biblio.dao.impl;

import com.biblio.dao.ISubCategoryDAO;
import com.biblio.entity.SubCategory;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCategoryDAOImpl extends GenericDAOImpl<SubCategory> implements ISubCategoryDAO {

    public SubCategoryDAOImpl() {
        super(SubCategory.class);
    }

    @Override
    public SubCategory getEntityById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<SubCategory> getEntityAll() {
        return super.findAll();
    }

    @Override
    public List<String> getTopSubCategory(Long id) {
        String sql = """
                    SELECT res.name
                    FROM (
                        SELECT s_cate.name, b.sub_category_id, COUNT(b.id) unique_books
                        FROM (
                            SELECT sc.id, sc.name
                            FROM sub_category sc
                            WHERE sc.id = :id
                        ) s_cate
                        JOIN book b
                        ON s_cate.id = b.sub_category_id
                        GROUP BY b.sub_category_id
                        ORDER BY unique_books DESC
                    ) res
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return super.findByNativeQuery(sql, params, String.class);
    }

    @Override
    public SubCategory createSubCategory(SubCategory subCategory) {
        return super.insert(subCategory);
    }

    @Override
    public void updateSubCategory(SubCategory subCategory) {
        super.update(subCategory);
    }

    @Override
    public void deleteSubCategory(Long id) {
        String sql = "DELETE FROM sub_category WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        super.executeNativeQuery(sql, params);
    }

    @Override
    public Integer countBooksTemplateAll(Long id) {
        String sql = """
                    SELECT SUM(res.unique_book_template) amount
                    FROM (
                        SELECT b.sub_category_id, s_cate.name, COUNT(DISTINCT b.book_template_id) unique_book_template
                        FROM (
                            SELECT sc.id, sc.name
                            FROM sub_category sc
                            WHERE sc.id = :id
                        ) s_cate
                        JOIN book b
                        ON s_cate.id = b.sub_category_id
                        GROUP BY b.sub_category_id
                        ORDER BY unique_book_template DESC
                    ) res
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksTemplateByStatus(Long id, EBookTemplateStatus status) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM (
                        SELECT b.sub_category_id, b.book_template_id
                        FROM (
                            SELECT sc.id, sc.name
                            FROM sub_category sc
                            WHERE sc.id = :id
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
        params.put("id", id);
        params.put("bookTemplateStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksInRangeByStatus(Long id, LocalDateTime from, LocalDateTime to, EBookMetadataStatus bookStatus, EOrderStatus orderStatus) {
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
                                        WHERE sc.id = :id
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
        params.put("id", id);
        params.put("bookStatus", bookStatus.name());
        params.put("orderStatus", orderStatus.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksByStatus(Long id, EBookMetadataStatus status) {
        String sql = """
                    SELECT COUNT(books.id) amount
                    FROM (
                        SELECT b.sub_category_id, b.id, b.book_metadata_id
                        FROM (
                            SELECT sc.id, sc.name
                            FROM sub_category sc
                            WHERE sc.id = :id
                        ) s_cate
                        JOIN book b
                        ON s_cate.id = b.sub_category_id
                    ) books
                    JOIN book_metadata bm
                    ON books.book_metadata_id = bm.id
                    WHERE bm.status = :bookStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("bookStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksByOrderStatus(Long id, EOrderStatus status) {
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
                                        WHERE sc.id = :id
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
        params.put("id", id);
        params.put("orderStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersAll(Long id) {
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
                                    WHERE sc.id = :id
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
        params.put("id", id);

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersInRangeByStatus(Long id, LocalDateTime from, LocalDateTime to, EOrderStatus status) {
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
                                    WHERE sc.id = :id
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
        params.put("id", id);
        params.put("orderStatus", status.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersByStatus(Long id, EOrderStatus status) {
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
                                    WHERE sc.id = :id
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
        params.put("id", id);
        params.put("orderStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueBooksSold(Long id) {
        String sql = """
                    SELECT SUM(books.selling_price) amount
                    FROM (
                        SELECT b.book_metadata_id, b.selling_price
                        FROM (
                            SELECT sc.id, sc.name
                            FROM sub_category sc
                            WHERE sc.id = :id
                        ) s_cate
                        JOIN book b
                        ON s_cate.id = b.sub_category_id
                    ) books
                    JOIN book_metadata bm
                    ON books.book_metadata_id = bm.id
                    WHERE bm.status = :bookStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("bookStatus", EBookMetadataStatus.SOLD.name());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueBooksSoldInRange(Long id, LocalDateTime from, LocalDateTime to) {
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
                                    WHERE sc.id = :id
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
        params.put("id", id);
        params.put("orderStatus", EOrderStatus.COMPLETE_DELIVERY.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueOrdersSoldByStatus(Long id, EOrderStatus status) {
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
                                    WHERE sc.id = :id
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
        params.put("id", id);
        params.put("orderStatus", status.name());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Double calculateAverageRating(Long id) {
        String sql = """
                    SELECT COALESCE(AVG(review.rate), 0) AS average_amount
                    FROM (
                        SELECT b.sub_category_id, b.book_template_id
                        FROM (
                            SELECT sc.id, sc.name
                            FROM sub_category sc
                            WHERE sc.id = :id
                        ) s_cate
                        JOIN book b
                        ON s_cate.id = b.sub_category_id
                    ) ab_template
                    JOIN review
                    ON ab_template.book_template_id = review.book_template_id;
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return (double) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public List<SubCategory> findAll() {
        return super.findAll();
    }

    @Override
    public List<SubCategory> findByJPQL(Long id) {
        String jpql = "SELECT s FROM SubCategory s WHERE s.category.id = :id"; // Dùng 'category.id' thay vì 'category_id'
        Map<String, Object> params = new HashMap<>();
        params.put("id", id); // Tham số cần khớp với ':id'
        return super.findByJPQL(jpql, params);
    }
}

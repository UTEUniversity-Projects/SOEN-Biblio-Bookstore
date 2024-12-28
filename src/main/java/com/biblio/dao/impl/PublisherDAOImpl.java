package com.biblio.dao.impl;

import com.biblio.dao.IPublisherDAO;
import com.biblio.entity.Publisher;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PublisherDAOImpl extends GenericDAOImpl<Publisher> implements IPublisherDAO {

    public PublisherDAOImpl() {
        super(Publisher.class);
    }

    @Override
    public Publisher getEntityById(Long publisherId) {
        return super.findById(publisherId);
    }

    @Override
    public List<Publisher> getEntityAll() {
        return super.findAll();
    }

    @Override
    public List<String> getTopSubCategory(Long publisherId) {
        String sql = """
                    SELECT sc.name
                    FROM (
                        SELECT b.sub_category_id, COUNT(DISTINCT ab_template.book_template_id) unique_book_template
                        FROM (
                            SELECT bt.id book_template_id
                            FROM book_template bt
                            WHERE bt.publisher_id = :publisherId
                        ) ab_template
                        JOIN book b
                        ON ab_template.book_template_id = b.book_template_id
                        GROUP BY b.sub_category_id
                        ORDER BY unique_book_template DESC
                    ) sc_book_template
                    JOIN sub_category sc
                    ON sc_book_template.sub_category_id = sc.id
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("publisherId", publisherId);

        return super.findByNativeQuery(sql, params, String.class);
    }

    @Override
    public Publisher createPublisher(Publisher publisher) {
        return super.save(publisher);
    }

    @Override
    public void updatePublisher(Publisher publisher) {
        super.update(publisher);
    }

    @Override
    public void deletePublisher(Long id) {
        super.delete(id);
    }

    @Override
    public Integer countBooksTemplateAll(Long publisherId) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM book_template bt
                    WHERE bt.publisher_id = :publisherId
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("publisherId", publisherId);

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksTemplateByStatus(Long publisherId, EBookTemplateStatus status) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM book_template bt
                    WHERE bt.publisher_id = :publisherId AND bt.status = :bookTemplateStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("publisherId", publisherId);
        params.put("bookTemplateStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksInRangeByStatus(Long publisherId, LocalDateTime from, LocalDateTime to, EBookMetadataStatus bookStatus, EOrderStatus orderStatus) {
        String sql = """
                    SELECT SUM(books_count) amount
                    FROM (
                        SELECT order_id, SUM(total_books) books_count
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT books.id book_id
                                FROM (
                                    SELECT b.id, b.book_metadata_id
                                    FROM (
                                        SELECT bt.id book_template_id
                                        FROM book_template bt
                                        WHERE bt.publisher_id = :publisherId
                                    ) ab_template
                                    JOIN book b
                                    ON ab_template.book_template_id = b.book_template_id
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
        params.put("publisherId", publisherId);
        params.put("bookStatus", bookStatus.name());
        params.put("orderStatus", orderStatus.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksByStatus(Long publisherId, EBookMetadataStatus status) {
        String sql = """
                    SELECT COUNT(*) publisher_books
                    FROM (
                        SELECT b.book_metadata_id
                        FROM (
                            SELECT bt.id book_template_id
                            FROM book_template bt
                            WHERE bt.publisher_id = :publisherId
                        ) ab_template
                        JOIN book b
                        ON ab_template.book_template_id = b.book_template_id
                    ) books
                    JOIN book_metadata bm
                    ON books.book_metadata_id = bm.id
                    WHERE bm.status = :bookStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("publisherId", publisherId);
        params.put("bookStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksByOrderStatus(Long publisherId, EOrderStatus status) {
        String sql = """
                    SELECT SUM(books_count) amount
                    FROM (
                        SELECT order_id, SUM(total_books) books_count
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT books.id book_id
                                FROM (
                                    SELECT b.id, b.book_metadata_id
                                    FROM (
                                        SELECT bt.id book_template_id
                                        FROM book_template bt
                                        WHERE bt.publisher_id = :publisherId
                                    ) ab_template
                                    JOIN book b
                                    ON ab_template.book_template_id = b.book_template_id
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
        params.put("publisherId", publisherId);
        params.put("orderStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersAll(Long publisherId) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM (
                        SELECT order_id, total_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT b.id
                                FROM (
                                    SELECT bt.id book_template_id
                                    FROM book_template bt
                                    WHERE bt.publisher_id = :publisherId
                                ) ab_template
                                JOIN book b
                                ON ab_template.book_template_id = b.book_template_id
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
        params.put("publisherId", publisherId);

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersInRangeByStatus(Long publisherId, LocalDateTime from, LocalDateTime to, EOrderStatus status) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM (
                        SELECT order_id, total_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT b.id
                                FROM (
                                    SELECT bt.id book_template_id
                                    FROM book_template bt
                                    WHERE bt.publisher_id = :publisherId
                                ) ab_template
                                JOIN book b
                                ON ab_template.book_template_id = b.book_template_id
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
        params.put("publisherId", publisherId);
        params.put("orderStatus", status.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersByStatus(Long publisherId, EOrderStatus status) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM (
                        SELECT order_id, total_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT b.id
                                FROM (
                                    SELECT bt.id book_template_id
                                    FROM book_template bt
                                    WHERE bt.publisher_id = :publisherId
                                ) ab_template
                                JOIN book b
                                ON ab_template.book_template_id = b.book_template_id
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
        params.put("publisherId", publisherId);
        params.put("orderStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueBooksSold(Long publisherId) {
        String sql = """
                    SELECT SUM(selling_price) price
                    FROM (
                       SELECT b.book_metadata_id, b.selling_price
                       FROM (
                            SELECT bt.id book_template_id
                            FROM book_template bt
                            WHERE bt.publisher_id = :publisherId
                       ) ab_template
                       JOIN book b
                       ON ab_template.book_template_id = b.book_template_id
                    ) books
                    JOIN book_metadata bm
                    ON books.book_metadata_id = bm.id
                    WHERE bm.status = :bookStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("publisherId", publisherId);
        params.put("bookStatus", EBookMetadataStatus.SOLD.name());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueBooksSoldInRange(Long publisherId, LocalDateTime from, LocalDateTime to) {
        String sql = """
                    SELECT SUM(sum_books) amount
                    FROM (
                        SELECT oi.order_id, SUM(sum_order_item) sum_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books, SUM(selling_price) sum_order_item
                            FROM (
                                SELECT b.id, b.selling_price
                                FROM (
                                    SELECT bt.id book_template_id
                                    FROM book_template bt
                                    WHERE bt.publisher_id = :publisherId
                                ) ab_template
                                JOIN book b
                                ON ab_template.book_template_id = b.book_template_id
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
                            GROUP BY oib.order_item_id
                        ) order_tb
                        JOIN order_item oi
                        ON order_tb.order_item_id = oi.id
                        GROUP BY oi.order_id) orders
                    JOIN `order` o
                    ON orders.order_id = o.id
                    WHERE o.status = :orderStatus AND (o.order_date BETWEEN :startDate AND :endDate);
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("publisherId", publisherId);
        params.put("orderStatus", EOrderStatus.COMPLETE_DELIVERY.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueOrdersSoldByStatus(Long publisherId, EOrderStatus status) {
        String sql = """
                    SELECT SUM(sum_books) amount
                    FROM (
                        SELECT order_id, SUM(sum_order_item) sum_books
                        FROM (
                            SELECT oib.order_item_id, SUM(books.selling_price) sum_order_item
                            FROM (
                                SELECT b.id, b.selling_price
                                FROM (
                                    SELECT bt.id book_template_id
                                    FROM book_template bt
                                    WHERE bt.publisher_id = :publisherId
                                ) ab_template
                                JOIN book b
                                ON ab_template.book_template_id = b.book_template_id
                            ) books
                            JOIN order_item_books oib
                            ON books.id = oib.book_id
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
        params.put("publisherId", publisherId);
        params.put("orderStatus", status.name());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Double calculateAverageRating(Long publisherId) {
        String sql = """
                    SELECT COALESCE(AVG(review.rate), 0) AS average_amount
                    FROM (
                        SELECT bt.id book_template_id
                        FROM book_template bt
                        WHERE bt.publisher_id = :publisherId
                    ) ab_template
                    JOIN review
                    ON ab_template.book_template_id = review.book_template_id;
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("publisherId", publisherId);

        return (double) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    public static void main(String[] args) {
        PublisherDAOImpl dao = new PublisherDAOImpl();
        Integer test = dao.countOrdersByStatus(1L, EOrderStatus.COMPLETE_DELIVERY);
        System.out.println(test);
        System.out.println(EOrderStatus.COMPLETE_DELIVERY.name());
    }
}
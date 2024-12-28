package com.biblio.dao.impl;

import com.biblio.dao.ITranslatorDAO;
import com.biblio.entity.Translator;
import com.biblio.entity.BookTemplate;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TranslatorDAOImpl extends GenericDAOImpl<Translator> implements ITranslatorDAO {

    public TranslatorDAOImpl() {
        super(Translator.class);
    }

    @Override
    public Translator getEntityById(Long translatorId) {
        String jpql = "SELECT t " +
                "FROM Translator t " +
                "WHERE t.id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", translatorId);
        return super.findSingleByJPQL(jpql, params);
    }

    @Override
    public List<Translator> getEntityAll() {
        return super.findAll();
    }

    @Override
    public List<Translator> findByBookTemplate(BookTemplate bookTemplate) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT DISTINCT a ")
                .append("FROM Translator a ")
                .append("JOIN a.bookTemplates bt ")
                .append("WHERE bt = :bookTemplate");

        Map<String, Object> params = new HashMap<>();
        params.put("bookTemplate", bookTemplate);
        return super.findByJPQL(jpql.toString(), params);
    }

    @Override
    public List<String> getTopSubCategory(Long translatorId) {
        String sql = """
                    SELECT sc.name
                    FROM (
                        SELECT b.sub_category_id, COUNT(DISTINCT ab_template.book_template_id) unique_book_template
                        FROM (
                            SELECT abt.book_template_id
                            FROM translator_book_template abt
                            WHERE abt.translator_id = :translatorId
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
        params.put("translatorId", translatorId);

        return super.findByNativeQuery(sql, params, String.class);
    }

    @Override
    public Translator createTranslator(Translator translator) {
        return super.save(translator);
    }

    @Override
    public void updateTranslator(Translator translator) {
        super.update(translator);
    }

    @Override
    public void deleteTranslator(Long id) {
        super.delete(id);
    }

    @Override
    public Integer countBooksTemplateAll(Long translatorId) {
        String sql = "SELECT COUNT(*) translator_works " +
                "FROM translator_book_template abt " +
                "WHERE abt.translator_id = :translatorId";

        Map<String, Object> params = new HashMap<>();
        params.put("translatorId", translatorId);

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksTemplateByStatus(Long translatorId, EBookTemplateStatus status) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM (
                            (SELECT book_template_id
                            FROM translator_book_template abt
                            WHERE abt.translator_id = :translatorId
                        ) ab_template
                        LEFT JOIN book_template bt
                        ON ab_template.book_template_id = bt.id
                    ) WHERE bt.status = :bookTemplateStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("translatorId", translatorId);
        params.put("bookTemplateStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksInRangeByStatus(Long translatorId, LocalDateTime from, LocalDateTime to, EBookMetadataStatus bookStatus, EOrderStatus orderStatus) {
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
                                        SELECT abt.book_template_id
                                        FROM translator_book_template abt
                                        WHERE abt.translator_id = :translatorId
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
        params.put("translatorId", translatorId);
        params.put("bookStatus", bookStatus.name());
        params.put("orderStatus", orderStatus.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksByStatus(Long translatorId, EBookMetadataStatus status) {
        String sql = """
                    SELECT COUNT(*) translator_books
                    FROM (
                        SELECT b.book_metadata_id
                        FROM (
                            SELECT abt.book_template_id
                            FROM translator_book_template abt
                            WHERE abt.translator_id = :translatorId
                        ) ab_template
                        JOIN book b
                        ON ab_template.book_template_id = b.book_template_id
                    ) books
                    JOIN book_metadata bm
                    ON books.book_metadata_id = bm.id
                    WHERE bm.status = :bookStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("translatorId", translatorId);
        params.put("bookStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countBooksByOrderStatus(Long translatorId, EOrderStatus status) {
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
                                        SELECT abt.book_template_id
                                        FROM translator_book_template abt
                                        WHERE abt.translator_id = :translatorId
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
        params.put("translatorId", translatorId);
        params.put("orderStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersAll(Long translatorId) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM (
                        SELECT order_id, total_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT b.id
                                FROM (
                                    SELECT abt.book_template_id
                                    FROM translator_book_template abt
                                    WHERE abt.translator_id = :translatorId
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
        params.put("translatorId", translatorId);

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersInRangeByStatus(Long translatorId, LocalDateTime from, LocalDateTime to, EOrderStatus status) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM (
                        SELECT order_id, total_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT b.id
                                FROM (
                                    SELECT abt.book_template_id
                                    FROM translator_book_template abt
                                    WHERE abt.translator_id = :translatorId
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
        params.put("translatorId", translatorId);
        params.put("orderStatus", status.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Integer countOrdersByStatus(Long translatorId, EOrderStatus status) {
        String sql = """
                    SELECT COUNT(*) amount
                    FROM (
                        SELECT order_id, total_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books
                            FROM (
                                SELECT b.id
                                FROM (
                                    SELECT abt.book_template_id
                                    FROM translator_book_template abt
                                    WHERE abt.translator_id = :translatorId
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
        params.put("translatorId", translatorId);
        params.put("orderStatus", status.name());

        return Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueBooksSold(Long translatorId) {
        String sql = """
                    SELECT SUM(selling_price) price
                    FROM (
                       SELECT b.book_metadata_id, b.selling_price
                       FROM (
                           SELECT abt.book_template_id
                           FROM translator_book_template abt
                           WHERE abt.translator_id = :translatorId
                       ) ab_template
                       JOIN book b
                       ON ab_template.book_template_id = b.book_template_id
                    ) books
                    JOIN book_metadata bm
                    ON books.book_metadata_id = bm.id
                    WHERE bm.status = :bookStatus
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("translatorId", translatorId);
        params.put("bookStatus", EBookMetadataStatus.SOLD.name());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueBooksSoldInRange(Long translatorId, LocalDateTime from, LocalDateTime to) {
        String sql = """
                    SELECT SUM(sum_books) amount
                    FROM (
                        SELECT oi.order_id, SUM(sum_order_item) sum_books
                        FROM (
                            SELECT oib.order_item_id, COUNT(*) AS total_books, SUM(selling_price) sum_order_item
                            FROM (
                                SELECT b.id, b.selling_price
                                FROM (
                                    SELECT abt.book_template_id
                                    FROM translator_book_template abt
                                    WHERE abt.translator_id = :translatorId
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
        params.put("translatorId", translatorId);
        params.put("orderStatus", EOrderStatus.COMPLETE_DELIVERY.name());
        params.put("startDate", from.toString());
        params.put("endDate", to.toString());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Long calculateValueOrdersSoldByStatus(Long translatorId, EOrderStatus status) {
        String sql = """
                    SELECT SUM(sum_books) amount
                    FROM (
                        SELECT order_id, SUM(sum_order_item) sum_books
                        FROM (
                            SELECT oib.order_item_id, SUM(books.selling_price) sum_order_item
                            FROM (
                                SELECT b.id, b.selling_price
                                FROM (
                                    SELECT abt.book_template_id
                                    FROM translator_book_template abt
                                    WHERE abt.translator_id = :translatorId
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
        params.put("translatorId", translatorId);
        params.put("orderStatus", status.name());

        return (long) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    @Override
    public Double calculateAverageRating(Long translatorId) {
        String sql = """
                    SELECT COALESCE(AVG(review.rate), 0) AS average_amount
                    FROM (
                        SELECT abt.book_template_id
                        FROM translator_book_template abt
                        WHERE abt.translator_id = :translatorId
                    ) ab_template
                    JOIN review
                    ON ab_template.book_template_id = review.book_template_id;
                 """;

        Map<String, Object> params = new HashMap<>();
        params.put("translatorId", translatorId);

        return (double) Math.toIntExact(super.countByNativeQuery(sql, params));
    }

    public static void main(String[] args) {
        TranslatorDAOImpl dao = new TranslatorDAOImpl();
        Integer test = dao.countOrdersByStatus(1L, EOrderStatus.COMPLETE_DELIVERY);
        System.out.println(test);
        System.out.println(EOrderStatus.COMPLETE_DELIVERY.name());
    }
}
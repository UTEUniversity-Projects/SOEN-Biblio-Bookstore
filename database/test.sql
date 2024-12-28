

SELECT res.name
FROM (
	SELECT s_cate.name, b.sub_category_id, COUNT(b.id) unique_books
	FROM (
		SELECT sc.id, sc.name
		FROM sub_category sc
		WHERE sc.id = 1
	) s_cate
	JOIN book b
	ON s_cate.id = b.sub_category_id
	GROUP BY b.sub_category_id
	ORDER BY unique_books DESC
) res;


SELECT sub_cate.name
FROM (
	SELECT s_cate.name, b.sub_category_id, COUNT(DISTINCT ab_template.book_template_id) unique_book_template
	FROM (
		SELECT sc.id, sc.name
		FROM sub_category sc
		WHERE sc.category_id = 1
	) s_cate
	JOIN book b
	ON s_cate.id = b.sub_category_id
	GROUP BY b.sub_category_id
	ORDER BY unique_book_template DESC
) sub_cate;

SELECT res.name
FROM (
	SELECT s_cate.name, b.sub_category_id, COUNT(b.id) unique_books
	FROM (
		SELECT sc.id, sc.name
		FROM sub_category sc
		WHERE sc.category_id = 1
	) s_cate
	JOIN book b
	ON s_cate.id = b.sub_category_id
	GROUP BY b.sub_category_id
	ORDER BY unique_books DESC
) res;

SELECT SUM(books.selling_price) amount
FROM (
	SELECT b.book_metadata_id, b.selling_price
	FROM (
		SELECT sc.id, sc.name
		FROM sub_category sc
		WHERE sc.category_id = 1
	) s_cate
	JOIN book b
	ON s_cate.id = b.sub_category_id
) books
JOIN book_metadata bm
ON books.book_metadata_id = bm.id
WHERE bm.status = 'SOLD';

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
				WHERE sc.category_id = 1
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
WHERE o.status = 'COMPLETE_DELIVERY' AND (o.order_date BETWEEN '2024-11-03' AND '2024-11-10');


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
				WHERE sc.category_id = 1
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
ON orders.order_id = o.id;


SELECT COUNT(*) amount
FROM (
	SELECT b.sub_category_id, b.book_template_id
	FROM (
		SELECT sc.id, sc.name
		FROM sub_category sc
		WHERE sc.category_id = 1
	) s_cate
	JOIN book b
	ON s_cate.id = b.sub_category_id
    GROUP BY b.sub_category_id, b.book_template_id
) s_bt
JOIN book_template bt
ON s_bt.book_template_id = bt.id
WHERE bt.status = 'ON_SALE';

SELECT COALESCE(AVG(review.rate), 0) AS average_amount
FROM (
	SELECT b.sub_category_id, b.book_template_id
	FROM (
		SELECT sc.id, sc.name
		FROM sub_category sc
		WHERE sc.category_id = 1
	) s_cate
	JOIN book b
	ON s_cate.id = b.sub_category_id
) ab_template
JOIN review
ON ab_template.book_template_id = review.book_template_id;


SELECT b.sub_category_id, s_cate.name, COUNT(DISTINCT b.book_template_id) unique_book_template
FROM (
	SELECT sc.id, sc.name
	FROM sub_category sc
	WHERE sc.category_id = 1
) s_cate
JOIN book b
ON s_cate.id = b.sub_category_id
GROUP BY b.sub_category_id
ORDER BY unique_book_template DESC;

SELECT COUNT(*) amount
FROM book_template bt
WHERE bt.publisher_id = 1 AND bt.status = 'ON_SALE';


SELECT COUNT(*) amount
FROM (
		(SELECT book_template_id
		FROM publisher_book_template abt
		WHERE abt.publisher_id = 1
	) ab_template
	LEFT JOIN book_template bt
	ON ab_template.book_template_id = bt.id
) WHERE bt.status = 'ON_SALE';

-- bookTemplate - countBooksInRangeByStatus
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
			WHERE bt.id = 1
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
WHERE o.status = 'COMPLETE_DELIVERY' AND (o.order_date BETWEEN '2024-11-03' AND '2024-11-10');

-- bookTemplate - calculateValueBooksSoldInRange
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
            WHERE bt.id = 1
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
WHERE o.status = 'COMPLETE_DELIVERY' AND (o.order_date BETWEEN '2024-11-03' AND '2024-11-10');


SELECT COUNT(*) amount 
FROM (
		(SELECT book_template_id 
		FROM author_book_template abt 
		WHERE abt.author_id = 1
	) ab_template 
    LEFT JOIN book_template bt
    ON ab_template.book_template_id = bt.id
) WHERE bt.status = 'ON_SALE';


-- countBooksByOrderStatus
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
					FROM author_book_template abt
					WHERE abt.author_id = 1
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
WHERE o.status = 'COMPLETE_DELIVERY';

-- coountBooksInSubCategory
SELECT sc.name
FROM (
	SELECT b.sub_category_id, COUNT(DISTINCT ab_template.book_template_id) unique_book_template
	FROM (
		SELECT abt.book_template_id
		FROM author_book_template abt
		WHERE abt.author_id = 1
	) ab_template
	JOIN book b
	ON ab_template.book_template_id = b.book_template_id
	GROUP BY b.sub_category_id
	ORDER BY unique_book_template DESC) sc_book_template
JOIN sub_category sc
ON sc_book_template.sub_category_id = sc.id;

-- countOrdersInRange
SELECT COUNT(*) amount
FROM (
	SELECT order_id, total_books
	FROM (
		SELECT oib.order_item_id, COUNT(*) AS total_books
		FROM (
			SELECT b.id
			FROM (
				SELECT abt.book_template_id
				FROM author_book_template abt
				WHERE abt.author_id = 1
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
WHERE o.status = 'COMPLETE_DELIVERY' AND (o.order_date BETWEEN '2024-11-03' AND '2024-11-10');

-- countBooksInRange
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
					FROM author_book_template abt
					WHERE abt.author_id = 1
				) ab_template
				JOIN book b
				ON ab_template.book_template_id = b.book_template_id
			) books
			JOIN book_metadata bm
			ON books.book_metadata_id = bm.id
			WHERE bm.status = 'SOLD'
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
WHERE o.status = 'COMPLETE_DELIVERY' AND (o.order_date BETWEEN '2024-11-03' AND '2024-11-10');

-- calculateValueOrdersSoldByStatus
SELECT SUM(sum_books) amount
FROM (
	SELECT order_id, SUM(sum_order_item) sum_books
	FROM (
		SELECT oib.order_item_id, SUM(books.selling_price) sum_order_item
		FROM (
			SELECT b.id, b.selling_price
			FROM (
				SELECT abt.book_template_id
				FROM author_book_template abt
				WHERE abt.author_id = 1
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
WHERE o.status = 'COMPLETE_DELIVERY';

-- countOrdersByStatus
SELECT COUNT(*) amount
FROM (
	SELECT order_id, total_books
	FROM (
		SELECT oib.order_item_id, COUNT(*) AS total_books
		FROM (
			SELECT b.id
			FROM (
				SELECT abt.book_template_id
				FROM author_book_template abt
				WHERE abt.author_id = 1
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
WHERE o.status = 'COMPLETE_DELIVERY';

-- calculateAverageRating
SELECT COALESCE(AVG(review.rate), 0) AS average_amount 
FROM (
	SELECT abt.book_template_id 
	FROM author_book_template abt 
	WHERE abt.author_id = 1
) ab_template
JOIN review
ON ab_template.book_template_id = review.book_template_id;


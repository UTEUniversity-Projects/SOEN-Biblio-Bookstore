package com.biblio.mapper;

import com.biblio.dto.response.OrderProductResponse;
import com.biblio.entity.Book;
import com.biblio.entity.BookTemplate;
import com.biblio.entity.OrderItem;
import com.biblio.entity.Publisher;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class OrderItemMapper {
    public static OrderProductResponse mapToOrderProductResponse(OrderItem orderItem) {
        Book book = orderItem.getBooks().iterator().next();
        return OrderProductResponse.builder()
                .bookTemplateId(book.getBookTemplate().getId())
                .title(book.getTitle())
                .imagePath(book.getBookTemplate()
                        .getMediaFiles().get(0)
                        .getStoredCode())
                .quantity(orderItem.getBooks().size())
                .sellingPrice(book.getSellingPrice())
                .build();
    }

    public static List<OrderProductResponse> toOrderProductCustomerResponse(OrderItem orderItem) {
        // Nhóm các sách trong orderItem theo book_template_id
        Map<Long, List<Book>> groupedBooks = orderItem.getBooks().stream()
                .collect(Collectors.groupingBy(book -> book.getBookTemplate().getId()));

        // Tạo danh sách OrderProductResponse cho từng nhóm
        return groupedBooks.entrySet().stream()
                .map(entry -> {
                    Long bookTemplateId = entry.getKey();
                    List<Book> booksInGroup = entry.getValue();

                    // Lấy thông tin của một sách trong nhóm (lấy bất kỳ vì chúng có chung bookTemplateId)
                    Book firstBook = booksInGroup.get(0);
                    String publisherName = null;

                    BookTemplate bookTemplate = firstBook.getBookTemplate();
                    if (bookTemplate != null) {
                        Publisher publisher = bookTemplate.getPublisher();
                        if (publisher != null) {
                            publisherName = publisher.getName();
                        }
                    }

                    return OrderProductResponse.builder()
                            .bookTemplateId(bookTemplateId)
                            .title(firstBook.getTitle()) // Giữ thông tin từ quyển đầu tiên trong nhóm
                            .imagePath(bookTemplate.getMediaFiles().get(0).getStoredCode())
                            .quantity(booksInGroup.size()) // Số lượng sách trong nhóm
                            .sellingPrice(firstBook.getSellingPrice()) // Giá từ quyển đầu tiên
                            .totalPrice(booksInGroup.stream().mapToDouble(Book::getSellingPrice).sum()) // Tổng giá trong nhóm
                            .publisherName(publisherName)
                            .build();
                })
                .collect(Collectors.toList());
    }

}

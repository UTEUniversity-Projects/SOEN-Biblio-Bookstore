package com.biblio.service.impl;

import com.biblio.dao.ICategoryDAO;
import com.biblio.dto.request.CategoryDeleteRequest;
import com.biblio.dto.request.CategoryCreateRequest;
import com.biblio.dto.request.CategoryUpdateRequest;
import com.biblio.dto.response.*;
import com.biblio.dto.request.SearchBookRequest;
import com.biblio.dto.response.CategoryBookCountResponse;
import com.biblio.dto.response.CategoryResponse;
import com.biblio.dto.response.CategorySidebarResponse;
import com.biblio.entity.Category;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.mapper.CategoryMapper;
import com.biblio.service.ICategoryService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements ICategoryService {
    @Inject
    ICategoryDAO categoryDAO;

    @Override
    public Category getEntityById(Long id) {
        return categoryDAO.getEntityById(id);
    }

    @Override
    public List<Category> getAllEntities() {
        return categoryDAO.getEntityAll();
    }

    @Override
    public List<CategoryLineResponse> getAll() {
        List<CategoryLineResponse> list = new ArrayList<CategoryLineResponse>();
        for (Category category : categoryDAO.getEntityAll()) {
            Integer works = categoryDAO.countBooksTemplateAll(category.getId());
            Double avgRate = categoryDAO.calculateAverageRating(category.getId());
            Double perValueBooksSold = calculateValueBooksSoldGrowth(category.getId());

            list.add(CategoryMapper.toCategoryLineResponse(category, works, avgRate, perValueBooksSold));
        }
        return list;
    }

    @Override
    public CategoryProfileResponse getProfileById(Long id) {
        Category category = categoryDAO.getEntityById(id);
        return CategoryMapper.toCategoryProfileResponse(category);
    }

    @Override
    public CategoryAnalysisResponse getAnalysisById(Long id) {
        Category category = categoryDAO.getEntityById(id);
        Integer works = categoryDAO.countBooksTemplateAll(id);
        Double avgRate = categoryDAO.calculateAverageRating(id);
        Integer sales = categoryDAO.countOrdersAll(id);
        Double perSales = calculateSaleGrowth(id);
        Integer booksSold = categoryDAO.countBooksByStatus(id, EBookMetadataStatus.SOLD);
        Double perBooksSold = calculateBooksSoldGrowth(id);
        Long valueBooksSold = categoryDAO.calculateValueBooksSold(id);
        Double perValueBooksSold = calculateValueBooksSoldGrowth(id);

        Integer booksInStock = categoryDAO.countBooksByStatus(id, EBookMetadataStatus.IN_STOCK);
        Integer booksCancelled = categoryDAO.countBooksByOrderStatus(id, EOrderStatus.CANCELED);
        Integer booksReturned = categoryDAO.countBooksByOrderStatus(id, EOrderStatus.REFUNDED);

        Integer salesThisMonth = countSaleThisMonth(id);
        Integer booksThisMonth = countBooksSoldThisMonth((id));
        Long revenueThisMonth = calculateRevenueThisMonth(id);

        Integer ordersCompleted = categoryDAO.countOrdersByStatus(id, EOrderStatus.COMPLETE_DELIVERY);
        Long valueOrdersCompleted = categoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.COMPLETE_DELIVERY);
        Integer ordersWaiting = categoryDAO.countOrdersByStatus(id, EOrderStatus.WAITING_CONFIRMATION);
        Long valueOrdersWaiting = categoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.WAITING_CONFIRMATION);
        Integer ordersPacking = categoryDAO.countOrdersByStatus(id, EOrderStatus.PACKING);
        Long valueOrderPacking = categoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.PACKING);
        Integer ordersShipping = categoryDAO.countOrdersByStatus(id, EOrderStatus.SHIPPING);
        Long valueOrderShipping = categoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.SHIPPING);
        Integer ordersCancelled = categoryDAO.countOrdersByStatus(id, EOrderStatus.CANCELED);
        Long valueOrdersCancelled = categoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.CANCELED);
        Integer ordersRequestRefund = categoryDAO.countOrdersByStatus(id, EOrderStatus.REQUEST_REFUND);
        Long valueOrdersRequestRefund = categoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.REQUEST_REFUND);
        Integer ordersRefunded = categoryDAO.countOrdersByStatus(id, EOrderStatus.REFUNDED);
        Long valueOrdersRefunded = categoryDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.REFUNDED);

        List<String> topSubCategory = findTopSubCategory(id, 3);

        return CategoryMapper.toCategoryAnalysisResponse(category, works, avgRate, sales, perSales, booksSold, perBooksSold,
                valueBooksSold, perValueBooksSold, booksInStock, booksCancelled, booksReturned, salesThisMonth, booksThisMonth, revenueThisMonth,
                ordersCompleted, valueOrdersCompleted, ordersWaiting, valueOrdersWaiting, ordersPacking, valueOrderPacking,
                ordersShipping, valueOrderShipping, ordersCancelled, valueOrdersCancelled, ordersRequestRefund, valueOrdersRequestRefund,
                ordersRefunded, valueOrdersRefunded, topSubCategory);
    }

    @Override
    public Category create(CategoryCreateRequest categoryCreateRequest) {
        return categoryDAO.create(categoryCreateRequest);
    }

    @Override
    public void update(CategoryUpdateRequest categoryUpdateRequest) {
        categoryDAO.update(categoryUpdateRequest);
    }
    
    @Override
    public void delete(CategoryDeleteRequest categoryDeleteRequest) {
        categoryDAO.delete(categoryDeleteRequest);
    }

    @Override
    public Integer countBookTemplate(Long id) {
        return categoryDAO.countBooksTemplateByStatus(id, EBookTemplateStatus.COMING_SOON)
                + categoryDAO.countBooksTemplateByStatus(id, EBookTemplateStatus.ON_SALE)
                + categoryDAO.countBooksTemplateByStatus(id, EBookTemplateStatus.OUT_OF_STOCK);
    }

    private Double calculateSaleGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Integer currentMonth = countSaleThisMonth(id);
        Integer lastMonth = categoryDAO.countOrdersInRangeByStatus(id, startOfLastMonth, endOfLastMonth, EOrderStatus.COMPLETE_DELIVERY);

        if (lastMonth != 0) {
            return ((double) (currentMonth - lastMonth) / lastMonth) * 100.0D;
        } else {
            if (currentMonth != 0) return 100.0D;
            else return 0.0D;
        }
    }
    private Integer countSaleThisMonth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        return categoryDAO.countOrdersInRangeByStatus(id, startOfThisMonth, endOfThisMonth, EOrderStatus.COMPLETE_DELIVERY);
    }

    private Double calculateBooksSoldGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Integer currentMonth = countBooksSoldThisMonth(id);
        Integer lastMonth = categoryDAO.countBooksInRangeByStatus(id, startOfLastMonth, endOfLastMonth, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);

        if (lastMonth != 0) {
            return ((double) (currentMonth - lastMonth) / lastMonth) * 100.0D;
        } else {
            if (currentMonth != 0) return 100.0D;
            else return 0.0D;
        }
    }
    private Integer countBooksSoldThisMonth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        return categoryDAO.countBooksInRangeByStatus(id, startOfThisMonth, endOfThisMonth, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);
    }

    private Double calculateValueBooksSoldGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Long currentMonth = calculateRevenueThisMonth(id);
        Long lastMonth = categoryDAO.calculateValueBooksSoldInRange(id, startOfLastMonth, endOfLastMonth);

        if (lastMonth != 0) {
            return ((double) (currentMonth - lastMonth) / lastMonth) * 100.0D;
        } else {
            if (currentMonth != 0) return 100.0D;
            else return 0.0D;
        }
    }
    private Long calculateRevenueThisMonth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        return categoryDAO.calculateValueBooksSoldInRange(id, startOfThisMonth, endOfThisMonth);
    }

    private List<String> findTopSubCategory(Long id, Integer numberElements) {
        List<String> list = categoryDAO.getTopSubCategory(id);

        while (list.size() < numberElements) {
            list.add("");
        }

        return list.subList(0, numberElements);
    }

    @Override
    public List<CategorySidebarResponse> getAllCategorySidebarResponse() {
        List<CategorySidebarResponse> list = new ArrayList<CategorySidebarResponse>();
        for (Category category : categoryDAO.getEntityAll()) {
            list.add(CategoryMapper.toCategorySidebarResponse(category));
        }
        return list;
    }

    @Override
    public List<CategoryProfileResponse> getAllCategories() {
        List<CategoryProfileResponse> list = new ArrayList<CategoryProfileResponse>();
        List<Category> categories = categoryDAO.getEntityAll();
        for (Category category : categories) {
            list.add(CategoryMapper.toCategoryProfileResponse(category));
        }
        return list;
    }

    @Override
    public CategoryResponse findById(Long id) {
        return CategoryMapper.toCategoryResponse(categoryDAO.getEntityById(id));
    }

    @Override
    public List<CategoryBookCountResponse> getBookQuantityPerCategory(SearchBookRequest request) {
        return categoryDAO.countBookPerCategory(request);
    }

}

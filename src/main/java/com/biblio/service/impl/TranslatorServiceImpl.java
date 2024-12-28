package com.biblio.service.impl;

import com.biblio.dao.ITranslatorDAO;
import com.biblio.dto.request.TranslatorCreateRequest;
import com.biblio.dto.request.TranslatorDeleteRequest;
import com.biblio.dto.request.TranslatorUpdateRequest;
import com.biblio.dto.response.TranslatorAnalysisResponse;
import com.biblio.dto.response.TranslatorLineResponse;
import com.biblio.dto.response.TranslatorProfileResponse;
import com.biblio.entity.Translator;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.mapper.TranslatorMapper;
import com.biblio.service.ITranslatorService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TranslatorServiceImpl implements ITranslatorService {
    @Inject
    ITranslatorDAO translatorDAO;

    @Override
    public List<TranslatorLineResponse> getAll() {
        List<TranslatorLineResponse> list = new ArrayList<TranslatorLineResponse>();
        for (Translator translator : translatorDAO.getEntityAll()) {
            Integer works = translatorDAO.countBooksTemplateAll(translator.getId());
            Double avgRate = translatorDAO.calculateAverageRating(translator.getId());
            Double perValueBooksSold = calculateValueBooksSoldGrowth(translator.getId());

            list.add(TranslatorMapper.toTranslatorLineResponse(translator, works, avgRate, perValueBooksSold));
        }
        return list;
    }

    @Override
    public TranslatorProfileResponse getProfileById(Long id) {
        Translator translator = translatorDAO.getEntityById(id);
        return TranslatorMapper.toTranslatorProfileResponse(translator);
    }

    @Override
    public TranslatorAnalysisResponse getAnalysisById(Long id) {
        Translator translator = translatorDAO.getEntityById(id);
        Integer works = translatorDAO.countBooksTemplateAll(id);
        Double avgRate = translatorDAO.calculateAverageRating(id);
        Integer sales = translatorDAO.countOrdersAll(id);
        Double perSales = calculateSaleGrowth(id);
        Integer booksSold = translatorDAO.countBooksByStatus(id, EBookMetadataStatus.SOLD);
        Double perBooksSold = calculateBooksSoldGrowth(id);
        Long valueBooksSold = translatorDAO.calculateValueBooksSold(id);
        Double perValueBooksSold = calculateValueBooksSoldGrowth(id);

        Integer booksInStock = translatorDAO.countBooksByStatus(id, EBookMetadataStatus.IN_STOCK);
        Integer booksCancelled = translatorDAO.countBooksByOrderStatus(id, EOrderStatus.CANCELED);
        Integer booksReturned = translatorDAO.countBooksByOrderStatus(id, EOrderStatus.REFUNDED);

        Integer salesThisMonth = countSaleThisMonth(id);
        Integer booksThisMonth = countBooksSoldThisMonth((id));
        Long revenueThisMonth = calculateRevenueThisMonth(id);

        Integer ordersCompleted = translatorDAO.countOrdersByStatus(id, EOrderStatus.COMPLETE_DELIVERY);
        Long valueOrdersCompleted = translatorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.COMPLETE_DELIVERY);
        Integer ordersWaiting = translatorDAO.countOrdersByStatus(id, EOrderStatus.WAITING_CONFIRMATION);
        Long valueOrdersWaiting = translatorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.WAITING_CONFIRMATION);
        Integer ordersPacking = translatorDAO.countOrdersByStatus(id, EOrderStatus.PACKING);
        Long valueOrderPacking = translatorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.PACKING);
        Integer ordersShipping = translatorDAO.countOrdersByStatus(id, EOrderStatus.SHIPPING);
        Long valueOrderShipping = translatorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.SHIPPING);
        Integer ordersCancelled = translatorDAO.countOrdersByStatus(id, EOrderStatus.CANCELED);
        Long valueOrdersCancelled = translatorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.CANCELED);
        Integer ordersRequestRefund = translatorDAO.countOrdersByStatus(id, EOrderStatus.REQUEST_REFUND);
        Long valueOrdersRequestRefund = translatorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.REQUEST_REFUND);
        Integer ordersRefunded = translatorDAO.countOrdersByStatus(id, EOrderStatus.REFUNDED);
        Long valueOrdersRefunded = translatorDAO.calculateValueOrdersSoldByStatus(id, EOrderStatus.REFUNDED);

        List<String> topSubCategory = findTopSubCategory(id, 3);

        return TranslatorMapper.toTranslatorAnalysisResponse(translator, works, avgRate, sales, perSales, booksSold, perBooksSold, valueBooksSold, perValueBooksSold, booksInStock, booksCancelled, booksReturned, salesThisMonth, booksThisMonth, revenueThisMonth, ordersCompleted, valueOrdersCompleted, ordersWaiting, valueOrdersWaiting, ordersPacking, valueOrderPacking, ordersShipping, valueOrderShipping, ordersCancelled, valueOrdersCancelled, ordersRequestRefund, valueOrdersRequestRefund, ordersRefunded, valueOrdersRefunded, topSubCategory);
    }

    @Override
    public Translator createTranslator(TranslatorCreateRequest translatorCreateRequest) {
        return translatorDAO.createTranslator(TranslatorMapper.toTranslatorCreate(translatorCreateRequest));
    }

    @Override
    public void updateTranslator(TranslatorUpdateRequest translatorUpdateRequest) {
        Translator translator = translatorDAO.getEntityById(Long.valueOf((translatorUpdateRequest.getId())));
        translatorDAO.updateTranslator(TranslatorMapper.toTranslatorUpdate(translatorUpdateRequest, translator));
    }

    @Override
    public void deleteTranslator(TranslatorDeleteRequest translatorDeleteRequest) {
        translatorDAO.deleteTranslator(Long.valueOf(translatorDeleteRequest.getId()));
    }

    @Override
    public Integer countBookTemplate(TranslatorDeleteRequest translatorDeleteRequest) {
        return translatorDAO.countBooksTemplateByStatus(Long.valueOf(translatorDeleteRequest.getId()), EBookTemplateStatus.COMING_SOON) + translatorDAO.countBooksTemplateByStatus(Long.valueOf(translatorDeleteRequest.getId()), EBookTemplateStatus.ON_SALE) + translatorDAO.countBooksTemplateByStatus(Long.valueOf(translatorDeleteRequest.getId()), EBookTemplateStatus.OUT_OF_STOCK);
    }

    private Double calculateSaleGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Integer currentMonth = countSaleThisMonth(id);
        Integer lastMonth = translatorDAO.countOrdersInRangeByStatus(id, startOfLastMonth, endOfLastMonth, EOrderStatus.COMPLETE_DELIVERY);

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

        return translatorDAO.countOrdersInRangeByStatus(id, startOfThisMonth, endOfThisMonth, EOrderStatus.COMPLETE_DELIVERY);
    }

    private Double calculateBooksSoldGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Integer currentMonth = countBooksSoldThisMonth(id);
        Integer lastMonth = translatorDAO.countBooksInRangeByStatus(id, startOfLastMonth, endOfLastMonth, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);

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

        return translatorDAO.countBooksInRangeByStatus(id, startOfThisMonth, endOfThisMonth, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);
    }

    private Double calculateValueBooksSoldGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Long currentMonth = calculateRevenueThisMonth(id);
        Long lastMonth = translatorDAO.calculateValueBooksSoldInRange(id, startOfLastMonth, endOfLastMonth);

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

        return translatorDAO.calculateValueBooksSoldInRange(id, startOfThisMonth, endOfThisMonth);
    }

    private List<String> findTopSubCategory(Long id, Integer numberElements) {
        List<String> list = translatorDAO.getTopSubCategory(id);

        while (list.size() < numberElements) {
            list.add("");
        }

        return list.subList(0, numberElements);
    }
}
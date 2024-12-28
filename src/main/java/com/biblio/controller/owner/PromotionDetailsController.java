package com.biblio.controller.owner;

import com.biblio.dto.request.PromotionTargetUpdateRequest;
import com.biblio.dto.request.PromotionTemplateUpdateRequest;
import com.biblio.dto.request.PromotionUpdateRequest;
import com.biblio.dto.response.PromotionResponse;
import com.biblio.dto.response.PromotionTargetResponse;
import com.biblio.dto.response.PromotionTemplateGetDetailsResponse;
import com.biblio.dto.response.PromotionTemplateResponse;
import com.biblio.enumeration.EPromotionStatus;
import com.biblio.enumeration.EPromotionTargetType;
import com.biblio.enumeration.EPromotionTemplateStatus;
import com.biblio.enumeration.EPromotionTemplateType;
import com.biblio.service.IPromotionTemplateService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servlet implementation class PromotionDetailsController
 */
@WebServlet("/owner/promotion-details")
public class PromotionDetailsController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    private IPromotionTemplateService promotionTemplateService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PromotionDetailsController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        PromotionTemplateGetDetailsResponse promotionTemplateGetDetailsResponse = promotionTemplateService.getPromotionTemplateById(Long.parseLong(id));

        Set<Long> selectedIds = promotionTemplateGetDetailsResponse.getPromotionTargetResponse()
                .stream()
                .map(PromotionTargetResponse::getApplicableObjectId)
                .collect(Collectors.toSet());

        String firstType = promotionTemplateGetDetailsResponse.getPromotionTargetResponse()
                .stream()
                .findFirst()
                .map(PromotionTargetResponse::getType)
                .orElse(null);

        // Lấy thời gian hiện tại tại múi giờ VN
        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));

        // Lấy thời gian hết hạn từ response
        String expirationDateStr = promotionTemplateGetDetailsResponse.getExpirationDate();

        // Cắt phần giây nếu có (ví dụ: '2024-11-30T23:59:59' -> '2024-11-30T23:59')
        if (expirationDateStr.length() > 16) {
            expirationDateStr = expirationDateStr.substring(0, 16);  // Cắt chuỗi đến 16 ký tự (yyyy-MM-dd'T'HH:mm)
        }

        // Định dạng ngày giờ không có giây
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime expirationDateTime = LocalDateTime.parse(expirationDateStr, formatter);

        // Chuyển đổi expirationDateTime thành ZonedDateTime ở múi giờ VN
        ZonedDateTime expirationDateTimeVN = expirationDateTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));

        System.out.println(expirationDateTimeVN);

        // Kiểm tra nếu thời gian hiện tại trước thời gian hết hạn
        boolean isBeforeExpiration = currentDateTime.isBefore(expirationDateTimeVN);

        request.setAttribute("promotion", promotionTemplateGetDetailsResponse);
        request.setAttribute("selectedType", firstType);
        request.setAttribute("selectedIds", selectedIds);
        request.setAttribute("isBeforeExpiration", isBeforeExpiration);

        request.getRequestDispatcher("/views/owner/promotion-details.jsp").forward(request, response);
    }






    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String formType = request.getParameter("formType");

        if ("editVoucher".equals(formType)) {
            handleEditVoucher(request, response);
        } else if ("editFreeShip".equals(formType)) {
            handleEditFreeShip(request, response);
        }
        else if ("editDiscount".equals(formType)) {
            handleEditDiscount(request, response);
        }
    }


    private void handleEditPromotion(HttpServletRequest request, HttpServletResponse response, EPromotionTemplateType type, Double percentDiscount, Double minValueApplied) {
        String code = request.getParameter("code");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String discountLimit = request.getParameter("discountLimit");
        String time = request.getParameter("dateeffective");
        String quantity = request.getParameter("quantity");

        try {
            PromotionTemplateGetDetailsResponse promotionTemplateGetDetailsResponse = promotionTemplateService.getPromotionTemplateByCode(code);

            if (Objects.equals(promotionTemplateGetDetailsResponse.getStatus(), EPromotionTemplateStatus.EFFECTIVE.toString())
                    || Objects.equals(promotionTemplateGetDetailsResponse.getStatus(), EPromotionTemplateStatus.COMING_SOON.toString()) ||
                    Objects.equals(promotionTemplateGetDetailsResponse.getStatus(), EPromotionTemplateStatus.USED_OUT.toString())) {

                PromotionTemplateResponse promotionTemplateResponse = promotionTemplateService.getPromotionTemplateDetailsById(promotionTemplateGetDetailsResponse.getId());

                PromotionTemplateUpdateRequest promotionTemplateUpdateRequest = convertToUpdateRequest(promotionTemplateResponse, quantity);

                Optional<String> optionalStartDate = promotionTemplateUpdateRequest
                        .getPromotionUpdates()
                        .stream()
                        .map(PromotionUpdateRequest::getEffectiveDate)
                        .findFirst();

                String startDate = optionalStartDate.orElse(null);
                if (quantity == null) {
                    quantity = "1";
                }
                for (int i = 0; i < Long.parseLong(quantity); i++) {
                    PromotionUpdateRequest promotionUpdateRequest = new PromotionUpdateRequest();

                    promotionUpdateRequest.setTitle(title);
                    promotionUpdateRequest.setDescription(description);
                    promotionUpdateRequest.setPercentDiscount(percentDiscount);
                    promotionUpdateRequest.setMinValueApplied(minValueApplied);
                    promotionUpdateRequest.setDiscountLimit(Double.parseDouble(discountLimit));
                    promotionUpdateRequest.setStatus(EPromotionStatus.NOT_USE);

                    if (promotionTemplateResponse.getStatus() == EPromotionTemplateStatus.EFFECTIVE || promotionTemplateResponse.getStatus() == EPromotionTemplateStatus.USED_OUT) {
                        promotionUpdateRequest.setEffectiveDate(startDate);
                        promotionUpdateRequest.setExpirationDate(convertToIsoFormat(request.getParameter("dateeffective")));
                    } else {
                        String[] parts = time.split(" - ");
                        promotionUpdateRequest.setEffectiveDate(convertToIsoFormat(parts[0]));
                        promotionUpdateRequest.setExpirationDate(convertToIsoFormat(parts[1]));
                    }

                    PromotionTargetUpdateRequest promotionTargetUpdate_add = new PromotionTargetUpdateRequest();
                    promotionTargetUpdate_add.setType(EPromotionTargetType.WHOLE);
                    promotionTargetUpdate_add.setApplicableObjectId(-1L);
                    promotionUpdateRequest.getPromotionTargets().add(promotionTargetUpdate_add);

                    promotionTemplateUpdateRequest.getPromotionUpdates().add(promotionUpdateRequest);
                }
                promotionTemplateService.updatePromotionTemplate(promotionTemplateUpdateRequest);
                response.sendRedirect("/owner/promotion-details?id=" + promotionTemplateUpdateRequest.getId());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String convertToIsoFormat(String input) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("M/dd/yyyy hh:mm a");

            LocalDateTime parsedDateTime = LocalDateTime.parse(input, inputFormatter);

            DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            return parsedDateTime.format(isoFormatter);

        } catch (Exception e) {
            throw new RuntimeException("Error parsing date: " + input, e);
        }
    }


    private void handleEditVoucher(HttpServletRequest request, HttpServletResponse response) {
        String minValueApplied = request.getParameter("minValueApplied");
        handleEditPromotion(request, response, EPromotionTemplateType.VOUCHER,  100.0, Double.parseDouble(minValueApplied));
    }

    private void handleEditFreeShip(HttpServletRequest request, HttpServletResponse response) {
        handleEditPromotion(request, response, EPromotionTemplateType.FREESHIP, 100.0, 0.0); // percentDiscount 100, minValueApplied 0
    }

    private void handleEditDiscount(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String percentDiscount = request.getParameter("percentDiscount");
        String time = request.getParameter("dateeffective");
        String minValueApplied = "0";
        String discountLimit = "0";
        String typeEffective = request.getParameter("selectOject");
        String[] selectedItemIds = request.getParameterValues("selectedItems");


        try {
            PromotionTemplateGetDetailsResponse promotionTemplateGetDetailsResponse = promotionTemplateService.getPromotionTemplateByCode(code);

            if (Objects.equals(promotionTemplateGetDetailsResponse.getStatus(), EPromotionTemplateStatus.EFFECTIVE.toString())
                    || Objects.equals(promotionTemplateGetDetailsResponse.getStatus(), EPromotionTemplateStatus.COMING_SOON.toString())) {

                PromotionTemplateResponse promotionTemplateResponse = promotionTemplateService.getPromotionTemplateDetailsById(promotionTemplateGetDetailsResponse.getId());

                PromotionTemplateUpdateRequest promotionTemplateUpdateRequest = convertToUpdateRequest(promotionTemplateResponse, null);

                Optional<String> optionalStartDate = promotionTemplateUpdateRequest
                        .getPromotionUpdates()
                        .stream()
                        .map(PromotionUpdateRequest::getEffectiveDate)
                        .findFirst();

                String startDate = optionalStartDate.orElse(null);
                PromotionUpdateRequest promotionUpdateRequest = new PromotionUpdateRequest();

                promotionUpdateRequest.setTitle(title);
                promotionUpdateRequest.setDescription(description);
                promotionUpdateRequest.setPercentDiscount(Double.parseDouble(percentDiscount));
                promotionUpdateRequest.setMinValueApplied(Double.parseDouble(minValueApplied));
                promotionUpdateRequest.setDiscountLimit(Double.parseDouble(discountLimit));
                promotionUpdateRequest.setStatus(EPromotionStatus.NOT_USE);

                if (promotionTemplateResponse.getStatus() == EPromotionTemplateStatus.EFFECTIVE || promotionTemplateResponse.getStatus() == EPromotionTemplateStatus.USED_OUT) {
                    promotionUpdateRequest.setEffectiveDate(startDate);
                    promotionUpdateRequest.setExpirationDate(convertToIsoFormat(request.getParameter("dateeffective")));
                } else {
                    String[] parts = time.split(" - ");
                    promotionUpdateRequest.setEffectiveDate(convertToIsoFormat(parts[0]));
                    promotionUpdateRequest.setExpirationDate(convertToIsoFormat(parts[1]));
                }

                if ("1".equals(typeEffective)) {
                    for (String itemId : selectedItemIds) {
                        PromotionTargetUpdateRequest promotionTargetUpdate_add = new PromotionTargetUpdateRequest();
                        promotionTargetUpdate_add.setType(EPromotionTargetType.BOOK);
                        promotionTargetUpdate_add.setApplicableObjectId(Long.parseLong(itemId));
                        promotionUpdateRequest.getPromotionTargets().add(promotionTargetUpdate_add);
                    }
                } else if ("2".equals(typeEffective)) {
                    for (String itemId : selectedItemIds) {
                        PromotionTargetUpdateRequest promotionTargetUpdate_add = new PromotionTargetUpdateRequest();
                        promotionTargetUpdate_add.setType(EPromotionTargetType.CATEGORY);
                        promotionTargetUpdate_add.setApplicableObjectId(Long.parseLong(itemId));
                        promotionUpdateRequest.getPromotionTargets().add(promotionTargetUpdate_add);
                    }
                } else if ("3".equals(typeEffective)) {
                    for (String itemId : selectedItemIds) {
                        PromotionTargetUpdateRequest promotionTargetUpdate_add = new PromotionTargetUpdateRequest();
                        promotionTargetUpdate_add.setType(EPromotionTargetType.SUBCATEGORY);
                        promotionTargetUpdate_add.setApplicableObjectId(Long.parseLong(itemId));
                        promotionUpdateRequest.getPromotionTargets().add(promotionTargetUpdate_add);
                    }
                } else if ("4".equals(typeEffective)) {
                    PromotionTargetUpdateRequest promotionTargetUpdate_add = new PromotionTargetUpdateRequest();
                    promotionTargetUpdate_add.setType(EPromotionTargetType.WHOLE);
                    promotionTargetUpdate_add.setApplicableObjectId(-1L);
                    promotionUpdateRequest.getPromotionTargets().add(promotionTargetUpdate_add);
                }
                promotionTemplateUpdateRequest.getPromotionUpdates().add(promotionUpdateRequest);
                promotionTemplateService.updatePromotionTemplate(promotionTemplateUpdateRequest);
                response.sendRedirect("/owner/promotion-details?id=" + promotionTemplateUpdateRequest.getId());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public PromotionTemplateUpdateRequest convertToUpdateRequest(PromotionTemplateResponse promotionTemplateResponse, String quantity) {
        PromotionTemplateUpdateRequest promotionTemplateUpdateRequest = new PromotionTemplateUpdateRequest();

        promotionTemplateUpdateRequest.setId(promotionTemplateResponse.getId());

        if (quantity == null) {
            promotionTemplateUpdateRequest.setInfinite(true);
        } else {
            promotionTemplateUpdateRequest.setInfinite(false);
        }

        promotionTemplateUpdateRequest.setCode(promotionTemplateResponse.getCode());
        promotionTemplateUpdateRequest.setCreateAt(promotionTemplateResponse.getCreateAt());
        promotionTemplateUpdateRequest.setStatus(promotionTemplateResponse.getStatus());
        promotionTemplateUpdateRequest.setType(promotionTemplateResponse.getType());

        for (PromotionResponse promotionResponse : promotionTemplateResponse.getPromotions()) {
            PromotionUpdateRequest promotionUpdateRequest = new PromotionUpdateRequest();

            promotionUpdateRequest.setId(promotionResponse.getId());
            promotionUpdateRequest.setTitle(promotionResponse.getTitle());
            promotionUpdateRequest.setDescription(promotionResponse.getDescription());
            promotionUpdateRequest.setPercentDiscount(promotionResponse.getPercentDiscount());
            promotionUpdateRequest.setMinValueApplied(promotionResponse.getMinValueApplied());
            promotionUpdateRequest.setDiscountLimit(promotionResponse.getDiscountLimit());
            promotionUpdateRequest.setStatus(EPromotionStatus.USED);
            promotionUpdateRequest.setEffectiveDate(promotionResponse.getEffectiveDate());
            promotionUpdateRequest.setExpirationDate(promotionResponse.getExpirationDate());

            for (PromotionTargetResponse promotionTargetResponse : promotionResponse.getPromotionTargets()) {
                PromotionTargetUpdateRequest promotionTargetUpdateRequest = new PromotionTargetUpdateRequest();

                promotionTargetUpdateRequest.setId(promotionTargetResponse.getId());
                promotionTargetUpdateRequest.setType(EPromotionTargetType.valueOf(promotionTargetResponse.getType()));
                promotionTargetUpdateRequest.setApplicableObjectId(promotionTargetResponse.getApplicableObjectId());
                promotionUpdateRequest.getPromotionTargets().add(promotionTargetUpdateRequest);
            }

            promotionTemplateUpdateRequest.getPromotionUpdates().add(promotionUpdateRequest);
        }
        return promotionTemplateUpdateRequest;
    }


}
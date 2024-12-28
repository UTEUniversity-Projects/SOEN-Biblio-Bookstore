package com.biblio.apis.owner;

import com.biblio.dto.response.OrderReturnAtTimeResponse;
import com.biblio.enumeration.EReasonReturn;
import com.biblio.service.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/owner/ecommerce/get-list-return-order-at-time")
public class GetListOrderReturnAtTimeAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    IOrderService orderService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListOrderReturnAtTimeAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String startParam = request.getParameter("startTime");
            String endParam = request.getParameter("endTime");

            if (startParam == null || endParam == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Missing required parameters: startTime or endTime\"}");
                return;
            }

            LocalDateTime startTime = LocalDateTime.parse(startParam);
            LocalDateTime endTime = LocalDateTime.parse(endParam);
            startTime = startTime.toLocalDate().atStartOfDay();
            endTime = endTime.toLocalDate().atTime(LocalTime.MAX);

            if (startTime.isAfter(endTime)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"startTime must be before or equal to endTime\"}");
                return;
            }

            List<OrderReturnAtTimeResponse> orderReturnAtTimeResponses = orderService.rateReturn(startTime, endTime);

            Map<EReasonReturn, Integer> reasonCounts = new HashMap<>();
            for (EReasonReturn reason : EReasonReturn.values()) {
                reasonCounts.put(reason, 0);
            }

            orderReturnAtTimeResponses.forEach(item -> {
                if (item.getReturnReason() != null) {
                    reasonCounts.put(item.getReturnReason(), reasonCounts.get(item.getReturnReason()) + 1);
                }
            });

            List<Map<String, Object>> orderReturnAtTimeJsonList = orderReturnAtTimeResponses.stream()
                    .map(item -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("idOrder", item.getOrderId());
                        map.put("isReturn", item.getIsReturned());
                        map.put("reasonReturn", item.getReturnReason() != null ? item.getReturnReason().toString() : null);
                        return map;
                    })
                    .toList();

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("orderReturnAtTimeList", orderReturnAtTimeJsonList);
            responseMap.put("reasonCounts", reasonCounts);

            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(objectMapper.writeValueAsString(responseMap));

        } catch (DateTimeParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid date format. Use 'yyyy-MM-dd'T'HH:mm:ss'\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"An error occurred while processing the request\"}");
        }
    }



    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
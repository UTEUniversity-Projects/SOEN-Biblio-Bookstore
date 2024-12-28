package com.biblio.apis.owner;

import com.biblio.dto.response.RevenueResponse;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servlet implementation class GetCategoriesAPI
 */
@WebServlet("/owner/ecommerce/list-revenue")
public class GetListRevenueAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    IOrderService orderService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetListRevenueAPI() {
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
            // Parse request parameters
            String startParam = request.getParameter("startTime");
            String endParam = request.getParameter("endTime");

            // Validate and convert to LocalDateTime
            if (startParam == null || endParam == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Missing required parameters: startTime or endTime\"}");
                return;
            }

            LocalDateTime startTime = LocalDateTime.parse(startParam);
            LocalDateTime endTime = LocalDateTime.parse(endParam);
            startTime = startTime.toLocalDate().atStartOfDay();
            endTime = endTime.toLocalDate().atTime(LocalTime.MAX);

            // Validate date range
            if (startTime.isAfter(endTime)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"startTime must be before or equal to endTime\"}");
                return;
            }

            List<RevenueResponse> revenueList = orderService.revenueStatistics(startTime, endTime);

            List<Map<String, Object>> revenueJsonList = revenueList.stream()
                    .map(item -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("date", item.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        map.put("revenue", item.getRevenue());
                        return map;
                    })
                    .collect(Collectors.toList());

            // Create a map to represent the JSON response
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("revenueList", revenueJsonList);

            // Serialize and send the response
            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(objectMapper.writeValueAsString(responseMap));
        } catch (DateTimeParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid date format. Use 'yyyy-MM-dd'T'HH:mm:ss'\"}");
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi chi tiết trong console
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
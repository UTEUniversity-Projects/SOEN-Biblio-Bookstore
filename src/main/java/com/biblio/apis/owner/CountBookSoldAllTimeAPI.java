package com.biblio.apis.owner;

import com.biblio.dto.response.BookSoldAllTimeResponse;
import com.biblio.service.IBookTemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class GetCategoriesAPI
 */
@WebServlet("/owner/ecommerce/count-book-sold-all-time")
public class CountBookSoldAllTimeAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    IBookTemplateService bookTemplateService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CountBookSoldAllTimeAPI() {
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

            List<BookSoldAllTimeResponse> listCountBookSold = bookTemplateService.getListCountBookSoldAllTime();

            List<Map<String, Object>> countBookSoldAllTimeJson = listCountBookSold.stream()
                    .map(item -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", item.getId());
                        map.put("img", item.getSrcImg());
                        map.put("title", item.getTitle());
                        map.put("category", item.getCategory());
                        map.put("countSold", item.getCountSold());
                        map.put("countInStock", item.getCountInStock());
                        return map;
                    })
                    .toList();

            // Create a map to represent the JSON response
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("countBookSoldAllTime", countBookSoldAllTimeJson);

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
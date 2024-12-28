package com.biblio.utils;

import com.biblio.dto.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiResponseWriter {
    public static void writeResponse(HttpServletResponse response, ApiResponse apiResponse) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
    }
}

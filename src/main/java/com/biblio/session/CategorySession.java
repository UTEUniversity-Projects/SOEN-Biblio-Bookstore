package com.biblio.session;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/session/owner/category/set-info")
public class CategorySession extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonData = sb.toString();

        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        String categoryId = jsonObject.has("categoryId") ? jsonObject.get("categoryId").getAsString() : null;
        String action = jsonObject.has("categoryAction") ? jsonObject.get("categoryAction").getAsString() : null;

        HttpSession session = request.getSession();
        session.setAttribute("categoryId", categoryId);

        if (action != null) {
            if (action.equals("delete")) {
                session.setAttribute("categoryAction", action);
            }
        }

        response.setContentType("application/json");
        response.getWriter().write("{\"status\":\"success\"}");
    }
}

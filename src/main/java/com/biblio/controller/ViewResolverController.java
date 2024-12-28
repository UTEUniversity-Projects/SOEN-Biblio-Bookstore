package com.biblio.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewResolverController
 */
@WebServlet(urlPatterns = {"/views/*"})
public class ViewResolverController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        String resourcePath = path.substring(request.getContextPath().length());

        if (resourcePath.startsWith("/views/")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF" + resourcePath);
            dispatcher.forward(request, response);
        }

    }
}

package com.biblio.controller.staff;

import com.biblio.entity.ResponseSupport;
import com.biblio.entity.Support;
import com.biblio.service.impl.ResponseSupportServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/staff/support-customer-dashboard")
public class SupportCustomerDashboardController extends HttpServlet {
    private final ResponseSupportServiceImpl supportService = new ResponseSupportServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("detail".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            Support support = supportService.getSupportById(id);
            ResponseSupport responseSupport = supportService.getResponseSupportBySupportId(id);
            request.setAttribute("supportRequest", support);
            request.setAttribute("responseSupport", responseSupport);
            request.getRequestDispatcher("/views/staff/support-customer-details.jsp").forward(request, response);
        } else {
            List<Support> supportRequests = supportService.getAllSupports();
            request.setAttribute("supportRequests", supportRequests);
            request.getRequestDispatcher("/views/staff/support-customer-dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");

            String action = request.getParameter("action");

            if ("respond".equals(action)) {
                Long supportId = Long.parseLong(request.getParameter("id"));
                String title = request.getParameter("title");
                String content = request.getParameter("feedbackContent");

                if (title == null || title.trim().isEmpty()) {
                    throw new RuntimeException("Title cannot be null or empty.");
                }

                Long staffId = 1L; // Example staff ID
                supportService.saveResponseSupport(title, content, staffId, supportId);

                response.sendRedirect(request.getContextPath() + "/staff/support-customer-dashboard");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }





}



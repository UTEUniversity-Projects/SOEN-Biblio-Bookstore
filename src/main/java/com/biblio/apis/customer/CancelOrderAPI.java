package com.biblio.apis.customer;

import com.biblio.dao.IOrderDAO;
import com.biblio.dao.impl.OrderDAOImpl;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/api/customer/cancelOrder")
public class CancelOrderAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private IOrderDAO orderDAO;

    public CancelOrderAPI() {super();}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderIdParam = request.getParameter("orderId");
        Long orderId = Long.parseLong(orderIdParam);

        boolean success = orderDAO.cancelOrder(orderId);

        response.setContentType("application/json");
        response.getWriter().write("{\"success\": " + success + "}");
    }
}
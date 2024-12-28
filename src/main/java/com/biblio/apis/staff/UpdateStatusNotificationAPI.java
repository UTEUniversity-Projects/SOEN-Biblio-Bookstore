package com.biblio.apis.staff;

import com.biblio.service.INotificationService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet(urlPatterns = {"/api/staff/notification/update"})
public class UpdateStatusNotificationAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    INotificationService notificationService;

    public UpdateStatusNotificationAPI() {
        super();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("notificationId"));
        notificationService.updateStatusNotificationById(id);
    }
}
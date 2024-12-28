package com.biblio.controller.owner;

import com.biblio.dto.response.StaffResponse;
import com.biblio.service.IStaffService;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/owner/staff-profile")
public class StaffProfileController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    private IStaffService staffService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StaffProfileController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String id = request.getParameter("id");
        StaffResponse staffResponse = staffService.findById(Long.parseLong(id));
        request.setAttribute("staff", staffResponse);
        request.getRequestDispatcher("/views/owner/profile.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}

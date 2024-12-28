package com.biblio.controller.owner;

import com.biblio.dto.response.StaffResponse;
import com.biblio.service.IStaffService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = {"/owner/staff/list", "/owner/staff/view",
        "/owner/staff/create", "/owner/staff/update", "/owner/staff/delete"})
public class ManageStaffController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    IStaffService staffService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageStaffController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<StaffResponse> list = staffService.findAll();
        request.setAttribute("staffList", list);
        request.getRequestDispatcher("/views/owner/staff-list.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String id = request.getParameter("id");

        if (action != null && id != null) {
            try {
                Long staffId = Long.parseLong(id);

                if (action.equals("activate")) {
                    staffService.activateStaff(staffId);
                    response.getWriter().write("success");
                } if (action.equals("deactivate")) {
                    staffService.deactivateStaff(staffId);
                    response.getWriter().write("success");
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("error");
            }
            return;
        }

        doGet(request, response);
    }

    /**
     * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    private String getAction(HttpServletRequest request) {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            String requestURI = request.getRequestURI();
            String[] pathParts = requestURI.split("/");
            action = pathParts[pathParts.length - 1];
        }

        return action;
    }
}

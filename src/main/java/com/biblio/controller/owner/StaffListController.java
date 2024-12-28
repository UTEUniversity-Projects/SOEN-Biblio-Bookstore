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
import java.util.List;

@WebServlet("/owner/staff-list")
public class StaffListController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    IStaffService staffService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StaffListController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        List<StaffResponse> list = staffService.findAll();
        request.setAttribute("staffList", list);
        request.getRequestDispatcher("/views/owner/staff-list.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
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
}

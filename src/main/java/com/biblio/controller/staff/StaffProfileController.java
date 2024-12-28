package com.biblio.controller.staff;

import com.biblio.dto.response.AccountGetResponse;
import com.biblio.dto.response.StaffResponse;
import com.biblio.service.IStaffService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;

/**
 * Servlet implementation class StaffProfileController
 */
@WebServlet("/staff/profile")
public class StaffProfileController extends HttpServlet {
    @Serial
	private static final long serialVersionUID = 1L;

	@Inject
	IStaffService staffService;

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

		HttpSession session = request.getSession();

		AccountGetResponse account = (AccountGetResponse) session.getAttribute("account");

		StaffResponse staff = staffService.getStaffByUsername(account.getUsername().trim());

		request.setAttribute("staff", staff);

		request.getRequestDispatcher("/views/staff/profile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

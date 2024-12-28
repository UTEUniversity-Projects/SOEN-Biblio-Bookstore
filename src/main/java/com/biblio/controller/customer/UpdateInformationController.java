package com.biblio.controller.customer;

import com.biblio.dto.request.CustomerInformationRequest;
import com.biblio.dto.response.AccountGetResponse;
import com.biblio.dto.response.CustomerDetailResponse;
import com.biblio.entity.Customer;
import com.biblio.enumeration.EGender;
import com.biblio.service.ICustomerService;

import java.io.IOException;
import java.io.Serial;
import java.time.LocalDate;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateInformationController
 */
@WebServlet("/update-information")
public class UpdateInformationController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    private ICustomerService customerService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateInformationController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub


        request.setAttribute("breadcrumb", "Cập nhật thông tin cá nhân");
        request.getRequestDispatcher("/views/customer/update-information.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        AccountGetResponse account = (AccountGetResponse) session.getAttribute("account");
        CustomerDetailResponse customer = customerService.getCustomerDetailByUsername(account.getUsername());
        // Lấy thông tin từ form
        String gender = request.getParameter("gender");
        String fullName = request.getParameter("fullName");
        String dateOfBirth = request.getParameter("dateOfBirth");

        // Lấy khách hàng hiện tại (từ session hoặc database)
        CustomerInformationRequest customerInformationRequest = new CustomerInformationRequest();

        // Cập nhật thông tin khách hàng
        if (fullName != null) customerInformationRequest.setFullName(fullName);
        if (gender != null) customerInformationRequest.setGender(EGender.valueOf(gender));
        if (dateOfBirth != null) customerInformationRequest.setDateOfBirth(LocalDate.parse(dateOfBirth));
        customerInformationRequest.setId(customer.getId());
        customerInformationRequest.setAvatar(customer.getAvatar());
        customerInformationRequest.setEmail(customer.getEmail());
        customerInformationRequest.setJoinAt(customer.getJoinAt());
        customerInformationRequest.setPhoneNumber(customer.getPhoneNumber());
        customerInformationRequest.setMemberShip(customer.getMemberShip());
        customerInformationRequest.setAccount(account);


        // Lưu thông tin vào cơ sở dữ liệu
        Customer isUpdated = customerService.updateCustomer(customerInformationRequest);

//        if (isUpdated) {
//            // Cập nhật session
//            request.getSession().setAttribute("currentCustomer", customer);
//
//            // Chuyển hướng về trang profile
//            response.sendRedirect("profile.jsp");
//        } else {
//            // Nếu cập nhật thất bại, hiển thị thông báo lỗi
//            request.setAttribute("error", "Cập nhật thông tin thất bại. Vui lòng thử lại.");
//            request.getRequestDispatcher("profile.jsp").forward(request, response);
//        }
        response.sendRedirect(request.getContextPath() + "/user-information");
    }


}

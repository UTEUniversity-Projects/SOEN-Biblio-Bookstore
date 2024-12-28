package com.biblio.controller.customer;

import com.biblio.dto.request.VerifyOTPRequest;
import com.biblio.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/verify-otp")
public class VerifyOTPController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyOTPController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setAttribute("breadcrumb", "Xác thực email");
        request.getRequestDispatcher("/views/customer/verify-otp.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        VerifyOTPRequest verifyOTPRequest = HttpUtil.of(request.getReader()).toModel(VerifyOTPRequest.class);

        System.out.println(verifyOTPRequest.getOtpCode());
        Map<String, Object> map = new HashMap<>();

        String enteredCode = verifyOTPRequest.getOtpCode();
        String sessionOtpCode = (String) request.getSession().getAttribute("otpCode");
        Long otpTimestamp = (Long) request.getSession().getAttribute("otpTimestamp");

        if (sessionOtpCode == null || otpTimestamp == null) {
            map.put("code", 400);
            map.put("message", "Mã OTP không tồn tại. Vui lòng yêu cầu mã OTP mới.");
        } else {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - otpTimestamp;

            if (elapsedTime > 2.5 * 60 * 1000 ) {
                map.put("code", 400);
                map.put("message", "Mã OTP đã hết hạn. Vui lòng yêu cầu gửi lại mã OTP mới.");
            } else {
                if (sessionOtpCode.equals(enteredCode)) {
                    map.put("code", 200);
                    map.put("message", "OTP xác thực thành công!");
                    request.getSession().removeAttribute("otpCode");
                    request.getSession().removeAttribute("otpTimestamp");
                } else {
                    map.put("code", 400);
                    map.put("message", "Mã OTP không chính xác. Vui lòng thử lại");
                }
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(map));
    }
}

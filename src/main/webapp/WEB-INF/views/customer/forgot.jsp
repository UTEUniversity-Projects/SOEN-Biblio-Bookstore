<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--Forgot password--%>
<section class="section-login padding-tb-100">
  <div class="container">
    <div class="row">
      <div class="col-12">
        <div class="cr-login" data-aos="fade-up" data-aos-duration="2000" data-aos-delay="400">
          <div class="form-logo">
            <img src="${pageContext.request.contextPath}/assets/commons/img/logo/biblio.png" alt="logo">
          </div>
          <form class="cr-content-form">
            <div class="form-group mb-2">
              <label class="text-md mb-1 ml-2">Username:</label>
              <input type="text" autocomplete="on" placeholder="Nhập username..."
                     id="username"
                     class="block w-full py-2 px-4 text-gray-900 border-1 border-gray-300 rounded-lg text-[16px] focus:ring-blue-500 focus:border-blue-500 transition-all ease-linear bg-white focus:shadow-lg focus:shadow-[rgba(3,_102,_214,_0.3)_0px_0px_0px_3px]">
            </div>
            <div class="flex justify-between items-center">
              <a href="login" class="inline-block px-4 py-2 border border-solid rounded hover:bg-gray-100 transition-all duration-300">
                Đăng nhập
              </a>
              <button type="button" class="btn-forgot px-8 py-2 flex items-center gap-2 rounded text-white bg-[#64b595] hover:bg-[#64b595]/90 transition-all duration-300">
                <span class="button-text">Gửi</span>
                <div class="spinner hidden w-[20px] h-[20px] border-[4px] border-solid border-[#fff] rounded-full border-t-transparent animate-spin"></div>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</section>

<script src="${pageContext.request.contextPath}/assets/customer/js/forgot-password.js" type="module"></script>

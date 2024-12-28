<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Verify otp page -->
<section class="section-login padding-tb-100">
    <div class="container-md">
        <div class="row">
            <div class="col-sm-12">
                <div class="cr-login" data-aos="fade-up" data-aos-duration="2000" data-aos-delay="400">
                    <div class="form-logo">
                        <img src="${pageContext.request.contextPath}/assets/customer/img/logo/biblio.png" alt="logo">
                    </div>
                    <form class="cr-content-form">
                        <div class="form-group mb-2">
                            <label for="otp" class="text-md mb-1 ml-2">Mã OTP:</label>
                            <input id="otp" type="text" autocomplete="on" placeholder="Nhập mã OTP..." maxlength="6"
                                   class="block w-full py-2 px-4 text-gray-900 border-1 border-gray-300 rounded-lg text-[16px] focus:ring-blue-500 focus:border-blue-500 transition-all ease-linear bg-white focus:shadow-lg focus:shadow-[rgba(3,_102,_214,_0.3)_0px_0px_0px_3px]" />
                        </div>
                        <span id="countdown" class="countdown"></span>
                        <button type="button"
                                class="btn-verify-otp px-8 py-2 rounded text-white bg-[#64b595] hover:bg-[#64b595]/90 transition-all duration-300">
                            Gửi
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="${pageContext.request.contextPath}/assets/customer/js/verify-otp.js" type="module"></script>
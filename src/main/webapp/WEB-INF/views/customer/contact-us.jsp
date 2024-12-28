<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Contact Section -->
<section class="section-Contact padding-tb-100">
  <div class="container">
    <div class="row">
      <div class="col-lg-12">
        <div class="mb-30" data-aos="fade-up" data-aos-duration="2000" data-aos-delay="400">
          <div class="cr-banner">
            <h2>Liên hệ</h2>
          </div>
          <div class="cr-banner-sub-title">
            <p>
              Nếu bạn có bất kỳ thắc mắc nào, hãy liên hệ chúng tôi để biết thêm chi tiết.
            </p>
          </div>
        </div>
      </div>
    </div>
    <!-- Google Map -->
    <div class="row padding-t-100 mb-minus-24">
      <div class="col-md-6 col-12 mb-24" data-aos="fade-up" data-aos-duration="2000" data-aos-delay="400">
        <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3270.5058479043046!2d106.77029843906374!3d10.850714932044081!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752763f23816ab%3A0x282f711441b6916f!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBTxrAgcGjhuqFtIEvhu7kgdGh14bqtdCBUaMOgbmggcGjhu5EgSOG7kyBDaMOtIE1pbmg!5e1!3m2!1svi!2s!4v1727073359759!5m2!1svi!2s" width="600" height="450" style="border: 0" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
      </div>

      <!-- Contact Form -->
      <div class="col-md-6 col-12 mb-24" data-aos="fade-up" data-aos-duration="2000" data-aos-delay="800">
        <form class="cr-content-form" method="POST" action="${pageContext.request.contextPath}/contact-us">
          <div class="form-group">
            <input type="text" name="title" placeholder="Tiêu đề..." class="cr-form-control" required />
          </div>
          <br>
          <div class="form-group">
            <textarea name="content" class="cr-form-control" id="exampleFormControlTextarea1" rows="4" placeholder="Thông điệp..." required></textarea>
          </div>
          <br>
          <button type="submit" class="cr-button">Gửi</button>
        </form>
      </div>
    </div>

    <!-- Contact Information -->
    <div class="row mb-minus-24">
      <div class="col-lg-4 col-md-6 col-12 mb-24" data-aos="fade-up" data-aos-duration="2000" data-aos-delay="400">
        <div class="cr-info-box">
          <div class="cr-icon">
            <i class="ri-phone-line"></i>
          </div>
          <div class="cr-info-content">
            <h4 class="heading">Điện thoại</h4>
            <p><a href="javascript:void(0)"><i class="ri-phone-line"></i> &nbsp; (+91)-9876XXXXX</a></p>
            <p><a href="javascript:void(0)"><i class="ri-phone-line"></i> &nbsp; (+91)-987654XXXX</a></p>
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-md-6 col-12 mb-24" data-aos="fade-up" data-aos-duration="2000" data-aos-delay="600">
        <div class="cr-info-box">
          <div class="cr-icon">
            <i class="ri-mail-line"></i>
          </div>
          <div class="cr-info-content">
            <h4 class="heading">Email & Website</h4>
            <p><a href="javascript:void(0)"><i class="ri-mail-line"></i> &nbsp; mail.example@gmail.com</a></p>
            <p><a href="javascript:void(0)"><i class="ri-globe-line"></i> &nbsp; www.yourdomain.com</a></p>
          </div>
        </div>
      </div>
      <div class="col-lg-4 col-12 mb-24" data-aos="fade-up" data-aos-duration="2000" data-aos-delay="800">
        <div class="cr-info-box">
          <div class="cr-icon">
            <i class="ri-map-pin-line"></i>
          </div>
          <div class="cr-info-content">
            <h4 class="heading">Địa chỉ</h4>
            <p><a href="javascript:void(0)"><i class="ri-map-pin-line"></i> &nbsp; 140 Ruami Moraes Filho, 987 - Salvador - MA, 40352, Brazil.</a></p>
          </div>
        </div>
      </div>
    </div>
  </div>

</section>
<script type="module" src="${pageContext.request.contextPath}/assets/customer/js/contact-us.js"></script>




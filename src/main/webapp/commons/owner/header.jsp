<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Header -->
<header class="cr-header">
    <div class="container-fluid">
        <div class="cr-header-items">
            <div class="left-header">
                <a href="javascript:void(0)" class="cr-toggle-sidebar">
                    <span class="outer-ring">
                        <span class="inner-ring"></span>
                    </span>
                </a>
                <div class="header-search-box">
                    <div class="header-search-drop">
                        <a href="javascript:void(0)" class="open-search"
                        ><i class="ri-search-line"></i
                        ></a>
                        <form class="cr-search">
                            <input
                                    class="search-input"
                                    type="text"
                                    placeholder="Search..."
                            />
                            <a href="javascript:void(0)" class="search-btn"
                            ><i class="ri-search-line"></i>
                            </a>
                        </form>
                    </div>
                </div>
            </div>
            <div class="right-header">
                <div class="cr-right-tool display-screen">
                    <a class="cr-screen full" href="javascript:void(0)"
                    ><i class="ri-fullscreen-line"></i
                    ></a>
                    <a class="cr-screen reset" href="javascript:void(0)"
                    ><i class="ri-fullscreen-exit-line"></i
                    ></a>
                </div>
                <div class="cr-right-tool">
                    <a class="cr-notify" href="javascript:void(0)">
                        <i class="ri-notification-2-line"></i>
                        <span class="label"></span>
                    </a>
                </div>
                <div class="cr-right-tool cr-user-drop">
                    <div class="cr-hover-drop">
                        <div class="cr-hover-tool">
                            <img class="user" src="${pageContext.request.contextPath}/assets/owner/img/user/1.jpg" alt="user" />
                        </div>
                        <div class="cr-hover-drop-panel right">
                            <div class="details">
                                <h6>Lê Hồng Phúc</h6>
                                <p>lhphuc@gmail.com</p>
                            </div>
                            <ul class="border-top">
                                <li><a href="team-profile.html">Hồ sơ</a></li>
                                <li><a href="faq.html">Trợ giúp</a></li>
                                <li><a href="team-update.html">Cài đặt</a></li>
                            </ul>
                            <ul class="border-top">
                                <li>
                                    <a href="${pageContext.request.contextPath}/logout"
                                    ><i class="ri-logout-circle-r-line"></i>Đăng xuất</a
                                    >
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
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
                            <img class="user" src="${pageContext.request.contextPath}${staff.avatar}" alt="user"/>
                        </div>
                        <div class="cr-hover-drop-panel right">
                            <div class="details">
                                <h6>${staff.fullName}</h6>
                                <p>${staff.emailAddress}</p>
                            </div>
                            <ul class="border-top">
                                <li>
                                    <a href="${pageContext.request.contextPath}/staff/profile">Thông tin cá nhân</a>
                                </li>
                                <li><a href="faq.html">Help</a></li>
                                <li>
                                    <a href="chatapp.html">Messages</a>
                                </li>
                                <li>
                                    <a href="project-overview.html">Projects</a>
                                </li>
                                <li>
                                    <a href="team-update.html">Settings</a>
                                </li>
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
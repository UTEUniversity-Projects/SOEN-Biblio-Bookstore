<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Login -->
<section class="section-login padding-tb-100">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div
                        class="cr-login"
                        data-aos="fade-up"
                        data-aos-duration="2000"
                        data-aos-delay="400"
                >
                    <div class="form-logo">
                        <img src="${pageContext.request.contextPath}/assets/commons/img/logo/logo.png" alt="logo"/>
                    </div>
                    <form class="cr-content-form" action="login" method="POST" id="loginForm">
                        <div class="form-group mb-2">
                            <label for="username" class="text-md text-slate-700 mb-1 ml-2">Username*</label> <input
                                name="username" type="text"
                                id="username"
                                placeholder="Nhập username..."
                                class="block w-full py-2 px-4 text-gray-900 border-1 border-gray-300 rounded-lg text-[16px] focus:ring-blue-500 focus:border-blue-500 transition-all ease-linear bg-white focus:shadow-lg focus:shadow-[rgba(3,_102,_214,_0.3)_0px_0px_0px_3px]"/>
                            <span class="form-message text-[16px] text-red-500"></span>
                        </div>
                        <div class="form-group mb-2">
                            <label for="username" class="text-md text-slate-700 mb-1 ml-2">Mật khẩu*</label> <input
                                name="password" type="password"
                                id="password"
                                placeholder="Nhập mật khẩu..."
                                class="block w-full py-2 px-4 text-gray-900 border-1 border-gray-300 rounded-lg text-[16px] focus:ring-blue-500 focus:border-blue-500 transition-all ease-linear bg-white focus:shadow-lg focus:shadow-[rgba(3,_102,_214,_0.3)_0px_0px_0px_3px]"/>
                            <span class="form-message text-[16px] text-red-500"></span>
                        </div>
                        <div class="flex justify-between items-center mb-2">
                            <div class="ml-2 flex items-center">
                                <label
                                        class="flex items-center cursor-pointer relative"
                                        for="check-with-link"
                                >
                                    <input
                                            type="checkbox"
                                            class="peer h-5 w-5 cursor-pointer transition-all appearance-none rounded shadow hover:shadow-md border border-slate-200 checked:bg-[#26aa99] checked:border-[#26bb99]"
                                            id="check-with-link"
                                            name="rememberMe"
                                            value="1"
                                            checked
                                    />
                                    <span
                                            class="absolute text-white opacity-0 peer-checked:opacity-100 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 flex items-center justify-center w-[12px] h-[12px]"
                                    >
                              <svg
                                      xmlns="http://www.w3.org/2000/svg"
                                      xmlns:xlink="http://www.w3.org/1999/xlink"
                                      version="1.1"
                                      width="10"
                                      height="10"
                                      viewBox="0 0 1080 1080"
                                      xml:space="preserve"
                              >
                                <defs></defs>
                                <rect
                                        x="0"
                                        y="0"
                                        width="100%"
                                        height="100%"
                                        fill="transparent"
                                ></rect>
                                <g
                                        transform="matrix(1 0 0 1 540 540)"
                                        id="8f5a25e9-c421-4450-b54c-cac6ab86c1ce"
                                ></g>
                                <g
                                        transform="matrix(1 0 0 1 540 540)"
                                        id="a986aab8-9d4c-4b79-84b5-670a6c9dd99d"
                                >
                                  <rect
                                          style="
                                      stroke: none;
                                      stroke-width: 1;
                                      stroke-dasharray: none;
                                      stroke-linecap: butt;
                                      stroke-dashoffset: 0;
                                      stroke-linejoin: miter;
                                      stroke-miterlimit: 4;
                                      fill: rgb(255, 255, 255);
                                      fill-rule: nonzero;
                                      opacity: 1;
                                      visibility: hidden;
                                    "
                                          vector-effect="non-scaling-stroke"
                                          x="-540"
                                          y="-540"
                                          rx="0"
                                          ry="0"
                                          width="1080"
                                          height="1080"
                                  />
                                </g>
                                <g transform="matrix(67.51 0 0 67.51 540 595.68)">
                                  <path
                                          style="
                                      stroke: rgb(255, 255, 255);
                                      stroke-width: 1;
                                      stroke-dasharray: none;
                                      stroke-linecap: butt;
                                      stroke-dashoffset: 0;
                                      stroke-linejoin: miter;
                                      stroke-miterlimit: 4;
                                      fill: rgb(255, 251, 251);
                                      fill-rule: evenodd;
                                      opacity: 1;
                                    "
                                          transform=" translate(-10.01, -10)"
                                          d="M 16.707 5.293 C 17.097381938332862 5.683499851485814 17.097381938332862 6.316500148514186 16.707 6.707000000000001 L 8.707 14.707 C 8.316500148514187 15.097381938332862 7.6834998514858155 15.097381938332864 7.293000000000001 14.707 L 3.293000000000001 10.707 C 2.9140277904919127 10.314621113035688 2.9194476261029205 9.690915222978894 3.3051814245409075 9.305181424540908 C 3.6909152229788935 8.91944762610292 4.314621113035688 8.914027790491913 4.707000000000001 9.293000000000001 L 8 12.586 L 15.293 5.293 C 15.683499851485813 4.902618061667136 16.316500148514187 4.902618061667136 16.707 5.292999999999999 z"
                                          stroke-linecap="round"
                                  />
                                </g>
                              </svg>
                            </span>
                                </label>
                                <label
                                        class="cursor-pointer ml-2 text-md text-slate-700 mb-1 ml-2"
                                        for="check-with-link"
                                >
                                    <p class="mt-1">Ghi nhớ đăng nhập</p>
                                </label>
                            </div>
                            <div>
                                <a href="${pageContext.request.contextPath}/forgot"
                                   class="text-md text-[#26a397] hover:opacity-80 transition-all duration-300">Quên mật
                                    khẩu?</a>
                            </div>
                        </div>
                        <div class="login-buttons">
                            <a
                                    href=${pageContext.request.contextPath}/ "verify-email"
                                    class="py-2 border border-solid px-4 inline-block rounded-md hover:bg-gray-100 transition-all duration-300"
                            >
                                Đăng ký
                            </a>

                            <button
                                    type="submit"
                                    class="btn-login font-medium text-md bg-emerald-600 px-4 py-2 rounded-md text-white hover:bg-emerald-700 transition-all duration-300"
                            >
                                <span class="button-text">Đăng nhập</span>
                                <div class="spinner hidden w-[20px] h-[20px] border-[4px] border-solid border-[#fff] rounded-full border-t-transparent animate-spin mx-auto"></div>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="${pageContext.request.contextPath}/assets/customer/js/login.js" type="module"></script>


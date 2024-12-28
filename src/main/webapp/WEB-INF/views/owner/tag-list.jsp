<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Main content -->
<div class="cr-main-content">
    <div class="container-fluid">
        <!-- Page title & breadcrumb -->
        <div class="cr-page-title cr-page-title-2">
            <div class="cr-breadcrumb">
                <h5>Nhãn sản phẩm</h5>
                <ul>
                    <li><a href="index.html">Biblio</a></li>
                    <li>Nhãn sản phẩm</li>
                </ul>
            </div>
        </div>
        <div class="row cr-category">
            <div class="col-xl-4 col-lg-12">
                <div class="team-sticky-bar">
                    <div class="col-md-12">
                        <div class="cr-cat-list cr-card card-default mb-24px">
                            <div class="cr-card-content">
                                <div class="cr-cat-form">
                                    <h3>Thêm nhãn mới</h3>

                                    <form>

                                        <div class="form-group">
                                            <label>Tên nhãn</label>
                                            <div class="col-12">
                                                <input id="text" name="text"
                                                       class="form-control here slug-title" type="text">
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <label>Mô tả ngắn</label>
                                            <div class="col-12">
														<textarea id="sortdescription" name="sortdescription" cols="40"
                                                                  rows="2" class="form-control"></textarea>
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <label>Mô tả đầy đủ</label>
                                            <div class="col-12">
														<textarea id="fulldescription" name="fulldescription" cols="40"
                                                                  rows="4" class="form-control"></textarea>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-12 d-flex">
                                                <button type="submit" class="cr-btn-primary">Xác nhận</button>
                                            </div>
                                        </div>

                                    </form>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-8 col-lg-12">
                <div class="cr-cat-list cr-card card-default">
                    <div class="cr-card-content ">
                        <div class="table-responsive tbl-800">
                            <table id="cat_data_table" class="table">
                                <thead>
                                <tr>
                                    <th>Tên</th>
                                    <th>Mô tả ngắn</th>
                                    <th>Số sản phẩm</th>
                                    <th>Trạng thái</th>
                                    <th>Xu hướng</th>
                                    <th>Thao tác</th>
                                </tr>
                                </thead>

                                <tbody>
                                <tr>
                                    <td>Bags</td>
                                    <td>
                                        This is a product tag.
                                    </td>
                                    <td>18</td>
                                    <td class="active">ACTIVE</td>
                                    <td><span class="badge badge-success">Top</span></td>
                                    <td>
                                        <div>
                                            <button type="button"
                                                    class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
                                                    data-bs-toggle="dropdown" aria-haspopup="true"
                                                    aria-expanded="false" data-display="static">
															<span class="sr-only"><i
                                                                    class="ri-settings-3-line"></i></span>
                                            </button>

                                            <div class="dropdown-menu">
                                                <a class="dropdown-item" href="#">Chỉnh sửa</a>
                                                <a class="dropdown-item" href="#">Xóa</a>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Computers</td>
                                    <td>
                                        This is a product tag.
                                    </td>
                                    <td>65</td>
                                    <td class="active">ACTIVE</td>
                                    <td><span class="badge bg-primary">Medium</span></td>
                                    <td>
                                        <div>
                                            <button type="button"
                                                    class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
                                                    data-bs-toggle="dropdown" aria-haspopup="true"
                                                    aria-expanded="false" data-display="static">
															<span class="sr-only"><i
                                                                    class="ri-settings-3-line"></i></span>
                                            </button>

                                            <div class="dropdown-menu">
                                                <a class="dropdown-item" href="#">Chỉnh sửa</a>
                                                <a class="dropdown-item" href="#">Xóa</a>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Cosmetics</td>
                                    <td>
                                        This is a product tag.
                                    </td>
                                    <td>18</td>
                                    <td class="disable">INACTIVE</td>
                                    <td><span class="badge bg-danger">Low</span></td>
                                    <td>
                                        <div>
                                            <button type="button"
                                                    class="btn btn-outline-success dropdown-toggle dropdown-toggle-split"
                                                    data-bs-toggle="dropdown" aria-haspopup="true"
                                                    aria-expanded="false" data-display="static">
															<span class="sr-only"><i
                                                                    class="ri-settings-3-line"></i></span>
                                            </button>

                                            <div class="dropdown-menu">
                                                <a class="dropdown-item" href="#">Chỉnh sửa</a>
                                                <a class="dropdown-item" href="#">Xóa</a>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
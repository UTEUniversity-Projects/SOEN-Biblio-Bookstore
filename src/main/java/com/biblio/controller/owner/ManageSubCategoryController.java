package com.biblio.controller.owner;

import com.biblio.dto.request.SubCategoryDeleteRequest;
import com.biblio.dto.request.SubCategoryCreateRequest;
import com.biblio.dto.request.SubCategoryUpdateRequest;
import com.biblio.dto.response.*;
import com.biblio.dto.response.SubCategoryProfileResponse;
import com.biblio.entity.SubCategory;
import com.biblio.enumeration.EClassificationStatus;
import com.biblio.service.ISubCategoryService;
import com.biblio.utils.HttpUtil;
import com.biblio.utils.ManageFileUtil;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = {"/owner/sub-category/list", "/owner/sub-category/view",
        "/owner/sub-category/create", "/owner/sub-category/update", "/owner/sub-category/delete"})
public class ManageSubCategoryController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    ISubCategoryService subCategoryService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageSubCategoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = getAction(request);

        switch (action) {
            case "list":
                getList(request, response);
                break;
            case "view":
                viewHandler(request, response);
                break;
            case "create":
                createHandlerGet(request, response);
                break;
            case "update":
                updateHandlerGet(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = getAction(request);

        switch (action) {
            case "create":
                createHandlerPost(request, response);
                break;
            case "update":
                updateHandlerPost(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    /**
     * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            SubCategoryDeleteRequest subCategoryDeleteRequest = HttpUtil.of(request.getReader()).toModelUnknown(SubCategoryDeleteRequest.class);

            subCategoryService.delete(subCategoryDeleteRequest);

            response.getWriter().write("{\"status\": \"success\", \"message\": \"Deleted successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error deleting: " + e.getMessage() + "\"}");
        }
    }

    private String getAction(HttpServletRequest request) {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            String requestURI = request.getRequestURI();
            String[] pathParts = requestURI.split("/");
            action = pathParts[pathParts.length - 1];
        }

        return action;
    }

    private void getList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<SubCategoryLineResponse> list = subCategoryService.getAll();
        request.setAttribute("subCategories", list);
        request.getRequestDispatcher("/views/owner/sub-category-list.jsp").forward(request, response);
    }

    private void viewHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = session.getAttribute("subCategoryId").toString();

        SubCategoryAnalysisResponse subCategoryResponse = subCategoryService.getAnalysisById(Long.parseLong(id));
        request.setAttribute("subCategory", subCategoryResponse);
        request.getRequestDispatcher("/views/owner/sub-category-profile.jsp").forward(request, response);
    }

    private void createHandlerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubCategoryCreateResponse subCategoryCreateResponse = subCategoryService.initCreateSubCategory();

        request.setAttribute("init", subCategoryCreateResponse);
        request.getRequestDispatcher("/views/owner/sub-category-create.jsp").forward(request, response);
    }

    private void createHandlerPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            SubCategoryCreateRequest subCategoryCreateRequest = HttpUtil.of(request.getReader()).toModelUnknown(SubCategoryCreateRequest.class);

            SubCategory subCategory = subCategoryService.create(subCategoryCreateRequest);

            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            response.getWriter().write("{\"status\": \"success\", \"id\": " + subCategory.getId() + ", \"message\": \"Created successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error creating: " + e.getMessage() + "\"}");
            log.error("e: ", e);
        }
    }

    private void updateHandlerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = session.getAttribute("subCategoryId").toString();

        SubCategoryProfileResponse subCategoryResponse = subCategoryService.getProfileById(Long.parseLong(id));
        SubCategoryCreateResponse subCategoryCreateResponse = subCategoryService.initCreateSubCategory();

        request.setAttribute("init", subCategoryCreateResponse);
        request.setAttribute("subCategory", subCategoryResponse);
        request.getRequestDispatcher("/views/owner/sub-category-update.jsp").forward(request, response);
    }

    private void updateHandlerPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            SubCategoryUpdateRequest subCategoryUpdateRequest = HttpUtil.of(request.getReader()).toModelUnknown(SubCategoryUpdateRequest.class);

            subCategoryService.update(subCategoryUpdateRequest);

            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Updated successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error updating: " + e.getMessage() + "\"}");
            log.error("e: ", e);
        }
    }
}

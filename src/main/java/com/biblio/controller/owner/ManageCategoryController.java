package com.biblio.controller.owner;

import com.biblio.dto.request.CategoryDeleteRequest;
import com.biblio.dto.request.CategoryCreateRequest;
import com.biblio.dto.request.CategoryUpdateRequest;
import com.biblio.dto.response.CategoryProfileResponse;
import com.biblio.dto.response.CategoryAnalysisResponse;
import com.biblio.dto.response.CategoryLineResponse;
import com.biblio.dto.response.CategoryProfileResponse;
import com.biblio.entity.Category;
import com.biblio.enumeration.EClassificationStatus;
import com.biblio.service.ICategoryService;
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
@WebServlet(urlPatterns = {"/owner/category/list", "/owner/category/view",
        "/owner/category/create", "/owner/category/update", "/owner/category/delete"})
public class ManageCategoryController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    ICategoryService categoryService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageCategoryController() {
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
            CategoryDeleteRequest categoryDeleteRequest = HttpUtil.of(request.getReader()).toModelUnknown(CategoryDeleteRequest.class);

            categoryService.delete(categoryDeleteRequest);

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
        List<CategoryLineResponse> list = categoryService.getAll();
        request.setAttribute("categories", list);
        request.getRequestDispatcher("/views/owner/category-list.jsp").forward(request, response);
    }

    private void viewHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = session.getAttribute("categoryId").toString();

        CategoryAnalysisResponse categoryResponse = categoryService.getAnalysisById(Long.parseLong(id));
        request.setAttribute("category", categoryResponse);
        request.getRequestDispatcher("/views/owner/category-profile.jsp").forward(request, response);
    }

    private void createHandlerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/owner/category-create.jsp").forward(request, response);
    }

    private void createHandlerPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            CategoryCreateRequest categoryCreateRequest = HttpUtil.of(request.getReader()).toModelUnknown(CategoryCreateRequest.class);

            Category category = categoryService.create(categoryCreateRequest);

            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            response.getWriter().write("{\"status\": \"success\", \"id\": " + category.getId() + ", \"message\": \"Created successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error creating: " + e.getMessage() + "\"}");
            log.error("e: ", e);
        }
    }

    private void updateHandlerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = session.getAttribute("categoryId").toString();

        CategoryProfileResponse categoryResponse = categoryService.getProfileById(Long.parseLong(id));
        request.setAttribute("category", categoryResponse);
        request.getRequestDispatcher("/views/owner/category-update.jsp").forward(request, response);
    }

    private void updateHandlerPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            CategoryUpdateRequest categoryUpdateRequest = HttpUtil.of(request.getReader()).toModelUnknown(CategoryUpdateRequest.class);

            categoryService.update(categoryUpdateRequest);

            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Updated successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error updating: " + e.getMessage() + "\"}");
            log.error("e: ", e);
        }
    }
}

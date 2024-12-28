package com.biblio.controller.owner;

import com.biblio.dto.request.AuthorCreateRequest;
import com.biblio.dto.request.AuthorDeleteRequest;
import com.biblio.dto.request.AuthorUpdateRequest;
import com.biblio.dto.response.AuthorAnalysisResponse;
import com.biblio.dto.response.AuthorLineResponse;
import com.biblio.dto.response.AuthorProfileResponse;
import com.biblio.entity.Author;
import com.biblio.service.IAuthorService;
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
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = {"/owner/author/list", "/owner/author/view",
        "/owner/author/create", "/owner/author/update", "/owner/author/delete"})
public class ManageAuthorController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    IAuthorService authorService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageAuthorController() {
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
            AuthorDeleteRequest authorDeleteRequest = HttpUtil.of(request.getReader()).toModel(AuthorDeleteRequest.class);
            AuthorProfileResponse authorProfileResponse = authorService.getProfileById(Long.valueOf(authorDeleteRequest.getId()));

            authorService.deleteAuthor(authorDeleteRequest);

            Boolean isImageDeleted = ManageFileUtil.deleteFileAvatar(authorProfileResponse.getAvatar(), "author");

            if (isImageDeleted) {
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Deleted successfully.\"}");
            } else {
                response.getWriter().write("{\"status\": \"fail\", \"message\": \"Failed to delete.\"}");
            }
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
        List<AuthorLineResponse> list = authorService.getAll();
        request.setAttribute("authors", list);
        request.getRequestDispatcher("/views/owner/author-list.jsp").forward(request, response);
    }

    private void viewHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = session.getAttribute("authorId").toString();

        AuthorAnalysisResponse authorResponse = authorService.getAnalysisById(Long.parseLong(id));
        request.setAttribute("author", authorResponse);
        request.getRequestDispatcher("/views/owner/author-profile.jsp").forward(request, response);
    }

    private void createHandlerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/owner/author-create.jsp").forward(request, response);
    }

    private void createHandlerPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            AuthorCreateRequest authorCreateRequest = HttpUtil.of(request.getReader()).toModel(AuthorCreateRequest.class);
            authorCreateRequest.setJoinAt(LocalDateTime.now().toString());

            Author author = authorService.createAuthor(authorCreateRequest);

            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            response.getWriter().write("{\"status\": \"success\", \"id\": " + author.getId() + ", \"message\": \"Created successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error creating: " + e.getMessage() + "\"}");
            log.error("e: ", e);
        }
    }

    private void updateHandlerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = session.getAttribute("authorId").toString();

        AuthorProfileResponse authorResponse = authorService.getProfileById(Long.parseLong(id));
        request.setAttribute("author", authorResponse);
        request.getRequestDispatcher("/views/owner/author-update.jsp").forward(request, response);
    }

    private void updateHandlerPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            AuthorUpdateRequest authorUpdateRequest = HttpUtil.of(request.getReader()).toModel(AuthorUpdateRequest.class);

            authorService.updateAuthor(authorUpdateRequest);

            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Updated successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error updating: " + e.getMessage() + "\"}");
            log.error("e: ", e);
        }
    }
}

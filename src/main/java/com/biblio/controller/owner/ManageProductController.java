package com.biblio.controller.owner;

import com.biblio.dto.request.AuthorCreateRequest;
import com.biblio.dto.request.BookCreateGlobalRequest;
import com.biblio.dto.request.BookUpdateGlobalRequest;
import com.biblio.dto.response.*;
import com.biblio.entity.Author;
import com.biblio.entity.BookTemplate;
import com.biblio.service.IBookService;
import com.biblio.service.IBookTemplateService;
import com.biblio.utils.HttpUtil;
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
@WebServlet(urlPatterns = {"/owner/product/list", "/owner/product/view",
        "/owner/product/create", "/owner/product/update", "/owner/product/delete"})
public class ManageProductController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    IBookTemplateService bookTemplateService;
    @Inject
    IBookService bookService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageProductController() {
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
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
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("account"));

        List<BookLineResponse> list = bookTemplateService.getAllBookLineResponse();

        request.setAttribute("books", list);
        request.getRequestDispatcher("/views/owner/product-list.jsp").forward(request, response);
    }

    private void viewHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = session.getAttribute("productId").toString();

        BookAnalysisResponse book = bookTemplateService.getBookAnalysisResponse(Long.parseLong(id));

        request.setAttribute("book", book);
        request.getRequestDispatcher("/views/owner/product-details.jsp").forward(request, response);
    }

    private void createHandlerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookCreateResponse bookCreateResponse = bookTemplateService.initCreateBook();

        request.setAttribute("init", bookCreateResponse);
        request.getRequestDispatcher("/views/owner/product-create.jsp").forward(request, response);
    }

    private void createHandlerPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BookCreateGlobalRequest bookCreateGlobalRequest = HttpUtil.of(request.getReader()).toModelUnknown(BookCreateGlobalRequest.class);

            BookTemplate bookTemplate = bookService.createBookSeries(bookCreateGlobalRequest);

            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            response.getWriter().write("{\"status\": \"success\", \"id\": " + bookTemplate.getId() + ", \"message\": \"Created successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error creating: " + e.getMessage() + "\"}");
            log.error("e: ", e);
        }
    }

    private void updateHandlerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = session.getAttribute("productId").toString();

        BookProfileResponse book = bookTemplateService.getBookProfileResponse(Long.parseLong(id));
        BookUpdateResponse bookUpdateResponse = bookTemplateService.initUpdateBook();

        request.setAttribute("book", book);
        request.setAttribute("init", bookUpdateResponse);
        request.getRequestDispatcher("/views/owner/product-update.jsp").forward(request, response);
    }

    private void updateHandlerPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BookUpdateGlobalRequest bookUpdateGlobalRequest = HttpUtil.of(request.getReader()).toModelUnknown(BookUpdateGlobalRequest.class);

            BookTemplate bookTemplate = bookService.updateBookSeries(bookUpdateGlobalRequest);

            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            response.getWriter().write("{\"status\": \"success\", \"id\": " + bookTemplate.getId() + ", \"message\": \"Updated successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error updating: " + e.getMessage() + "\"}");
            log.error("e: ", e);
        }
    }
}

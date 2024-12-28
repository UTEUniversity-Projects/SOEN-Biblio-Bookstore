package com.biblio.controller.owner;

import com.biblio.dto.request.PublisherCreateRequest;
import com.biblio.dto.request.PublisherDeleteRequest;
import com.biblio.dto.request.PublisherUpdateRequest;
import com.biblio.dto.response.PublisherAnalysisResponse;
import com.biblio.dto.response.PublisherLineResponse;
import com.biblio.dto.response.PublisherProfileResponse;
import com.biblio.entity.Publisher;
import com.biblio.service.IPublisherService;
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
@WebServlet(urlPatterns = {"/owner/publisher/list", "/owner/publisher/view",
        "/owner/publisher/create", "/owner/publisher/update", "/owner/publisher/delete"})
public class ManagePublisherController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    IPublisherService publisherService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagePublisherController() {
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
            PublisherDeleteRequest publisherDeleteRequest = HttpUtil.of(request.getReader()).toModel(PublisherDeleteRequest.class);
            PublisherProfileResponse publisherProfileResponse = publisherService.getProfileById(Long.valueOf(publisherDeleteRequest.getId()));

            String filePath = publisherProfileResponse.getAvatar();

            publisherService.deletePublisher(publisherDeleteRequest);

            Boolean isImageDeleted = ManageFileUtil.deleteFileAvatar(filePath, "publisher");

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
        List<PublisherLineResponse> list = publisherService.getAll();
        request.setAttribute("publishers", list);
        request.getRequestDispatcher("/views/owner/publisher-list.jsp").forward(request, response);
    }

    private void viewHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = session.getAttribute("publisherId").toString();

        PublisherAnalysisResponse publisherResponse = publisherService.getAnalysisById(Long.parseLong(id));
        request.setAttribute("publisher", publisherResponse);
        request.getRequestDispatcher("/views/owner/publisher-profile.jsp").forward(request, response);
    }

    private void createHandlerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/owner/publisher-create.jsp").forward(request, response);
    }

    private void createHandlerPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            PublisherCreateRequest publisherCreateRequest = HttpUtil.of(request.getReader()).toModel(PublisherCreateRequest.class);
            publisherCreateRequest.setJoinAt(LocalDateTime.now().toString());

            Publisher publisher = publisherService.createPublisher(publisherCreateRequest);

            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            response.getWriter().write("{\"status\": \"success\", \"id\": " + publisher.getId() + ", \"message\": \"Created successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error creating: " + e.getMessage() + "\"}");
            log.error("e: ", e);
        }
    }

    private void updateHandlerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = session.getAttribute("publisherId").toString();

        PublisherProfileResponse publisherResponse = publisherService.getProfileById(Long.parseLong(id));
        request.setAttribute("publisher", publisherResponse);
        request.getRequestDispatcher("/views/owner/publisher-update.jsp").forward(request, response);
    }

    private void updateHandlerPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            PublisherUpdateRequest publisherUpdateRequest = HttpUtil.of(request.getReader()).toModel(PublisherUpdateRequest.class);

            publisherService.updatePublisher(publisherUpdateRequest);

            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Updated successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error updating: " + e.getMessage() + "\"}");
            log.error("e: ", e);
        }
    }
}

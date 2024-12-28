package com.biblio.controller.owner;

import com.biblio.dto.request.TranslatorCreateRequest;
import com.biblio.dto.request.TranslatorDeleteRequest;
import com.biblio.dto.request.TranslatorUpdateRequest;
import com.biblio.dto.response.TranslatorAnalysisResponse;
import com.biblio.dto.response.TranslatorLineResponse;
import com.biblio.dto.response.TranslatorProfileResponse;
import com.biblio.entity.Translator;
import com.biblio.service.ITranslatorService;
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
@WebServlet(urlPatterns = {"/owner/translator/list", "/owner/translator/view",
        "/owner/translator/create", "/owner/translator/update", "/owner/translator/delete"})
public class ManageTranslatorController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    ITranslatorService translatorService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageTranslatorController() {
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
            TranslatorDeleteRequest translatorDeleteRequest = HttpUtil.of(request.getReader()).toModel(TranslatorDeleteRequest.class);
            TranslatorProfileResponse translatorProfileResponse = translatorService.getProfileById(Long.valueOf(translatorDeleteRequest.getId()));

            translatorService.deleteTranslator(translatorDeleteRequest);

            Boolean isImageDeleted = ManageFileUtil.deleteFileAvatar(translatorProfileResponse.getAvatar(), "translator");

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
        List<TranslatorLineResponse> list = translatorService.getAll();
        request.setAttribute("translators", list);
        request.getRequestDispatcher("/views/owner/translator-list.jsp").forward(request, response);
    }

    private void viewHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = session.getAttribute("translatorId").toString();

        TranslatorAnalysisResponse translatorResponse = translatorService.getAnalysisById(Long.parseLong(id));
        request.setAttribute("translator", translatorResponse);
        request.getRequestDispatcher("/views/owner/translator-profile.jsp").forward(request, response);
    }

    private void createHandlerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/owner/translator-create.jsp").forward(request, response);
    }

    private void createHandlerPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            TranslatorCreateRequest translatorCreateRequest = HttpUtil.of(request.getReader()).toModel(TranslatorCreateRequest.class);
            translatorCreateRequest.setJoinAt(LocalDateTime.now().toString());

            Translator translator = translatorService.createTranslator(translatorCreateRequest);

            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            response.getWriter().write("{\"status\": \"success\", \"id\": " + translator.getId() + ", \"message\": \"Created successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error creating: " + e.getMessage() + "\"}");
            log.error("e: ", e);
        }
    }

    private void updateHandlerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = session.getAttribute("translatorId").toString();

        TranslatorProfileResponse translatorResponse = translatorService.getProfileById(Long.parseLong(id));
        request.setAttribute("translator", translatorResponse);
        request.getRequestDispatcher("/views/owner/translator-update.jsp").forward(request, response);
    }

    private void updateHandlerPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            TranslatorUpdateRequest translatorUpdateRequest = HttpUtil.of(request.getReader()).toModel(TranslatorUpdateRequest.class);

            translatorService.updateTranslator(translatorUpdateRequest);

            response.setStatus(HttpServletResponse.SC_OK);  // 200 OK
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Updated successfully.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error updating: " + e.getMessage() + "\"}");
            log.error("e: ", e);
        }
    }
}

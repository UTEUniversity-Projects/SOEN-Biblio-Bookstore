package com.biblio.apis.owner;

import com.biblio.dto.request.PublisherDeleteRequest;
import com.biblio.service.IPublisherService;
import com.biblio.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@Slf4j
@WebServlet(urlPatterns = {"/api/owner/publisher/delete"})
public class CheckDeletePublisherAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    IPublisherService publisherService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckDeletePublisherAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PublisherDeleteRequest publisherDeleteRequest = HttpUtil.of(request.getReader()).toModel(PublisherDeleteRequest.class);

            Integer amount = publisherService.countBookTemplate(publisherDeleteRequest);

            response.setStatus(HttpServletResponse.SC_OK);
            if (amount.equals(0)) {
                response.getWriter().write("{\"can\": \"true\", \"amount\": " + amount + ", \"message\": \"Can delete.\"}");
            } else {
                response.getWriter().write("{\"can\": \"false\", \"amount\": " + amount + ", \"message\": \"Can not delete.\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"fail\", \"message\": \"Error deleting: " + e.getMessage() + "\"}");
        }
    }
}

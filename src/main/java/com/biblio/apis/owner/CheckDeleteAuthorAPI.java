package com.biblio.apis.owner;

import com.biblio.dto.request.AuthorDeleteRequest;
import com.biblio.service.IAuthorService;
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
@WebServlet(urlPatterns = {"/api/owner/author/delete"})
public class CheckDeleteAuthorAPI extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    IAuthorService authorService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckDeleteAuthorAPI() {
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
            AuthorDeleteRequest authorDeleteRequest = HttpUtil.of(request.getReader()).toModel(AuthorDeleteRequest.class);

            Integer amount = authorService.countBookTemplate(authorDeleteRequest);

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

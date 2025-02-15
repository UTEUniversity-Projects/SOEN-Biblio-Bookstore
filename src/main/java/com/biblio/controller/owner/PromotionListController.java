package com.biblio.controller.owner;

import com.biblio.dto.response.PromotionGetResponse;
import com.biblio.dto.response.PromotionTemplateGetResponse;
import com.biblio.service.IPromotionService;
import com.biblio.service.IPromotionTemplateService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

@WebServlet("/owner/promotion-list")
public class PromotionListController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    @Inject
    IPromotionTemplateService promotionTemplateService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PromotionListController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        List<PromotionTemplateGetResponse> list = promotionTemplateService.getAllPromotionTemplates();
        request.setAttribute("promotions", list);
        request.getRequestDispatcher("/views/owner/promotion-list.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}

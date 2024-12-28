package com.biblio.controller;

import com.biblio.utils.UploadFileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 15)   // 15 MB
public class UploadImageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UploadImageController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dir = request.getParameter("dir");
        String fileName = request.getParameter("fileName");
        String typeAction = request.getParameter("typeAction");

        System.out.println(fileName);
        List<String> imageLinks = new ArrayList<String>();

        Collection<Part> parts = request.getParts();

        for (Part part : parts) {
            if (part.getName().equals("files")) {
                if (typeAction.equals("nonContextPath")) {
                    imageLinks.add(UploadFileUtil.UploadImageNonContextPath(part, request.getServletContext(), dir, fileName));
                } else {
                    imageLinks.add(UploadFileUtil.UploadImage(part, request.getServletContext(), dir, fileName));
                }
            }
        }

//        String fileName = request.getParameter("fileName");
//        String avatar = UploadFileUtil.UploadImage(part, request.getServletContext(), fileName);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("imageLinks", imageLinks);
        map.put("fileName", fileName);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}

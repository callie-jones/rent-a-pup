package com.rentapup.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentapup.web.obj.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by elijahstaple on 4/16/17.
 */
@WebServlet("/test")
public class QueryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        json = br.readLine();

        ObjectMapper mapper = new ObjectMapper();
        Query query = mapper.readValue(json, Query.class);
        System.out.println(query.toString());
        response.setContentType("text");
        mapper.writeValue(response.getOutputStream(), "Sucessful AJAX query!!!");
    }
}

package com.rentapup.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
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
 * Created by elijahstaple on 4/22/17.
 */
@WebServlet("/profile")
public class RenterProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json;
        json = br.readLine();

        ObjectMapper mapper = new ObjectMapper();
        Query<String, String> query = mapper.readValue(json, new TypeReference<Query<String, Object>>(){});

        DBCollection renterCol = ((MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT"))
                .getDB("rapTest").getCollection("renterData");
        System.out.println(QueryHelper.renterProfile(renterCol, query.get("id")));
        response.setContentType("text");
        mapper.writeValue(response.getOutputStream(), QueryHelper.renterProfile(renterCol, query.get("id")));
    }
}

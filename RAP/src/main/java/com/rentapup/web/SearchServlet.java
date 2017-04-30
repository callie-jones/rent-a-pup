package com.rentapup.web;

/**
 * Created by callie on 4/27/17.
 */

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
 * Created by callie on 4/26/17.
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json;
        json = br.readLine();

        ObjectMapper mapper = new ObjectMapper();
        Query<String, String> query = mapper.readValue(json, new TypeReference<Query<String, String>>(){});

        DBCollection bookCol = ((MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT"))
                .getDB("rapTest").getCollection("bookingData");
        DBCollection dogCol = ((MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT"))
                .getDB("rapTest").getCollection("dogData");
        DBCollection userCol = ((MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT"))
                .getDB("rapTest").getCollection("renterData");
        response.setContentType("text");
        System.out.println(query.get("qType"));

        if(query.get("qType").equals("byTime")) {
            mapper.writeValue(response.getOutputStream(), QueryHelper.searchByTime(bookCol, dogCol, query.get("dog"), query.get("start"), query.get("end")));
        }
        else {
            mapper.writeValue(response.getOutputStream(), QueryHelper.searchByDog(bookCol, dogCol, query.get("dog")));
        }
    }
}


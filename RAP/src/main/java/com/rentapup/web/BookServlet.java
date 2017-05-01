package com.rentapup.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.rentapup.web.obj.Query;
import org.bson.types.ObjectId;

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
@WebServlet("/book")
public class BookServlet extends HttpServlet {

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
        //System.out.println(QueryHelper.addBooking(bookCol, dogCol, userCol,  query.get("start"), query.get("end"), query.get("dog"), query.get("user")));
        response.setContentType("text");

        //addBooking
        if(query.get("qType") == "addBooking") {
            mapper.writeValue(response.getOutputStream(), QueryHelper.addBooking(bookCol, dogCol, userCol, query.get("start"), query.get("end"), query.get("dog"), query.get("user")));
        }
        //cancelBooking
        else { //if(query.get("qType") == "cancelBooking") {
            mapper.writeValue(response.getOutputStream(), QueryHelper.cancelBooking(bookCol, query.get("bookId")));
        }
    }
}

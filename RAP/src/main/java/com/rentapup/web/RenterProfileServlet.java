package com.rentapup.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.rentapup.web.obj.Booking;
import com.rentapup.web.obj.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
        String id = query.get("id");

        DBCollection renterCol = ((MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT"))
                .getDB("rapTest").getCollection("renterData");
        DBCollection bookingCol = ((MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT"))
                .getDB("rapTest").getCollection("bookingData");
        DBCollection dogCol = ((MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT"))
                .getDB("rapTest").getCollection("dogData");
        Query<String, Object> renterData = QueryHelper.renterProfile(renterCol, id);
        Query<String, ArrayList> bookings = QueryHelper.getRenterBookings(bookingCol, id);
        renterData.put("bookings", bookings.get("1"));
        renterData.put("dogNames", QueryHelper.getDogNames(dogCol, bookings.get("2")));
//        System.out.println(bookings.get("2").toString() + renterData);
        System.out.println(renterData);
        response.setContentType("text");
        mapper.writeValue(response.getOutputStream(), renterData);
    }
}

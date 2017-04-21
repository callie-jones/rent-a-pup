package com.rentapup.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.rentapup.web.obj.Query;
import com.rentapup.web.obj.UserAuthData;

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
@WebServlet("/auth")
public class UserAuthServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json;
        json = br.readLine();

        ObjectMapper mapper = new ObjectMapper();
        Query<String, String> query = mapper.readValue(json, new TypeReference<Query<String, String>>(){});

        System.out.print("User : ");
        System.out.println(query.get("user"));
        System.out.print("Password : ");
        System.out.println(query.get("pass"));

        DBCollection authCol = ((MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT"))
                .getDB("rapTest").getCollection("authData");
        System.out.println(UserAuthData.testAuth(authCol, query.get("user"), query.get("pass")));
        response.setContentType("text");
        mapper.writeValue(response.getOutputStream(), UserAuthData.testAuth(authCol, query.get("user"), query.get("pass")));
    }
}

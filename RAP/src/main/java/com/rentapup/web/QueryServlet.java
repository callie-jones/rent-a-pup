package com.rentapup.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentapup.web.obj.Query;

import com.mongodb.*;
import java.net.UnknownHostException;
import com.rentapup.web.obj.UserAuthData;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;


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

    static private DB database = getClient().getDB("testPet");
    static private DBCollection authData = database.getCollection("authData");
    private static MongoClient getClient(){
        try {
            MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
            return client;
        } catch (UnknownHostException e) { System.out.println(e); }
        return null;
    }

    private static Boolean isConnected(){
        try{
            MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
            DB database = client.getDB("testPet");
            DBCollection petData = database.getCollection("petData");
            DBCollection authData = database.getCollection("authData");
            return true;
        } catch (UnknownHostException e) { System.out.println(e); }
        return false;
    }

    public String testAuth(String username, String password){
        if(isConnected()) {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return "Failed1";
            }
            //String passwordstr = "jones";
            byte[] passwordHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            DBObject query = QueryBuilder.start("username").is(username).and("passwordHash").is(passwordHash).get();
            //query.put("name", "callie");
            DBCursor cursor = authData.find(query);
            if(cursor != null){
                //System.out.println(cursor.next());
                return "Successful Authentication";
            }
        }
        return "Failed";

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        json = br.readLine();

        ObjectMapper mapper = new ObjectMapper();
        Query query = mapper.readValue(json, Query.class);
        System.out.print("User : ");
        System.out.println(query.getUser());
        System.out.print("Password : ");
        System.out.println(query.getPass());
        response.setContentType("text");
        mapper.writeValue(response.getOutputStream(), testAuth(query.getUser(), query.getPass()));
    }
}

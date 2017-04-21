package com.rentapup.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
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
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by elijahstaple on 4/16/17.
 */
@WebServlet("/test")
public class QueryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static private DB database = getClient().getDB("rapTest");
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
                return null;
            }

            byte[] passwordHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            DBObject searchquery = QueryBuilder.start("username").is(username)
                    .and("passwordHash").is(passwordHash).get();
            DBCursor cursor = authData.find(searchquery, QueryBuilder.start("_id").is(0).and("userId").is(1).get());

            if(cursor.count() == 1){
                return ((ObjectId) cursor.next().get("userId")).toHexString();
            }
            return null;
        }
        return null;
    }

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
        System.out.println(testAuth(query.get("user"), query.get("pass")));
        response.setContentType("text");
        mapper.writeValue(response.getOutputStream(), testAuth(query.get("user"), query.get("pass")));
    }
}

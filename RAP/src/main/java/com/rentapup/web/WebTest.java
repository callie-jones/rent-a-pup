package com.rentapup.web;

import java.applet.*;
import com.mongodb.*;

import javax.swing.*;
import java.awt.*;
import java.net.UnknownHostException;



/**
 * Created by callie on 4/16/17.
 */

public class WebTest extends Applet {
    /*static private DB database = getClient().getDB("testPet");

    //create collections
    static private DBCollection petData = database.getCollection("petData");
    static private DBCollection userData = database.getCollection("userData");
    static private DBCollection testAuth = database.getCollection("testAuth");

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
            DBCollection testAuth = database.getCollection("testAuth");
            return true;

        } catch (UnknownHostException e) { System.out.println(e); }
        return false;
    }


    //TODO get user's password hash
    public static String createUser(String name, String email, String password){
        if(isConnected()){
            BasicDBObject doc = new BasicDBObject();
            doc.put("name", name);
            doc.put("password", password);
            doc.put("email", email);
            testAuth.insert(doc);
            return "Success";
        }
        return "Failed";
    }

    public static String verifyLogin(String name, String password){
        if(isConnected()) {
            DBObject query = QueryBuilder.start("name").is(name).and("password").is(password).get();
            //query.put("name", "callie");
            DBCursor cursor = testAuth.find(query);
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        }
        return "Failed";
    }

    public static void test(){
        System.out.println("Yo");
    }


   /* public static void main(String[] args){
        test();
    }
*/
    //TODO add web query stuff

    TextArea textBox;

    public void init(){
        setLayout(new FlowLayout());
        textBox = new TextArea(5,40);
        add(textBox);
    }

    public void appendText(String text){
        textBox.append(text);
        System.out.println("testing this ");

    }
    public static void test(){
        System.out.println("Yo");
    }


}
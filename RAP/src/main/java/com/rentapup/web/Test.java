package com.rentapup.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.rentapup.web.obj.Dog;
import com.rentapup.web.obj.Renter;
import com.rentapup.web.obj.UserAuthData;

import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by elijahstaple on 4/14/17.
 * Added testing for renter - calvin 4/15/17
 */
public class Test {
    public static void main(String args[]) {
        if(!TestDB.initialize()) {
            System.out.println("Failed to populate database.");
            return;
        }

        MongoClient client;
        try {
            client = new MongoClient(new ServerAddress("localhost", 27017));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        DBCollection petData = client.getDB("testPet").getCollection("petData");
        DBCollection renterData = client.getDB("testPet").getCollection("renterData");
        DBCollection authData = client.getDB("testPet").getCollection("authData");
        Dog testProfile = new Dog("German Shepard", "Don", 14, "Fun doggy and such",
                System.getProperty("user.dir") + "/assets/images/tiny.png");
        Renter testRenter = new Renter("Jimmy", 32, "Uh, I'm a renter lol",
                System.getProperty("user.dir") + "/assets/images/tiny.png");
        String jimmyPassword = "longassjimmy";
        UserAuthData testAuthData = new UserAuthData("jimmyjim", jimmyPassword, testRenter.getid());
        petData.insert(testProfile);
        renterData.insert(testRenter);
        authData.insert(testAuthData);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "Don");

        BasicDBObject renterQuery = new BasicDBObject();
        renterQuery.put("name", "Jimmy");

        BasicDBObject authQuery = new BasicDBObject();
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        }
        byte[] passwordHash = digest.digest(jimmyPassword.getBytes(StandardCharsets.UTF_8));
        authQuery.put("passwordHash", passwordHash);
        authQuery.put("username", "jimmyjim");
        authQuery.put("userId", testRenter.getid());

        DBCursor cursor = petData.find(searchQuery);
        while(cursor.hasNext()){
            Dog dog = new Dog(cursor.next());
            searchQuery = new BasicDBObject();
            searchQuery.put("_id", dog.getid());
            dog.setbreed("Lab");
            petData.update(searchQuery, dog);
            dog.getprofileImageFile(System.getProperty("user.dir") + "/assets/images/tiny_2.png");
            System.out.println(dog.gettype());
            System.out.println(dog.getid());
            System.out.println(dog.getid().getDate());
        }

        DBCursor search = renterData.find(renterQuery);
        while(search.hasNext()){
            Renter renti = new Renter(search.next());
            renterQuery = new BasicDBObject();
            renterQuery.put("_id", renti.getid());
            renterData.update(renterQuery, renti);
            renti.getprofileImageFile(System.getProperty("user.dir") + "/assets/images/tiny_3.png");
            System.out.println(renti.gettype());
            System.out.println(renti.getid());
            System.out.println(renti.getid().getDate());
        }

        if(authData.find(authQuery).count() == 1) System.out.println("Authorization successful!");
        else System.out.println("Authorization failed :(");

        try {
            System.out.println(new ObjectMapper().writeValueAsString(QueryHelper.renterProfile(renterData, testRenter.getid().toHexString())));
        } catch (JsonProcessingException e) {
            System.out.println(QueryHelper.renterProfile(renterData, testRenter.getid().toHexString()));
        }
    }
}

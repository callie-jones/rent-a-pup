package com.rentapup.web;

import com.mongodb.*;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by elijahstaple on 4/14/17.
 * Added testing for renter - calvin 4/15/17
 */
public class Test {
    public static void main(String args[]) throws IOException {
        MongoClient client;
        try {
            client = new MongoClient(new ServerAddress("localhost", 27017));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        DBCollection petData = client.getDB("testPet").getCollection("petData");
        DBCollection renterData = client.getDB("testPet").getCollection("renterData");
        Dog testProfile = new Dog("German Shepard", "Don", 14, "Fun doggy and such",
                System.getProperty("user.dir") + "/assets/images/tiny.png");
        Renter testRenter = new Renter("Jimmy", 32, "Uh, I'm a renter lol",
                System.getProperty("user.dir") + "/assets/images/tiny.png");
        petData.insert(testProfile);
        renterData.insert(testRenter);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "Don");

        BasicDBObject renterQuery = new BasicDBObject();
        renterQuery.put("name", "Jimmy");

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
    }
}

package org.kodejava.example.mongodb;

import com.mongodb.*;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by elijahstaple on 4/14/17.
 * Added testing for renter - calvin 4/15/17
 */
public class Tester {
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
        searchQuery.put("Name", "Don");

        BasicDBObject renterQuery = new BasicDBObject();
        renterQuery.put("Name", "Jimmy");

        DBCursor cursor = petData.find(searchQuery);
        while(cursor.hasNext()){
            Dog dog = new Dog(cursor.next());
            searchQuery = new BasicDBObject();
            searchQuery.put("_id", dog.getId());
            dog.setBreed("Lab");
            petData.update(searchQuery, dog);
            dog.getProfileImageFile(System.getProperty("user.dir") + "/assets/images/tiny_2.png");
            System.out.println(dog.getType());
            System.out.println(dog.getId());
            System.out.println(dog.getId().getDate());
        }

        DBCursor search = renterData.find(renterQuery);
        while(search.hasNext()){
            Renter renti = new Renter(search.next());
            renterQuery = new BasicDBObject();
            renterQuery.put("_id", renti.getId());
            renterData.update(renterQuery, renti);
            renti.getProfileImageFile(System.getProperty("user.dir") + "/assets/images/tiny_3.png");
            System.out.println(renti.getType());
            System.out.println(renti.getId());
            System.out.println(renti.getId().getDate());
        }
    }
}

package org.kodejava.example.mongodb;

import com.mongodb.*;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by elijahstaple on 4/14/17.
 */
public class Tester {
    public static void main(String args[]) throws IOException {
        MongoClient client = null;
        try {
            client = new MongoClient(new ServerAddress("localhost", 27017));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        DBCollection petData = client.getDB("testPet").getCollection("petData");
        Dog testProfile = new Dog("German Shepard", "Don", 14, "Fun doggy and such",
                System.getProperty("user.dir") + "/assets/images/dog.jpg");
        petData.insert(testProfile);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("Name", "Don");

        DBCursor cursor = petData.find(searchQuery);
        while(cursor.hasNext()){
            (new Dog((BasicDBObject) cursor.next())).getProfileImageFile(System.getProperty("user.dir") + "/assets/images/dog_2.jpg");
        }
    }
}

package com.rentapup.web;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Created by elijahstaple on 4/15/17.
 */
public abstract class TestDB {

    public static boolean initialize() {
        MongoClient client = clientConnect();
        if(client == null) return false;

        DB testDB = client.getDB("rapTest");
        testDB.dropDatabase();

        DBCollection dogData = testDB.getCollection("dogData");
        DBCollection renterData = testDB.getCollection("renterData");
        DBCollection authData = testDB.getCollection("authData");
        DBCollection bookingData = testDB.getCollection("bookingData");

        fillCollections(dogData, renterData, authData, bookingData);

        return true;
    }

    public static MongoClient clientConnect() {
        try {
            return new MongoClient(new ServerAddress("localhost", 27017));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void fillCollections(DBCollection dogData, DBCollection renterData, DBCollection authData,
            DBCollection bookingData) {//, ArrayList<ArrayList> dogs, ArrayList<ArrayList> renters) {

        ArrayList<ArrayList> dogs = new ArrayList<>();
        dogs.add(new ArrayList<String>(Arrays.asList("Golden Retriever", "Lab", "Poodle", "German Shepard",
                "Husky", "Pug", "Beagle", "Boxer", "Chihuahua", "Border Collie", "Bailey", "Bella", "Charlie",
                "Molly")));
        dogs.add(new ArrayList<String>(Arrays.asList("Max", "Lucy", "Duke", "Bailey", "Bella", "Charlie", "Molly",
                "Buddy", "Daisy", "Rocky", "Maggie", "Zoe", "Jake", "Sophie", "Jack", "Sadie", "Toby", "Chloe", "Cody",
                "Bailey", "Buster", "Lola", "Copper", "Abby", "Riley")));
        dogs.add(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));

        ArrayList<ArrayList> renters = new ArrayList<>();
        renters.add(new ArrayList<String>(Arrays.asList("James", "Jennifer", "Doug", "Kelly", "Zach", "Anna", "Ben",
                "Sarah", "Jake", "Carly", "Dan", "Jayne", "Tim", "Laura", "Jack", "Timothy", "Sophie", "Hannah",
                "Elias", "Callie", "Calvin", "Elena", "Austin", "Xandra", "Leroy")));
        renters.add(new ArrayList<Integer>(Arrays.asList(21, 18, 22, 20, 21, 19, 27, 36, 19, 20)));
        renters.add(new ArrayList<String>(Arrays.asList("jamesk", "jenniferc", "dougs", "kellyg", "zachw", "annak",
                "benw", "sarahc", "jakew", "carlyi", "danp", "jayneo", "timn", "lauram", "jackv", "timothyj", "sophiep",
                "hannahs", "eliaso", "calliej", "calvins", "elenaz", "austinm", "xandrar", "leroyj")));

        for(int i = 0; i < 25; i++) {
            Dog testDog = new Dog(((ArrayList<String>) dogs.get(0)).get((int)(Math.random() * 14)),
                    ((ArrayList<String>) dogs.get(1)).get(i),
                    ((ArrayList<Integer>) dogs.get(2)).get((int)(Math.random() * 10)), "I'M A DAWWWG",
                    System.getProperty("user.dir") + "/assets/images/tiny.png");
            dogData.insert(testDog, WriteConcern.ACKNOWLEDGED);
            Renter testRenter = new Renter(((ArrayList<String>) renters.get(0)).get(i),
                    ((ArrayList<Integer>) renters.get(1)).get((int)(Math.random() * 10)), "I'm a renter lol",
                    System.getProperty("user.dir") + "/assets/images/tiny.png");
            renterData.insert(testRenter, WriteConcern.ACKNOWLEDGED);
            UserAuthData testAuthData = new UserAuthData(((ArrayList<String>) renters.get(2)).get(i),
                    UUID.randomUUID().toString(), testRenter.getid());
            authData.insert(testAuthData, WriteConcern.ACKNOWLEDGED);
            long time = System.currentTimeMillis() + 1800 * i * 1000;
            Booking booking = new Booking(new Date(time), new Date(time + 7200 * 1000), testDog.getid(), testRenter.getid());
            bookingData.insert(booking, WriteConcern.ACKNOWLEDGED);
        }
    }
}

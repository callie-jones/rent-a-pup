package org.kodejava.example.mongodb;

import com.mongodb.*;
import java.util.Date;
import java.net.UnknownHostException;

/**
 * Hello world!
 *
 */
public class TestPuppy
{
//    //create new db connection
//    MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

    //creates the database
    //TODO : handle if getClient returns null
    DB database = getClient().getDB("testPet");

    //create collections
    DBCollection petData = database.getCollection("petData");
    DBCollection userData = database.getCollection("userData");

    public MongoClient getClient(){
        try {
            MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
            return client;
        } catch (UnknownHostException e) { System.out.println(e); }

        return null;
    }

    public Boolean isConnected(){
        try{
            MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
            DB database = client.getDB("testPet");
            DBCollection petData = database.getCollection("petData");
            return true;

        } catch (UnknownHostException e) { System.out.println(e); }
        return false;
    }

    //Admin to add puppy to database
    public String insertPet(String name, int age, String status, String appt, String info) {

        if(isConnected()) {

            //BASIC insert data
            //TODO : handle if puppy already in db - dont allow duplicate doc (return "already exists")
            BasicDBObject doc = new BasicDBObject();
            doc.put("name", name);
            doc.put("age", age);
            doc.put("status", status);
            doc.put("appts", appt); //initially empty
            doc.put("info", info);
            petData.insert(doc);

            return ("Success");

        } else { return ("Failed"); }
    }

    //Admin to remove puppy from database
    public String removePet(){
        return "";

    }

    //Admin to change puppy status
    public String changeStatus(){
        return "";
    }

    //User to schedule puppy


    public static void main( String[] args) {

        //idk what we should do here

}



//  INITIAL TESTING
//    public static void main( String[] args )
//    {
//        try {
//
//            //INITIAL DB SETUP
//            //create new db connection
//            MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
//
//            //creates the database
//            DB database = client.getDB("testPet");
//
//            //create collection
//            DBCollection petData = database.getCollection("petData");
//            DBCollection userData = database.getCollection("userData");
//
//
//            //insert data
//            //insertData(petData, userData);
//            BasicDBObject doc = new BasicDBObject();
//            doc.put("name", "callie");
//            doc.put("age", 21);
//            doc.put("createDate", new Date());
//            petData.insert(doc);
//
//            BasicDBObject searchQuery = new BasicDBObject();
//            searchQuery.put("name", "callie");
//
//            DBCursor cursor = petData.find(searchQuery);
//            while(cursor.hasNext()){
//                System.out.println(cursor.next());
//            }
//
//            //update
//            BasicDBObject query = new BasicDBObject();
//            query.put("name", "callie");
//
//            BasicDBObject newDoc = new BasicDBObject();
//            newDoc.put("name", "callie-updated");
//            BasicDBObject updateObj = new BasicDBObject();
//            updateObj.put("$set", newDoc);
//
//            petData.update(query, updateObj);
//
//
//
//
//            //prints out the document
//            System.out.println(doc);
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (MongoException e) {
//            e.printStackTrace();
//        }
//        //System.out.println( "Hello World!" );
//    }
}

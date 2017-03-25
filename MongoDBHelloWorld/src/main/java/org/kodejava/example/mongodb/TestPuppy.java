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
    static private DB database = getClient().getDB("testPet");

    //create collections
    static private DBCollection petData = database.getCollection("petData");
    static private DBCollection userData = database.getCollection("userData");

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
            return true;

        } catch (UnknownHostException e) { System.out.println(e); }
        return false;
    }

    //Admin to add puppy to database
    public static String addPup(String name, int age, String status, String apt, String info) {

        if(isConnected()) {
            //BASIC insert data
            //TODO : handle if puppy already in db - dont allow duplicate doc (return "already exists")
            BasicDBObject doc = new BasicDBObject();
            doc.put("name", name);
            doc.put("age", age);
            doc.put("status", status);
            doc.put("apts", apt); //initially empty
            doc.put("info", info);
            petData.insert(doc);

            return ("Success");

        } else { return ("Failed"); }
    }

    //Admin to remove puppy from database
    public static String removePup(String name){
        if(isConnected()){
            BasicDBObject delDoc = new BasicDBObject();
            delDoc.put("name", name);
            //removes doc with specified name
            petData.remove(delDoc);

            return "Success";
        }
        return "Failed";
    }

    //Admin to change puppy status
    public static String changeStatus(String name, String change){
        if(isConnected()){
            BasicDBObject oldStat = new BasicDBObject();
            oldStat.put("name", name);

            BasicDBObject newStat = new BasicDBObject();
            newStat.put("status", change);

            BasicDBObject updateDoc = new BasicDBObject();
            updateDoc.put("$set", newStat);

            //updates the status of the doc with the matching pup name
            petData.update(oldStat, updateDoc);
            return "Success";
        }
        return "Failed";
    }

    //User to schedule puppy
    public static String addApts(String name, String newApt){
        if(isConnected()){
            BasicDBObject oldApt = new BasicDBObject();
            oldApt.put("name", name);

            BasicDBObject addApt = new BasicDBObject();
            addApt.put("status", newApt);

            BasicDBObject updateDoc = new BasicDBObject();
            updateDoc.put("$set", addApt);

            //updates the apt of the doc with the matching pup name
            petData.update(oldApt, updateDoc);
            return "Success";
        }
        return "Failed";
    }

    //stor user info to userData collection in db
    public static String addUser(String name, String email, String password){
        if(isConnected()){
            BasicDBObject doc = new BasicDBObject();
            doc.put("name", name);
            doc.put("password", password);
            doc.put("email", email);
            userData.insert(doc);
            return "Success";
        }
        return "Failed";
    }


    public static void main( String[] args) {

        //just testing rn
        //add to /etc/mongod.conf
        //net:
        //   http:
        //      enabled: true
        //      RESTInterfaceEnabled: true

        //if mongod running data displayed at http://localhost:28017/testPet/petData

        //displaying docs where name = callie
        String complete = addPup("callie", 21, "null", "", "super cool");
        if(complete == "Success"){

            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("name", "callie");

            DBCursor cursor = petData.find(searchQuery);
            while(cursor.hasNext()){
                System.out.println(cursor.next());
            }
        }

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
//            DB database = client.getDB("testDB");
//
//            //create collection
//            DBCollection petData = database.getCollection("user");
//            //DBCollection userData = database.getCollection("userData");
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

package com.rentapup.web;

import com.mongodb.*;
import com.rentapup.web.obj.Booking;
import com.rentapup.web.obj.Dog;
import com.rentapup.web.obj.Query;
import com.rentapup.web.obj.Renter;
import org.bson.types.ObjectId;

import java.awt.print.Book;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * Created by elijahstaple on 4/22/17.
 */
class QueryHelper {
    static String testAuth(DBCollection authData, String username, String password){
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

    static String addBooking(DBCollection bookingData, DBCollection dogData, DBCollection renterData, String start, String end, String dog, String user){

        ObjectId dogId = DBHandling.getDogId(dogData, dog);
        ObjectId renterId = DBHandling.getRenterId(renterData, user);
        Date startDate = DBHandling.getDate(start);
        Date endDate = DBHandling.getDate(end);

        if(dogAvailable(dogData, bookingData, dog, start, end) == "Success") {
            Booking booking = new Booking(startDate, endDate, dogId, renterId);
            bookingData.insert(booking);
            return "Success";
        }
        return null;
    }

    static String dogAvailable(DBCollection dogData, DBCollection bookingData, String dogName, String start, String end){
        ObjectId dogId = DBHandling.getDogId(dogData, dogName);
        Date startDate = DBHandling.getDate(start);
        Date endDate = DBHandling.getDate(end);

        //start time occurs during existing booking
        DBObject sdm = QueryBuilder.start("startTime").lessThan(startDate).and("endTime").greaterThan(startDate).and("dogId").is(dogId).get();
        //existing booking ends during attempted booking
        DBObject etm = QueryBuilder.start("endTime").greaterThan(startDate).and("endTime").lessThan(endDate).and("dogId").is(dogId).get();
        //existing booking starts during attempted booking
        DBObject stm = QueryBuilder.start("startTime").greaterThan(startDate).and("startTime").lessThan(endDate).and("dogId").is(dogId).get();
        //end time occurs during existing booking
        DBObject edm = QueryBuilder.start("startTime").lessThan(startDate).and("endTime").greaterThan(startDate).and("dogId").is(dogId).get();

        DBCursor sdmcursor = bookingData.find(sdm);
        DBCursor etmcursor = bookingData.find(etm);
        DBCursor edmcursor = bookingData.find(edm);
        DBCursor stmcursor = bookingData.find(stm);
        if(sdmcursor.count() > 0 || edmcursor.count() > 0 || stmcursor.count() > 0 || etmcursor.count() > 0 ){
            return null;
        }
        return "Success";
    }

    static List<String> searchByDog(DBCollection bookingData, DBCollection dogData, String dogName){
        /*//search available bookings for given dog or available dogs for given start time
        ObjectId dogId = DBHandling.getDogId(dogData, dogName);
        DBObject search = QueryBuilder.start("dogId").is(dogId).get();
        DBCursor cursor = bookingData.find(search, QueryBuilder.start("_id").is(0).and("startTime").is(1).and("endTime").is(1).get()).sort(new BasicDBObject("startTime", 1));
        List<Date> startTimes = new ArrayList<Date>();
        List<Date> endTimes = new ArrayList<Date>();
        List<String> availableStart = new ArrayList<>();
        List<String> availableEnd = new ArrayList<>();

        while(cursor.hasNext()){
            System.out.println(cursor.next());

            Date start = (Date) cursor.curr().get("startTime");
            Date end = (Date) cursor.curr().get("endTime");
            startTimes.add(start);
            endTimes.add(end);

            System.out.println(startTimes);
            System.out.println(endTimes);
        }
        //compare dates to get availability
        long beginTime = 10;
        long finalTime = 20;
        Date first = startTimes.get(0);
        first.setHours(10);
        first.setMinutes(00);
        first.setSeconds(00);

        long before = startTimes.get(0).getTime() - beginTime;
        long last = finalTime - endTimes.get(endTimes.size()-1).getTime();

        System.out.print("before:::");
        System.out.println(before);

        System.out.print("first-time-set:::");
        System.out.println(first);
        if(before > 60){
            availableStart.add(first.toString());
            System.out.println(availableStart);
            //availableEnd.add(startTimes.get(0));
        }

        for (int i=1; i<startTimes.size(); i++){
            Date sTime = startTimes.get(i);
            Date eTime = endTimes.get(i-1);
            long diff = sTime.getTime() - eTime.getTime();
            long diffMins = TimeUnit.MILLISECONDS.toMinutes(diff);
            //.setTime(eTime.getTime())
            System.out.println(sTime);
            System.out.println(eTime);
            System.out.println(diffMins);
            System.out.println(eTime.getDate());
            //System.out.println(eTime.getDay());
            System.out.println(sTime.getDate());
            System.out.println("test");
            System.out.println(diffMins);
            System.out.println(eTime.getDate());
            System.out.println(sTime.getDate());
            System.out.println("end");

            if(diffMins > 60 && eTime.getDate() == sTime.getDate()) { //
                Date aStart = eTime; //+15min
                Date aEnd = sTime; //-15 min
                System.out.println("aStart"); //add to list
                System.out.println(aStart);
                System.out.println(aEnd);
                //availableStart.add(sTime);
               // availableEnd.add(eTime);
            }

        }

        return availableStart;*/ return null;
    }

    static String cancelBooking(DBCollection bookingData, String bookingId){
        ObjectId id = new ObjectId(bookingId);
        DBObject searchquery = QueryBuilder.start("_id").is(id).get();
        DBObject remove = bookingData.find(searchquery).one();
        DBCursor cursor = bookingData.find(searchquery);
        System.out.print(remove);
        return null;
    }

    static String searchByTime(DBCollection bookingData, DBCollection dogData, String dog, String start, String end){

        if(dogAvailable(dogData, bookingData, dog, start, end) == "Success"){
            String returning = String.format("The dog %s is available from %s to %s", dog, DBHandling.getDate(start), DBHandling.getDate(end));
            return returning;
        }

        return null;
    }

    static String newRenter(DBCollection col, String name, int age) {
        Renter renter = new Renter(name, age, "", "/assets/images/default.png");
        col.insert(renter);
        return renter.getid().toHexString();
    }

    static Query<String, Object> renterProfile(DBCollection col, String id) {
        Query<String, Object> results;
        DBObject query = QueryBuilder.start("_id").is(new ObjectId(id)).and("type").is(2).get();
        DBCursor cursor = col.find(query);
        if(cursor.count() == 1){
            Renter renter =  new Renter(cursor.next());
            results = new Query<String, Object>();
            results.put("id", renter.getid().toHexString());
            results.put("name", renter.getname());
            results.put("age", renter.getage());
            results.put("description", renter.getdescription());
            results.put("location", renter.getlocation().toString());
        } else {
            results = null;
        }
        return results;
    }

    static Query<String, ArrayList> getRenterBookings(DBCollection bookingData, String renterId) {
        Query<String, ArrayList> results = new Query<>();
        ArrayList<String> res1 = new ArrayList<>();
        ArrayList<String> res2 = new ArrayList<>();
        DBObject searchquery = QueryBuilder.start("renterId").is(renterId).get();
        DBCursor cursor = bookingData.find(searchquery);
        while(cursor.hasNext()) {
            System.out.println(((ObjectId) cursor.next().get("dogId")).toHexString());
            res1.add(cursor.curr().toString());
            res2.add(((ObjectId) cursor.curr().get("dogId")).toHexString());
        }
        results.put("1", res1);
        results.put("2", res2);
        return results;
    }

    static ArrayList<String> getDogNames(DBCollection dogData, ArrayList<String> bookings) {
        ArrayList<String> results = new ArrayList<>();
        for(String booking : bookings) {
            DBObject searchquery = QueryBuilder.start("_id").is(new ObjectId(booking)).get();
            DBCursor cursor = dogData.find(searchquery);
            if (cursor.hasNext())
                results.add(((BasicDBObject) cursor.next()).getString("name"));
        }
        return results;
    }
}

package com.rentapup.web;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.rentapup.web.obj.Booking;
import com.rentapup.web.obj.Query;
import com.rentapup.web.obj.Renter;
import org.bson.types.ObjectId;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by callie on 4/27/17.
 */
public class DBHandling {

    static ObjectId getDogId(DBCollection dogData, String dogName){

        DBObject searchquery = QueryBuilder.start("name").is(dogName).get();
        DBCursor dogcursor = dogData.find(searchquery);
        ObjectId dogId = (ObjectId) dogcursor.one().get("_id");
        return dogId;
    }

    static ObjectId getRenterId(DBCollection renterData, String renterName){

        DBObject searchquery = QueryBuilder.start("name").is(renterName).get();
        DBCursor rentercursor = renterData.find(searchquery);
        ObjectId renterId = (ObjectId) rentercursor.one().get("_id");
        return renterId;
    }

    static Date getStartDate(String start){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd h:mm");
        try {
            Date startDate = formatter.parse(start);
            return startDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Date getEndDate(String end){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd h:mm");
        try {
            Date endDate = formatter.parse(end);
            return endDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}

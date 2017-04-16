package com.rentapup.web;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.ArrayList;

/**
 * Created by elijahstaple on 4/15/17.
 */
public abstract class Location {
    public static BasicDBObject newLocation() {
        ArrayList<Double> loc = new ArrayList<>();
        loc.add(11.0);
        loc.add(10.0);
        BasicDBObject location = new BasicDBObject();
        location.append("type", "Point");
        location.append("coordinates", loc);
        return location;
    }
}

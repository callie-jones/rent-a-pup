package com.rentapup.web;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.ArrayList;

/**
 * Created by elijahstaple on 4/14/17.
 */
public class Dog extends Profile {

    private String breed;
    private BasicDBObject location;

    Dog(String breed, String name, int age, String description, String profileImagePathname) {
        super(Type.DOG, name, age, description, profileImagePathname);
        this.location = Location.newLocation();
        this.breed = breed;
        this.set_id(new ObjectId());
    }

    Dog(DBObject o) {
        super(o);
        BasicDBObject b = (BasicDBObject) o;
        this.breed = b.getString("breed");
        this.location = (BasicDBObject) b.get("location");
        this.set_id(b.getObjectId("_id"));
    }

    public String getbreed() {
        return breed;
    }

    public void setbreed(String breed) {
        this.breed = breed;
    }

    public ObjectId getid() {
        return (ObjectId) this.get_id();
    }

    public BasicDBObject getlocation() {
        return location;
    }

    public void setlocation(BasicDBObject location) {
        this.location = location;
    }
}

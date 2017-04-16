package com.rentapup.web;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.ReflectionDBObject;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by elijahstaple on 4/15/17.
 */
public class Booking extends ReflectionDBObject implements hasId, hasLocation {
    private int type;
    private Date startTime, endTime;
    private ObjectId dogId, renterId;
    private BasicDBObject location;

    Booking(Date startTime, Date endTime, ObjectId dogId, ObjectId renterId) {
        this.type = Type.BOOKING;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dogId = dogId;
        this.renterId = renterId;
        this.location = Location.newLocation();
        this.set_id(new ObjectId());
    }

    Booking(DBObject o) {
        BasicDBObject b = (BasicDBObject) o;
        this.type = b.getInt("type");
        this.startTime = b.getDate("startTime");
        this.endTime = b.getDate("endTime");
        this.dogId = b.getObjectId("dogId");
        this.renterId = b.getObjectId("renterId");
        this.location = (BasicDBObject) b.get("location");
        this.set_id(b.getObjectId("_id"));
    }

    public int gettype() {
        return type;
    }

    public void settype(int type) {
        this.type = type;
    }

    public ObjectId getid() {
        return (ObjectId) this.get_id();
    }

    public Date getstartTime() {
        return startTime;
    }

    public void setstartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getendTime() {
        return endTime;
    }

    public void setendTime(Date endTime) {
        this.endTime = endTime;
    }

    public ObjectId getdogId() {
        return dogId;
    }

    public void setdogId(ObjectId dogId) {
        this.dogId = dogId;
    }

    public ObjectId getrenterId() {
        return renterId;
    }

    public void setrenterId(ObjectId renterId) {
        this.renterId = renterId;
    }

    public BasicDBObject getlocation() {
        return location;
    }

    public void setlocation(BasicDBObject loc) {
        this.location = loc;
    }
}

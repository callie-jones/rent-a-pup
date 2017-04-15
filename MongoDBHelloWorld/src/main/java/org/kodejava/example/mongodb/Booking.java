package org.kodejava.example.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.ReflectionDBObject;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by elijahstaple on 4/15/17.
 */
public class Booking extends ReflectionDBObject implements hasId {
    private int type;
    private Date startTime, endTime;
    private ObjectId dogId, renterId;

    Booking(Date startTime, Date endTime, ObjectId dogId, ObjectId renterId) {
        this.type = Type.BOOKING;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dogId = dogId;
        this.renterId = renterId;
        this.set_id(new ObjectId());
    }

    Booking(DBObject o) {
        BasicDBObject b = (BasicDBObject) o;
        this.type = b.getInt("Type");
        this.startTime = b.getDate("StartTime");
        this.endTime = b.getDate("EndTime");
        this.dogId = b.getObjectId("DogId");
        this.renterId = b.getObjectId("RenterId");
        this.set_id(b.getObjectId("_id"));
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ObjectId getId() {
        return (ObjectId) this.get_id();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ObjectId getDogId() {
        return dogId;
    }

    public void setDogId(ObjectId dogId) {
        this.dogId = dogId;
    }

    public ObjectId getRenterId() {
        return renterId;
    }

    public void setRenterId(ObjectId renterId) {
        this.renterId = renterId;
    }
}

package com.rentapup.web.obj;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

/**
 * Created by calvin on 4/15/17.
 */
public class Renter extends Profile {

    private BasicDBObject location;

    public Renter(String name, int age, String description, String photo){
        super(Type.RENTER, name, age, description, photo);
        this.location = Location.newLocation();
        this.set_id(new ObjectId());
    }

    public Renter(DBObject o) {
        super(o);
        BasicDBObject b = (BasicDBObject) o;
        this.location = (BasicDBObject) b.get("location");
        this.set_id(o.get("_id"));
    }

    public ObjectId getid() {
        return (ObjectId) this.get_id();
    }

    public BasicDBObject getlocation() {
        return this.location;
    }

    public void setlocation(BasicDBObject loc) {
        this.location = loc;
    }
}

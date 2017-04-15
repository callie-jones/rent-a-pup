package org.kodejava.example.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

/**
 * Created by calvin on 4/15/17.
 */
public class Renter extends Profile implements hasId{

    Renter(String name, int age, String description, String photo){
        super(Type.RENTER, name, age, description, photo);
        this.set_id(new ObjectId());
    }

    Renter(DBObject o) {
        super(o);
        this.set_id(o.get("_id"));
    }

    public ObjectId getId() {
        return (ObjectId) this.get_id();
    }
}

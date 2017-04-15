package org.kodejava.example.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

/**
 * Created by elijahstaple on 4/14/17.
 */
public class Dog extends Profile {

    private String breed;

    Dog(String breed, String name, int age, String description, String profileImagePathname) {
        super(Type.DOG, name, age, description, profileImagePathname);
        this.breed = breed;
        this.set_id(new ObjectId());
    }

    Dog(DBObject o) {
        super(o);
        BasicDBObject b = (BasicDBObject) o;
        this.breed = b.getString("Breed");
        this.set_id(o.get("_id"));
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public ObjectId getId() {
        return (ObjectId) this.get_id();
    }
}

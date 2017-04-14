package org.kodejava.example.mongodb;

import com.mongodb.BasicDBObject;

/**
 * Created by elijahstaple on 4/14/17.
 */
public class Dog extends Profile {

    private String breed;

    Dog(String breed, String name, int age, String description, String profileImagePathname) {
        super(name, age, description, profileImagePathname);
        this.breed = breed;
    }

//    Dog(String breed, String name, int age, String description, dbImage profileDbImage) {
//        super(name, age, description, profileDbImage);
//        this.breed = breed;
//    }

    Dog(BasicDBObject o) {
        super(o);
        this.breed = o.getString("Breed");
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

//    Dog(BasicDBObject o) {
//        return new Dog(
//                o.getString("Breed"),
//                o.getString("Name"),
//                o.getInt("Age"),
//                o.getString("Description"),
//                (dbImage) o.get("ProfileImage"));
//    }
}

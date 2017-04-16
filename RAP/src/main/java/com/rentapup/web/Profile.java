package com.rentapup.web;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.ReflectionDBObject;

import java.io.File;
import java.util.Objects;

/**
 * Created by elijahstaple on 4/14/17.
 */
public abstract class Profile extends ReflectionDBObject implements hasId, hasLocation {
    private int type;
    private String name;
    private int age;
    private String description;
    private dbImage profileImage;

    Profile(int type, String name, int age, String description, String profileImagePathname) {
        this.type = type;
        this.name = name;
        this.age = age;
        this.description = description;
        this.profileImage = new dbImage(profileImagePathname);
        if(Objects.equals(profileImage.getimageByteString(), "")) {
            System.out.println("Error encoding image file. Retry upload.");
        }
    }

    Profile(DBObject o) {
        BasicDBObject b = (BasicDBObject) o;
        this.type = b.getInt("type");
        this.name = b.getString("name");
        this.age = b.getInt("age");
        this.description = b.getString("description");
        this.profileImage = new dbImage((DBObject) o.get("profileImage"));
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getage() {
        return age;
    }

    public void setage(int age) {
        this.age = age;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public dbImage getprofileImage() {
        return profileImage;
    }

    public int setprofileImage(String profileImagePathname) {
        int errorCode;
        if((errorCode = profileImage.replaceImage(profileImagePathname)) != 0) {
            //TODO: Throw an error because it failed to replace the image
            if(errorCode == -1) {
                System.out.println("Failed to encode and couldn't delete newly created file");
            } else {
                System.out.println("Failed to encode but successfully delete newly created file");
            }
        }
        return errorCode;
    }

    public File getprofileImageFile(String pathname) {
        File file = new File(pathname);
        if(profileImage.getImageFile(file) != 0) {
            System.out.println("Error decoding image file.");
        }
        return file;
    }

    public int gettype() {
        return type;
    }

    public void settype(int type) {
        this.type = type;
    }
}

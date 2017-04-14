package org.kodejava.example.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.ReflectionDBObject;

import java.io.File;

/**
 * Created by elijahstaple on 4/14/17.
 */
public abstract class Profile extends ReflectionDBObject {
    private String name;
    private int age;
    private String description;
    private dbImage profileImage;

    Profile(String name, int age, String description, String profileImagePathname) {
        this.name = name;
        this.age = age;
        this.description = description;
        this.profileImage = new dbImage(profileImagePathname);
        if(profileImage.isNull()) {
            System.out.println("Created file but failed while trying to encode image");
        }
    }

//    Profile(String name, int age, String description, dbImage profileDbImage) {
//        this.name = name;
//        this.age = age;
//        this.description = description;
//        this.profileImage = profileDbImage;
//    }

    Profile(BasicDBObject o) {
        this.name = o.getString("Name");
        this.age = o.getInt("Age");
        this.description = o.getString("Description");
        this.profileImage = new dbImage((BasicDBObject) o.get("ProfileImage"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public dbImage getProfileImage() {
        return profileImage;
    }

    public int setProfileImage(String profileImagePathname) {
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

    public File getProfileImageFile(String pathname) {
        return profileImage.getImageFile(pathname);
    }
}

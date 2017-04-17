package com.rentapup.web.obj;

import com.mongodb.ReflectionDBObject;
import org.bson.types.ObjectId;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by elijahstaple on 4/15/17.
 */
public class UserAuthData extends ReflectionDBObject {
    private byte[] passwordHash;
    private String username;
    private ObjectId userId;

    public UserAuthData(String username, String password, ObjectId userId) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        }
        this.passwordHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        this.username = username;
        this.userId = userId;
    }

    public byte[] getpasswordHash() {
        return passwordHash;
    }

    public void setpasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public ObjectId getuserId() {
        return userId;
    }

    public void setuserId(ObjectId userId) {
        this.userId = userId;
    }
}

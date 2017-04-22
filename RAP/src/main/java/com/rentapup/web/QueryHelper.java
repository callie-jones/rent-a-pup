package com.rentapup.web;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.rentapup.web.obj.Renter;
import org.bson.types.ObjectId;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by elijahstaple on 4/22/17.
 */
class QueryHelper {
    static String testAuth(DBCollection authData, String username, String password){
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] passwordHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        DBObject searchquery = QueryBuilder.start("username").is(username)
                .and("passwordHash").is(passwordHash).get();
        DBCursor cursor = authData.find(searchquery, QueryBuilder.start("_id").is(0).and("userId").is(1).get());

        if(cursor.count() == 1){
            return ((ObjectId) cursor.next().get("userId")).toHexString();
        }
        return null;
    }

    static String newRenter(DBCollection col, String name, int age) {
        Renter renter = new Renter(name, age, "", "/assets/images/default.png");
        col.insert(renter);
        return renter.getid().toHexString();
    }
}

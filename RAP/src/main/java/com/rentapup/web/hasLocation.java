package com.rentapup.web;

import com.mongodb.BasicDBObject;

/**
 * Created by elijahstaple on 4/15/17.
 */
public interface hasLocation {
    BasicDBObject getlocation();
    void setlocation(BasicDBObject loc);
}

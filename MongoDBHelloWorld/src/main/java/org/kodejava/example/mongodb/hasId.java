package org.kodejava.example.mongodb;

import org.bson.types.ObjectId;

/**
 * Created by elijahstaple on 4/14/17.
 */
public interface hasId {
    int getType();
    void setType(int type);
    ObjectId getId();
}

package com.rentapup.web.obj;

import org.bson.types.ObjectId;

/**
 * Created by elijahstaple on 4/14/17.
 */
public interface hasId {
    int gettype();
    void settype(int type);
    ObjectId getid();
}

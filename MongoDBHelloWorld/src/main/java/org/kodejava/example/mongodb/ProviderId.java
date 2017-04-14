package org.kodejava.example.mongodb;

/**
 * Created by elijahstaple on 4/14/17.
 */
public class ProviderId extends Id {
    IdType setType() {
        return IdType.PROVIDER;
    }
}

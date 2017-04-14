package org.kodejava.example.mongodb;

import com.mongodb.ReflectionDBObject;

import java.util.UUID;

/**
 * Created by elijahstaple on 4/14/17.
 */
public abstract class Id extends ReflectionDBObject {
    private final UUID uuid = generateId();
    private final IdType type = setType();

    private UUID generateId() {
        return java.util.UUID.randomUUID();
    }

    abstract IdType setType();

    public UUID getUuid() {
        return uuid;
    }

    public IdType getType() {
        return type;
    }
}

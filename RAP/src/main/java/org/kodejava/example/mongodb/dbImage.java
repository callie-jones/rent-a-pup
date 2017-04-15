package org.kodejava.example.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.ReflectionDBObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by elijahstaple on 4/14/17.
 */
public class dbImage extends ReflectionDBObject implements hasId {

    private int type;
    private String imageByteString;

    dbImage(String imagePathname) {
        this.type = Type.IMAGE;
        File file = new File(imagePathname);
        try
        {
            imageByteString = Base64.encode(FileUtils.readFileToByteArray(file));
        }
        catch (IOException e)
        {
            System.out.println("Error encoding image file: " + e);
            imageByteString = "";
        }
        finally
        {
            this.set_id(new ObjectId());
        }
    }

    dbImage(DBObject o) {
        BasicDBObject b = (BasicDBObject) o;
        this.type = b.getInt("Type");
        this.imageByteString = b.getString("ImageByteString");
        this.set_id(b.getObjectId("_id"));
    }

    public void setImageByteString(String i) {
        return;
    }

    public String getImageByteString() {
        return imageByteString;
    }

    // pathname: Where to save the created/returned file because we can't keep it in this object or the database as-is
    public int getImageFile(File file) {
        if(Objects.equals(imageByteString, "")) {
            System.out.println("File empty");

            return 0;
        }
        try
        {
            FileUtils.writeByteArrayToFile(file, Base64.decode(imageByteString));
            return 0;
        }
        catch (IOException e)
        {
            System.out.println("Error decoding image file: " + e);
            return -1;
        }
    }

    public int replaceImage(String imagePathname) {
        File file = new File(imagePathname);
        String tmp = imageByteString;
        try
        {
            imageByteString = Base64.encode(FileUtils.readFileToByteArray(file));
            return 0;
        }
        catch (IOException e)
        {
            System.out.println("Error encoding image file: " + e);
            imageByteString = tmp; // Set imgByteString back to it's previous value if failed

            // Successfully deleted newly created file after failed encoding
            if(file.delete()) {
                return 1;
            }

            // Could not delete new file after failed encoding, so it's still j chillin
            return -1;
        }
    }

    public boolean isNull() {
        return imageByteString == null;
    }

    public ObjectId getId() {
        return (ObjectId) this.get_id();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

package org.kodejava.example.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.ReflectionDBObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by elijahstaple on 4/14/17.
 */
public class dbImage extends ReflectionDBObject {

    private String imageByteString;

    dbImage(String imagePathname) {
        File file = new File(imagePathname);
        try {
            imageByteString = Base64.encode(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
            imageByteString = null;
        }
    }

    dbImage(BasicDBObject o) {
        this.imageByteString = o.getString("ImageByteString");
    }

    public void setImageByteString(String i) {
        return;
    }

    public String getImageByteString() {
        return imageByteString;
    }

    // pathname: Where to save the created/returned file because we can't keep it in this object or the database as-is
    public File getImageFile(String pathname) {
        File file = new File(pathname);
        try {
            FileUtils.writeByteArrayToFile(file, Base64.decode(imageByteString));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    public int replaceImage(String imagePathname) {
        File file = new File(imagePathname);
        try {
            imageByteString = Base64.encode(FileUtils.readFileToByteArray(file));
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            if(file.delete()) {
                // Successfully deleted newly created file after failed encoding
                return 1;
            }
            // Could not delete new file after failed encoding, so it's still j chillin
            return -1;
        }
    }

    public boolean isNull() {
        return imageByteString == null;
    }
}

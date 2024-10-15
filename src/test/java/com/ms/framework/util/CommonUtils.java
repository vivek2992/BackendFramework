package com.ms.framework.util;

import com.google.gson.JsonParser;
import com.ms.framework.world.CommonWorld;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.FileReader;
import java.io.IOException;

@Component
public class CommonUtils {
    @Autowired
    CommonWorld cw;

    public String readJSONData(String pathOfJSON, String testDataID){
        JsonObject jsonObject=null;
        try{
            Object obj = JsonParser.parseReader(new FileReader(pathOfJSON));
            jsonObject = (JsonObject)obj;
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
        Assert.assertNotNull(jsonObject);
        return jsonObject.getAsJsonObject(testDataID).toString();
    }

}

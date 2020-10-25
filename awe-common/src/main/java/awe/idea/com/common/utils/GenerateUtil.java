package awe.idea.com.common.utils;

import java.util.UUID;

public class GenerateUtil {

    public static String randomUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}

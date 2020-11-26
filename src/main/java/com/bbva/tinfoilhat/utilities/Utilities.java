package com.bbva.tinfoilhat.utilities;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;

public class Utilities {

    private static final Logger LOGGER = Logger.getLogger(Utilities.class);

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object object){
        if (object == null) return true;
        if (object instanceof String) return object.equals(((String)object).isEmpty());
        if (object instanceof List) return ((List)object).isEmpty();
        if (object instanceof Object[]) return ((Object[])object).length == 0;
        if (object instanceof Map) return ((Map)object).isEmpty();

        return false;
    }

    public static String toJson(Object object){
        LOGGER.info("Utilities.toJson.in");

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_EMPTY);

        String json = "{}";

        if (Utilities.isEmpty(object)){
            LOGGER.info("Utilities.toJson.out - Empty JSON");
            return json;
        }

        try{
            json = mapper.writeValueAsString(object);
        } catch (IOException excp){
            LOGGER.error("Utilities.toJson.out - Something went wrong during deserialization");
        }
        LOGGER.info("Utilities.toJson.out");
        return json;
    }

    public static <T> T toObject(String json, Class<T> targetClass){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        if (Utilities.isEmpty(json) || Utilities.isEmpty(targetClass)){
            LOGGER.warn("Utilities.toObject - Null");
            return null;
        }

        try{
            return (T) mapper.readValue(json, targetClass);
        }catch(IOException excp){
            LOGGER.error("Utilities.toObject - Null");
            return null;    
        }
    }
}
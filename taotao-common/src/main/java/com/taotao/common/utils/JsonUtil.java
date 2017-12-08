package com.taotao.common.utils;


  
import java.io.IOException;  
import java.util.List;  
  
import com.fasterxml.jackson.core.JsonProcessingException;  
import com.fasterxml.jackson.databind.JavaType;  
import com.fasterxml.jackson.databind.ObjectMapper;  
  
public class JsonUtil {  
      
    //定义jackson对象  
    private static final ObjectMapper mapper = new ObjectMapper();  
      
    /**  
     * 将对象转换成json字符串  
     * @param data  
     * @return  
     */  
    public static String objectToJson(Object data) {  
        try {  
            String json = mapper.writeValueAsString(data);  
            return json;  
        } catch (JsonProcessingException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    /**  
     * 将json串转化为对象  
     * @param jsonData  
     * @param objectType  
     * @return  
     */  
    public static <T> T jsonToObject(String jsonData, Class<T> objectType) {  
        try {  
            T t = mapper.readValue(jsonData, objectType);  
            return t;  
        }  catch (IOException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    /**  
     * 将json数据转换成pojo对象list  
     * <p>Title: jsonToObjectList</p>  
     * @param jsonData  
     * @param objectType  
     * @return  
     */  
    public static <T> List<T> jsonToObjectList(String jsonData, Class<T> objectType) {  
        try {  
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, objectType);  
            List<T> list = mapper.readValue(jsonData, javaType);  
            return list;  
        }  catch (IOException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
}  
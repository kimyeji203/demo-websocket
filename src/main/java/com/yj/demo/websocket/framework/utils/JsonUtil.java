package com.yj.demo.websocket.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil
{
    /**
     * Object를 Json String으로 변경한다.
     *
     * @param obj 변경할 Object
     * @return Json String
     */
    public static String parseJsonObject(Object obj) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializerProvider(new DefaultSerializerProvider.Impl());
        return mapper.writeValueAsString(obj);
    }

    /**
     * JsonString을 선언된 Type으로 변경한다.
     *
     * @param str       변경할 JsonString
     * @param classType 변경할 Type
     * @return <T>
     */
    public static <T> T parseString(String str, Class<T> classType) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(str, classType);
    }
}

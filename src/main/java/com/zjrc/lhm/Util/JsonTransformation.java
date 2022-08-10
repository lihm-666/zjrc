package com.zjrc.lhm.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjrc.lhm.Server.Entry.JsonObjectDome;

public class JsonTransformation {
    //bean转换为json对象
    public static JSONObject toJsonObject(String message, Object o){
        JsonObjectDome jsonObjectDome = new JsonObjectDome(message,o);
        String s = JSON.toJSONString(jsonObjectDome);
        JSONObject jsonObject = JSONObject.parseObject(s);
        return jsonObject;
    }
}

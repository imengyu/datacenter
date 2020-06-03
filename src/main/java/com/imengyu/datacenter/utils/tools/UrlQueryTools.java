package com.imengyu.datacenter.utils.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imengyu.datacenter.utils.encryption.Base64Utils;
import com.imengyu.datacenter.utils.request.RequestUtils;


public class UrlQueryTools {


  public static <T> T getObjectFromBase64Json(String base64Json, Class<T> c) {
    String json = Base64Utils.decode(base64Json);
    JSONObject jsonObject = JSON.parseObject(json);

    T instance = JSONObject.toJavaObject(jsonObject, c);
    return instance;
  }
  public static <T> T getObjectFromUrlJson(String urlJson, Class<T> c) {
    String json = RequestUtils.decoderURLString(urlJson);
    JSONObject jsonObject = JSON.parseObject(json);

    T instance = JSONObject.toJavaObject(jsonObject, c);
    return instance;
  }
}

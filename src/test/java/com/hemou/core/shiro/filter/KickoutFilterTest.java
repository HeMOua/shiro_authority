package com.hemou.core.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.hemou.common.utils.Result;
import org.junit.Test;

public class KickoutFilterTest {

    @Test
    public void test(){
        Result result = Result.error("您已经在其他地方登录，请重新登录！");
        String jsonString = JSONObject.toJSONString(result);
        System.out.println(jsonString);
    }
}
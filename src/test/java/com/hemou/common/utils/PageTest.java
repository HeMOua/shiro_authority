package com.hemou.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageTest {

    @Test
    public void getLastStart() {
        Page page = new Page(2, 5);
        page.setTotal(34);

        System.out.println(page.getTotalPage());
    }
}
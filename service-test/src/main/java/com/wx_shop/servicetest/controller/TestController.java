package com.wx_shop.servicetest.controller;


import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestController   {



    @SuppressWarnings("resource")
    public static void main(String[] args) {
            int i = 4;
            System.out.println(i);
            int b = i++;
            int a = ++i;
            System.out.println(b);
            System.out.println(a);
    }



}

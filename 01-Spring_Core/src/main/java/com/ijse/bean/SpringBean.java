package com.ijse.bean;

import org.springframework.stereotype.Component;

@Component
public class SpringBean {
    public SpringBean() {
        System.out.println("SpringBean Constructor Called");
    }

    public void test() {
        System.out.println("SpringBean test called");
    }
}

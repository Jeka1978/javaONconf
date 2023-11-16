package com.epam.javaonconf;

public class JavaONconfApplication {

    public static void main(String[] args) {
        DevRelza devRelza = ObjectFactory.getInstance().createObject(DevRelza.class);
        devRelza.executeDevRelStrategy();
        devRelza.executeDevRelStrategy();
    }
}

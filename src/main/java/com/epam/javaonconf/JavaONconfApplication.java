package com.epam.javaonconf;

public class JavaONconfApplication {

    public static void main(String[] args) {
        ObjectFactory.getInstance().createObject(DevRelza.class);
        ObjectFactory.getInstance().createObject(DevRelza.class);
        ObjectFactory.getInstance().createObject(DevRelza.class);
        ObjectFactory.getInstance().createObject(DevRelza.class);
        DevRelza devRelza = ObjectFactory.getInstance().createObject(DevRelza.class);

        devRelza.executeDevRelStrategy();
        devRelza.executeDevRelStrategy();
    }
}

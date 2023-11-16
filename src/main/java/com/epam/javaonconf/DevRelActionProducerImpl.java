package com.epam.javaonconf;

public class DevRelActionProducerImpl implements DevRelActionProducer {
    @Override
    public void produce(String devRelActivity) {
        System.out.println("starting DevRel activity: "+devRelActivity);
        System.out.println("providing non tech sessions");
        System.out.println("Gathering best potential speakers");
        System.out.println("Providing speaker courses");
    }
}

package com.epam.javaonconf;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;

@Singleton
public class DevRelza {
    @InjectByType
    private DevRelAnalyzer analyzer;
    @InjectByType
    private MetricsCollector metricCollector;
    @InjectByType
    private DevRelActionProducer producer;

    @PostConstruct
    public void init() {
        System.out.println("************** "+analyzer);
    }

    @Deprecated
    public void executeDevRelStrategy() {

        String devRelActivity = analyzer.findMostCriticalActivity();

        double howMuch = metricCollector.collect(devRelActivity);

        producer.produce(devRelActivity);

        System.out.println("improved by: " + (metricCollector.collect(devRelActivity) - howMuch) * 100 +
                " DevDollars");
    }
}

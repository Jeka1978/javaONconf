package com.epam.javaonconf;

public class LazyDevRelAnalyzer implements DevRelAnalyzer {
    @Override
    public String findMostCriticalActivity() {
        return "nothing to do, all good";
    }
}

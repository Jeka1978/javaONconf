package com.epam.javaonconf;

public class DevRelAnalyzerImpl implements DevRelAnalyzer {
    @Override
    @Deprecated
    public String findMostCriticalActivity() {
        return "engaging new speakers";
    }
}

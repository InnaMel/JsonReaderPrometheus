package com.prometheus.json.reader.parser;

import com.prometheus.json.reader.model.Car;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonCarParser {

    String patternEachRecordPair = "\"[a-zA-Z]+\"\\:\s[\"[\\w\s]+\"]+";

    public Car[] parseCars(String fileContent) {
        //PUT YOUR CODE HERE
        fileContent = fileContent.replaceAll("\\[|\\]", "").trim();
        String[] splitFileContent = fileContent.split("\\},");
//        printArray(splitFileContent);

        Car[] cars = new Car[splitFileContent.length];

        int i = 0;
        for (String eachRcordCar : splitFileContent) {
            cars[i] = parseCar(eachRcordCar);
        }

        return cars;
    }

    private Car parseCar(String eachRcordCar) {
        Car currentCar = new Car();
        Pattern pattern = Pattern.compile(patternEachRecordPair);
        Matcher matcher = pattern.matcher(eachRcordCar);

        while (matcher.find()){
            System.out.println("*****");
            System.out.println(matcher.group());
        }

        return currentCar;
    }

    // PUT YOUR CODE HERE

    private void printArray(String[] strArr) {
        for (String each : strArr) {
            System.out.println("---------------------");
            System.out.println(each.trim());
        }
    }
}
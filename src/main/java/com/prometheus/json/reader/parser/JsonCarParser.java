package com.prometheus.json.reader.parser;

import com.prometheus.json.reader.model.Car;

import java.lang.reflect.Method;
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

        while (matcher.find()) {
//            System.out.println("*****");
//            System.out.println(matcher.group());
            String[] parseKeyValueLine = parseToKeyValueLine(matcher.group());

//            System.out.println();
//            System.out.println();
//            printArray(parseToKeyValueLine);

        }
        String[] namesMethodsCarClass = getNamesMethodsCarClass(currentCar);

        return currentCar;
    }

    // PUT YOUR CODE HERE

    private String[] getNamesMethodsCarClass(Car car) {
        Method[] methodsCarClass = car.getClass().getMethods();
        int capacityNameArray = 0;
        for (Method method : methodsCarClass) {
            if (method.getName().startsWith("set")) {
                capacityNameArray++;
            }
        }
        String[] namesMethodsCarClass = new String[capacityNameArray];

        int currentItemName = 0;

        for (int i = 0; i < methodsCarClass.length; i++) {
            if (methodsCarClass[i].getName().startsWith("set")) {
                namesMethodsCarClass[currentItemName] = methodsCarClass[i].getName().substring(3).toLowerCase();
                currentItemName++;
                if (currentItemName == capacityNameArray) {
                    break;
                }
            }
        }
        printArray(namesMethodsCarClass);

        return namesMethodsCarClass;
    }

    private String[] parseToKeyValueLine(String parseLine) {
        String[] parsedLine = parseLine.split("\\:");

        for (int i = 0; i < parsedLine.length; i++) {
            parsedLine[i] = parsedLine[i].replaceAll("\"", "");
            parsedLine[i] = parsedLine[i].trim();
        }
        return parsedLine;
    }

    private void printArray(String[] strArr) {
        for (String each : strArr) {
//            System.out.println("---------------------");
            System.out.print(each + ",");
        }
    }
}
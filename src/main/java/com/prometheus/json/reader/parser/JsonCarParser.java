package com.prometheus.json.reader.parser;

import com.prometheus.json.reader.model.Car;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonCarParser {

    String patternEachRecordPair = "\"[a-zA-Z]+\"\\:\s[\"[\\w\s]+\"]+";

    public Car[] parseCars(String fileContent) throws InvocationTargetException, IllegalAccessException {
        //PUT YOUR CODE HERE
        fileContent = fileContent.replaceAll("\\[|\\]", "").trim();
        String[] splitFileContent = fileContent.split("\\},");

        Car[] cars = new Car[splitFileContent.length];

        int i = 0;
        for (String eachRecordCar : splitFileContent) {
            cars[i] = parseCar(eachRecordCar);
            i++;
        }

        return cars;
    }

    // PUT YOUR CODE HERE

    private Car parseCar(String eachRecordCar) throws InvocationTargetException, IllegalAccessException {
        Car currentCar = new Car();
        Pattern pattern = Pattern.compile(patternEachRecordPair);
        Matcher matcher = pattern.matcher(eachRecordCar);

        Method[] setMethodsCarClass = getSetMethodsCarClass(currentCar);
        String[] namesMethodsCarClass = getNamesMethodsCarClass(setMethodsCarClass);

        while (matcher.find()) {
            String[] parseKeyValueLine = parseToKeyValueLine(matcher.group());
            currentCar = mapKeyValue(currentCar, parseKeyValueLine, setMethodsCarClass, namesMethodsCarClass);
        }

        return currentCar;
    }

    private Car mapKeyValue(
            Car car,
            String[] parseKeyValueLine,
            Method[] setMethods,
            String[] nameMethods) throws InvocationTargetException, IllegalAccessException {

        for (int i = 0; i < nameMethods.length; i++) {
            if (parseKeyValueLine[0].equals(nameMethods[i])) {
                try {
                    setMethods[i].invoke(car, parseKeyValueLine[1]);
                } catch (NullPointerException e) {
                    System.out.println("Json has key with no Value! Fill the gap and try again later.");
                } catch (Exception e) {
                    int value = Integer.parseInt(parseKeyValueLine[1]);
                    setMethods[i].invoke(car, value);
                }
                break;
            }
        }

        return car;
    }

    private Method[] getSetMethodsCarClass(Car car) {
        int capacityNameArray = 0;
        ArrayList<Integer> indexesSetMethod = new ArrayList<Integer>();
        Method[] methodsCarClass = car.getClass().getMethods();
        for (int i = 0; i < methodsCarClass.length; i++) {
            if (methodsCarClass[i].getName().startsWith("set")) {
                capacityNameArray++;
                indexesSetMethod.add(i);
            }
        }
        Method[] setMethodsCarClass = new Method[capacityNameArray];
        for (int i = 0; i < setMethodsCarClass.length; i++) {
            setMethodsCarClass[i] = methodsCarClass[indexesSetMethod.get(i)];
        }

        return setMethodsCarClass;
    }

    private String[] getNamesMethodsCarClass(Method[] setMethods) {
        String[] namesMethodsCarClass = new String[setMethods.length];
        int quantityItemName = 0;

        for (Method setMethod : setMethods) {
            namesMethodsCarClass[quantityItemName] = setMethod.getName().substring(3).toLowerCase();
            quantityItemName++;
            if (quantityItemName == setMethods.length) {
                break;
            }

        }

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
}
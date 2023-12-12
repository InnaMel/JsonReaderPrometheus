package com.prometheus.json.reader;

import com.prometheus.json.reader.model.Car;
import com.prometheus.json.reader.parser.JsonCarParser;
import com.prometheus.json.reader.reader.JsonCarsReader;

public class Main {
    public static void main(String[] args) {
        JsonCarsReader jsonCarsReader = new JsonCarsReader();
        JsonCarParser jsonCarParser = new JsonCarParser();

        String fileContent = jsonCarsReader.readFromFile("JsonReaderPrometheus/cars.json");
        Car[] parsedCars = jsonCarParser.parseCars(fileContent);

        for (Car car : parsedCars) {
            System.out.println("name: \"" + car.getName() + "\", "
                    + "year: " + car.getYear() + ", "
                    + "speed: " + car.getSpeed());
        }
    }
}

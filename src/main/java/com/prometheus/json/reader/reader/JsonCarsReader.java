package com.prometheus.json.reader.reader;

import java.io.*;

public class JsonCarsReader {
    public String readFromFile(String fileName) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            while (bufferedReader.ready()) {
                resultStringBuilder
                        .append(bufferedReader.readLine())
                        .append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultStringBuilder.toString();
    }
}

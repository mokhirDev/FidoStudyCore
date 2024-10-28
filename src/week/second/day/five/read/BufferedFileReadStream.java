package week.second.day.five.read;

import week.second.day.five.read.examples.Employee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BufferedFileReadStream {
    public static void main(String[] args) {
        Path path = Paths.get("src/week/second/day/five/read/examples/employees.csv");
        Path savePath = Paths.get("src/week/second/day/five/read/examples/employees_result.csv");
        try(BufferedReader br = Files.newBufferedReader(path)) {
            Stream<String> stringStream = br.lines();
            List<Employee> employees = new ArrayList<>();
            stringStream
                    .skip(1)
                    .flatMap(line -> {
                        List<String> list = Stream.of(line.split(",")).toList();
                        employees.add(new Employee(
                                list.get(0),
                                list.get(1),
                                list.get(2),
                                BigDecimal.valueOf(Integer.valueOf(list.get(3))),
                                Integer.valueOf(list.get(4)),
                                Integer.valueOf(list.get(5)),
                                list.get(6),
                                list.get(7)
                                )
                        );
                        return employees.stream();
                    }).toList();

            br.close();
            BufferedWriter writer = Files.newBufferedWriter(savePath);
            employees
                    .stream()
                    .forEach(employee -> {
                        try {
                            writer.write(employee.toString()+System.lineSeparator());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package week.second.day.five.read;

import week.second.day.five.read.examples.Employee;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

public class ProcessingCSVStream {
    public static void main(String[] args) {
        Path employeePath = Paths.get("src/week/second/day/five/read/examples/employees.csv");
        Path saveChanges = Paths.get("src/week/second/day/five/read/examples/employees_result.csv");
        Path saveObjects = Paths.get("src/week/second/day/five/read/examples/employee_objects.txt");
        List<Employee> employees = Collections.synchronizedList(new ArrayList<>());
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        try (Stream<String> lines = Files.lines(employeePath).parallel()) {
            lines
                    .skip(1)
                    .forEach(line -> {
                        List<String> streamList = Arrays.stream(line.split(","))
                                .map(String::trim)
                                .toList();
                        employees.add(
                                new Employee(
                                        streamList.get(0),
                                        streamList.get(1),
                                        streamList.get(2),
                                        BigDecimal.valueOf(Integer.parseInt(streamList.get(3))),
                                        Integer.parseInt(streamList.get(4)),
                                        Integer.parseInt(streamList.get(5)),
                                        streamList.get(6),
                                        streamList.get(7)
                                ));
                    });

            forkJoinPool.submit(() -> {
                employees.parallelStream()
                        .forEach(employee -> employee.setCalculatedSalary(calculateSalary(employee)));
            }).get();

            forkJoinPool.submit(() -> {
                employees.parallelStream()
                        .forEach(employee -> {
                            try {
                                synchronized (saveChanges) {
                                    Files.writeString(
                                            saveChanges,
                                            employee.toString() + System.lineSeparator(),
                                            StandardOpenOption.CREATE,
                                            StandardOpenOption.APPEND);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }).get();

            forkJoinPool.submit(() -> {
                try (FileOutputStream fos = new FileOutputStream(String.valueOf(saveObjects));
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(employees);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).get();


            forkJoinPool.submit(() -> {
                try (FileInputStream inputStream = new FileInputStream(String.valueOf(saveObjects));
                     ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                    Object readObject = objectInputStream.readObject();
                    List<Employee> employeeList = (List<Employee>) readObject;
                    System.out.println("Objects:" + employeeList);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).get();

            forkJoinPool.shutdown();

        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static synchronized BigDecimal calculateSalary(Employee employee) {
        double levelMultiplier = switch (employee.getLevel()) {
            case "Junior" -> 1.0;
            case "Mid" -> 1.2;
            case "Senior" -> 1.4;
            default -> 1.0;
        };
        double positionMultiplier = switch (employee.getPosition()) {
            case "Software Engineer" -> 1.1;
            case "Data Analyst" -> 1.0;
            case "Project Manager" -> 1.2;
            case "HR Manager" -> 1.05;
            case "Marketing Specialist" -> 1.0;
            case "Sales Executive" -> 1.15;
            default -> 1.0;
        };
        int kpi = employee.getKpi();
        BigDecimal baseSalary = employee.getSalary();
        int experience = employee.getExperience();
        double kpiBonus = (kpi > 70) ? (kpi - 70) * 0.01 : 0;
        double experienceBonus = 0.01 * experience;
        double calculatedSalary = (1 + kpiBonus) * (1 + experienceBonus) * levelMultiplier * positionMultiplier;
        return BigDecimal.valueOf(calculatedSalary).multiply(baseSalary);
    }
}

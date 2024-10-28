package week.second.day.five.read;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadFileStream {
    public static void main(String[] args) {
        Path path = Paths.get("src/week/second/day/five/info.txt");
        try {
            List<String> strings = Files.readAllLines(path);
            long count = strings.size();
            strings.stream().forEach(System.out::println);
//            strings.stream().parallel().forEach(System.out::println);
            System.out.println("Count: " + count);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

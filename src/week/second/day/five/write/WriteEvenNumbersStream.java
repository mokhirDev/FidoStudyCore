package week.second.day.five.write;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.IntStream;

public class WriteEvenNumbersStream {
    public static void main(String[] args) {
        Path numbersPath = Paths.get("src/week/second/day/five/write/even_numbers.txt");
        try {
            IntStream.rangeClosed(1, 200)
                    .filter(number -> number % 2 == 0)
                    .mapToObj(String::valueOf)
                    .forEach(number -> {
                        try {
                            Files.writeString(numbersPath,
                                    number+System.lineSeparator(),
                                    StandardOpenOption.CREATE,
                                    StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

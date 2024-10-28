package week.second.day.five.read;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class LongestLineStream {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/week/second/day/five/info.txt");
        Stream<String> stringStream = Files.lines(path);
        String fileMaxLength = stringStream.max(Comparator.comparingInt(String::length)).orElse("File is empty");
        System.out.println("max length word: "+fileMaxLength);
    }
}

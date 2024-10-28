package week.second.day.five.write;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class WriteUniqueWordsStream {
    public static void main(String[] args) {
        Path pathUniqueWords = Paths.get("src/week/second/day/five/write/unique_words.txt");
        Path pathInfo = Paths.get("src/week/second/day/five/info.txt");
        try{
            Stream<String> lines = Files.lines(pathInfo);
            List<String> list = lines
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .map(word -> word.toLowerCase())
                    .map(word -> word.replaceAll(",", ""))
                    .distinct()
                    .toList();
            Files.write(pathUniqueWords, list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

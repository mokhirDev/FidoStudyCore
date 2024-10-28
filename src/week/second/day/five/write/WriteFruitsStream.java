package week.second.day.five.write;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class WriteFruitsStream {
    public static void main(String[] args) throws IOException {
        List<String> fruits = Arrays.asList("apple", "banana", "orange", "pineapple");
        Path infoPath = Paths.get("src/week/second/day/five/info.txt");
        Path fruitsPath = Paths.get("src/week/second/day/five/write/fruits.txt");
        try(Stream<String> stringStream = Files.lines(infoPath)){
            AtomicInteger count = new AtomicInteger(0);
            List<String> result = stringStream
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .map(word -> {
                        return word.replace(",", "");
                    })
                    .filter(word -> !word.isEmpty() && fruits.contains(word))
                    .map(word->{
                        return count.incrementAndGet()+" : "+word;
                    })
                    .toList();
            Files.write(fruitsPath, result);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}

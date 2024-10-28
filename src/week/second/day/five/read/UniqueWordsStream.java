package week.second.day.five.read;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UniqueWordsStream {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/week/second/day/five/info.txt");
        Stream<String> stringStream = Files.lines(path);
        Set<String> collected = stringStream
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .peek(System.out::println)
                .collect(Collectors.toSet());
        AtomicInteger i= new AtomicInteger();
        collected.forEach(x->{
            i.addAndGet(1);
            System.out.println("result["+i+"]="+x);
        });
    }
}

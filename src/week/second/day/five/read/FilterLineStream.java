package week.second.day.five.read;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class FilterLineStream {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/week/second/day/five/info.txt");
        Stream<String> stringStream = Files.lines(path);
        AtomicInteger i = new AtomicInteger(0);
        stringStream
                .filter(line -> line.contains("important"))
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .filter(Objects::nonNull)
                .map(s->s.replace(",", ""))
                .skip(1)
                .forEach(x->{
                    i.incrementAndGet();
                    System.out.println(i.get()+": "+x);
                });

    }
}

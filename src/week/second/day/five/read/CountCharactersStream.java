package week.second.day.five.read;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class CountCharactersStream {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/week/second/day/five/info.txt");
        AtomicInteger i = new AtomicInteger(0);
        Stream<String> stringStream = Files.lines(path);
        Map<String, Integer> map = new HashMap<>();
        stringStream
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .map(x -> {
                    map.put(x.replace(",", ""), x.length()-1);
                    return x;
                }).toList();
        map.forEach((k, v) -> {
            System.out.println("["+i.incrementAndGet()+"] "+k + " : " + v);
        });
    }
}

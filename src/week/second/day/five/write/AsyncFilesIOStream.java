package week.second.day.five.write;

import week.second.day.five.write.examples.Employee;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncFilesIOStream {
    public static void main(String[] args) {
        Path empPath = Paths.get("src/week/second/day/five/write/examples/employees.csv");
        Path resEmpPath = Paths.get("src/week/second/day/five/write/examples/employees_result.csv");
        List<Employee> employees = new ArrayList<>();
        try (AsynchronousFileChannel readFileChannel = AsynchronousFileChannel.open(empPath, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Future<Integer> read = readFileChannel.read(buffer, 0);
            read.get();
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String content = new String(bytes);

            String[] lines = content.split(System.lineSeparator());
            for (int i = 1; i < lines.length-1; i++) {
                String line = lines[i];
                String[] fields = line.split(",");

                Employee employee = new Employee(
                        fields[0].trim(),
                        fields[1].trim(),
                        fields[2].trim(),
                        BigDecimal.valueOf(Integer.parseInt(fields[3].trim())),
                        Integer.parseInt(fields[4].trim()),
                        Integer.parseInt(fields[5].trim()),
                        fields[6].trim(),
                        fields[7].trim()
                );
                employees.add(employee);
            }
            System.out.println(employees);

            AsynchronousFileChannel writeChannel = AsynchronousFileChannel.open(resEmpPath, StandardOpenOption.WRITE);
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            writeBuffer.put(content.getBytes());
            writeBuffer.flip();
            writeChannel.write(writeBuffer, 0);

        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
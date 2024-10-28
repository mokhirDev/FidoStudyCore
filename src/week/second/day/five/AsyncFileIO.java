package week.second.day.five;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsyncFileIO {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("async_file.txt");

        // Асинхронная запись в файл
        try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("This is an example of asynchronous file writing.".getBytes());
            buffer.flip();

            Future<Integer> operation = fileChannel.write(buffer, 0);
            while (!operation.isDone()) {
                System.out.println("Writing to file...");
            }
            System.out.println("Write completed!");
        }

        // Асинхронное чтение из файла
        try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Future<Integer> operation = fileChannel.read(buffer, 0);
            while (!operation.isDone()) {
                System.out.println("Reading from file...");
            }
            System.out.println("Read completed!");
        }
    }
}


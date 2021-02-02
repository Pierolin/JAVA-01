package Week_02.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpServer {
    public static void main(String[] args) throws IOException {
//        singleThread(8801);
        //multiThreads(8802);
        poolThreads(8803);
    }

    private static void poolThreads(int port) throws IOException {
        final ServerSocket server = new ServerSocket(port);
        int threadNumber = Runtime.getRuntime().availableProcessors() + 2;
        ExecutorService executorService = new ThreadPoolExecutor(threadNumber, threadNumber, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        while (true) {
            final Socket socket = server.accept();
            executorService.execute(() -> service(socket));
        }
    }

    private static void multiThreads(int port) throws IOException {
        final ServerSocket server = new ServerSocket(port);
        while (true) {
            final Socket socket = server.accept();
            new Thread(() -> {
                service(socket);
            }).start();
        }
    }

    private static void singleThread(int port) throws IOException {
        final ServerSocket server = new ServerSocket(port);
        while (true) {
            final Socket socket = server.accept();
            service(socket);
        }
    }


    private static void service(Socket socket) {
        try {
            PrintWriter writer = null;
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type:text/html;charset=utf-8");
            String body = "Hello, NIO! " + new Random().nextInt(10);
            writer.println("Content-Length:" + body.getBytes().length);
            writer.println();
            writer.write(body);
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

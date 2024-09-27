import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) {
            System.out.println("Подключен к чату");


            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Поток для отправки сообщений на сервер
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);


            Thread receiveThread = new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = reader.readLine()) != null) {
                        System.out.println("Сообщение: " + serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();


            Scanner scanner = new Scanner(System.in);
            String message;
            while (true) {
                message = scanner.nextLine();
                writer.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

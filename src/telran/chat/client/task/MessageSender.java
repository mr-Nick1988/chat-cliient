package telran.chat.client.task;

import telran.chat.model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;

public class MessageSender implements Runnable {
    private final Socket socket;

    public MessageSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Socket socket = this.socket) {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter your name");
            String name = br.readLine();
            Message message = new Message(name, "");
            System.out.println("Enter your message, or type exit for quit");
            String msg = br.readLine();
            while (!"exit".equalsIgnoreCase(msg)) {
                message.setMessage(msg);
                message.setTime(LocalTime.now());
                oos.writeObject(message);
                oos.reset();
                msg = br.readLine();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

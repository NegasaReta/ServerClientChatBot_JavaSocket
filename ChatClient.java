import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        out.println(msg);
        try {
            String response = in.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.startConnection("127.0.0.1", 6666);

        Scanner scanner = new Scanner(System.in);
        String message;
        while (true) {
            System.out.print("You: ");
            message = scanner.nextLine();
            client.sendMessage(message);
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
        }

        client.stopConnection();
        scanner.close();
    }
}

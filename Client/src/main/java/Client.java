import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class Client {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
//            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            in = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public String sendMessage(String msg) {
        try {
            out.println("Sent msg: " + msg);
            return msg;
        } catch (Exception e) {
            return null;
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        System.out.println("new client");
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("hello server");
        String msg = client.in.readLine();
//        String msg = client.sendMessage(msg);

        while(!Objects.equals(msg, "stop")){
            client.sendMessage(msg);
            msg = client.in.readLine();
        }

        System.out.println("Client end");
    }

}

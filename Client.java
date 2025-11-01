import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
  final static String MOVE_UP = "\033[1A";
  final static String CLEAR_LINE = "\033[2K";

  public static void main(String[] args) {
    String host = "127.0.0.1";
    int port = 8080;

    try (Socket socket = new Socket(host, port)) {
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

      ServerListener listener = new ServerListener(in);
      new Thread(listener).start();

      String message;
      while ((message = consoleReader.readLine()) != null) {
        System.out.println(MOVE_UP + CLEAR_LINE);
        out.println(message);
      }

    } catch (UnknownHostException e) {
      System.out.println("Server not found: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("Input/Output exception: " + e.getMessage());
    }
  }

}

class ServerListener implements Runnable {
  private final BufferedReader in;

  public ServerListener(BufferedReader in) {
    this.in = in;
  }

  @Override
  public void run() {
    try {
      String serverMessage;
      while ((serverMessage = in.readLine()) != null) {
        System.out.println(serverMessage);
      }

    } catch (IOException e) {
      System.out.println("Disconnected from server: " + e.getMessage());
    }

  }

}

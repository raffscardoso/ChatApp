import java.io.*;
import java.net.*;

class Server implements Runnable {

  private boolean running;

  @Override
  public void run() {
    int port = 8080;
    running = true;
    try (ServerSocket server = new ServerSocket(port)) {
      while (running) {
        Socket client = server.accept();
        connectionHandler(client);
      }
    } catch (IOException e) {
      System.err.println("Server error: " + e.getMessage());
    }
  }

  public void connectionHandler(Socket clientSocket) {

    try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

      String message = in.readLine();
      out.println("Enter a nickname: ");
      String clientNickname = message;
      out.println(clientNickname + " joined the chat.");

      while (message != null) {
        String trimmedMessage = message.trim();
        if (trimmedMessage.startsWith("/nick")) {
          String[] parts = trimmedMessage.split(" ", 2);
          String newNickaname = parts[1];
          System.out.println(clientNickname + " changed nickname to: " + newNickaname);
          clientNickname = newNickaname;

        } else if (trimmedMessage.startsWith("/quit")) {
          System.out.println("Client " + clientSocket.getRemoteSocketAddress() + "wants to leave.");
          out.println(clientNickname + " quit.");
          clientSocket.close();
          break;
        } else {
          out.println(clientNickname + ": " + trimmedMessage);
        }
      }
    } catch (IOException e) {
      System.err.println("Error on connectionHandler: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    Server s = new Server();
    s.run();
  }
}

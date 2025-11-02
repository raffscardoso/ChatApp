import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
  private Socket clientSocket;
  private Server server;
  private PrintWriter out;
  private BufferedReader in;
  private String clientNickname;

  public ClientHandler(Socket socket, Server server) {
    try {
      this.clientSocket = socket;
      this.server = server;
      this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      this.out = new PrintWriter(clientSocket.getOutputStream(), true);
    } catch (IOException e) {
      System.err.println("Error creating client handler: " + e.getMessage());
    }
  }

  @Override
  public void run() {
    try {
      out.println("Enter a nickname: ");
      String message = in.readLine();
      this.clientNickname = message;

      out.println("Welcome, " + clientNickname + "!");
      server.broadcast(clientNickname + " joined the chat!", this);

      message = in.readLine();

      while (message != null) {
        String trimmedMessage = message.trim();
        if (trimmedMessage.startsWith("/nick")) {
          String[] parts = trimmedMessage.split(" ", 2);
          String newNickaname = parts[1];
          server.broadcast(clientNickname + " changed nickname to: " + newNickaname, this);
          this.clientNickname = newNickaname;

        } else if (trimmedMessage.startsWith("/quit")) {
          System.out.println("Client " + clientSocket.getRemoteSocketAddress() + " wants to leave.");
          server.broadcast(clientNickname + " quit.", this);
          clientSocket.close();
          break;
        } else {
          server.broadcast(this.clientNickname + ": " + trimmedMessage, this);
        }
        message = in.readLine();
      }
    } catch (IOException e) {
      System.err.println("Error on ClientHandler: " + e.getMessage());
    } finally {
    }
  }

  public void sendMessage(String message) {
    out.println(message);
  }
}

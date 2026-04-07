package com.chatapp.server;

import com.chatapp.shared.ChatDefaults;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server implements Runnable {

  private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

  @Override
  public void run() {
    int port = ChatDefaults.DEFAULT_PORT;
    try (ServerSocket server = new ServerSocket(port)) {
      while (true) {
        Socket client = server.accept();

        ClientHandler clientHandler = new ClientHandler(client, this);

        clients.add(clientHandler);

        new Thread(clientHandler).start();
      }
    } catch (IOException e) {
      System.err.println("Server error: " + e.getMessage());
    }
  }

  public void broadcast(String message, ClientHandler sender) {
    for (ClientHandler client : clients) {
      client.sendMessage(message);
    }
  }

  public void removeClient(ClientHandler clientHandler) {
    clients.remove(clientHandler);
    System.out.println("Client " + clientHandler.getClientNickname() + " removed.");
  }

  public static void main(String[] args) {
    Server s = new Server();
    new Thread(s).start();
  }
}

# ChatApp

This is a simple command-line chat application built in Java.

## Description

The project consists of two main components:

* **Server:** A multi-threaded server that listens for client connections and broadcasts messages to all connected clients.
* **Client:** A command-line client that connects to the server and allows users to send and receive messages.

## How to Run

1. **Compile the code:**

    ```bash
    javac Server.java Client.java
    ```

2. **Start the server:**

    ```bash
    java Server
    ```

3. **Start the client:**
    Open a new terminal and run:

    ```bash
    java Client
    ```

    You can start multiple clients to chat with each other.

## How to Use

* Upon connecting, you will be prompted to enter a nickname.
* Type your message and press `Enter` to send it to the chat.
* To change your nickname, use the `/nick` command:

    ```
    /nick <new_nickname>
    ```

* To quit the chat, use the `/quit` command.

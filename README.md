# ChatApp

This is a simple command-line chat application built in Java.

## Project Structure

The project now uses a Maven multi-module layout:

- `shared`: common constants and shared code used by client and server.
- `server`: server runtime and client connection handling.
- `client`: command-line chat client.

## Requirements

- Java 17+
- Maven 3.9+

## How to Run

1. **Compile all modules:**

   ```bash
   mvn clean compile
   ```

2. **Start the server:**

   ```bash
   mvn -pl server exec:java
   ```

3. **Start the client:**
   Open another terminal and run:

   ```bash
   mvn -pl client exec:java
   ```

   You can start multiple clients to chat with each other.

## How to Use

- Upon connecting, you will be prompted to enter a nickname.
- Type your message and press `Enter` to send it to the chat.
- To change your nickname, use the `/nick` command:

  ```
  /nick <new_nickname>
  ```

- To quit the chat, use the `/quit` command.

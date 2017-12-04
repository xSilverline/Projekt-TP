/**
 * The class for the helper threads in this multithreaded server
 * application.  A Player is identified by a playerName.
 * For communication with the
 * client the player has a socket with its input and output
 * streams.  Since only text is being communicated we use a
 * reader and a writer.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player extends Thread {
    String playerName;
    int playerId;
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    Boolean isReady;

    /**
     * Constructs a handler thread for a given socket and mark
     * initializes the stream fields, displays the first two
     * welcoming messages.
     */
    public Player(Socket socket, int playerId) {
        this.socket = socket;
        this.playerId = playerId;
        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + playerName);

        } catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }

    /**
     * The run method of this thread.
     */
    public void run() {
        try {

            while (true) {
                output.println("SUBMITNAME");
                playerName = input.readLine();
                if (playerName == null) {
                    return;
                }
                synchronized (Server.names) {
                    if (!Server.names.contains(playerName)) {
                        Server.names.add(playerName);
                        break;
                    }
                }
            }


            // Repeatedly get commands from the client and process them.
            while (true) {
                String command = input.readLine();
                if (command.startsWith("START"))
                {
                    isReady=true;
                }

                else if (command.startsWith("QUIT"))
                {
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Player disconnected: " + e);
        } finally {
            try {socket.close();} catch (IOException e) {}
        }
    }
}
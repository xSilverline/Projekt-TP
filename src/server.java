import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    /**
     * Runs the server.
     */
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(9090);
        System.out.println("Server is running");
        try {
            while (true) {
                Game game = new Game();
                Game.Player player1 = game.new Player(listener.accept(), 1);
                Game.Player player2 = game.new Player(listener.accept(), 2);
                Game.Player player3 = game.new Player(listener.accept(),3);
                Game.Player player4 = game.new Player(listener.accept(),4);
                Game.Player player5 = game.new Player(listener.accept(),5);
                Game.Player player6 = game.new Player(listener.accept(),6);
                game.currentPlayer = player1;
                player1.start();
                player2.start();
                player3.start();
                player4.start();
                player5.start();
                player6.start();
            }
        } finally {
            listener.close();
        }
    }
}

/**
 * A six-player game.
 */
class Game {

    private Player[] board = {};

    /**
     * The current player.
     */
    Player currentPlayer;


    /**
     * Called by the player threads when a player tries to make a
     * move.  This method checks to see if the move is legal: that
     * is, the player requesting the move must be the current player
     * and the square in which she is trying to move must not already
     * be occupied.  If the move is legal the game state is updated
     * (the square is set and the next player becomes current) and
     * the other player is notified of the move so it can update its
     * client.
     */
  /*  public synchronized boolean legalMove(int location, Player player)
    {
        return false;
    }
*/
    /**
     * The class for the helper threads in this multithreaded server
     * application.  A Player is identified by a playerName.
     * For communication with the
     * client the player has a socket with its input and output
     * streams.  Since only text is being communicated we use a
     * reader and a writer.
     */
    class Player extends Thread {
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
                output.println("MESSAGE Waiting for opponent to connect");
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            }
        }

        /**
         * The run method of this thread.
         */
        public void run() {
            try {
                // The thread is only started after everyone connects.
                output.println("MESSAGE All players connected");

                // Tell the first player that it is her turn.
                if (playerId == 1) {
                    output.println("MESSAGE Your move");
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
}
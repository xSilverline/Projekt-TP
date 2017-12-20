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
    private String playerName;
    private int playerId;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    int gameType=0;

    /**
     * Constructs a handler thread for a given socket and mark
     * initializes the stream fields, displays the first two
     * welcoming messages.
     */
    public Player(Socket socket, int playerId) {
        this.socket = socket;
        this.playerId=playerId;
    }

    public void currentOpponent() {
        /*
         * TODO: Return opponent.
         */
    }

    public void otherPlayerMoved(int location, int playerID) {
        out.println("OPPONENT_MOVED " + playerID + location);
        /*
         * TODO: If game has winner:
         *          output.println("DEFEAT");
         */
    }

    /**
     * The run method of this thread.
     */
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                out.println("SUBMITNAME");
                playerName = in.readLine();
                if (playerName == null) {
                    return;
                }
                synchronized (Server.names) {
                    if (!Server.names.contains(playerName))
                    {
                        Server.names.add(playerName);
                        break;
                    }else
                    {
                        out.println("INVALID_NAME");
                        break;
                    }
                }
            }

            out.println("NAMEACCEPTED");
            Server.players.add(this);




            while (true) {
                String command = in.readLine();

                if (command.startsWith("MOVE")) {
                    int expectPosition = Integer.parseInt(command.substring(5));
                    /*
                     * TODO: - if move is legal
                     *            - if game has winner
                     *                  - output.println("VICTORY");
                     *       - else
                     *            - output.println("MESSAGE Illegal move!");
                     *
                     */
                } else if (command.startsWith("QUIT")) {
                    return;
                } else if (command.startsWith("JOIN_GAME"))
                {
                    while(true)
                    {
                        if(command.startsWith("GAME_TYPE"))
                        {
                            this.gameType = Integer.parseInt(command.substring(9));
                            break;
                            /*
                             * TODO: if room <type> is not full join this player
                             *       search for free room in chosen gametype
                             *       join to lobby
                             *       if no room send message and create new game
                             */

                        }
                        else if(command.startsWith("RETURN"))
                        {
                            break;
                        }
                    }
                }else if (command.startsWith("NEW_GAME")) {
                    while (true) {
                        if (command.startsWith("GAME_TYPE")) {
                            this.gameType = Integer.parseInt(command.substring(9));

                            break;
                            /*
                             * TODO: create new game
                             *
                             */

                        } else if (command.startsWith("RETURN")) {
                            break;
                        }
                    }
                }else if (command.startsWith("ADD_BOT")) {
                    /*
                     * TODO: add one bot to game
                     */
                } else if (command.startsWith("REMOVE_BOT")) {
                    /*
                     * TODO: remove one bot from game
                     */
                }
            }
        } catch (IOException e) {
            System.out.println("Player disconnected: " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }
}
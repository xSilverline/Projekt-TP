/**
 * The class for the helper threads in this multithreaded server
 * application.  A Player is identified by a playerName.
 * For communication with the
 * client the player has a socket with its input and output
 * streams.  Since only text is being communicated we use a
 * reader and a writer.
 */


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Player extends Thread {

    private String playerName;
    private int playerId;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    int gameType=0;
    private int lobbyId;

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

    void checkLobby()
    {
        Lobby toRemove=null;
        for(Lobby l: Server.lobbyList)
        {
            if(l.getId() == lobbyId)
            {
                l.players.remove(this);
                toRemove = l;
                break;
            }
        }
        if(toRemove != null)
        {
            Server.lobbyList.remove(toRemove);
        }
    }

    /**
     * The run method of this thread.
     */
    public void run()
    {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            while (true)
            {
                out.println("SUBMITNAME");
                playerName = in.readLine();
                if (playerName == null)
                {
                    out.println("INVALID_NAME");
                }
                else {
                    synchronized (Server.names) {
                        if (!Server.names.contains(playerName)) {
                            Server.names.add(playerName);
                            break;
                        } else {
                            out.println("INVALID_NAME");

                        }
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
                } else if (command.startsWith("JOIN_GAME_GET_LOBBY"))
                {
                    int lobbySize=Server.lobbyList.size();
                    if(lobbySize == 0)
                    {
                        System.out.println(lobbySize);
                        out.println(lobbySize);
                        out.println("No games found. Create a new game");
                    }

                    else
                    {
                        out.println(lobbySize);
                        for (Lobby l : Server.lobbyList)
                        {
                            out.println("Room " + l.getId() + "  Type: " + l.getGameType() + "  Number of players: " + l.getNumberOfPlayers());
                        }

                    }


                }
                else if(command.startsWith("RETURN_FROM_LOBBY"))
                {
                    checkLobby();
                }
                else if(command.startsWith("GET_LOBBY_ID"))
                {
                    out.println(lobbyId);
                }
                else if (command.startsWith("NEW_GAME_TYPE"))
                {
                    int id;

                    gameType = Integer.parseInt(command.substring(13));
                    System.out.println(gameType);
                    if(!Server.lobbyList.isEmpty())
                    {
                        id = Server.lobbyList.get(Server.lobbyList.size() - 1).getId() + 1;
                    }
                    else
                        {
                        id = 1;
                    }
                        Lobby lobby = new Lobby(gameType, id);
                        lobby.joinLobby(this);
                        lobbyId = id;
                        System.out.println(id);

                        Server.lobbyList.add(lobby);


                            /*
                             * TODO: create new game
                             *
                             */


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
        } catch (IOException e)
        {
            System.out.println("Player disconnected: " + e);
            checkLobby();
            Server.names.remove(playerName);
            Server.players.remove(this);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }
}
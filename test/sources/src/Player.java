package sources.src; /**
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
    private int gameType=0;
    private int lobbyId;
    private boolean isReady;
    private Game game;

    private String currentPlayerName;

    /**
     * Constructs a handler thread for a given socket and mark
     * initializes the stream fields, displays the first two
     * welcoming messages.
     */
    public Player(Socket socket, int playerId) {
        this.socket = socket;
        this.playerId = playerId;
        isReady=false;
    }


    public boolean returnReady()
    {
        return isReady;
    }

    public void swapPawns(String message) {
        for(Player p : Server.players) {
            p.out.println(message);
        }
    }

    public void win(String color) {
        color = "END"+color;
        for(Player p : Server.players) {
            p.out.println(color);
        }
    }

    private void checkLobby()
    {
        Lobby toRemove=null;
        for(Lobby l: Server.lobbyList)
        {
            if(l.getId() == lobbyId)
            {
                l.players.remove(this);
                for (Player p : l.players)
                {
                    p.out.println("PLAYER_REFRESH");
                }
                if(l.getNumberOfPlayers() == 0)
                {
                    toRemove = l;
                }
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
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);


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

                if (command.startsWith("MOVE")) { // MOVE XX YY XX YY
                    int pX = Integer.parseInt(command.substring(5,7));
                    int pY = Integer.parseInt(command.substring(8,10));
                    int toX = Integer.parseInt(command.substring(11,13));
                    int toY = Integer.parseInt(command.substring(14,16));
                    Server.game.move(pX, pY, toX, toY);
                } else if (command.startsWith("SKIP_MOVE")) {
                    for(Player p : Server.players) {
                        p.out.println("SKIP");
                    }
                } else if (command.startsWith("QUIT")) {
                    return;
                } else if (command.startsWith("JOIN_GAME_GET_LOBBY"))
                {
                    int lobbySize= Server.lobbyList.size();
                    if(lobbySize == 0)
                    {
                        out.println("NULL_LOBBY_SIZE");
                        //out.println("No games found. Create a new game");
                    }

                    else
                    {
                        out.println("LOBBY_SIZE"+lobbySize);
                        for (Lobby l : Server.lobbyList)
                        {
                            out.println(l.getId());
                            out.println(l.getGameType());
                            out.println(l.getNumberOfPlayers());
                        }

                    }


                }
                else if(command.startsWith("RETURN_FROM_LOBBY"))
                {

                    checkLobby();
                    for(Player p: Server.players)
                    {
                        if(p != this)
                        p.out.println("LOBBY_REFRESH");
                    }

                }
                else if(command.startsWith("READY_PLAYER"))
                {
                    isReady = true;
                    for(Lobby l: Server.lobbyList)
                    {
                        if(l.getId() == lobbyId)
                        {
                            if (l.areReady() && l.getNumberOfPlayers() == l.getGameType())
                            {
                                for(Player p: l.players)
                                {
                                    p.out.println("START_GAME"+l.getGameType());
                                }
                                Server.lobbyList.remove(l);
                                for(Player p: Server.players)
                                {
                                    if(p != this)
                                    p.out.println("LOBBY_REFRESH");
                                }
                            }
                            else
                            {
                                out.println("WAIT_FOR_PLAYERS");
                            }
                            break;
                        }
                    }
                }
                else if(command.startsWith("NOT_READY_PLAYER"))
                {
                    isReady = false;
                    for(Lobby l: Server.lobbyList)
                    {
                        if(l.getId() == lobbyId)
                        {



                            break;
                        }
                    }
                }
                else if(command.startsWith("GET_LOBBY_INFO"))
                {
                    out.println("ROOM_INFO");
                    out.println(lobbyId);
                    for(Lobby l: Server.lobbyList)
                    {
                        if(l.getId() == lobbyId)
                        {
                            out.println(l.getNumberOfPlayers());
                            if (l.getNumberOfPlayers() != 0)
                            {
                                for (Player p : l.players)
                                {
                                    out.println(p.playerName);
                                }
                                break;
                            }
                        }
                    }
                }
                else if(command.startsWith("JOIN_TO"))
                {
                    lobbyId= Integer.parseInt(command.substring(7));
                    for (Lobby l : Server.lobbyList)
                    {
                        if(l.getId() == lobbyId)
                        {
                            if(l.isFree())
                            {
                                for(Player p: l.players)
                                {
                                    p.out.println("PLAYER_REFRESH");
                                }
                                l.joinLobby(this);
                                for(Player p: Server.players)
                                {
                                    if(p != this)
                                    p.out.println("LOBBY_REFRESH");
                                }

                            }
                            break;
                        }
                    }

                }
                else if (command.startsWith("NEW_GAME_TYPE"))
                {
                    int id;

                    gameType = Integer.parseInt(command.substring(13));
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

                    Server.lobbyList.add(lobby);

                    for(Player p: Server.players)
                    {
                        if(p != this)
                        p.out.println("LOBBY_REFRESH");
                    }

                }else if (command.startsWith("ADD_BOT")) {
                    /*
                     * TODO: add one bot to game
                     */
                } else if (command.startsWith("REMOVE_BOT")) {
                    /*
                     * TODO: remove one bot from game
                     */
                } else if (command.startsWith("CREATE_GAME")) {
                    int numOfPl = Integer.parseInt(command.substring(12));
                    Server.game(numOfPl, this);
                }
            }
        } catch (IOException e)
        {
            checkLobby();
            Server.names.remove(playerName);
            Server.players.remove(this);
            System.out.println("Player disconnected: " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }
}
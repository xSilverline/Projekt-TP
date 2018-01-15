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


public class PlayerThread extends Thread {

    private String playerName;
    private int playerId;
    private Socket socket;
    private String in;
    private String out;
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
    PlayerThread(int playerId)
    {

        this.playerId = playerId;
        isReady=false;
    }


    boolean returnReady()
    {
        return isReady;
    }

    void swapPawns(String message) {
        for(PlayerThread p : ServerTestClass.players) {
            p.out = message ;
        }
    }

    void win(String color) {
        color = "END"+color;
        for(PlayerThread p : ServerTestClass.players) {
            p.out =color;
        }
    }

    private void checkLobby()
    {
        LobbyTestClass toRemove=null;
        for(LobbyTestClass l: ServerTestClass.lobbyList)
        {
            if(l.getId() == lobbyId)
            {
                l.players.remove(this);
                for (PlayerThread p : l.players)
                {
                    p.out ="PLAYER_REFRESH";
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
            ServerTestClass.lobbyList.remove(toRemove);
        }
    }

    public void setOut(String message)
    {
        in = message;
    }

    public String retOut()
    {
        return out;
    }

    public void newPlayer()
    {
        out="SUBMITNAME";
        playerName = in;
        if (playerName == null)
        {
            out = "INVALID_NAME";
        }
        else
            {
                if (!ServerTestClass.names.contains(playerName)) {
                    ServerTestClass.names.add(playerName);
                    out="NAMEACCEPTED";
                    ServerTestClass.players.add(this);

                }
                else
                {
                    out= "INVALID_NAME";
                }
            }



    }

    void getLobbySize()
    {
        int lobbySize=ServerTestClass.lobbyList.size();
        if(lobbySize == 0)
        {
            out="NULL_LOBBY_SIZE";
            //out.println("No games found. Create a new game");

        }

        else
        {
            out="LOBBY_SIZE"+lobbySize;

        }
    }

    /**
     * The run method of this thread.
     */


/*

            while (true)
            {

*/
            void runThread(String command) {


                if (command.startsWith("MOVE")) { // MOVE XX YY XX YY
                    int pX = Integer.parseInt(command.substring(5,7));
                    int pY = Integer.parseInt(command.substring(8,10));
                    int toX = Integer.parseInt(command.substring(11,13));
                    int toY = Integer.parseInt(command.substring(14,16));
                    ServerTestClass.game.move(pX, pY, toX, toY);
                } else if (command.startsWith("SKIP_MOVE")) {
                    for(PlayerThread p : ServerTestClass.players) {
                        p.out="SKIP";
                    }
                } else if (command.startsWith("QUIT")) {
                    return;
                } else if (command.startsWith("JOIN_GAME_GET_LOBBY"))
                {
                    int lobbySize=ServerTestClass.lobbyList.size();
                    if(lobbySize == 0)
                    {
                        out="NULL_LOBBY_SIZE";
                        //out.println("No games found. Create a new game");
                    }

                    else
                    {
                        out="LOBBY_SIZE"+lobbySize;
                        for (LobbyTestClass l : ServerTestClass.lobbyList)
                        {
                            out = Integer.toString(l.getId());
                            out= Integer.toString(l.getGameType());
                            out= Integer.toString(l.getNumberOfPlayers());
                        }

                    }


                }
                else if(command.startsWith("RETURN_FROM_LOBBY"))
                {

                    checkLobby();
                    for(PlayerThread p: ServerTestClass.players)
                    {
                        if(p != this)
                            p.out = "LOBBY_REFRESH";
                    }

                }
                else if(command.startsWith("READY_PLAYER"))
                {
                    isReady = true;
                    for(LobbyTestClass l: ServerTestClass.lobbyList)
                    {
                        if(l.getId() == lobbyId)
                        {
                            if (l.areReady() && l.getNumberOfPlayers() == l.getGameType())
                            {
                                for(PlayerThread p: l.players)
                                {
                                    p.out = "START_GAME"+l.getGameType();
                                }
                                ServerTestClass.lobbyList.remove(l);
                                for(PlayerThread p: ServerTest.players)
                                {
                                    if(p != this)
                                        p.out="LOBBY_REFRESH";
                                }
                            }
                            else
                            {
                                out="WAIT_FOR_PLAYERS";
                            }
                            break;
                        }
                    }
                }
                else if(command.startsWith("NOT_READY_PLAYER"))
                {
                    isReady = false;
                    for(LobbyTestClass l: ServerTestClass.lobbyList)
                    {
                        if(l.getId() == lobbyId)
                        {

                            break;
                        }
                    }
                }
                else if(command.startsWith("GET_LOBBY_INFO"))
                {
                    out="ROOM_INFO";
                    out= Integer.toString(lobbyId);
                    for(LobbyTestClass l: ServerTestClass.lobbyList)
                    {
                        if(l.getId() == lobbyId)
                        {
                            out= Integer.toString(l.getNumberOfPlayers());
                            if (l.getNumberOfPlayers() != 0)
                            {
                                for (PlayerThread p : l.players)
                                {
                                    out= p.playerName;
                                }
                                break;
                            }
                        }
                    }
                }
                else if(command.startsWith("JOIN_TO"))
                {
                    lobbyId= Integer.parseInt(command.substring(7));
                    for (LobbyTestClass l : ServerTestClass.lobbyList)
                    {
                        if(l.getId() == lobbyId)
                        {
                            if(l.isFree())
                            {
                                for(PlayerThread p: l.players)
                                {
                                    p.out="PLAYER_REFRESH";
                                }
                                l.joinLobby(this);
                                for(PlayerThread p: ServerTestClass.players)
                                {
                                    if(p != this)
                                        p.out ="LOBBY_REFRESH";
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
                    if(!ServerTestClass.lobbyList.isEmpty())
                    {
                        id = ServerTestClass.lobbyList.get(ServerTestClass.lobbyList.size() - 1).getId() + 1;
                    }
                    else
                    {
                        id = 1;
                    }
                    LobbyTestClass lobby = new LobbyTestClass(gameType, id);
                    lobby.joinLobby(this);
                    lobbyId = id;

                    ServerTestClass.lobbyList.add(lobby);

                    for(PlayerThread p: ServerTestClass.players)
                    {
                        if(p != this)
                            p.out ="LOBBY_REFRESH";
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
                    ServerTestClass.game(numOfPl, this);
                }
            }
}
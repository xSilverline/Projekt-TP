
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashSet;
/*
 *  How the data travel:
 *
 *  Client -> Server                                 Server -> Client
 *  -------------------------------------            --------------------
 *  GAME_TYPE <n> (n - number of players)            VALID_MOVE
 *  ADD_BOT                                          OTHER_PLAYER_MOVED <n> (n - position after move)
 *  REMOVE_BOT                                       VICTORY
 *  MOVE <n> (n - expected position)                 DEFEAT
 *  QUIT                                             MESSAGE <text>
*/

public class ServerTestClass
{

/**
 * Runs the server.
 */
static final HashSet<String> names = new HashSet<String>();
static ArrayList<PlayerThread> players = new ArrayList<PlayerThread>();
static ArrayList<LobbyTestClass> lobbyList = new ArrayList<LobbyTestClass>();
static public GameTestClass game;

static public void game(int n, PlayerThread player) {
    game = new GameTestClass(n,player);
}

public static void main(String[] args) throws Exception {

    ServerSocket listener = new ServerSocket(9090);
    System.out.println("Server is running");

    int id=0;

    try {
        while(true) {
                new Player(listener.accept(),id).start();
                id++;
        }
    } finally {
        listener.close();
    }
}



}

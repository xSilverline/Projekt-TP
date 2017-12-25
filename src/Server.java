
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

    public class Server {

    /**
     * Runs the server.
     */
    static HashSet<String> names = new HashSet<String>();
    //static ArrayList<PlayerStatus> playerList = new ArrayList<PlayerStatus>();
    static ArrayList<Player> players = new ArrayList<Player>();
    static ArrayList<Lobby> lobbyList = new ArrayList<Lobby>();

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


import java.net.ServerSocket;
import java.util.HashSet;


public class Server {

    /**
     * Runs the server.
     */
    static HashSet<String> names = new HashSet<String>();

    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(9090);
        System.out.println("Server is running");
        try {
            while (true) {

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

}
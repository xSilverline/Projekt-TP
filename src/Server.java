
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashSet;


public class Server {

    /**
     * Runs the server.
     */
    static HashSet<String> names = new HashSet<String>();
    ArrayList<PlayerStatus> playerInfo = new ArrayList();

    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(9090);
        System.out.println("Server is running");
        int id=0;
        try {
            while (true)
            {

                if(id<6)
                {
                    new Player(listener.accept(), id);
                    id++;
                }
                else
                {
                    System.out.println("No room");
                }


            }
        } finally {
            listener.close();
        }
    }
}

import sun.font.GlyphDisposedListener;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {


    SetGui setGui;
    static String hostName = "localhost";
    static String playerName = "Player";

    private static int PORT = 9090;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Constructs the client by connecting to a server, laying out the
     * GUI and registering GUI listeners.
     */
    public Client(String serverAddress) throws Exception {

        // Setup networking
        socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Layout GUI
        setGui = new SetGui();
}

    /**
     * The main thread of the client will listen for messages
     * from the server.  The first message will be a "WELCOME"
     * message in which we receive our mark.  Then we go into a
     * loop listening for "VALID_MOVE", "OPPONENT_MOVED", "VICTORY",
     * "DEFEAT", "TIE", "OPPONENT_QUIT or "MESSAGE" messages,
     * and handling each message appropriately.  The "VICTORY",
     * "DEFEAT" and "TIE" ask the user whether or not to play
     * another game.  If the answer is no, the loop is exited and
     * the server is sent a "QUIT" message.  If an OPPONENT_QUIT
     * message is recevied then the loop will exit and the server
     * will be sent a "QUIT" message also.
     */
    public void play() throws Exception {
        String response;
        try {
            response = in.readLine();
            if (response.startsWith("WELCOME"))
            {
                setGui.messageLabel.setText(response + playerName);
                setGui.frame.setTitle("Player " + playerName);
            }
            while (true)
            {
                response = in.readLine();
                if(response.startsWith("SUBMITNAME"))
                {
                    out.println(playerName);
                } else if (response.startsWith("MESSAGE")) {
                    setGui.messageLabel.setText(response.substring(8));
                }
                if (response.startsWith("QUIT"))
                {
                    break;
                }
            }
            out.println("QUIT");
        }
        finally {
            socket.close();
        }
    }

    private boolean wantsToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(setGui.frame,
                "Want to play again?",
                "Tic Tac Toe is Fun Fun Fun",
                JOptionPane.YES_NO_OPTION);
        setGui.frame.dispose();
        return response == JOptionPane.YES_OPTION;
    }


    /**
     * Runs the client as an application.
     */
    public static void main(String[] args) throws Exception {


        SetupWindow setupDialog = new SetupWindow();

        while (true) {

            String serverAddress = hostName;
            Client client = new Client(serverAddress);
            client.play();
            if (!client.wantsToPlayAgain()) {
                break;
            }
        }
    }
}
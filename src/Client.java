import sun.font.GlyphDisposedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client{

    private SetGui setGui;
    private boolean connected=false;

    private Socket socket;

    private BufferedReader in;
    private PrintWriter out;
    static String playerName;
    static String hostName;
    /**
     * Constructs the client by connecting to a server, laying out the
     * GUI and registering GUI listeners.
     */
    public Client() throws Exception
    {
        // Layout GUI
        setGui = new SetGui(this,out,in);
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
    private void play() throws Exception {
        String response;
        try {

            int PORT = 9090;
            socket = new Socket(hostName, PORT);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            setGui.setConn(out,in);
            setGui.setButtonsDisabled();


            while (true)
            {
                response = in.readLine();
                if (response.startsWith("SUBMITNAME")) {
                    out.println(playerName);
                } else if (response.startsWith("INVALID_NAME")) {
                    JOptionPane.showMessageDialog(null, "Name already taken\nchoose another one");
                    SetupWindow setupWindow = new SetupWindow();
                } else if (response.startsWith("NAMEACCEPTED")) {
                    connected = true;
                    JOptionPane.showMessageDialog(null, "CONNECTED");
                    setGui.setButtonsEnabled();

                    break;
                }
            }

            while (true)
            {
                if (response.startsWith("VALID_MOVE")) {
                    setGui.messageLabel.setText("Valid move, wait for next turn.");
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    int playerID = Integer.parseInt(response.substring(15, 16)); //place where opponent moved
                    int location = Integer.parseInt(response.substring(17));
                    /*
                     * TODO: Replace opponents pawn to right position.
                     */
                } else if (response.startsWith("VICTORY")) {
                    setGui.messageLabel.setText("You win");
                    /*
                     * TODO: * Ask player about playing again:
                     *       * If all everyone chooses YES run new game.
                     *       * If someone choose NO close game window and back to lobby.
                     */
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    setGui.messageLabel.setText("You lose");
                    /*
                     * TODO: * Ask player about playing again:
                     *       * If all everyone chooses YES run new game.
                     *       * If someone choose NO close game window and back to lobby.
                     */
                    break;
                } else if (response.startsWith("MESSAGE")) {
                    setGui.messageLabel.setText(response.substring(8));
                }
            }
            out.println("QUIT");
        } finally {
            socket.close();
        }
    }

    private boolean wantsToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(null,
                "Want to play again?",
                "Chinese checkers ",
                JOptionPane.YES_NO_OPTION);
        setGui.frame.dispose();
        return response == JOptionPane.YES_OPTION;
    }

    /**
     * Runs the client as an application.
     */
    public static void main(String[] args) throws Exception
    {
       SetupWindow setupWindow = new SetupWindow();


        while (true) {
            Client client = new Client();
            client.play();
            if (!client.wantsToPlayAgain()) {
                break;
            }
        }


    }
}
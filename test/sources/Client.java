package sources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client{

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
            while (true) {
                response = in.readLine();
                if (response.startsWith("VALID_MOVE")) {
                    messageLabel.setText("Valid move, wait for next turn.");
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    int playerID = Integer.parseInt(response.substring(15, 16)); //place where opponent moved
                    int location = Integer.parseInt(response.substring(17));
                    /*
                     * TODO: Replace opponents pawn to right position.
                     */
                } else if (response.startsWith("VICTORY")) {
                    messageLabel.setText("You win");
                    /*
                     * TODO: * Ask player about playing again:
                     *       * If all everyone chooses YES run new game.
                     *       * If someone choose NO close game window and back to lobby.
                     */
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    messageLabel.setText("You lose");
                    /*
                     * TODO: * Ask player about playing again:
                     *       * If all everyone chooses YES run new game.
                     *       * If someone choose NO close game window and back to lobby.
                     */
                    break;
                } else if (response.startsWith("MESSAGE")) {
                    messageLabel.setText(response.substring(8));
                }
            }
            out.println("QUIT");
        } finally {
            socket.close();
        }
    }

    private boolean wantsToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(frame,
                "Want to play again?",
                "Chinese checkers ",
                JOptionPane.YES_NO_OPTION);
        frame.dispose();
        return response == JOptionPane.YES_OPTION;
    }


    JFrame frame = new JFrame();
    JLabel messageLabel = new JLabel("");

    private JLabel logoText;
    private JLabel gameTypeText;
    private JButton twoPlayerButton;
    private JButton threePlayerButton;
    private JButton fourPlayerButton;
    private JButton sixPlayerButton;
    private JButton instructionButton;
    private JButton exitButton;

    public class SetGui implements ActionListener{
        {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle(messageLabel.getText());

            //construct components
            logoText = new JLabel("Chinese Checkers!", SwingConstants.CENTER);
            gameTypeText = new JLabel("Play multiplayer:", SwingConstants.CENTER);
            twoPlayerButton = new JButton("2 Players");
            threePlayerButton = new JButton("3 Players");
            fourPlayerButton = new JButton("4 Players");
            sixPlayerButton = new JButton("6 Players");
            instructionButton = new JButton("How to play?");
            exitButton = new JButton("Exit");

            logoText.setFont(logoText.getFont().deriveFont(54f));
            gameTypeText.setFont(gameTypeText.getFont().deriveFont(20f));

            //adjust size and set layout
            frame.setSize(640, 480);
            frame.setResizable(false);
            frame.setLocation(250,50);
            frame.setLayout(null);

            //add components
            frame.add(logoText);
            frame.add(gameTypeText);
            frame.add(twoPlayerButton);
            frame.add(threePlayerButton);
            frame.add(fourPlayerButton);
            frame.add(sixPlayerButton);
            frame.add(instructionButton);
            frame.add(exitButton);

            //set component bounds
            logoText.setBounds(0, 0, 640, 55);
            gameTypeText.setBounds(60, 110, 165, 50);
            twoPlayerButton.setBounds(80, 185, 130, 40);
            threePlayerButton.setBounds(80, 245, 130, 40);
            fourPlayerButton.setBounds(80, 300, 130, 40);
            sixPlayerButton.setBounds(80, 355, 130, 40);
            instructionButton.setBounds(345, 185, 175, 55);
            exitButton.setBounds(420, 370, 100, 25);

            twoPlayerButton.addActionListener(this);
            threePlayerButton.addActionListener(this);
            fourPlayerButton.addActionListener(this);
            sixPlayerButton.addActionListener(this);
            instructionButton.addActionListener(this);
            exitButton.addActionListener(this);

            frame.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == twoPlayerButton) {
                out.println("GAME_TYPE" + 2);
                frame.dispose();
                showWaitingRoom(2);
            } else if (source == threePlayerButton) {
                out.println("GAME_TYPE" + 3);
                frame.dispose();
                showWaitingRoom(3);
            } else if (source == fourPlayerButton) {
                out.println("GAME_TYPE" + 4);
                frame.dispose();
                showWaitingRoom(4);
            } else if (source == sixPlayerButton) {
                out.println("GAME_TYPE" + 6);
                frame.dispose();
                showWaitingRoom(6);
            } else if (source == instructionButton) {
                JOptionPane.showMessageDialog(null,
                        "1. W jednej grze uczestniczy 2, 3, 4 lub 6 graczy. Kazdy gracz ma 10 pionów\n" +
                        "2. Gra toczy się na planszy w kształcie szesciopromiennej gwiazdy. Kazdy promień zawiera 10 pól, w których\n" +
                        "początkowo umieszczane są piony. Wewnętrzny szesciokęt ma 61 pól, na każdy jego bok składa się z 5 pól.\n" +
                        "3. Celem kazdego gracza jest umieszczenie wszystkich swoich pionów w przeciwległym promieniu. Wygrywa\n" +
                        "gracz, który dokona tego jako pierwszy. Pozostali gracze mogą kontynuowac grę walcząc o kolejne miejsca.\n" +
                        "4. Gracz rozpoczynający grę wybierany jest losowo. Następnie gracze wykonują ruchy po kolei zgodnie z\n" +
                        "kierunkiem ruchu wskazówek zegara.\n" +
                        "5. Ruch polega na przesunięciu piona na sąsiednie puste pole lub na przeskakiwaniu pionem innych pionów\n" +
                        "(swoich lub przeciwnika) tak jak w warcabach, ale bez mozliwości zbijania pionów. Ruch może odbywać\n" +
                        "się w dowolnym kierunku, poza przypadkiem, gdy pion wejdzie do docelowego (przeciwległego) promienia.\n" +
                        "Wówczas pion moze wykonywaę ruchy jedynie w obrębie tego promienia.\n" +
                        "6. Dopuszczalne jest pozostawianie swoich pionów w promieniu docelowym innego gracza (tzw. blokowanie).\n" +
                        "7. Gracz moze zrezygnować z ruchu w danej turze.", "How to play?", JOptionPane.INFORMATION_MESSAGE);
            } else if (source == exitButton) {
                System.exit(0);
            }
        }

        public void showWaitingRoom(int k) {
            String tempText;

            JFrame waitingRoomFrame = new JFrame();
            ArrayList<JLabel> playerLabels = new ArrayList<JLabel>();
            playerLabels.add(new JLabel ("Player 1"));
            playerLabels.get(0).setBackground(Color.green);
            playerLabels.get(0).setOpaque(true);

            waitingRoomFrame.setSize(865, 480);
            waitingRoomFrame.setLocation(250,50);
            waitingRoomFrame.setResizable(false);
            waitingRoomFrame.setLayout (null);

            for(int i=1;i<=k;i++) {
                tempText = "Waiting for Player " + i + "...";
                if(i>1) {
                    playerLabels.add(new JLabel(tempText));
                    playerLabels.get(i - 1).setBackground(Color.red);
                }
                playerLabels.get(i-1).setBounds (20, 25+(60*(i-1)), 185, 50);
                playerLabels.get(i-1).setOpaque(true);
                waitingRoomFrame.add(playerLabels.get(i-1));
            }

            waitingRoomFrame.setVisible(true);
        }
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
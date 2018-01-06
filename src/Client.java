
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client{

    private SetGui setGui;
    private boolean connected=false;

    private WaitingRoomFrame waitingRoomFrame;
    private ChooseGameFrame chooseGameFrame;
    private ChooseLobby chooseLobby;
    private GameFrame gameFrame;


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
        makeGui();
        setGui.setButtonsDisabled();

    }

    void setWaitingRoomFrame(int k)
    {
        waitingRoomFrame = new WaitingRoomFrame(k,out,this);
    }

    void setChooseGameFrame()
    {
        chooseGameFrame = new ChooseGameFrame(out,this);
    }

    void setChooseLobby()
    {
        chooseLobby = new ChooseLobby(out,this);
    }

    void makeGui()
    {
        setGui = new SetGui(this);
    }

    private void createGame()
    {
        gameFrame = new GameFrame(2);

        //TODO: Give type to gameframe from server
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

            setGui.setButtonsDisabled();


            while (true)
            {
                response = in.readLine();
                if (response.startsWith("SUBMITNAME")) {
                    out.println(playerName);
                } else if (response.startsWith("INVALID_NAME")) {
                    JOptionPane.showMessageDialog(null, "Name already taken\nchoose another one");
                    new SetupWindow();
                } else if (response.startsWith("NAMEACCEPTED")) {
                    connected = true;
                    JOptionPane.showMessageDialog(null, "CONNECTED");
                    setGui.setButtonsEnabled();

                    break;
                }
            }

            while (true)
            {
                response = in.readLine();
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
                } else if (response.startsWith("PLAYER_REFRESH"))
                {
                    waitingRoomFrame.getList();

                    System.out.println("UP");
                }
                else if (response.startsWith("START_GAME"))
                {
                    waitingRoomFrame.closeWindow();
                    createGame();

                }
                else if(response.startsWith("NULL_LOBBY_SIZE"))
                {
                    String temp = "No games found. Create a new game";
                    //System.out.println(temp);
                    chooseLobby.list.addElement(temp);
                    chooseLobby.joinButton.setEnabled(false);
                    chooseLobby.lobbyList.setModel(chooseLobby.list);
                }
                else if(response.startsWith("LOBBY_SIZE"))
                {
                    int lobbySize = Integer.parseInt(response.substring(10));
                    for(int i=0; i< lobbySize; i++)
                    {
                        String tempId = in.readLine();
                        String tempType = in.readLine();
                        String tempNOP = in.readLine();
                        Lobby tempLobby = new Lobby(Integer.parseInt(tempType),Integer.parseInt(tempId));
                        tempLobby.setNumberOfPlayers(Integer.parseInt(tempNOP));
                        chooseLobby.lobbyListHelper.add(tempLobby);
                        String temp ="Room: "+tempId+" Type: "+tempType+" Number of Players: "+tempNOP;
                        //System.out.println(temp);
                        chooseLobby.list.addElement(temp);
                        chooseLobby.joinButton.setEnabled(true);
                    }
                    chooseLobby.lobbyList.setModel(chooseLobby.list);
                }
                else if(response.startsWith("ROOM_INFO"))
                {
                    int lobbyId;
                    int numOfPlayers =0;
                    ArrayList<String> playerList = new ArrayList<>();
                    try {
                        lobbyId = Integer.parseInt(in.readLine());
                        numOfPlayers = Integer.parseInt(in.readLine());
                        if (numOfPlayers != 0) {
                            for (int i = 0; i < numOfPlayers; i++) {
                                playerList.add(in.readLine());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println(numOfPlayers);

                    //---------------
                    String tempText;
                    for (int i = 0; i < numOfPlayers; i++) {
                        tempText = playerList.get(i);
                        waitingRoomFrame.list.add(i,tempText);
                    }

                    for (int i = numOfPlayers; i < waitingRoomFrame.size; i++) {
                        tempText = "Waiting for Player " + (i + 1) + "...";
                        waitingRoomFrame.list.add(i, tempText);
                    }

                    waitingRoomFrame.pList.setModel(waitingRoomFrame.list);
                    playerList.clear();

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
        Client client = new Client();
        new SetupWindow();

        client.play();

    }
}
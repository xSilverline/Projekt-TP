
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client
{

    private SetGui setGui;
    private boolean connected = false;

    private WaitingRoomFrame waitingRoomFrame;
    private ChooseGameFrame chooseGameFrame;
    private ChooseLobby chooseLobby;
    private GameRunFrame gameRunFrame;
    private boolean isChoosingLobby = false;


    private Socket socket;

    private BufferedReader in;
    private PrintWriter out;
    static String playerName;
    static String hostName;
    private ArrayList<String> playerList;

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
        waitingRoomFrame = new WaitingRoomFrame(k, out, this);
    }

    void setChooseGameFrame()
    {
        chooseGameFrame = new ChooseGameFrame(out, this);
    }

    void setChooseLobby()
    {
        chooseLobby = new ChooseLobby(out, this);
    }

    void setChoosingLobby()
    {
        isChoosingLobby = true;
    }

    void setNotChoosing()
    {
        isChoosingLobby = false;
    }

    void makeGui()
    {
        setGui = new SetGui(this);
    }

    private void createGame(int k)
    {
        gameRunFrame = new GameRunFrame(k,this,out,playerList);
    }

    void closeGame()
    {
        gameRunFrame.dispose();
        makeGui();
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
                if(response == null) continue;

                if (response.startsWith("SUBMITNAME")) {
                    out.println(playerName);
                } else if (response.startsWith("INVALID_NAME")) {
                    JOptionPane.showMessageDialog(null, "Name already taken\nchoose another one");
                    new SetupWindow();
                } else if (response.startsWith("NAMEACCEPTED")) {
                    connected = true;
                    JOptionPane.showMessageDialog(null, "CONNECTED");
                    setGui.setButtonsEnabled();
                }

                if (response.startsWith("VALID_MOVE")) { // VALID_MOVE XX YY XX YY
                    int pX = Integer.parseInt(response.substring(11,13));
                    int pY = Integer.parseInt(response.substring(14,16));
                    int toX = Integer.parseInt(response.substring(17,19));
                    int toY = Integer.parseInt(response.substring(20,22));
                    gameRunFrame.swap(pX, pY, toX, toY);
                } else if (response.startsWith("SKIP")) {
                    gameRunFrame.next();
                } else if (response.startsWith("END")) {
                    String winner = response.substring(3);
                    gameRunFrame.end(winner);
                    break;
                }

                if (response.startsWith("VALID_MOVE")) {
                    setGui.messageLabel.setText("Valid move, wait for next turn.");
                } else if (response.startsWith("MESSAGE")) {
                    setGui.messageLabel.setText(response.substring(8));
                } else if (response.startsWith("PLAYER_REFRESH"))
                {
                    waitingRoomFrame.getList();
                }else if(response.startsWith("LOBBY_REFRESH") && isChoosingLobby)
                {
                    chooseLobby.getList();
                }
                else if (response.startsWith("START_GAME"))
                {
                    waitingRoomFrame.closeWindow();
                    createGame(Integer.parseInt(response.substring(10)));
                }
                else if(response.startsWith("WAIT_FOR_PLAYERS"))
                {
                    waitingRoomFrame.showWaitMessage();
                }
                else if(response.startsWith("NULL_LOBBY_SIZE"))
                {
                    String temp = "No games found. Create a new game";
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
                        chooseLobby.list.addElement(temp);
                        //chooseLobby.joinButton.setEnabled(true);
                        chooseLobby.lobbyList.addListSelectionListener(chooseLobby);
                    }
                    chooseLobby.lobbyList.setModel(chooseLobby.list);
                }
                else if(response.startsWith("ROOM_INFO"))
                {
                    int lobbyId;
                    int numOfPlayers =0;
                    playerList = new ArrayList<>();

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
                    //---------------
                    String tempText;
                    for (int i = 0; i < numOfPlayers; i++) {
                        tempText = playerList.get(i);
                        waitingRoomFrame.playerLabels.get(i).setText(tempText);
                        waitingRoomFrame.playerLabels.get(i).setBackground(Color.GREEN);
                        //waitingRoomFrame.list.add(i,tempText);
                    }

                    for (int i = numOfPlayers; i < waitingRoomFrame.size; i++) {
                        tempText = "Waiting for Player " + (i + 1) + "...";
                        waitingRoomFrame.playerLabels.get(i).setText(tempText);
                        waitingRoomFrame.playerLabels.get(i).setBackground(Color.ORANGE);
                        //waitingRoomFrame.list.add(i, tempText);
                    }

                    waitingRoomFrame.pList.setModel(waitingRoomFrame.list);
                }
            }
            out.println("QUIT");

        } finally {
            socket.close();
        }
    }

    String getPlayerName() {
        return playerName;
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
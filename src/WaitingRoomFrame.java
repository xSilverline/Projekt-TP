import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WaitingRoomFrame extends NewWindowFrame
{
    private JButton returnButton;
    private JButton runButton;
    private JButton refreshButton;
    private JFrame waitingRoomFrame;

    int size;
    private boolean readyPlayer;

    JList pList;
    DefaultListModel<String> list;

    ArrayList<JLabel> playerLabels;

    private PrintWriter out;
    private Client client;

    WaitingRoomFrame(int k, PrintWriter out, Client client)
    {
        this.size=k;
        this.out=out;
        this.client=client;

        makeGui();
        getList();
        readyPlayer = false;

        //refreshPlayers();
    }

    void makeGui()
    {
        waitingRoomFrame = new JFrame();
        waitingRoomFrame.setUndecorated(true);

        // playerLabels.add(new JLabel (playerList.get(0)));
        // playerLabels.get(0).setBackground(Color.green);
        // playerLabels.get(0).setOpaque(true);

        waitingRoomFrame.setSize(1366, 768);
        waitingRoomFrame.setLocation(250,50);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        waitingRoomFrame.setLocation(dim.width/2-waitingRoomFrame.getSize().width/2, dim.height/2-waitingRoomFrame.getSize().height/2);
        waitingRoomFrame.getContentPane().setBackground(new Color(74, 73, 75));
        waitingRoomFrame.setResizable(false);
        waitingRoomFrame.setLayout (null);
        runButton = new JButton("Ready");
        waitingRoomFrame.add(runButton);
        runButton.setBounds(1200,200,150,50);
        runButton.setBackground(Color.GREEN);
        returnButton = new JButton("Return");
        waitingRoomFrame.add(returnButton);
        returnButton.setBounds(1200,700,150,50);

        refreshButton = new JButton("Refresh");
        waitingRoomFrame.add(refreshButton);
        refreshButton.setBounds(1200,300,150,50);
        pList=new JList();
        pList.setFont(pList.getFont().deriveFont(30f));
        pList.setForeground(Color.WHITE);
        pList.setBackground(new Color(0x585757));
        waitingRoomFrame.add(pList);
       // pList.setBounds(30,184,766,400);

        playerLabels = new ArrayList<>();
        for(int i=0;i<size;i++)
        {
            playerLabels.add(new JLabel());
            playerLabels.get(i).setBounds (20, 25+(60*(i)), 185, 50);
            playerLabels.get(i).setBackground(Color.GRAY);
            playerLabels.get(i).setOpaque(true);
            waitingRoomFrame.add(playerLabels.get(i));
        }

        runButton.addActionListener(this);
        returnButton.addActionListener(this);
        refreshButton.addActionListener(this);
        waitingRoomFrame.setVisible(true);
    }

    void getList()
    {

            list = new DefaultListModel<>();
            out.println("GET_LOBBY_INFO");
          /*  try {
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
            System.out.println(numOfPlayers);

            //---------------
            String tempText;
            for (int i = 0; i < numOfPlayers; i++) {
                tempText = playerList.get(i);
                list.add(i,tempText);

            /*
            playerLabels.add(new JLabel(tempText));
            playerLabels.get(i).setBackground(Color.green);
            playerLabels.get(i).setBounds (20, 25+(60*(i)), 185, 50);
            playerLabels.get(i).setOpaque(true);
            //waitingRoomFrame.add(playerLabels.get(i));
            }

            for (int i = numOfPlayers; i < size; i++) {
                tempText = "Waiting for Player " + (i + 1) + "...";
                list.add(i,tempText);
           /* playerLabels.add(new JLabel(tempText));
            playerLabels.get(i).setBackground(Color.red);

            playerLabels.get(i).setBounds (20, 25+(60*(i)), 185, 50);
            playerLabels.get(i).setOpaque(true);
            //waitingRoomFrame.add(playerLabels.get(i));
            }
            pList.setModel(list);*/
    }

    @Override
    void closeWindow()
    {
        waitingRoomFrame.dispose();
    }

    void showWaitMessage()
    {
        JOptionPane.showMessageDialog(null,"Waiting for other players!\n When everybody ready, game starts!","Please Wait a while...",JOptionPane.INFORMATION_MESSAGE);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source==returnButton)
        {
            out.println("RETURN_FROM_LOBBY");
            waitingRoomFrame.dispose();
            client.makeGui();
        }
        else if(source == refreshButton)
        {

            //new WaitingRoomFrame(size,in,out,client);

            getList();
        }
        else if(source == runButton)
        {

            if(readyPlayer)
            {
                out.println("NOT_READY_PLAYER");
                runButton.setText("Ready");
                runButton.setBackground(Color.GREEN);
                readyPlayer = false;
            }
            else
            {
                out.println("READY_PLAYER");
                readyPlayer = true;
                runButton.setBackground(Color.RED);
                runButton.setText("Leave");
            }


        }

    }
}

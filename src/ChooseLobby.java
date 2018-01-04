import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;


public class ChooseLobby implements ActionListener
{
    private JButton joinButton;
    private JButton returnButton;
    private JButton refreshButton;
    private ArrayList<Lobby> lobbyListHelper = new ArrayList<>();
    private JFrame chooseLobbyFrame = new JFrame();

    private PrintWriter out;
    private BufferedReader in;

    private JList lobbyList;
    private DefaultListModel<String> list = new DefaultListModel<>();


    private Client client;

    ChooseLobby(BufferedReader in, PrintWriter out, Client client)
    {
        this.in = in;
        this.client=client;
        this.out=out;

        makeGui();
        getList();
    }

    private void makeGui()
    {
        chooseLobbyFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        chooseLobbyFrame.setUndecorated(true);

        chooseLobbyFrame.setSize(1366, 768);
        chooseLobbyFrame.setResizable(false);
        //chooseLobbyFrame.setLocation(250,50);
        chooseLobbyFrame.setLayout(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        chooseLobbyFrame.setLocation(dim.width/2-chooseLobbyFrame.getSize().width/2, dim.height/2-chooseLobbyFrame.getSize().height/2);
        chooseLobbyFrame.getContentPane().setBackground(new Color(74, 73, 75));

        joinButton = new JButton("Join");
        returnButton = new JButton("Return");
        refreshButton = new JButton("Refresh List");

        chooseLobbyFrame.add(refreshButton);
        chooseLobbyFrame.add(joinButton);
        chooseLobbyFrame.add(returnButton);

        joinButton.setBounds(1200,50,150,50);
        returnButton.setBounds(1200,700,150,50);
        refreshButton.setBounds(30,50,150,50);

        returnButton.addActionListener(this);
        joinButton.addActionListener(this);
        refreshButton.addActionListener(this);

        chooseLobbyFrame.setVisible(true);

        lobbyList = new JList();
        lobbyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lobbyList.setLayoutOrientation(JList.VERTICAL);
        lobbyList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(lobbyList);
        chooseLobbyFrame.add(listScroller);

        listScroller.setBounds(30,184,766,400);
    }

    private void getList()
    {
        out.println("JOIN_GAME_GET_LOBBY");
        list.clear();
        try {
            int lobbySize = Integer.parseInt(in.readLine());
            if(lobbySize != 0)
            {
                for(int i=0; i< lobbySize; i++)
                {
                    String tempId = in.readLine();
                    String tempType = in.readLine();
                    String tempNOP = in.readLine();
                    Lobby tempLobby = new Lobby(Integer.parseInt(tempType),Integer.parseInt(tempId));
                    tempLobby.setNumberOfPlayers(Integer.parseInt(tempNOP));
                    lobbyListHelper.add(tempLobby);
                    String temp ="Room: "+tempId+" Type: "+tempType+" Number of Players: "+tempNOP;
                    //System.out.println(temp);
                    list.addElement(temp);
                    joinButton.setEnabled(true);
                }
            }
            else
            {
                String temp = in.readLine();
                //System.out.println(temp);
                list.addElement(temp);
                joinButton.setEnabled(false);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        lobbyList.setModel(list);

    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
         if (source == returnButton)
        {
            out.println("RETURN");
            chooseLobbyFrame.dispose();
            new SetGui(client,out,in);
        }
        else if (source == joinButton)
         {

            String selectedLobby = list.getElementAt(lobbyList.getSelectedIndex());
            String tempId = selectedLobby.substring(5,selectedLobby.indexOf("Type")).trim();
            for(Lobby l: lobbyListHelper)
            {
                if(l.getId() == Integer.parseInt(tempId))
                {
                    if(l.getGameType()>l.getWrittenNumberOfPlayers())
                    {
                        out.println("JOIN_TO"+tempId);
                        new WaitingRoomFrame(l.getGameType(),in,out,client);
                        chooseLobbyFrame.dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Room is full\n Choose another one!");
                }
            }


         }
         else if (source == refreshButton)
         {
             getList();
         }
    }
}

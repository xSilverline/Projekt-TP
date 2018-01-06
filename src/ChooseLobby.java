import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.PrintWriter;
import java.util.ArrayList;


public class ChooseLobby extends NewWindowFrame
{
    JButton joinButton;
    private JButton returnButton;
    private JButton refreshButton;
    ArrayList<Lobby> lobbyListHelper = new ArrayList<>();
    private JFrame chooseLobbyFrame = new JFrame();

    private PrintWriter out;

    JList lobbyList;
    DefaultListModel<String> list = new DefaultListModel<>();


    private Client client;

    ChooseLobby(PrintWriter out, Client client)
    {
        this.client=client;
        this.out=out;

        makeGui();
        getList();
    }

    void makeGui()
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

    void getList()
    {
        out.println("JOIN_GAME_GET_LOBBY");
        list.clear();

    }

    @Override
    void closeWindow()
    {
        chooseLobbyFrame.dispose();
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
         if (source == returnButton)
        {
            out.println("RETURN");
            chooseLobbyFrame.dispose();
            client.makeGui();
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
                        client.setWaitingRoomFrame(l.getGameType());
                        //new WaitingRoomFrame(l.getGameType(),in,out,client);
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

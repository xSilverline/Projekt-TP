import javax.swing.*;
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
    private ArrayList<String> tempList = new ArrayList<String>();
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

        chooseLobbyFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        chooseLobbyFrame.setSize(800, 600);
        chooseLobbyFrame.setResizable(false);
        chooseLobbyFrame.setLocation(250,50);
        chooseLobbyFrame.setLayout(null);

        joinButton = new JButton("Join");
        returnButton = new JButton("Return");
        refreshButton = new JButton("Refresh List");

        chooseLobbyFrame.add(refreshButton);
        chooseLobbyFrame.add(joinButton);
        chooseLobbyFrame.add(returnButton);

        joinButton.setBounds(620,10,150,50);
        returnButton.setBounds(325,490,150,50);
        refreshButton.setBounds(30,10,150,50);

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

        listScroller.setBounds(30,70,740,390);

        getList();
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
                    String temp = in.readLine();
                    //System.out.println(temp);
                    list.addElement(temp);
                }
            }
            else
            {
                String temp = in.readLine();
                //System.out.println(temp);
                list.addElement(temp);
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
            SetGui setGui = new SetGui(client,out,in);
        }
        else if (source == joinButton)
         {

            // WaitingRoomFrame waitingRoomFrame = new WaitingRoomFrame()
         }
         else if (source == refreshButton)
         {
             getList();
         }
    }
}

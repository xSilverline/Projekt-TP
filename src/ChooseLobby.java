import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;

import java.io.PrintWriter;


public class ChooseLobby implements ActionListener
{
    private JButton joinButton;
    private JButton returnButton;
    private JList lobbyList;
    private DefaultListModel list = new DefaultListModel();
  //  private ArrayList<String> tempObjectList = new ArrayList<String>();
    private JFrame chooseLobbyFrame = new JFrame();

    private PrintWriter out;
    private BufferedReader in;

    private Client client;

    ChooseLobby(BufferedReader in, PrintWriter out, Client client)
    {
        this.in = in;
        this.client=client;
        this.out=out;

        chooseLobbyFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        chooseLobbyFrame.setSize(640, 480);
        chooseLobbyFrame.setResizable(false);
        chooseLobbyFrame.setLocation(250,50);
        chooseLobbyFrame.setLayout(null);

        lobbyList = new JList(list);
        lobbyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lobbyList.setLayoutOrientation(JList.VERTICAL);
        lobbyList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(lobbyList);

        joinButton = new JButton("Join");
        returnButton = new JButton("Return");

        chooseLobbyFrame.add(joinButton);
        chooseLobbyFrame.add(returnButton);
        chooseLobbyFrame.add(listScroller);


        listScroller.setBounds(100,30,420,350);
        joinButton.setBounds(250,400,150,50);
        returnButton.setBounds(450,400,130,30);

        chooseLobbyFrame.setVisible(true);

        out.println("GET_LOBBY");

        while(true)
        {
            try
            {
                String tempList = in.readLine();
                if(tempList == "EOL")
                {
                    if(list.isEmpty())
                    {
                        list.addElement("No games");
                    }
                    break;
                }
                else
                {
                    list.addElement(tempList);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
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
             out.println("x");

             chooseLobbyFrame.dispose();
            // WaitingRoomFrame waitingRoomFrame = new WaitingRoomFrame()
         }
    }
}

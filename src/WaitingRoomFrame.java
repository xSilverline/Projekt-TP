import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WaitingRoomFrame implements ActionListener
{
    private JButton returnButton;
    private JFrame waitingRoomFrame;
    private int lobbyId;
    private int numOfPlayers;
    private ArrayList<String> playerList = new ArrayList<String>();
    private BufferedReader in;

    private PrintWriter out;
    private Client client;

    WaitingRoomFrame(int k, BufferedReader in, PrintWriter out, Client client)
    {

        out.println("GET_LOBBY_INFO");
        try {
            lobbyId= Integer.parseInt(in.readLine());
            numOfPlayers = Integer.parseInt(in.readLine());
            if(numOfPlayers !=0)
            {
                for(int i=0;i<numOfPlayers;i++)
                {
                    playerList.add(in.readLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        String tempText;


        this.in = in;
        this.out=out;
        this.client=client;

        waitingRoomFrame = new JFrame();
        waitingRoomFrame.setUndecorated(true);
        ArrayList<JLabel> playerLabels = new ArrayList<JLabel>();
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
        returnButton = new JButton("Return");
        waitingRoomFrame.add(returnButton);
        returnButton.setBounds(1200,700,150,50);

        returnButton.addActionListener(this);

        for(int i=0;i<=numOfPlayers-1;i++)
        {
            tempText = playerList.get(i);
            playerLabels.add(new JLabel(tempText));
            playerLabels.get(i).setBackground(Color.green);
            playerLabels.get(i).setBounds (20, 25+(60*(i)), 185, 50);
            playerLabels.get(i).setOpaque(true);
            waitingRoomFrame.add(playerLabels.get(i));
        }


        for(int i=numOfPlayers;i<k;i++)
        {
            tempText = "Waiting for Player " + (i+1) + "...";

                playerLabels.add(new JLabel(tempText));
                playerLabels.get(i).setBackground(Color.red);

            playerLabels.get(i).setBounds (20, 25+(60*(i)), 185, 50);
            playerLabels.get(i).setOpaque(true);
            waitingRoomFrame.add(playerLabels.get(i));
        }

        waitingRoomFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source==returnButton)
        {
            out.println("RETURN_FROM_LOBBY");
            waitingRoomFrame.dispose();
            new SetGui(client,out,in);
        }
    }
}

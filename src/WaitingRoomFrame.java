import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WaitingRoomFrame  implements ActionListener
{
    private JButton returnButton;
    private JFrame waitingRoomFrame;
    private int lobbyId;
    private BufferedReader in;

    private PrintWriter out;
    private Client client;
    WaitingRoomFrame(int k, BufferedReader in,PrintWriter out, Client client)
    {
        out.println("GET_LOBBY_ID");
        try {
            lobbyId= Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tempText;


        this.in = in;
        this.out=out;
        this.client=client;

        waitingRoomFrame = new JFrame();
        ArrayList<JLabel> playerLabels = new ArrayList<JLabel>();
        playerLabels.add(new JLabel ("Player 1"));
        playerLabels.get(0).setBackground(Color.green);
        playerLabels.get(0).setOpaque(true);

        waitingRoomFrame.setSize(800, 600);
        waitingRoomFrame.setLocation(250,50);
        waitingRoomFrame.setResizable(false);
        waitingRoomFrame.setLayout (null);
        returnButton = new JButton("Return");
        waitingRoomFrame.add(returnButton);
        returnButton.setBounds(600,400,150,50);

        returnButton.addActionListener(this);


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
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source==returnButton)
        {
            out.println("RETURN_FROM_LOBBY");
            waitingRoomFrame.dispose();
            ChooseGameFrame chooseGameFrame = new ChooseGameFrame(in,out,client);
        }
    }
}

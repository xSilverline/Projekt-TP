

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

public class ChooseGameFrame implements ActionListener {
    private JButton twoPlayerButton;
    private JButton threePlayerButton;
    private JButton fourPlayerButton;
    private JButton sixPlayerButton;
    private JButton returnButton;
    private JFrame chooseGameFrame = new JFrame();

    private PrintWriter out;
    private BufferedReader in;

    private Client client;

    ChooseGameFrame(BufferedReader in,PrintWriter out, Client client)
    {
        this.client=client;
        this.in = in;

        JLabel chooseText = new JLabel("Choose Game Type",SwingConstants.CENTER);
        chooseText.setFont(chooseText.getFont().deriveFont(20f));
        this.out=out;
        chooseGameFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        chooseGameFrame.setUndecorated(true);

        chooseGameFrame.setSize(1366, 768);
        chooseGameFrame.setResizable(false);
        //chooseGameFrame.setLocation(250,50);
        chooseGameFrame.setLayout(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        chooseGameFrame.setLocation(dim.width/2-chooseGameFrame.getSize().width/2, dim.height/2-chooseGameFrame.getSize().height/2);

        twoPlayerButton = new JButton("2 Players");
        threePlayerButton = new JButton("3 Players");
        fourPlayerButton = new JButton("4 Players");
        sixPlayerButton = new JButton("6 Players");
        returnButton = new JButton("Return");

        chooseGameFrame.add(twoPlayerButton);
        chooseGameFrame.add(threePlayerButton);
        chooseGameFrame.add(fourPlayerButton);
        chooseGameFrame.add(sixPlayerButton);
        chooseGameFrame.add(chooseText);
        chooseGameFrame.add(returnButton);

        chooseText.setBounds(205, 10, 230, 50);
        twoPlayerButton.setBounds(205, 65, 230, 60);
        threePlayerButton.setBounds(205, 145, 230, 60);
        fourPlayerButton.setBounds(205, 225, 230, 60);
        sixPlayerButton.setBounds(205, 305, 230, 60);
        returnButton.setBounds(450,400,130,30);

        twoPlayerButton.addActionListener(this);
        threePlayerButton.addActionListener(this);
        fourPlayerButton.addActionListener(this);
        sixPlayerButton.addActionListener(this);
        returnButton.addActionListener(this);

        chooseGameFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source == twoPlayerButton) {
            out.println("NEW_GAME_TYPE" + 2);
            chooseGameFrame.dispose();
            WaitingRoomFrame waitingRoomFrame = new WaitingRoomFrame(2,in,out,client);
        } else if (source == threePlayerButton) {
            out.println("NEW_GAME_TYPE" + 3);
            chooseGameFrame.dispose();
            WaitingRoomFrame waitingRoomFrame = new WaitingRoomFrame(3,in,out,client);
        } else if (source == fourPlayerButton) {
            out.println("NEW_GAME_TYPE" + 4);
            chooseGameFrame.dispose();
            WaitingRoomFrame waitingRoomFrame = new WaitingRoomFrame(4,in,out,client);
        } else if (source == sixPlayerButton) {
            out.println("NEW_GAME_TYPE" + 6);
            chooseGameFrame.dispose();
            WaitingRoomFrame waitingRoomFrame = new WaitingRoomFrame(6,in,out,client);
        } else if (source == returnButton)
        {
            out.println("RETURN");
            chooseGameFrame.dispose();
            SetGui setGui = new SetGui(client,out,in);
        }
    }
}
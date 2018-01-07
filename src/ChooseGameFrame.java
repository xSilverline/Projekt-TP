

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;

public class ChooseGameFrame extends NewWindowFrame {
    private ButtonGui twoPlayerButton;
    private ButtonGui threePlayerButton;
    private ButtonGui fourPlayerButton;
    private ButtonGui sixPlayerButton;
    private ButtonGui returnButton;
    private JFrame chooseGameFrame = new JFrame();

    private PrintWriter out;
    private Client client;

    ChooseGameFrame(PrintWriter out, Client client)
    {
        this.client=client;
        this.out = out;

        makeGui();
    }
    void makeGui()
    {
        JLabel chooseText = new JLabel("Choose Game Type",SwingConstants.CENTER);
        chooseText.setFont(chooseText.getFont().deriveFont(40f));
        chooseGameFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        chooseGameFrame.setUndecorated(true);

        chooseGameFrame.setSize(1366, 768);
        chooseGameFrame.setResizable(false);
        //chooseGameFrame.setLocation(250,50);
        chooseGameFrame.setLayout(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        chooseGameFrame.setLocation(dim.width/2-chooseGameFrame.getSize().width/2, dim.height/2-chooseGameFrame.getSize().height/2);
        chooseGameFrame.getContentPane().setBackground(new Color(74, 73, 75));
        twoPlayerButton = new ButtonGui("2 Players");
        threePlayerButton = new ButtonGui("3 Players");
        fourPlayerButton = new ButtonGui("4 Players");
        sixPlayerButton = new ButtonGui("6 Players");
        returnButton = new ButtonGui("Return");

        chooseGameFrame.add(twoPlayerButton);
        chooseGameFrame.add(threePlayerButton);
        chooseGameFrame.add(fourPlayerButton);
        chooseGameFrame.add(sixPlayerButton);
        chooseGameFrame.add(chooseText);
        chooseGameFrame.add(returnButton);

        chooseText.setBounds(533, 50, 300, 50);
        twoPlayerButton.setBounds(350, 200, 666, 70);
        threePlayerButton.setBounds(350, 270, 666, 70);
        fourPlayerButton.setBounds(350, 340, 666, 70);
        sixPlayerButton.setBounds(350, 410, 666, 70);
        returnButton.setBounds(350,480,666,70);


        twoPlayerButton.setFont(twoPlayerButton.getFont().deriveFont(30f));
        threePlayerButton.setFont(threePlayerButton.getFont().deriveFont(30f));
        fourPlayerButton.setFont(fourPlayerButton.getFont().deriveFont(30f));
        sixPlayerButton.setFont(sixPlayerButton.getFont().deriveFont(30f));

        twoPlayerButton.addActionListener(this);
        threePlayerButton.addActionListener(this);
        fourPlayerButton.addActionListener(this);
        sixPlayerButton.addActionListener(this);
        returnButton.addActionListener(this);

        chooseGameFrame.setVisible(true);
    }

    @Override
    void getList() {

    }

    @Override
    void closeWindow()
    {
        chooseGameFrame.dispose();
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source == twoPlayerButton) {
            out.println("NEW_GAME_TYPE" + 2);
            chooseGameFrame.dispose();
            client.setWaitingRoomFrame(2);
            //new WaitingRoomFrame(2,in,out,client);

        } else if (source == threePlayerButton) {
            out.println("NEW_GAME_TYPE" + 3);
            chooseGameFrame.dispose();
            client.setWaitingRoomFrame(3);
            //new WaitingRoomFrame(3,in,out,client);

        } else if (source == fourPlayerButton) {
            out.println("NEW_GAME_TYPE" + 4);
            chooseGameFrame.dispose();
            client.setWaitingRoomFrame(4);
            //new WaitingRoomFrame(4,in,out,client);

        } else if (source == sixPlayerButton) {
            out.println("NEW_GAME_TYPE" + 6);
            chooseGameFrame.dispose();
            client.setWaitingRoomFrame(6);
           // new WaitingRoomFrame(6,in,out,client);

        } else if (source == returnButton)
        {
            out.println("RETURN");
            chooseGameFrame.dispose();
            client.makeGui();
        }
    }
}
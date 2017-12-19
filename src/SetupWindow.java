import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class SetupWindow extends JDialog
{
    boolean blockade=true;
    SetupWindow()
    {
        setModal(true);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Setup connection");
        setLayout(null);

        setSize(400,200);
        setLocation(400,300);
        JButton acceptButton = new JButton ("CONNECT");
        final JTextField hostField = new JTextField("localhost", 30);
        final JTextField playerField = new JTextField("Player", 30);
        acceptButton.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                dispose();
                Client.playerName=playerField.getText();
                Client.hostName=hostField.getText();
                blockade=false;
            }
        });

        add( new JLabel ("Set your host and player name:"));
        add(hostField);
        add(playerField);
        add(acceptButton);

        hostField.setBounds(100,50,200,20);
        playerField.setBounds(100,85,200,20);
        acceptButton.setBounds(150,120,100,40);

        setResizable(false);
        setLocation(600,400);
        setVisible(true);

    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class SetupWindow extends JDialog
{

    SetupWindow()
    {
        setModal(true);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Setup connection");
        setLayout( new FlowLayout() );
        JButton acceptButton = new JButton ("CONNECT");
        final JTextField hostField = new JTextField("localhost", 30);
        final JTextField playerField = new JTextField("Player", 30);
        acceptButton.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                Client.hostName = hostField.getText();
                Client.playerName = playerField.getText();
                dispose();
            }
        });
        add( new JLabel ("Set your host and player name:"));
        add(hostField);
        add(playerField);
        add(acceptButton);
        setSize(400,200);
        setResizable(false);
        setLocation(600,400);
        setVisible(true);
        pack();
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class SetupWindow extends JDialog
{
    private JTextField hostField;
    private JTextField playerField;
    SetupWindow()
    {
        setModal(true);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Setup connection");
        setLayout(null);

        setSize(400,200);
        setLocation(400,300);
        JButton acceptButton = new JButton ("CONNECT");
        getContentPane().setBackground(new Color(111, 69, 73));

        hostField = new JTextField("", 30);

        playerField = new JTextField("", 30);
        hostField.setText("localhost");
        playerField.setText("player");

        acceptButton.addActionListener (e -> {
            dispose();
            Client.playerName = playerField.getText();
            Client.hostName = hostField.getText();
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

package sources.src;

import javax.swing.*;
import java.awt.*;


public class SetupWindow extends JDialog
{
    private JTextField hostField;
    private JTextField playerField;
    SetupWindow()
    {
        setModal(true);
        setUndecorated(true);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Setup connection");
        setLayout(null);
        setSize(400,200);
        setLocationRelativeTo(null);
        JButton acceptButton = new JButton ("CONNECT");
        getContentPane().setBackground(new Color(7, 113, 26));

        hostField = new JTextField("", 30);

        playerField = new JTextField("", 30);
        hostField.setText("localhost");
        playerField.setText("player");

        acceptButton.addActionListener (e -> {
            dispose();
            Client.playerName = playerField.getText();
            Client.hostName = hostField.getText();
        });
        JLabel conLab = new JLabel ("Set your host and player name:");
        conLab.setHorizontalAlignment(SwingConstants.CENTER);
        conLab.setVerticalAlignment(SwingConstants.CENTER);
        conLab.setForeground(Color.WHITE);
        JLabel hostLabel = new JLabel("HOST:");
        JLabel playerLabel = new JLabel("NAME:");
        add(hostLabel);
        add(playerLabel);
        add(conLab);
        add(hostField);
        add(playerField);
        add(acceptButton);

        hostLabel.setForeground(Color.WHITE);
        playerLabel.setForeground(Color.WHITE);

        hostLabel.setFont(hostLabel.getFont().deriveFont(20f));
        playerLabel.setFont(playerLabel.getFont().deriveFont(20f));

        hostLabel.setBounds(40,70,70,30);
        playerLabel.setBounds(40,110,70,30);
        conLab.setBounds(0,0,400,40);
        conLab.setFont(conLab.getFont().deriveFont(20f));
        hostField.setBounds(120,70,200,30);
        hostField.setFont(hostField.getFont().deriveFont(20f));
        playerField.setBounds(120,110,200,30);
        playerField.setFont(playerField.getFont().deriveFont(20f));
        acceptButton.setBounds(100,150,200,40);

        setResizable(false);
        //setLocation(600,400);
        setVisible(true);

    }
}

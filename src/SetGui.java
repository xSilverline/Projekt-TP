import javax.swing.*;
import java.awt.*;


public class SetGui
{
    JFrame frame = new JFrame("Game");
    JLabel messageLabel = new JLabel("");
    ImageIcon icon;
    ImageIcon opponentIcon;


    SetGui()
    {
        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, "South");

        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.black);
        boardPanel.setLayout(new GridLayout(3, 3, 2, 2));

        frame.getContentPane().add(boardPanel, "Center");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(240, 160);
        frame.setVisible(true);
        frame.setResizable(true);

    }
}

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WaitingRoomFrame
{
    WaitingRoomFrame(int k)
    {
        String tempText;

        JFrame waitingRoomFrame = new JFrame();
        ArrayList<JLabel> playerLabels = new ArrayList<JLabel>();
        playerLabels.add(new JLabel ("Player 1"));
        playerLabels.get(0).setBackground(Color.green);
        playerLabels.get(0).setOpaque(true);

        waitingRoomFrame.setSize(865, 480);
        waitingRoomFrame.setLocation(250,50);
        waitingRoomFrame.setResizable(false);
        waitingRoomFrame.setLayout (null);



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
}

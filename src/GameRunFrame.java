import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GameRunFrame extends JFrame
{
    private GamePanel gameFrame;

    GameRunFrame(int size, Client client, PrintWriter out,ArrayList<String> playerList)
    {
        gameFrame = new GamePanel(size,client,out,playerList);
        add(gameFrame);
        setSize(1366, 768);
        setUndecorated(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
        setResizable(false);
        setVisible(true);
    }

}

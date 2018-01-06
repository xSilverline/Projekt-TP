import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;

public class GameRunFrame extends JFrame
{
    private GameFrame gameFrame;

    GameRunFrame(int size, Client client, PrintWriter out)
    {
        gameFrame = new GameFrame(size,client,out);
        add(gameFrame);
        setSize(1366, 768);
        setUndecorated(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
        setResizable(false);
        setVisible(true);
    }

}

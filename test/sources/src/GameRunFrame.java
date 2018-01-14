package sources.src;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;

class GameRunFrame extends JFrame
{
    private GamePanel gameFrame;

    GameRunFrame(int size, Client client, PrintWriter out, ArrayList<String> playerList)
    {
        this.gameFrame = new GamePanel(size, client, out, playerList);
        add(gameFrame);
        setSize(1366, 768);
        setUndecorated(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
        setResizable(false);
        setVisible(true);
    }

    void swap(int a, int b, int c, int d) {
        gameFrame.swapPawnPossitions(a, b, c, d);
    }

    void next() {
        gameFrame.nextPlayer();
    }

    void end(String winner) {
        gameFrame.endGame(winner);
    }

}

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertEquals;

public class ServerTest
{
    private static Server server;

    @Before
    public void setServer()
    {
        server = new Server();
    }

    @Test
    public void IsGameWorking() throws IOException
    {
        Player player = new Player(new ServerSocket(9090).accept(),1);
        Game game = new Game(2,player);

    }

    @Test
    public void IsServerRuning() throws Exception
    {

    }

}
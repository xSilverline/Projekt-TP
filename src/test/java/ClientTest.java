import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class ClientTest
{
    private Server server;
    private Client client;
    @Before
    public void setUp() throws Exception
    {
        client = new Client();
    }

    @Test
    public void IsPlayerNameOkay()
    {
        Client.playerName = "player";
        assertEquals("player",client.getPlayerName());
    }




}

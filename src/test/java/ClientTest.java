import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientTest
{

    Client client;
    @Before
    void setUpClient() throws Exception
    {
        client = new Client();
    }

    @Test
    void ShouldReturnPlayerProperly()
    {
        Client.playerName = "player";
        assertEquals("player",client.getPlayerName());
    }


}
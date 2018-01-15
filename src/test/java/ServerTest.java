import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ServerTest
{
    private static ServerTestClass server;
    static ArrayList<PlayerThread> players = new ArrayList<>();
    PlayerThread p;
    PlayerThread p2;

    @Before
    public void setServer() throws IOException
    {
        server = new ServerTestClass();
        p = new PlayerThread(0);
        p2 = new PlayerThread(1);
    }

    @Test
    public void ShouldBeNotReady() throws Exception
    {
        assertEquals(false,p.returnReady());
    }

    @Test
    public void ShouldLogIn()
    {
        p.setOut("player");
        p.newPlayer();
        assertEquals("NAMEACCEPTED",p.retOut());
        assertTrue(ServerTestClass.names.contains("player"));
        assertNotNull(ServerTestClass.players);

        p.setOut("player2");
        p.newPlayer();
        assertEquals(2,ServerTestClass.players.size());
    }

    @Test
    public void ShouldNotLogIn()
    {
        p.setOut("player");
        p.newPlayer();
        p2.setOut("player");
        p2.newPlayer();
        assertEquals("INVALID_NAME",p2.retOut());
        assertEquals(1,ServerTestClass.names.size());

    }

    @Test
    public void ShouldBeEmpty()
    {
        ServerTestClass.lobbyList.clear();
        p.getLobbySize();
        assertEquals("NULL_LOBBY_SIZE",p.retOut());
    }

    @Test
    public void ShouldCreateAndClearLobby()
    {
        assertEquals(0,ServerTestClass.lobbyList.size());
        p.runThread("NEW_GAME_TYPE2");
        assertEquals(1,ServerTestClass.lobbyList.size());
        LobbyTestClass l = ServerTestClass.lobbyList.get(0);
        assertEquals(2,l.getGameType());
        assertEquals(p,l.players.get(0));

        p.runThread("RETURN_FROM_LOBBY");
        assertEquals(0,ServerTestClass.lobbyList.size());
    }

    @Test
    public void ShouldBeReadyAndJoinAndStart()
    {
        p.runThread("NEW_GAME_TYPE2");
        p.runThread("READY_PLAYER");
        assertEquals("WAIT_FOR_PLAYERS",p.retOut());
        assertTrue(p.returnReady());
        p.runThread("NOT_READY_PLAYER");
        assertFalse(p.returnReady());
        p2.runThread("JOIN_TO1");
        assertEquals(2,ServerTestClass.lobbyList.get(0).players.size());
        p.runThread("READY_PLAYER");
        assertEquals("WAIT_FOR_PLAYERS",p.retOut());
        p2.runThread("READY_PLAYER");
        assertEquals("START_GAME2",p.retOut());
        assertEquals("START_GAME2",p2.retOut());
    }

    @Test
    public void ShouldCreateGame()
    {
        p.runThread("CREATE_GAME 2");
        assertNotNull(ServerTestClass.game);

    }

}
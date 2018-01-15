import java.util.ArrayList;

public class LobbyTestClass
{
    private int gameType;
    ArrayList<PlayerThread> players= new ArrayList<>();
    private int id;
    private int numberOfPlayers;

    LobbyTestClass(int gameType, int id)
    {
        this.gameType=gameType;
        this.id=id;
    }
    void setNumberOfPlayers(int x)
    {
        this.numberOfPlayers = x;
    }
    int getWrittenNumberOfPlayers()
    {
        return numberOfPlayers;
    }

    boolean areReady()
    {
        int count = 0;
        for(PlayerThread p : players)
        {
            if(p.returnReady())
            {
                count++;
            }
        }
        if(count == getNumberOfPlayers())
        {
            return true;
        }
        else
            return false;
    }

    boolean isFree()
    {
        if(players.size() < gameType)
        {
            return true;
        }
        else
            return false;
    }
    public int getId()
    {
        return id;
    }

    int getGameType() {
        return gameType;
    }

    void joinLobby(PlayerThread player)
    {
        if(players.size() < gameType)
        {
            players.add(player);
        }
    }

    int getNumberOfPlayers()
    {
        return players.size();
    }


    ArrayList getPlayers()
    {
        return players;
    }
}

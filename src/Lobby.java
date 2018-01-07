import java.util.ArrayList;

public class Lobby
{
    private int gameType;
    ArrayList<Player> players= new ArrayList<>();
    private int id;
    private int numberOfPlayers;

    Lobby(int gameType,int id)
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
        for(Player p : players)
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

    void joinLobby(Player player)
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

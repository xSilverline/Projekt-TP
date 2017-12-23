import java.util.ArrayList;

public class Lobby
{
    private int gameType;
    ArrayList<Player> players= new ArrayList();
    private int id;

    Lobby(int gameType,int id)
    {
        this.gameType=gameType;
        this.id=id;
    }

    public boolean isFree()
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

    public int getGameType() {
        return gameType;
    }

    public void joinLobby(Player player)
    {
        if(players.size() < gameType)
        {
            players.add(player);
        }
    }

    public void leaveLobby(Player player)
    {
        players.remove(player);
    }

    public int getNumberOfPlayers()
    {
        return players.size();
    }


    ArrayList getPlayers()
    {
        return players;
    }
}

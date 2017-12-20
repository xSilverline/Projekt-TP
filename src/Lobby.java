import java.util.ArrayList;

public class Lobby
{
    private int gameType;
    ArrayList<Player> players= new ArrayList();

    Lobby(int gameType)
    {
        this.gameType=gameType;

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


    ArrayList getPlayers()
    {
        return players;
    }
}

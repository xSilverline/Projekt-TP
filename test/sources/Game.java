package sources;

public class Game{
    int numberOfPlayers;
    Player currentPlayer;

    Game(int n) {
        this.numberOfPlayers=n;
    }

    public boolean hasWinner() {
        /*
         * TODO: return true if one of the players is a winner / false if not
         */
        return false;
    }

    public boolean moveIsLegal(Player player, int location) {
        /*
         * TODO: return false if move is illegal:
         *       - move out off the board
         *       - move to taken position
         *       - if pawn can jump over other pawn
         *              - check if can't jump into expect position (using recursion)
         *       - if not
         *              - check if move farther than one place
         *
         *       return true otherwise
         */
        return false;
    }
}

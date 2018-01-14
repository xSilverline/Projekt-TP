package sources.src;

public class Game{
    private int numberOfPlayers;
    private Player currentPlayer;
    private Board board;

    Game(int numberOfPlayers, Player player) {
        this.currentPlayer = player;
        this.numberOfPlayers = numberOfPlayers;
        board = new Board(this.numberOfPlayers);
    }

    public void hasWinner() {
        int tester = 0;
        //RED WIN (BLUE POS)
        for (int i = 9; i <= 15; i++) {
            for (int j = 0; j <= 3; j++) {
                if (board.getType(i, j) >= 1 && board.getType(i, j) != 5) {
                    tester = 1;
                    break;
                }
            }
            if (tester == 1) break;
        }
        if (tester == 0) {
            currentPlayer.win("RED");
            return;
        }

        tester = 0;
        //GREEN WIN (ORANGE POS)
        for (int i = 18; i <= 24; i++) {
            for (int j = 4; j <= 7; j++) {
                if (board.getType(i, j) >= 1 && board.getType(i, j) != 6) {
                    tester = 1;
                    break;
                }
            }
            if (tester == 1) break;
        }
        if (tester == 0) {
            currentPlayer.win("GREEN");
            return;
        }

        tester = 0;
        //PURPLE WIN (YELLOW POS)
        for (int i = 18; i <= 24; i++) {
            for (int j = 9; j <= 12; j++) {
                if (board.getType(i, j) >= 1 && board.getType(i, j) != 7) {
                    tester = 1;
                    break;
                }
            }
            if (tester == 1) break;
        }
        if (tester == 0) {
            currentPlayer.win("PURPLE");
            return;
        }

        tester = 0;
        //BLUE WIN (RED POS)
        for (int i = 9; i <= 15; i++) {
            for (int j = 13; j <= 16; j++) {
                if (board.getType(i, j) >= 1 && board.getType(i, j) != 2) {
                    tester = 1;
                    break;
                }
            }
            if (tester == 1) break;
        }
        if (tester == 0) {
            currentPlayer.win("BLUE");
            return;
        }

        tester = 0;
        //ORANGE WIN (GREEN POS)
        for (int i = 0; i <= 6; i++) {
            for (int j = 9; j <= 12; j++) {
                if (board.getType(i, j) >= 1 && board.getType(i, j) != 3) {
                    tester = 1;
                    break;
                }
            }
            if (tester == 1) break;
        }
        if (tester == 0) {
            currentPlayer.win("ORANGE");
            return;
        }

        tester = 0;
        //YELLOW WIN (PURPLE POS)
        for (int i = 0; i <= 6; i++) {
            for (int j = 4; j <= 7; j++) {
                if (board.getType(i, j) >= 1 && board.getType(i, j) != 4) {
                    tester = 1;
                    break;
                }
            }
            if (tester == 1) break;
        }
        if (tester == 0) {
            currentPlayer.win("YELLOW");
            return;
        }


    }

    public void move(int fromPx, int fromPy, int targetPx, int targetPy) {
        String message;
        if(moveIsValid(fromPx, fromPy, targetPx, targetPy)) {
            int temp = board.getType(fromPx, fromPy);
            board.setType(fromPx, fromPy, board.getType(targetPx, targetPy));
            board.setType(targetPx, targetPy, temp);

            System.out.println("From: " + board.getType(fromPx, fromPy) + ", Target: " + board.getType(targetPx, targetPy));

            message="VALID_MOVE ";
            if(fromPx<10) message +="0";
            message += Integer.toString(fromPx) + " ";
            if(fromPy<10) message +="0";
            message += Integer.toString(fromPy) + " ";
            if(targetPx<10) message +="0";
            message += Integer.toString(targetPx) + " ";
            if(targetPy<10) message +="0";
            message += Integer.toString(targetPy);
        } else {
            message = "BAD_MOVE";
        }
        currentPlayer.swapPawns(message);

        hasWinner();
    }

    private boolean moveIsValid (int fx, int fy, int tx, int ty) {
        int boardXfrom = fx;
        int boardYfrom = fy;

        int boardXtarget = tx;
        int boardYtarget = ty;

        if( ( (( boardXtarget==(boardXfrom-1) || boardXtarget==(boardXfrom+1) ) && ( boardYtarget==(boardYfrom-1) || boardYtarget==(boardYfrom+1) ) )
                || ( ( boardXtarget==(boardXfrom-2) || boardXtarget==(boardXfrom+2) ) && ( boardYtarget==boardYfrom) ) )
                || ((fx==tx) && (fy==ty)) ){
            return true;
        }


        //Move by jumping
        // *     0  1
        // *   2  X  3
        // *     4  5
        /////////////////
        int numOfPlace=0;
        while(numOfPlace<6) {
            switch(numOfPlace) {
                case 0:
                    if((board.getType(boardXfrom-1, boardYfrom-1) >= 2 && board.getType(boardXfrom-1, boardYfrom-1) <= 7) &&
                            board.getType(boardXfrom-2, boardYfrom-2)==1 && fx==tx+2 && fy==ty+2)
                        return true;
                    break;
                case 1:
                    if((board.getType(boardXfrom+1, boardYfrom-1) >= 2 && board.getType(boardXfrom+1, boardYfrom-1) <= 7) &&
                            board.getType(boardXfrom+2, boardYfrom-2)==1 && fx==tx-2 && fy==ty+2 )
                        return true;
                    break;
                case 2:
                    if((board.getType(boardXfrom-2, boardYfrom) >= 2 && board.getType(boardXfrom-2, boardYfrom) <= 7) &&
                            board.getType(boardXfrom-4, boardYfrom)==1 && fx==tx+4 && fy==ty )
                        return true;
                    break;
                case 3:
                    if((board.getType(boardXfrom+2, boardYfrom) >= 2 && board.getType(boardXfrom+2, boardYfrom) <= 7) &&
                            board.getType(boardXfrom+4, boardYfrom)==1 && fx==tx-4 && fy==ty  )
                        return true;
                    break;
                case 4:
                    if((board.getType(boardXfrom-1, boardYfrom+1) >= 2 && board.getType(boardXfrom-1, boardYfrom+1) <= 7) &&
                            board.getType(boardXfrom-2, boardYfrom+2)==1 && fx==tx+2 && fy==ty-2  )
                        return true;
                    break;
                case 5:
                    if((board.getType(boardXfrom+1, boardYfrom+1) >= 2 && board.getType(boardXfrom+1, boardYfrom+1) <= 7) &&
                            board.getType(boardXfrom+2, boardYfrom+2)==1  && fx==tx-2 && fy==ty-2  )
                        return true;
                    break;
                default:
                    break;
            }
            numOfPlace++;
        }
        return false;
    }
}

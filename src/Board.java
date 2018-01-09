/*
    Array size [25,17]
    types:
        -1 - out of board

        0 - empty (impossible to move)

        1 - without pawn (possible to move)

        2 - blue spawn place
        3 - orange spawn place
        4 - yellow spawn place
        5 - red spawn place
        6 - green spawn place
        7 - purple spawn place

    If 2 players then blue, red.
    If 3 players then blue, yellow, green.
    If 4 players then blue, yellow, red, purple.
    If 6 players then all colors.

*/

class Board {
    int BOARD_HEIGHT = 17;
    int BOARD_WIDTH = 25;
    private int numOfPlayers;
    private int[][] type = new int[BOARD_WIDTH][BOARD_HEIGHT];

    Board(int n) {
        this.numOfPlayers = n;
        //FILL BOARD ARRAY
        for(int i=0;i<BOARD_WIDTH;i++) {
            for(int j=0;j<BOARD_HEIGHT;j++) {
                if((i==9 && j==3) || (i==10 && j==2) || (i==11 && j==1) || (i==11 && j==3) || (i==12 && j==0) ||
                        (i==12 && j==2) || (i==13 && j==3) || (i==13 && j==1) || (i==14 && j==2) || (i==15 && j==3)) { // BLUE SPAWN PLACES
                    type[i][j]=2;
                }else if((i==18 && j==4) || (i==19 && j==5) || (i==20 && j==4) || (i==20 && j==6) || (i==21 && j==5) ||
                        (i==21 && j==7) || (i==22 && j==4) || (i==22 && j==6) || (i==23 && j==5) || (i==24 && j==4)) { // ORANGE SPAWN PLACES
                    if(this.numOfPlayers==6) type[i][j]=3;
                    else type[i][j]=1;
                }else if((i==21 && j==9) || (i==18 && j==12) || (i==19 && j==11) || (i==20 && j==10) || (i==20 && j==12) ||
                        (i==21 && j==11) || (i==22 && j==10) || (i==22 && j==12) || (i==23 && j==11) || (i==24 && j==12)) { // YELLOW SPAWN PLACES
                    if(this.numOfPlayers>=3) type[i][j]=4;
                    else type[i][j]=1;
                }else if((i==9 && j==13) || (i==10 && j==14) || (i==11 && j==13) || (i==11 && j==15) || (i==12 && j==14) ||
                        (i==12 && j==16) || (i==13 && j==13) || (i==13 && j==15) || (i==14 && j==14) || (i==15 && j==13)) { // RED SPAWN PLACES
                    if(this.numOfPlayers!=3) type[i][j]=5;
                    else type[i][j]=1;
                }else if((i==0 && j==12) || (i==1 && j==11) || (i==2 && j==10) || (i==2 && j==12) || (i==3 && j==9) ||
                        (i==3 && j==11) || (i==4 && j==10) || (i==4 && j==12) || (i==5 && j==11) || (i==6 && j==12)) { // GREEN SPAWN PLACES
                    if(this.numOfPlayers==3 || this.numOfPlayers==6) type[i][j]=6;
                    else type[i][j]=1;
                }else if((i==0 && j==4) || (i==1 && j==5) || (i==2 && j==4) || (i==2 && j==6) || (i==3 && j==5) ||
                        (i==3 && j==7) || (i==4 && j==4) || (i==4 && j==6) || (i==5 && j==5) || (i==6 && j==4)) { // PURPLE SPAWN PLACES
                    if(this.numOfPlayers>=4) type[i][j]=7;
                    else type[i][j]=1;
                }else if( (( (i%2)==0 && (j%2)==0 ) || ( (i%2)!=0 && (j%2)!=0 )) && (i>=4&&i<=20) && (j>=4&&j<=12)) { //WITHOUT PAWNS
                    type[i][j]=1;
                }else if( (( (i%2)==0 && (j%2)==0 ) || ( (i%2)!=0 && (j%2)!=0 )) && !((i>=4&&i<=20) && (j>=4&&j<=12))){ //EMPTY
                    type[i][j]=-1;
                }else{
                    type[i][j]=0;
                }
            }
        }
    }

    int getType(int i, int j) {
        return type[i][j];
    }

    void setType(int i, int j, int typ) {
        this.type[i][j]=typ;
    }
}

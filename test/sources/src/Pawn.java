package sources.src;

import java.awt.*;

class Pawn {
    private Color color;
    private int x;
    private int y;
    private boolean chosen=false;
    private int boardXpos;
    private int boardYpos;


    Pawn(int x, int y, Color color, int i, int j) {
        this.x=x;
        this.y=y;
        this.color=color;
        this.boardXpos=i;
        this.boardYpos=j;
    }

    int getX() {
        return this.x;
    }
    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return this.y;
    }
    void setY(int y) {
        this.y = y;
    }

    int getBoardXpos() {
        return this.boardXpos;
    }
    void setBoardXpos(int x) {
        this.boardXpos = x;
    }

    int getBoardYpos() {
        return this.boardYpos;
    }
    void setBoardYpos(int y) {
        this.boardYpos = y;
    }

    Color getColor() {
        return this.color;
    }

    void select(boolean t) {
        this.chosen=t;
    }
    boolean ifSelected() {
        return this.chosen;
    }

}

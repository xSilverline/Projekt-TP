import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.ArrayList;

//TODO: Implement method that checks if player moved. Remember bout changing pawns. Repair colors (in 2p P2 has orange but red is shown)


public class GamePanel extends JPanel implements MouseListener,ActionListener{
    private int PAWN_DIAMETER = 30;

    private ArrayList<String> playerList = new ArrayList<String>();
    //private BufferedReader in;
    private PrintWriter out;
    private Client client;
    private ButtonGui exitButton;
    private ButtonGui skipButton;
    private ButtonGui botButton;
    private JLabel currentName;
    private int numOfPlayers;
    private Board board;
    private ArrayList<Pawn> pawns = new ArrayList<>();
    private String currentPlayer;
    private JFrame gameFrame;
    private boolean clicked=false;
    private int selectedPosition;
    private boolean ended = false;
    private String winner = "";

    private Color currentColor;

    private Color mycolor;

    GamePanel( int n,Client client,PrintWriter out,ArrayList<String> playerList) {
        addMouseListener(this);

        this.playerList = playerList;
        this.out = out;
        //this.in = in;
        this.client = client;
        this.numOfPlayers = n;
        //setResizable(false);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentPlayer=playerList.get(0);
        setLayout(null);
        exitButton = new ButtonGui("EXIT");
        add(exitButton);
        exitButton.setBounds(1200,710,150,50);
        exitButton.addActionListener(this);

        skipButton = new ButtonGui("SKIP MOVE");
        add(skipButton);
        skipButton.setBounds(500,630,200,50);
        skipButton.addActionListener(this);

        botButton = new ButtonGui("MOVE AS BOT");
        add(botButton);
        botButton.setBackground(new Color(70, 70, 255));
        botButton.setBounds(1000,430,250,50);
        botButton.addActionListener(this);

        currentName = new JLabel("CURRENT PLAYER:  "+currentPlayer);
        add(currentName);
        currentName.setBounds(486,700,400,50);
        currentName.setFont(currentName.getFont().deriveFont(25f));
        currentName.setForeground(new Color(0x0B5EB3));

        board = new Board(this.numOfPlayers);
        String create_game = "CREATE_GAME " + Integer.toString(this.numOfPlayers);
        this.out.println(create_game);

        //addMouseMotionListener(this);

        currentColor = Color.BLUE;

        //SET PAWNS
        int posX=450;
        int posY=20;
        for(int i=0;i<board.BOARD_WIDTH;i++) {
            for(int j=0;j<board.BOARD_HEIGHT;j++) {
                if(board.getType(i,j)==0) {
                    if (j == board.BOARD_HEIGHT - 1) {
                        posX += 20;
                        posY = 20;
                    }else
                        posY += 32;
                    continue;
                }
                if(board.getType(i, j)!= -1) {
                    if(board.getType(i, j) == 1) pawns.add(new Pawn(posX, posY, Color.WHITE, i, j));
                    if(board.getType(i, j) == 2) pawns.add(new Pawn(posX, posY, Color.BLUE, i, j));
                    if(board.getType(i, j) == 3) pawns.add(new Pawn(posX, posY, Color.ORANGE, i, j));
                    if(board.getType(i, j) == 4) pawns.add(new Pawn(posX, posY, Color.YELLOW, i, j));
                    if(board.getType(i, j) == 5) pawns.add(new Pawn(posX, posY, Color.RED, i, j));
                    if(board.getType(i, j) == 6) pawns.add(new Pawn(posX, posY, Color.GREEN, i, j));
                    if(board.getType(i, j) == 7) pawns.add(new Pawn(posX, posY, (new Color(126,0,195)), i, j));
                }
                if (j == board.BOARD_HEIGHT - 1) {
                    posX += 23;
                    posY = 20;
                } else
                    posY += 32;
            }
        }
    }

    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
        super.paintComponent(g);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.setBackground(new Color(111, 111, 111));

        //DRAW GAME BACKGROUND
        int posX=450;
        int posY=20;
        for(int i=0;i<board.BOARD_WIDTH;i++) {
            for(int j=0;j<board.BOARD_HEIGHT;j++) {
                if(board.getType(i,j)==0) {
                    if (j == board.BOARD_HEIGHT - 1) {
                        posX += 20;
                        posY = 20;
                    }else
                        posY += 32;
                    continue;
                }
                if(board.getType(i, j)!= -1) {
                    g.setColor(Color.BLACK);
                    g.fillOval(posX, posY, PAWN_DIAMETER+1, PAWN_DIAMETER+1);
                    if(board.getType(i, j) == 2) g.setColor(new Color(170,170,255));
                    if(board.getType(i, j) == 3) g.setColor(new Color(255,170,100));
                    if(board.getType(i, j) == 4) g.setColor(new Color(250,255,170));
                    if(board.getType(i, j) == 5) g.setColor(new Color(255,100,100));
                    if(board.getType(i, j) == 6) g.setColor(new Color(100,255,100));
                    if(board.getType(i, j) == 7) g.setColor(new Color(220,100,255));
                    if(board.getType(i, j) == 1) g.setColor(Color.WHITE);
                    g.fillOval(posX, posY, PAWN_DIAMETER, PAWN_DIAMETER);
                }
                if (j == board.BOARD_HEIGHT - 1) {
                    posX += 23;
                    posY = 20;
                } else
                    posY += 32;
            }
        }

        ////////////DRAW LEFT PART
        posX = 30;
        posY = 20;
        for(int i=2; i<playerList.size()+2; i++) {
            g.setColor(Color.BLACK);
            g.fillOval(posX, posY, PAWN_DIAMETER+1, PAWN_DIAMETER+1);

            switch (playerList.size()) {
                case 2:
                    if (i == 2) g.setColor(Color.BLUE);
                    if (i == 3) g.setColor(Color.RED);
                    break;
                case 3:
                    if (i == 2) g.setColor(Color.BLUE);
                    if (i == 4) g.setColor(Color.GREEN);
                    if (i == 3) g.setColor(Color.YELLOW);
                    break;
                case 4:
                    if (i == 5) g.setColor(new Color(126, 0, 195));
                    if (i == 2) g.setColor(Color.BLUE);
                    if (i == 3) g.setColor(Color.YELLOW);
                    if (i == 4) g.setColor(Color.RED);
                    break;
                case 6:
                    if (i == 2) g.setColor(Color.BLUE);
                    if (i == 3) g.setColor(Color.ORANGE);
                    if (i == 4) g.setColor(Color.YELLOW);
                    if (i == 5) g.setColor(Color.RED);
                    if (i == 6) g.setColor(Color.GREEN);
                    if (i == 7) g.setColor(new Color(126, 0, 195));
                    break;
                default:
                    break;
            }

            g.fillOval(posX, posY, PAWN_DIAMETER+1, PAWN_DIAMETER+1);

            posX+=35;
            posY+=20;

            String name="";

            if( this.client.getPlayerName().equals(playerList.get(i-2)) ) {
                name += "me: ";
                mycolor=g.getColor();
            }

            if((i-2)<playerList.size())
            {
                name += playerList.get(i-2);
            }
            else
                name = "PlayerName " + Integer.toString(i-1);

            g.setColor(Color.BLACK);
            g.drawString(name, posX, posY);
            posX-=35;
            posY+=20;
        }


        ///////////DRAW PAWNS
        for(Pawn temp : pawns) {
            g.setColor(temp.getColor());
            g.fillOval(temp.getX(), temp.getY(), PAWN_DIAMETER, PAWN_DIAMETER);
            if(temp.ifSelected()) {
                g.setColor(new Color(127,73,78));
                g.setStroke(new BasicStroke(3F));
                g.drawOval(temp.getX()-2, temp.getY()-2, PAWN_DIAMETER+2, PAWN_DIAMETER+2);
            }
        }

        if(this.ended) {
            String temp = this.winner + " wygral gre.";
            g.drawString(temp, 550, 310);
        }
    }

    String getCurrentPlayer() {
        return this.currentPlayer;
    }

    void setCurrentPlayer(String p) {
        this.currentPlayer = p;
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();

        if(currentPlayer.equals(this.client.getPlayerName())) {
            if (!clicked) {
                for (int i = 0; i < pawns.size(); i++) {

                    if (x > pawns.get(i).getX() && x < (pawns.get(i).getX() + PAWN_DIAMETER) && y > pawns.get(i).getY() && y < (pawns.get(i).getY() + PAWN_DIAMETER)
                            && board.getType(pawns.get(i).getBoardXpos(), pawns.get(i).getBoardYpos()) != 1) {
                            if(!pawns.get(i).getColor().equals(currentColor)) break;
                            pawns.get(i).select(true);
                            selectedPosition = i;
                    }
                }
                clicked = true;
            } else {
                for (int i = 0; i < pawns.size(); i++) {
                    if (x > pawns.get(i).getX() && x < (pawns.get(i).getX() + PAWN_DIAMETER) && y > pawns.get(i).getY() && y < (pawns.get(i).getY() + PAWN_DIAMETER)
                            && board.getType(pawns.get(i).getBoardXpos(), pawns.get(i).getBoardYpos()) == 1) {
                        if (selectedPosition != -1) {

                            moveTo(pawns.get(selectedPosition).getBoardXpos(), pawns.get(selectedPosition).getBoardYpos(),
                                    pawns.get(i).getBoardXpos(), pawns.get(i).getBoardYpos());
                        }
                    }
                    if (pawns.get(i).ifSelected()) {
                        pawns.get(i).select(false);
                    }
                }
                selectedPosition = -1;
                clicked = false;
            }
        }
        repaint();
    }

    void moveTo(int fromPx, int fromPy, int targetPx, int targetPy) {
        String move = "MOVE ";
        if(fromPx<10) move +="0";
        move += Integer.toString(fromPx) + " ";
        if(fromPy<10) move +="0";
        move += Integer.toString(fromPy) + " ";
        if(targetPx<10) move +="0";
        move += Integer.toString(targetPx) + " ";
        if(targetPy<10) move +="0";
        move += Integer.toString(targetPy);

        this.out.println(move); // MOVE XX YY XX YY
    }



    void swapPawnPossitions(int pX, int pY, int toX, int toY) {
        int selectedPos=0;
        int n=0;
        for (int i = 0; i < pawns.size(); i++) {
            if(pawns.get(i).getBoardXpos() == pX && pawns.get(i).getBoardYpos() == pY)
                selectedPos=i;
            if(pawns.get(i).getBoardXpos() == toX && pawns.get(i).getBoardYpos() == toY)
                n=i;
        }

        int temporary = pawns.get(selectedPos).getX(); //SWAP Xpos
        pawns.get(selectedPos).setX(pawns.get(n).getX());
        pawns.get(n).setX(temporary);

        temporary = pawns.get(selectedPos).getY();    //SWAP Ypos
        pawns.get(selectedPos).setY(pawns.get(n).getY());
        pawns.get(n).setY(temporary);

        temporary = pawns.get(selectedPos).getBoardXpos();    //SWAP boardXpos
        pawns.get(selectedPos).setBoardXpos(pawns.get(n).getBoardXpos());
        pawns.get(n).setBoardXpos(temporary);

        temporary = pawns.get(selectedPos).getBoardYpos();    //SWAP boardYpos
        pawns.get(selectedPos).setBoardYpos(pawns.get(n).getBoardYpos());
        pawns.get(n).setBoardYpos(temporary);

        temporary = board.getType(pawns.get(selectedPos).getBoardXpos(), pawns.get(selectedPos).getBoardYpos());    //SWAP type in board
        board.setType(pawns.get(selectedPos).getBoardXpos(), pawns.get(selectedPos).getBoardYpos(),
                board.getType(pawns.get(n).getBoardXpos(), pawns.get(n).getBoardYpos()));
        board.setType(pawns.get(n).getBoardXpos(), pawns.get(n).getBoardYpos(),
                temporary);

        nextPlayer();
        repaint();
    }

    void nextPlayer() {
        //STEP TO NEXT PLAYER NAME
        for(int i=0; i<this.numOfPlayers; i++) {
            if(i==(this.numOfPlayers-1)) {
                currentPlayer=playerList.get(0);
                break;
            }
            if(currentPlayer==playerList.get(i)) {
                currentPlayer=playerList.get(i+1);
                break;
            }
        }

        //STEP TO NEXT PLAYER COLOR
        switch(this.numOfPlayers) {
            case 2:
                if(currentColor.equals(Color.BLUE))
                    currentColor=Color.RED;
                else
                    currentColor=Color.BLUE;
                break;
            case 3:
                if(currentColor.equals(Color.BLUE))
                    currentColor=Color.YELLOW;
                else if (currentColor.equals(Color.YELLOW))
                    currentColor=Color.GREEN;
                else if (currentColor.equals(Color.GREEN))
                    currentColor=Color.BLUE;
                break;
            case 4:
                if(currentColor.equals(Color.BLUE))
                    currentColor=Color.YELLOW;
                else if (currentColor.equals(Color.YELLOW))
                    currentColor=Color.RED;
                else if (currentColor.equals(Color.RED))
                    currentColor=(new Color(126,0,195));
                else if (currentColor.equals((new Color(126,0,195))))
                    currentColor=Color.BLUE;
                break;
            case 6:
                if(currentColor.equals(Color.BLUE))
                    currentColor=Color.ORANGE;
                else if (currentColor.equals(Color.ORANGE))
                    currentColor=Color.YELLOW;
                else if (currentColor.equals(Color.YELLOW))
                    currentColor=Color.RED;
                else if (currentColor.equals(Color.RED))
                    currentColor=Color.GREEN;
                else if (currentColor.equals(Color.GREEN))
                    currentColor=(new Color(126,0,195));
                else if (currentColor.equals((new Color(126,0,195))))
                    currentColor=Color.BLUE;
                break;
        }
        currentName.setText("CURRENT PLAYER " + currentPlayer);
        currentName.setForeground(currentColor);
        repaint();
    }

    void endGame(String winner) {
        winner+=" HAS WON THE GAME!";
        int n = JOptionPane.showConfirmDialog(this,"exit?", winner, JOptionPane.YES_NO_OPTION);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source == exitButton)
        {
            int result = JOptionPane.showConfirmDialog(null,"Do You really want to leave this game?","You are leaving...",JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION)
            {
                out.println("RETURN_FROM_LOBBY");
                client.closeGame();

                //TODO: add bot in the place of player
            }

        }

        if (source == skipButton && currentPlayer.equals(this.client.getPlayerName()))
        {
            out.println("SKIP_MOVE");
        }

        if (source == botButton && currentColor.equals(mycolor))
        {
            if(currentColor.equals(Color.BLUE)) {
                selectBotPawn(2, 12, 16, "DOWN",16,24);
            }
            else if(currentColor.equals( Color.ORANGE)) {
                selectBotPawn(3, 0, 12, "LEFT",0,0);
            }
            else if(currentColor.equals(Color.YELLOW)) {
                selectBotPawn(4, 0, 4, "LEFT",0,0);
            }
            else if(currentColor.equals(Color.RED)) {
                selectBotPawn(5, 12, 0, "UP",0,0);
            }
            else if(currentColor.equals(Color.GREEN)) {
                selectBotPawn(6, 24, 4, "RIGHT",16,0);
            }
            else if(currentColor.equals(new Color(126, 0, 195))) {
                selectBotPawn(7, 24, 12, "RIGHT",16,0);
            }
        }
    }



    private void moveAsBot(int x, int y, int type, int targetX, int targetY, String stCheckFrom) {

        int core=-1;
        if(stCheckFrom.equals("UP") && x>targetX) {
            core=0;
        } else if(stCheckFrom.equals("UP")) {
            core=1;
        } else if(stCheckFrom.equals("LEFT") && y>targetY) {
            core=0;
        } else if(stCheckFrom.equals("LEFT") && y<targetY) {
            core=4;
        } else if(stCheckFrom.equals("LEFT") && y==targetY) {
            core=2;
        } else if(stCheckFrom.equals("RIGHT") && y>targetY) {
            core=1;
        } else if(stCheckFrom.equals("RIGHT") && y<targetY) {
            core=5;
        } else if(stCheckFrom.equals("RIGHT") && y==targetY) {
            core=3;
        } else if(stCheckFrom.equals("DOWN") && x>targetX) {
            core=4;
        } else if(stCheckFrom.equals("DOWN")) {
            core=5;
        }

        // *     0  1
        // *   2  X  3
        // *     4  5
        /////////////////
        int xt=x, yt=y;
        switch(core) {
            case 0:
                if(board.getType(x-1,y-1)==1) {
                    xt=x-1; yt=y-1;
                } else if(board.getType(x+1,y-1)==1) {
                    xt=x+1; yt=y-1;
                } else if(board.getType(x-2,y)==1) {
                    xt=x-2; yt=y;
                } else {
                    clearPawnsSelect();
                    selectBotPawn(type, targetX, targetY, stCheckFrom, x+2, y);
                    return;
                }
                break;
            case 1:
                if(board.getType(x+1,y-1)==1) {
                    xt=x+1; yt=y-1;
                } else if(board.getType(x-1,y-1)==1) {
                    xt=x-1; yt=y-1;
                } else if(board.getType(x+2,y)==1) {
                    xt=x+2; yt=y;
                } else {
                    clearPawnsSelect();
                    selectBotPawn(type, targetX, targetY, stCheckFrom, x+2, y);
                    return;
                }
                break;
            case 2:
                if(board.getType(x-2,y)==1) {
                    xt=x-21; yt=y;
                } else if(board.getType(x-1,y+1)==1) {
                    xt=x-1; yt=y+1;
                } else if(board.getType(x-1,y-1)==1) {
                    xt=x-1; yt=y-1;
                } else {
                    clearPawnsSelect();
                    selectBotPawn(type, targetX, targetY, stCheckFrom, x+1, y+1);
                    return;
                }
                break;
            case 3:
                if(board.getType(x+2,y)==1) {
                    xt=x+2; yt=y;
                } else if(board.getType(x+1,y+1)==1) {
                    xt=x+1; yt=y+1;
                } else if(board.getType(x+1,y-1)==1) {
                    xt=x+1; yt=y-1;
                } else {
                    clearPawnsSelect();
                    selectBotPawn(type, targetX, targetY, stCheckFrom, x-1, y-11);
                    return;
                }
                break;
            case 4:
                if(board.getType(x-1,y+1)==1) {
                    xt=x-1; yt=y+1;
                } else if(board.getType(x-2,y)==1) {
                    xt=x-2; yt=y;
                } else if(board.getType(x+1,y+1)==1) {
                    xt=x+1; yt=y+1;
                } else {
                    clearPawnsSelect();
                    selectBotPawn(type, targetX, targetY, stCheckFrom, x-1, y-1);
                    return;
                }
                break;
            case 5:
                if(board.getType(x+1,y+1)==1) {
                    xt=x+1; yt=y+1;
                } else if(board.getType(x-1,y+1)==1) {
                    xt=x-1; yt=y+1;
                } else if(board.getType(x+2,y)==1) {
                    xt=x+2; yt=y;
                } else {
                    clearPawnsSelect();
                    selectBotPawn(type, targetX, targetY, stCheckFrom, x-1, y-1);
                    return;
                }
                break;
            default:
                System.out.println("Core point fail.");
                break;
        }

        moveTo(x , y, xt, yt);
        clearPawnsSelect();
        selectedPosition=-1;

        repaint();
    }

    private void clearPawnsSelect() {
        for (int i = 0; i < pawns.size(); i++) {
            if(pawns.get(i).ifSelected()){
                pawns.get(i).select(false);
            }
        }
    }

    private void selectBotPawn(int type, int targetX, int targetY, String stCheckFrom, int startX, int startY) {
        int x=0, y=0;
        // *     0  1
        // *   2  X  3
        // *     4  5
        /////////////////
        if(stCheckFrom.equals("DOWN")) {
            for(int i=startX; i>=0; i--) {
                for(int j=startY; j>=0; j--) {
                    if(board.getType(i,j)==type && (board.getType(i-2,j)==1 || board.getType(i-1,j+1)==1
                                                    || board.getType(i+1,j+1)==1)) {
                        x=i;
                        y=j;
                        break;
                    }
                }
            }
        } else if(stCheckFrom.equals("LEFT")) {
            for(int j=startY; j<=24; j++) {
                for(int i=startX; i<=16; i++) {
                    if(board.getType(i,j)==type && (board.getType(i-1,j-1)==1 || board.getType(i-2,j)==1
                                                    || board.getType(i-1,j+1)==1)) {
                        x=i;
                        y=j;
                        break;
                    }
                }
            }
        } else if(stCheckFrom.equals("RIGHT")) {
            for(int j=startY; j<=24; j++) {
                for(int i=startX; i>=0; i--) {
                    if(board.getType(i,j)==type && (board.getType(i+1,j-1)==1 || board.getType(i+2,j)==1
                                                    || board.getType(i+1,j+1)==1)) {
                        x=i;
                        y=j;
                        break;
                    }
                }
            }
        } else if(stCheckFrom.equals("UP")) {
            for(int i=startX; i<=16; i++) {
                for(int j=startY; j<=24; j++) {
                    if(board.getType(i,j)==type && (board.getType(i-2,j)==1 || board.getType(i-1,j-1)==1
                                                    || board.getType(i+1,j-1)==1)) {
                        x=i;
                        y=j;
                        break;
                    }
                }
            }
        }


        for (int i = 0; i < pawns.size(); i++) {
            if(pawns.get(i).getBoardXpos()==x && pawns.get(i).getBoardYpos()==y) {
                pawns.get(i).select(true);
                selectedPosition=i;
                break;
            }
        }



        moveAsBot(x, y, type, targetX, targetY, stCheckFrom);
    }

}



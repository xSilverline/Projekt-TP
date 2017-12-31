import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class SetGui implements ActionListener {

    JFrame frame = new JFrame();
    JLabel messageLabel = new JLabel("");
    private Client client;
    private JButton instructionButton;
    private JButton exitButton;
    private JButton newGameButton;
    private JButton joinGameButton;
    private PrintWriter out;
    private BufferedReader in;



    SetGui(Client client, PrintWriter out, BufferedReader in)
    {
        this.out=out;
        this.client=client;
        this.in = in;

        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setTitle(messageLabel.getText());
        frame.setUndecorated(true);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //construct components

        JLabel logoText = new JLabel("Chinese Checkers!", SwingConstants.CENTER);


        instructionButton = new JButton("How to play?");
        exitButton = new JButton("Exit");
        newGameButton = new JButton("New Game");
        joinGameButton = new JButton("Join Game");

        logoText.setFont(logoText.getFont().deriveFont(60f));


        //adjust size and set layout
        frame.setSize(1366, 768);
        frame.setResizable(false);
        //frame.setLocation(250,50);
        frame.setLayout(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //frame.setSize(dim.width/2,dim.height/2);
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.getContentPane().setBackground(new Color(74, 73, 75));
        newGameButton.setFont(newGameButton.getFont().deriveFont(30f));
        newGameButton.setBackground(new Color(111, 45, 49));
        newGameButton.setForeground(Color.white);
        joinGameButton.setFont(joinGameButton.getFont().deriveFont(30f));
        joinGameButton.setForeground(Color.white);
        joinGameButton.setBackground(new Color(111, 45, 49));

        //add components
        frame.add(logoText);;
        frame.add(instructionButton);
        frame.add(exitButton);
        frame.add(newGameButton);
        frame.add(joinGameButton);

        //set component bounds
        logoText.setBounds(350, 50, 666, 55);


        instructionButton.setBounds(1200, 50, 150, 50);
        exitButton.setBounds(1200,700,150,50);
        newGameButton.setBounds(350,300,300,300);
        joinGameButton.setBounds(716,300,300,300);

        instructionButton.addActionListener(this);
        exitButton.addActionListener(this);
        newGameButton.addActionListener(this);
        joinGameButton.addActionListener(this);

        frame.setVisible(true);
    }

    void setConn(PrintWriter out, BufferedReader in)
    {
        this.out=out;
        this.in=in;
    }
    void setButtonsEnabled()
    {
        joinGameButton.setEnabled(true);
        newGameButton.setEnabled(true);
    }
    void setButtonsDisabled()
    {
        joinGameButton.setEnabled(false);
        newGameButton.setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
         if (source == instructionButton) {
            JOptionPane.showMessageDialog(null,
                    "1. W jednej grze uczestniczy 2, 3, 4 lub 6 graczy. Kazdy gracz ma 10 pionów\n" +
                            "2. Gra toczy się na planszy w kształcie szesciopromiennej gwiazdy. Kazdy promień zawiera 10 pól, w których\n" +
                            "początkowo umieszczane są piony. Wewnętrzny szesciokęt ma 61 pól, na każdy jego bok składa się z 5 pól.\n" +
                            "3. Celem kazdego gracza jest umieszczenie wszystkich swoich pionów w przeciwległym promieniu. Wygrywa\n" +
                            "gracz, który dokona tego jako pierwszy. Pozostali gracze mogą kontynuowac grę walcząc o kolejne miejsca.\n" +
                            "4. Gracz rozpoczynający grę wybierany jest losowo. Następnie gracze wykonują ruchy po kolei zgodnie z\n" +
                            "kierunkiem ruchu wskazówek zegara.\n" +
                            "5. Ruch polega na przesunięciu piona na sąsiednie puste pole lub na przeskakiwaniu pionem innych pionów\n" +
                            "(swoich lub przeciwnika) tak jak w warcabach, ale bez mozliwości zbijania pionów. Ruch może odbywać\n" +
                            "się w dowolnym kierunku, poza przypadkiem, gdy pion wejdzie do docelowego (przeciwległego) promienia.\n" +
                            "Wówczas pion moze wykonywaę ruchy jedynie w obrębie tego promienia.\n" +
                            "6. Dopuszczalne jest pozostawianie swoich pionów w promieniu docelowym innego gracza (tzw. blokowanie).\n" +
                            "7. Gracz moze zrezygnować z ruchu w danej turze.", "How to play?", JOptionPane.INFORMATION_MESSAGE);
        } else if (source == exitButton)
        {
            System.exit(0);
        } else if (source == newGameButton)
        {
            frame.dispose();
            new ChooseGameFrame(in,out,client);
        } else if (source == joinGameButton)
        {
             frame.dispose();
             new ChooseLobby(in,out,client);
        }
    }
}
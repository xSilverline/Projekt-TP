import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WaitingRoomFrame extends NewWindowFrame
{
    private ButtonGui returnButton;
    private JButton runButton;
    private ButtonGui refreshButton;
    private JFrame waitingRoomFrame;

    int size;
    private boolean readyPlayer;

    JList pList;
    DefaultListModel<String> list;

    ArrayList<JLabel> playerLabels;

    private PrintWriter out;
    private Client client;

    WaitingRoomFrame(int k, PrintWriter out, Client client)
    {
        this.size=k;
        this.out=out;
        this.client=client;

        makeGui();
        getList();
        readyPlayer = false;

        //refreshPlayers();
    }

    void makeGui()
    {
        waitingRoomFrame = new JFrame();
        waitingRoomFrame.setUndecorated(true);

        // playerLabels.add(new JLabel (playerList.get(0)));
        // playerLabels.get(0).setBackground(Color.green);
        // playerLabels.get(0).setOpaque(true);

        waitingRoomFrame.setSize(1366, 768);
        waitingRoomFrame.setLocation(250,50);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        waitingRoomFrame.setLocation(dim.width/2-waitingRoomFrame.getSize().width/2, dim.height/2-waitingRoomFrame.getSize().height/2);
        waitingRoomFrame.getContentPane().setBackground(new Color(74, 73, 75));
        waitingRoomFrame.setResizable(false);
        waitingRoomFrame.setLayout (null);
        runButton = new JButton("Ready");
        waitingRoomFrame.add(runButton);
        runButton.setBounds(533,680,300,70);
        runButton.setBackground(Color.GREEN);
        runButton.setFont(runButton.getFont().deriveFont(30f));
        returnButton = new ButtonGui("Return");
        waitingRoomFrame.add(returnButton);
        returnButton.setBounds(1200,30,150,50);
        runButton.setFocusPainted(false);

        refreshButton = new ButtonGui("Refresh");
        waitingRoomFrame.add(refreshButton);
        refreshButton.setBounds(16,30,150,50);
        pList=new JList();
        pList.setFont(pList.getFont().deriveFont(30f));
        pList.setForeground(Color.WHITE);
        pList.setBackground(new Color(0x585757));
        waitingRoomFrame.add(pList);
       // pList.setBounds(30,184,766,400);

        playerLabels = new ArrayList<>();
        for(int i=0;i<size;i++)
        {
            playerLabels.add(new JLabel());
            playerLabels.get(i).setHorizontalAlignment(SwingConstants.CENTER);
            playerLabels.get(i).setVerticalAlignment(SwingConstants.CENTER);
            playerLabels.get(i).setFont(playerLabels.get(i).getFont().deriveFont(20f));
            playerLabels.get(i).setBounds (483, 30+(85*(i)), 400, 70);
            playerLabels.get(i).setBackground(Color.GRAY);
            playerLabels.get(i).setOpaque(true);
            waitingRoomFrame.add(playerLabels.get(i));
        }

        runButton.addActionListener(this);
        returnButton.addActionListener(this);
        refreshButton.addActionListener(this);
        waitingRoomFrame.setVisible(true);
    }

    void getList()
    {

            list = new DefaultListModel<>();
            out.println("GET_LOBBY_INFO");

    }

    @Override
    void closeWindow()
    {
        waitingRoomFrame.dispose();
    }

    void showWaitMessage()
    {
        JOptionPane.showMessageDialog(null,"Waiting for other players!\n When everybody ready, game starts!","Please Wait a while...",JOptionPane.INFORMATION_MESSAGE);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source==returnButton)
        {
            out.println("RETURN_FROM_LOBBY");
            waitingRoomFrame.dispose();
            client.makeGui();
        }
        else if(source == refreshButton)
        {

            //new WaitingRoomFrame(size,in,out,client);

            getList();
        }
        else if(source == runButton)
        {

            if(readyPlayer)
            {
                out.println("NOT_READY_PLAYER");
                runButton.setText("Ready");
                runButton.setBackground(Color.GREEN);
                readyPlayer = false;
            }
            else
            {
                out.println("READY_PLAYER");
                readyPlayer = true;
                runButton.setBackground(Color.RED);
                runButton.setText("Leave Queue");
            }

        }

    }
}

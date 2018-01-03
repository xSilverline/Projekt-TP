public class RefreshLobbyThread extends Thread
{
    private ChooseLobby chooseLobby;
    private volatile boolean running = true;

    RefreshLobbyThread(ChooseLobby chooseLobby)
    {
        this.chooseLobby=chooseLobby;
    }
    void kill()
    {
        running = false;
    }

    public void run()
    {


        while (running)
        {
            System.out.println("Thread running");

            chooseLobby.getList();
            //waitingRoomFrame.refreshPlayers();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread finish");
    }
}

public class RefreshThread extends Thread
{
    private WaitingRoomFrame waitingRoomFrame;
    private volatile boolean running = true;

    RefreshThread(WaitingRoomFrame waitingRoomFrame)
    {
        this.waitingRoomFrame=waitingRoomFrame;
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

                waitingRoomFrame.getInfo();
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

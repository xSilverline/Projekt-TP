public class RefreshThread extends Thread
{
    WaitingRoomFrame waitingRoomFrame;

    RefreshThread(WaitingRoomFrame waitingRoomFrame)
    {
        this.waitingRoomFrame=waitingRoomFrame;
    }

    public void run()
    {
        while(true)
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
    }
}

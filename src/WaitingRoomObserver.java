public class WaitingRoomObserver extends Observer {

    private NewWindowFrame subject;

    @Override
    void setSubject(NewWindowFrame newWindowFrame)
    {
        this.subject=newWindowFrame;
    }
    void update()
    {
        subject.getList();
    }
}

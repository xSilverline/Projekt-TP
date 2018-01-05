public abstract class Observer
{
    private NewWindowFrame newWindowFrame;
    abstract void setSubject(NewWindowFrame newWindowFrame);
    abstract void update();
}

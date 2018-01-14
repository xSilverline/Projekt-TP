import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class NewWindowFrame implements ActionListener
{
    abstract void makeGui();
    abstract void getList();
    abstract void closeWindow();
    public abstract void actionPerformed(ActionEvent e);
}

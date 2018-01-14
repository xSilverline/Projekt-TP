package sources.src;

import javax.swing.*;
import java.awt.*;

class ButtonGui extends JButton
{
    ButtonGui(String text)
    {
        setText(text);
        setFont(this.getFont().deriveFont(30f));
        setBackground(new Color(111, 45, 49));
        setForeground(Color.white);
        setFocusPainted(false);
        //setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }
}

import java.awt.event.FocusListener;
import javax.swing.JTextField;

class Cell extends JTextField implements FocusListener {
  
  public String name;
  public String value;
  public String caption;
  public Cell next;

  public Cell(String name) {
    this.name = name;
    value = "";
    caption = "";
    next = null;
    addFocusListener(this);
  }

  public void focusLost(java.awt.event.FocusEvent e) {
    value = getText();
    caption = Main.getCaption(value);
    setText(caption);
  }

  public void focusGained(java.awt.event.FocusEvent e) {
    setText(value);
    Main.focusIn = name;
  }

}

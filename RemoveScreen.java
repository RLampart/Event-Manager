import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class RemoveScreen extends JPanel {
  private MainPlace place;
  private RemoveScreen rm;

  private JFrame frame;
  private ImagePanel r_pan;
  private JButton rembutton, back;
  private JPanel b_pan;
  private JScrollPane s_pane;

  private ArrayList<JRadioButton> r_buttons = new ArrayList<>();
  private ArrayList<Event> events = new ArrayList<>();

  public RemoveScreen(MainPlace thisform, JFrame m_frame) {
    place = thisform;
    frame = m_frame;
    rm = this;
    events = place.getEvents();
    r_pan = new ImagePanel(place.getImage());
    s_pane = new JScrollPane(r_pan);
    createRadioButtons();
    r_pan.setLayout(new GridLayout(0, 1));

    add(s_pane);
    b_pan = new JPanel();
    rembutton = new JButton("Remove Event");
    rembutton.addActionListener(new RemoveListener());
    back = new JButton("Back");
    back.addActionListener(new BackListener());
    b_pan.add(rembutton);
    b_pan.add(back);
    add(b_pan);
    setLayout(new GridLayout(0, 1));
  }

  private void createRadioButtons() {
    for (Event e : events) {
      JRadioButton button = new JRadioButton(e.getName());
      button.setForeground(Color.RED);
      button.setOpaque(false);
      r_buttons.add(button);
      r_pan.add(button);

    }
  }

  private class RemoveListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      boolean go = false;
      int[] selected = new int[r_buttons.size()];

      int count = 0;
      for (JRadioButton radio : r_buttons)
        if (radio.isSelected()) {
          go = true;
          selected[count] = r_buttons.indexOf(radio);
          count++;
        }
      if (go) {
        Option check = new Option(rm);
        if (check.getChoice()) {
          for (int x = 0; x < count; x++) {
            events.remove(selected[x]);
            place.getList().remove(selected[x]);
          }
        }
        place.saveEvents();
        setVisible(false);
        place.setVisible(true);
        frame.setContentPane(place);
      }

    }

  }

  private class BackListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      rm.setVisible(false);
      place.setVisible(true);
      frame.setContentPane(place);
    }
  }

}
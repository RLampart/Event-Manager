import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.*;

import java.awt.*;

public class EventDetailsGUI extends JFrame {

    private JButton editAnEvent;
    private JButton closeWindow;

    private JPanel pnlCommand;

    private Event event;

    private EventDetailsGUI thisGUI;
    private MainPlace frame;

    public EventDetailsGUI(Event eevent, MainPlace frame) {
        thisGUI = this;
        event = eevent;
        this.frame = frame;

        pnlCommand = new JPanel();
        pnlCommand.setLayout(new BoxLayout(pnlCommand, BoxLayout.PAGE_AXIS));

        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(new GridLayout(0, 2));
        JLabel titleLabel = new JLabel("Title: ");
        pnlInfo.add(titleLabel);
        JLabel titleValue = new JLabel(event.getName());
        pnlInfo.add(titleValue);
        JLabel VenueLabel = new JLabel("Venue: ");
        pnlInfo.add(VenueLabel);
        JLabel VenueValue = new JLabel(event.getVenue());
        pnlInfo.add(VenueValue);
        JLabel DateLabel = new JLabel("Date: ");
        pnlInfo.add(DateLabel);
        JLabel DateValue = new JLabel(event.datetoString());
        pnlInfo.add(DateValue);
        JLabel TimeLabel = new JLabel("Time: ");
        pnlInfo.add(TimeLabel);
        JLabel TimeValue = new JLabel(event.timeToString());
        pnlInfo.add(TimeValue);
        JLabel GuestsLabel = new JLabel("Maximum Guests: ");
        pnlInfo.add(GuestsLabel);
        JLabel GuestsValue = new JLabel(event.get_Expected_Guests() + "");
        pnlInfo.add(GuestsValue);
        pnlInfo.setBackground(Color.WHITE);

        editAnEvent = new JButton("Edit");
        closeWindow = new JButton("Close");
        closeWindow.addActionListener(new CloseButtonListener());
        editAnEvent.addActionListener(new EditListener());

        closeWindow.setBackground(Color.RED);
        editAnEvent.setBackground(Color.LIGHT_GRAY);

        pnlCommand.add(pnlInfo, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel();
        pnlButtons.add(editAnEvent);
        pnlButtons.add(closeWindow);

        pnlCommand.add(pnlButtons, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(pnlCommand);
        pack();
        this.setVisible(true);
    }

    private class CloseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame main = MainPlace.getFrame();
            main.setContentPane(new MainPlace());
            main.setVisible(true);
            thisGUI.setVisible(false);
        }
    }

    private class EditListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new EditEventGUI(event, thisGUI, frame);
            thisGUI.setVisible(false);
        }
    }
}

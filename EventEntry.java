import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EventEntry extends JFrame {
    private MainPlace Event_listing;
    private EventEntry thisEntry;

    private JTextField txtTitle;
    private JTextField txtDate;
    private JTextField txtVenue;
    private JTextField txtTime;
    private JTextField txtAtendee;
    private JButton cmdSave;
    private JButton cmdClose;

    private JPanel pnlCommand;
    private JPanel pnlDisplay;

    public EventEntry(MainPlace pl) {
        Event_listing = pl;
        thisEntry = this;

        setTitle("Entering new Event");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        pnlDisplay.add(new JLabel("Event Title:"));
        txtTitle = new JTextField(20);
        pnlDisplay.add(txtTitle);
        pnlDisplay.add(new JLabel("Venue:"));
        txtVenue = new JTextField(20);
        pnlDisplay.add(txtVenue);
        pnlDisplay.add(new JLabel("Date (dd/mm/yyyy):"));
        txtDate = new JTextField(20);
        pnlDisplay.add(txtDate);
        pnlDisplay.add(new JLabel("Time:"));
        txtTime = new JTextField(20);
        pnlDisplay.add(txtTime);
        pnlDisplay.add(new JLabel("Maximum # of Guests:"));
        txtAtendee = new JTextField(20);
        pnlDisplay.add(txtAtendee);
        pnlDisplay.setLayout(new GridLayout(5, 4));

        cmdSave = new JButton("Save");
        cmdSave.addActionListener(new saveEventButton());
        cmdClose = new JButton("Close");
        cmdClose.addActionListener(new setVisibleFalse());

        pnlCommand.add(cmdSave);
        pnlCommand.add(cmdClose);
        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

    private class setVisibleFalse implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            thisEntry.setVisible(false);
        }
    }

    private class saveEventButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = txtTitle.getText();
            String venue = txtVenue.getText();
            String date = txtDate.getText();
            String time = txtTime.getText();
            boolean mistakeMade = false;
            try {
                int expGuests = Integer.parseInt(txtAtendee.getText());
                if (!checkDate(date)) {
                    txtDate.setText("");
                    mistakeMade = true;
                }
                if (!checkTime(time)) {
                    txtTime.setText("");
                    mistakeMade = true;
                }
                if (name.length() != 0 && venue.length() != 0 && checkDate(date) && checkTime(time)) {
                    Event nevent = new Event(name, date, time, venue, expGuests);
                    Event_listing.addEvent(nevent);
                    setVisible(false);
                }
            } catch (NumberFormatException b) {
                txtAtendee.setText("");
                mistakeMade = true;
            }
            if (mistakeMade)
                JOptionPane.showMessageDialog(pnlDisplay, "Please ensure information is entered in correct format",
                        "Error", JOptionPane.ERROR_MESSAGE);
        }

        private boolean checkDate(String date) {
            Integer[][] dates = new Integer[][] { { 1, 3, 5, 7, 8, 10, 12 }, { 4, 6, 9, 11 }, { 2 } };

            // ArrayList daten = new int[] { 1, 3, 5, 7, 8, 10, 12 };
            boolean check = false;

            try {
                String[] ddate = date.split("/");
                String datePattern = "\\d{1,2}/\\d{1,2}/\\d{4}";
                if (date.matches(datePattern)) {

                    if ((Arrays.asList(dates[0]).contains(Integer.parseInt(ddate[1]))
                            && Integer.parseInt(ddate[0]) <= 31)
                            || (Arrays.asList(dates[1]).contains(Integer.parseInt(ddate[1]))
                                    && Integer.parseInt(ddate[0]) <= 30)
                            || (Arrays.asList(dates[2]).contains(Integer.parseInt(ddate[1]))
                                    && Integer.parseInt(ddate[0]) <= 28)
                            || (Arrays.asList(dates[2]).contains(Integer.parseInt(ddate[1]))
                                    && Integer.parseInt(ddate[0]) == 29
                                    && new GregorianCalendar().isLeapYear(Integer.parseInt(ddate[2])))) {
                        check = true;

                    }

                }

            } catch (IndexOutOfBoundsException e) {
            } catch (NumberFormatException e) {
            }
            return check;
        }

        private boolean checkTime(String time) {
            boolean check = false;
            try {
                String[] ttime = time.split(":");
                if (ttime.length == 2 && Integer.parseInt(ttime[0]) <= 24 && Integer.parseInt(ttime[1]) <= 59)
                    check = true;
            } catch (IndexOutOfBoundsException e) {
            } catch (NumberFormatException e) {
            }
            return check;
        }
    }

}
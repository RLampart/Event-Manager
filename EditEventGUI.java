import java.util.*;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class EditEventGUI extends JFrame {
    private JTextField txtName;
    private JTextField txtVenue;
    private JTextField txtDate;
    private JTextField txtTime;
    private JTextField txtGuests;

    private JButton cmdSave;
    private JButton cmdClose;

    private JPanel pnlCommand;
    private JPanel pnlDisplay;

    private Event event;
    private MainPlace place;

    public EditEventGUI(Event event, EventDetailsGUI frame, MainPlace place) {
        this.setTitle("Edit the " + event.getName() + " event");
        this.event = event;
        this.place = place;

        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        txtName = new JTextField(20);
        txtVenue = new JTextField(20);
        txtDate = new JTextField(20);
        txtTime = new JTextField(20);
        txtGuests = new JTextField(20);

        pnlDisplay.add(new JLabel("Title:"));
        pnlDisplay.add(txtName);
        pnlDisplay.add(new JLabel("Venue:"));
        pnlDisplay.add(txtVenue);
        pnlDisplay.add(new JLabel("Date dd/mm/yyyy:"));
        pnlDisplay.add(txtDate);
        pnlDisplay.add(new JLabel("Time hh:mm:"));
        pnlDisplay.add(txtTime);
        pnlDisplay.add(new JLabel("Maximum # of Attendees :"));
        pnlDisplay.add(txtGuests);
        pnlDisplay.setLayout(new GridLayout(5, 3));

        cmdSave = new JButton("Save");
        cmdSave.addActionListener(new saveEventButton());
        cmdClose = new JButton("Close");
        cmdClose.addActionListener(new CloseListener());

        pnlCommand.add(cmdSave);
        pnlCommand.add(cmdClose);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    private class saveEventButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = txtName.getText();
            String venue = txtVenue.getText();
            String date = txtDate.getText();
            String time = txtTime.getText();
            boolean errorMade = false;
            try {
                int expGuests = Integer.parseInt(txtGuests.getText());
                if (!checkDate(date)) {
                    txtDate.setText("");
                    errorMade = true;
                }
                if (!checkTime(time)) {
                    txtTime.setText("");
                    errorMade = true;
                }
                if (name.length() != 0 && venue.length() != 0 && checkDate(date) && checkTime(time)) {
                    event.changeName(name);
                    event.changeDate(date);
                    event.changeTime(time);
                    event.changeVenue(venue);
                    event.changeExpectedGuests(expGuests);
                    place.saveEvents();
                    new EventDetailsGUI(event, place);

                    setVisible(false);
                }
            } catch (NumberFormatException b) {
                txtGuests.setText("");
                errorMade = true;
            }
            if (errorMade)
                JOptionPane.showMessageDialog(pnlDisplay, "Please ensure information is entered in correct format",
                        "Error", JOptionPane.ERROR_MESSAGE);

        }

        private boolean checkDate(String date) {
            Integer[][] dates = new Integer[][] { { 1, 3, 5, 7, 8, 10, 12 }, { 4, 6, 9, 11 }, { 2 } };
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

    private class CloseListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            new EventDetailsGUI(event, place);
        }
    }
}
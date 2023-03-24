import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JButton;
import java.util.Comparator;
import java.util.Collections;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.sound.sampled.*;

public class MainPlace extends JPanel {
    private MainPlace thisform;
    private static Clip md;

    private JPanel com_list;

    private JScrollPane s_pane;

    private JButton addbutton, mute;
    private JButton sortvbutton;
    private JButton sortabutton;
    private JButton removebutton;

    private static JFrame m_frame;

    private Image img;

    private ImageIcon immg;

    private ImagePanel but_list;

    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<JButton> buttons = new ArrayList<>();

    public MainPlace() {
        loadEvents();
        thisform = this;

        com_list = new JPanel();
        createButtons();

        immg = new ImageIcon("pic.jpg");
        img = immg.getImage();

        but_list = new ImagePanel(img);

        s_pane = new JScrollPane(com_list);

        com_list.setLayout(new GridLayout(0, 1));

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        but_list.setLayout(grid);
        add(s_pane);
        s_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(but_list);

        addbutton = new JButton("Add Event");
        addbutton.addActionListener(new AddEventListener());
        removebutton = new JButton("Remove Event");
        mute = new JButton("Mute");
        mute.addActionListener(new MuteListener());
        sortabutton = new JButton("Sort by Date");
        sortabutton.addActionListener(new DateListener());
        sortvbutton = new JButton("Sort by Name");
        sortvbutton.addActionListener(new NameListener());

        removebutton.addActionListener(new RemoveListener());

        setLayout(new GridLayout(0, 1));
        but_list.add(removebutton);
        but_list.add(addbutton);
        but_list.add(sortvbutton);
        but_list.add(sortabutton);

        gbc.gridx = 2;
        gbc.gridy = 3;
        but_list.add(mute, gbc);
        saveEvents();

    }

    public static void main(String[] args) {
        m_frame = new JFrame("Event Planner");
        m_frame.setPreferredSize(new Dimension(500, 200));
        m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m_frame.setContentPane(new MainPlace());
        m_frame.pack();
        m_frame.setVisible(true);
        music();
    }

    public static void music() {
        AudioInputStream ms;
        try {
            ms = AudioSystem.getAudioInputStream(new File("Event-Manager/nicesong.wav"));
            md = AudioSystem.getClip();
            md.open(ms);
            md.start();
            md.loop(-1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            System.out.println(e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }

    }

    public static JFrame getFrame() {
        return m_frame;
    }

    public Image getImage() {
        return img;
    }

    public JPanel getList() {
        return com_list;
    }

    private void loadEvents() {

        try {
            File eventf = new File("event_list.txt");
            Scanner scan = new Scanner(eventf);
            while (scan.hasNext()) {
                String[] event = scan.next().split(">");
                Event eevent = new Event(event[0], event[1], event[2], event[3],
                        Integer.parseInt(event[4]));
                if (eevent.isValid())
                    events.add(eevent);

            }
            scan.close();
        } catch (FileNotFoundException e) {
        } catch (IndexOutOfBoundsException e) {
        } catch (NumberFormatException e) {
        }

    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void saveEvents() {
        try {
            File eventf = new File("event_list.txt");
            PrintWriter author = new PrintWriter(eventf);
            for (Event e : events) {
                author.println(e);
            }
            author.close();
        } catch (FileNotFoundException e) {
        } catch (IndexOutOfBoundsException e) {
        } catch (NumberFormatException e) {
        }
    }

    public void addEvent(Event eventn) {
        if (!events.contains(eventn)) {
            events.add(eventn);
            JButton button = new JButton(eventn.getName());
            button.addActionListener(new DetailListener());
            buttons.add(button);
            com_list.setVisible(false);
            com_list.add(button);
            com_list.setVisible(true);
            saveEvents();
        }

    }

    private void createButtons() {
        for (Event e : events) {
            JButton button = new JButton(e.getName());
            button.addActionListener(new DetailListener());
            buttons.add(button);
            com_list.add(button);

        }

    }

    private class AddEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new EventEntry(thisform);

        }
    }

    private class DateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Collections.sort(events);
            com_list.setVisible(false);
            com_list.removeAll();
            createButtons();
            com_list.setVisible(true);
            saveEvents();
        }
    }

    private class NameListener implements ActionListener {
        private class sortByName implements Comparator<Event> {
            // sorts persons in ascending order based on name
            public int compare(Event e1, Event e2) {
                return e1.getName().compareTo(e2.getName());
            }
        }

        public void actionPerformed(ActionEvent e) {
            Collections.sort(events, new sortByName());
            com_list.setVisible(false);
            com_list.removeAll();
            createButtons();
            com_list.setVisible(true);
            saveEvents();
        }
    }

    private class DetailListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new EventDetailsGUI(events.get(buttons.indexOf((JButton) e.getSource())), thisform);
            m_frame.setVisible(false);
        }
    }

    private class MuteListener implements ActionListener {
        private int click = 0;

        public void actionPerformed(ActionEvent e) {
            click++;
            if (click % 2 == 1) {
                md.stop();
                mute.setText("Unmute");
            } else {
                md.start();
                mute.setText("Mute");
            }
        }
    }

    private class RemoveListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            thisform.setVisible(false);
            m_frame.setContentPane(new RemoveScreen(thisform, m_frame));

        }
    }

}
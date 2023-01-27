import javax.swing.*;
import java.awt.event.*;

public class Option extends WindowAdapter {
    private boolean choice = false;

    Option(RemoveScreen here) {
        int a = JOptionPane.showConfirmDialog(here, "Are you sure?");
        if (a == JOptionPane.YES_OPTION) {
            choice = true;

        }
    }

    public boolean getChoice() {
        return choice;
    }
}
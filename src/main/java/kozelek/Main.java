package kozelek;

import kozelek.gui.controller.MainController;
import kozelek.gui.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        new MainController(window);
    }
    // Strategia A: 23178,01
    // Strategia B: 11651,01
    // Strategia C: 15724,25
    // Strategia D: 16013,85
}
package kozelek;

import kozelek.gui.controller.MainController;
import kozelek.gui.view.MainWindow;
import kozelek.mc.MonteCarlo;
import kozelek.mc.strategies.*;

public class Main {
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        MainController controller = new MainController(window);

//        DiscreteEmpiricGenerator ceg = new DiscreteEmpiricGenerator(Constants.DISTRIBUTIONS_SVETLO, new SeedGenerator(5));
//        try {
//            PrintWriter pw = new PrintWriter(new File("testdisc.csv"));
//
//            for (int i = 0; i < 1000000; i++) {
//                pw.write(ceg.sample().toString() + "\n");
//            }
//
//            pw.close();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }
    // Strategia A: 23178,01
    // Strategia B: 11651,01
    // Strategia C: 15724,25
    // Strategia D: 16013,85
}
package kozelek;

import kozelek.generator.SeedGenerator;
import mc.MonteCarlo;

public class Main {
    public static void main(String[] args) {
        MonteCarlo mc = new MonteCarlo(100000);
        mc.simuluj();
    }
}
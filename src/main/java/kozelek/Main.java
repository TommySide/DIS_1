package kozelek;

import mc.MonteCarlo;
import mc.strategies.Strategy;
import mc.strategies.StrategyA;

public class Main {
    public static void main(String[] args) {
        Strategy a = new StrategyA();
        Strategy[] strategies = new Strategy[]{a};

        MonteCarlo mc = new MonteCarlo(1000000, strategies);
        mc.simuluj();
    }
}
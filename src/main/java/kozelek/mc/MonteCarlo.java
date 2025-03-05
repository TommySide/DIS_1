package kozelek.mc;

import kozelek.config.Constants;
import kozelek.gui.ChartUpdateListener;
import kozelek.mc.strategies.Strategy;

public class MonteCarlo extends SimulationCore {
    private final double[] totalCosts;
    private final Strategy[] strategies;
    private final ChartUpdateListener listener;

    protected int currentRep;
    private final Long seed;

    public MonteCarlo(int numberOfReps, Long seed, Strategy[] strategies, ChartUpdateListener listener) {
        super(numberOfReps);
        this.strategies = strategies;
        totalCosts = new double[strategies.length];
        this.seed = seed;
        this.listener = listener;
    }

    @Override
    public void replication() {
        for (int i = 0; i < strategies.length; i++) {
            strategies[i].run();
            totalCosts[i] += strategies[i].getNaklady();
        }
    }

    @Override
    public void beforeReplications() {
        for (int i = 0; i < strategies.length; i++) {
            strategies[i].createGenerators(seed);
            totalCosts[i] = 0;
        }
        currentRep = 0;
    }

    @Override
    public void beforeReplication() {
        for (Strategy strategy : strategies) {
            strategy.clear();
        }
    }

    @Override
    public void afterReplication() {
        currentRep++;
        if (currentRep >= (getNumberOfReps() * Constants.PERCENTAGE_CUT_DATA) &&
                currentRep % (getNumberOfReps() * Constants.PERCENTAGE_UPDATE_DATA) == 0) {
            this.listener.updateChart(totalCosts, currentRep);
        }
    }

    @Override
    public void afterReplications() {
        for (int i = 0; i < strategies.length; i++) {
            System.out.format("Strategia %c: %.2f\n", i + 'A', totalCosts[i] / currentRep);
        }
    }
}

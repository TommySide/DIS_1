package mc;

import mc.strategies.Strategy;

public class MonteCarlo extends SimulationCore {
    private final double[] totalCosts;
    private final Strategy[] strategies;

    protected int currentRep;

    public MonteCarlo(int numberOfReps, Strategy[] strategies) {
        super(numberOfReps);
        this.strategies = strategies;
        totalCosts = new double[strategies.length];
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
        for (Strategy strategy : strategies) {
            strategy.createGenerators();
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
    }

    @Override
    public void afterReplications() {
        for (int i = 0; i < strategies.length; i++) {
            System.out.format("Strategia %d: %.2f\n", i, totalCosts[i] / currentRep);
        }
    }
}

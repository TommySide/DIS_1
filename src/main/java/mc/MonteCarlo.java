package mc;

import kozelek.config.Constants;
import kozelek.generator.SeedGenerator;
import kozelek.generator.continuos.ContinuosEmpiricGenerator;
import kozelek.generator.continuos.ContinuosUniformGenerator;
import kozelek.generator.discrete.DiscreteEmpiricGenerator;
import kozelek.generator.discrete.DiscreteUniformGenerator;

import java.util.Random;

public class MonteCarlo extends SimulationCore {
    // suciastky
    private DiscreteUniformGenerator randTlmice;
    private DiscreteUniformGenerator randBrzdy;
    private DiscreteEmpiricGenerator randSvetla;

    // dodavatel 1
    private ContinuosUniformGenerator randDodavatel1_10;
    private ContinuosUniformGenerator randDodavetel1_11;

    // dodavatel 2
    private ContinuosEmpiricGenerator randDodavatel2_15;
    private ContinuosEmpiricGenerator randDodavatel2_16;

    public MonteCarlo(int numberOfReps) {
        super(numberOfReps);
    }

    @Override
    public void replication() {

    }

    @Override
    public void beforeReplications() {
        SeedGenerator seed = new SeedGenerator(5);

        this.randTlmice = new DiscreteUniformGenerator(Constants.TLMICE_MIN, Constants.TLMICE_MAX, seed);
        this.randBrzdy = new DiscreteUniformGenerator(Constants.BRZDY_MIN, Constants.BRZDY_MAX, seed);
        this.randSvetla = new DiscreteEmpiricGenerator(Constants.DISTRIBUTIONS_SVETLO, seed);

        this.randDodavatel1_10 = new ContinuosUniformGenerator(Constants.DODAVATEL1_10_MIN, Constants.DODAVATEL1_10_MAX, seed);
        this.randDodavetel1_11 = new ContinuosUniformGenerator(Constants.DODAVATEL1_11_MIN, Constants.DODAVATEL1_11_MAX, seed);

        this.randDodavatel2_15 = new ContinuosEmpiricGenerator(Constants.DISTRIBUTIONS_DODAVATEL2_15, seed);
        this.randDodavatel2_16 = new ContinuosEmpiricGenerator(Constants.DISTRIBUTIONS_DODAVATEL2_16, seed);
    }

    @Override
    public void beforeReplication() {

    }

    @Override
    public void afterReplication() {

    }

    @Override
    public void afterReplications() {

    }
}

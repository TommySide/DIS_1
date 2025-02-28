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
    private Random randDodavatel1;

    // dodavatel 2
    private ContinuosEmpiricGenerator randDodavatel2_15;
    private ContinuosEmpiricGenerator randDodavatel2_16;
    private Random randDodavatel2;

    // sklad
    private int skladTlmice, skladBrzd, skladSvetla;
    private double naklady;
    private double total;

    private int currentRep;

    public MonteCarlo(int numberOfReps) {
        super(numberOfReps);
    }

    public void strategyA() {
        double deliveryChance = 0.0;
        for (int i = 0; i < 30; i++) {
            if (i < 10) {
                deliveryChance = this.randDodavatel1_10.sample() / 100;
            } else {
                deliveryChance = this.randDodavetel1_11.sample() / 100;
            }
            double chance = this.randDodavatel1.nextDouble();
            if (chance < deliveryChance) {
                this.delivery();
            }

            // naklady po - št
            this.calculateCost(4);

            // odoberatel ide kupit
            this.buyer();

            // naklady pi - ne
            this.calculateCost(3);
        }
    }

    public void buyer() {
        skladTlmice -= this.randTlmice.sample();
        skladBrzd -= this.randBrzdy.sample();
        skladSvetla -= this.randSvetla.sample();
        if (skladTlmice < 0) {
            naklady += Math.abs(Constants.POKUTA_KS * skladTlmice);
            skladTlmice = 0;
        }
        if (skladBrzd < 0) {
            naklady += Math.abs(Constants.POKUTA_KS * skladBrzd);
            skladBrzd = 0;
        }
        if (skladSvetla < 0) {
            naklady += Math.abs(Constants.POKUTA_KS * skladSvetla);
            skladSvetla = 0;
        }
    }

    public void delivery() {
        skladTlmice += Constants.TLMICE_COUNT;
        skladBrzd += Constants.BRZDY_COUNT;
        skladSvetla += Constants.SVETLA_COUNT;
    }

    public void calculateCost(int times) {
        naklady += (skladTlmice * Constants.TLMICE_PRICE_PD
                + skladBrzd * Constants.BRZDY_PRICE_PD
                + skladSvetla * Constants.SVETLA_PRICE_PD) * times;
    }

    @Override
    public void replication() {
        this.strategyA();
    }

    @Override
    public void beforeReplications() {
        SeedGenerator seed = new SeedGenerator();

        this.randTlmice = new DiscreteUniformGenerator(Constants.TLMICE_MIN, Constants.TLMICE_MAX, seed);
        this.randBrzdy = new DiscreteUniformGenerator(Constants.BRZDY_MIN, Constants.BRZDY_MAX, seed);
        this.randSvetla = new DiscreteEmpiricGenerator(Constants.DISTRIBUTIONS_SVETLO, seed);

        this.randDodavatel1_10 = new ContinuosUniformGenerator(Constants.DODAVATEL1_10_MIN, Constants.DODAVATEL1_10_MAX, seed);
        this.randDodavetel1_11 = new ContinuosUniformGenerator(Constants.DODAVATEL1_11_MIN, Constants.DODAVATEL1_11_MAX, seed);

        this.randDodavatel2_15 = new ContinuosEmpiricGenerator(Constants.DISTRIBUTIONS_DODAVATEL2_15, seed);
        this.randDodavatel2_16 = new ContinuosEmpiricGenerator(Constants.DISTRIBUTIONS_DODAVATEL2_16, seed);

        this.randDodavatel1 = new Random();
        this.randDodavatel2 = new Random();

        currentRep = 0;
        total = 0;
    }

    @Override
    public void beforeReplication() {
        naklady = 0;
        skladTlmice = 0;
        skladBrzd = 0;
        skladSvetla = 0;
    }

    @Override
    public void afterReplication() {
        currentRep++;
        total += naklady;
        if (currentRep % 1000 == 0) {
            System.out.println(total / currentRep);
        }
    }

    @Override
    public void afterReplications() {

    }
}

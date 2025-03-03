package mc.strategies;

import kozelek.config.Constants;
import kozelek.generator.SeedGenerator;
import kozelek.generator.continuos.ContinuosEmpiricGenerator;
import kozelek.generator.continuos.ContinuosUniformGenerator;
import kozelek.generator.discrete.DiscreteEmpiricGenerator;
import kozelek.generator.discrete.DiscreteUniformGenerator;

import java.util.Random;

public abstract class Strategy {
    protected int skladTlmice, skladBrzd, skladSvetla;
    protected double naklady;

    // suciastky
    protected DiscreteUniformGenerator randTlmice;
    protected DiscreteUniformGenerator randBrzdy;
    protected DiscreteEmpiricGenerator randSvetla;

    // dodavatel 1
    protected ContinuosUniformGenerator randDodavatel1_10;
    protected ContinuosUniformGenerator randDodavetel1_11;
    protected Random randDodavatel1;

    // dodavatel 2
    protected ContinuosEmpiricGenerator randDodavatel2_15;
    protected ContinuosEmpiricGenerator randDodavatel2_16;
    protected Random randDodavatel2;

    public abstract void run();

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

    public void clear() {
        naklady = 0;
        skladTlmice = 0;
        skladBrzd = 0;
        skladSvetla = 0;
    }

    public void createGenerators() {
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
    }

    public double getNaklady() {
        return naklady;
    }
}

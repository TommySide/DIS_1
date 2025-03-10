package kozelek.mc.strategies;

import kozelek.config.Constants;
import kozelek.generator.SeedGenerator;
import kozelek.generator.continuos.ContinuosEmpiricGenerator;
import kozelek.generator.continuos.ContinuosUniformGenerator;
import kozelek.generator.discrete.DiscreteEmpiricGenerator;
import kozelek.generator.discrete.DiscreteUniformGenerator;

import java.util.ArrayList;
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

    protected boolean day;
    private final ArrayList<Double> nakladyPerRun = new ArrayList<>(210);

    public abstract void run();

    public void buyer() {
        double tempNaklady = 0.0;
        skladTlmice -= this.randTlmice.sample();
        skladBrzd -= this.randBrzdy.sample();
        skladSvetla -= this.randSvetla.sample();
        if (skladTlmice < 0) {
            tempNaklady += Math.abs(Constants.POKUTA_KS * skladTlmice);
            skladTlmice = 0;
        }
        if (skladBrzd < 0) {
            tempNaklady += Math.abs(Constants.POKUTA_KS * skladBrzd);
            skladBrzd = 0;
        }
        if (skladSvetla < 0) {
            tempNaklady += Math.abs(Constants.POKUTA_KS * skladSvetla);
            skladSvetla = 0;
        }
        naklady += tempNaklady;
        if (!day)
            this.nakladyPerRun.add(tempNaklady + this.nakladyPerRun.getLast());
    }

    public void delivery() {
        skladTlmice += Constants.TLMICE_COUNT;
        skladBrzd += Constants.BRZDY_COUNT;
        skladSvetla += Constants.SVETLA_COUNT;
    }

    public void calculateCost(int times) {
        double temp = (skladTlmice * Constants.TLMICE_PRICE_PD
                + skladBrzd * Constants.BRZDY_PRICE_PD
                + skladSvetla * Constants.SVETLA_PRICE_PD);
        naklady += temp * times;

        if (!day) {
            for (int i = 0; i < times; i++) {
                if (!nakladyPerRun.isEmpty())
                    if (times == 3 && i == 0) {
                        double last = nakladyPerRun.getLast();
                        nakladyPerRun.set(nakladyPerRun.size() - 1, temp + last);
                    } else
                        nakladyPerRun.add(temp + nakladyPerRun.getLast());
                else
                    nakladyPerRun.add(temp);
            }
        }
    }

    public void clear() {
        naklady = 0;
        skladTlmice = 0;
        skladBrzd = 0;
        skladSvetla = 0;
    }

    public double sampleDodavatel1(int week) {
        if (week < 10)
            return this.randDodavatel1_10.sample() / 100;
        else
            return this.randDodavetel1_11.sample() / 100;
    }

    public double sampleDodavatel2(int week) {
        if (week < 15)
            return this.randDodavatel2_15.sample() / 100;
        else
            return this.randDodavatel2_16.sample() / 100;
    }

    public void createGenerators(Long s) {
        SeedGenerator seed;
        if (s == null)
            seed = new SeedGenerator();
        else
            seed = new SeedGenerator(s);


        this.randTlmice = new DiscreteUniformGenerator(Constants.TLMICE_MIN, Constants.TLMICE_MAX + 1, seed);
        this.randBrzdy = new DiscreteUniformGenerator(Constants.BRZDY_MIN, Constants.BRZDY_MAX + 1, seed);
        this.randSvetla = new DiscreteEmpiricGenerator(Constants.DISTRIBUTIONS_SVETLO, seed);

        this.randDodavatel1_10 = new ContinuosUniformGenerator(Constants.DODAVATEL1_10_MIN, Constants.DODAVATEL1_10_MAX, seed);
        this.randDodavetel1_11 = new ContinuosUniformGenerator(Constants.DODAVATEL1_11_MIN, Constants.DODAVATEL1_11_MAX, seed);

        this.randDodavatel2_15 = new ContinuosEmpiricGenerator(Constants.DISTRIBUTIONS_DODAVATEL2_15, seed);
        this.randDodavatel2_16 = new ContinuosEmpiricGenerator(Constants.DISTRIBUTIONS_DODAVATEL2_16, seed);

        this.randDodavatel1 = new Random(seed.sample());
        this.randDodavatel2 = new Random(seed.sample());
    }

    public double getNaklady() {
        return naklady;
    }

    public ArrayList<Double> getNakladyPerRun() {
        return nakladyPerRun;
    }

    public void setDay(boolean value) {
        this.day = value;
    }
}

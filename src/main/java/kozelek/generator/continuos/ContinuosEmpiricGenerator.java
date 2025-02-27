package kozelek.generator.continuos;

import kozelek.generator.IGenerator;
import kozelek.generator.Distribution;

import java.util.Random;

public class ContinuosEmpiricGenerator implements IGenerator<Double> {
    private ContinuosGenerator[] generators;
    private Random probRand;
    private Distribution[] dists;
    public ContinuosEmpiricGenerator(Distribution[] dists) {
        this.generators = new ContinuosGenerator[dists.length];
        this.dists = dists;
        for (int i = 0; i < dists.length; i++) {
            this.generators[i] = new ContinuosGenerator(this.dists[i].getMin(), this.dists[i].getMax());
        }
        this.probRand = new Random();
    }

    @Override
    public Double sample() {
        double prob = this.probRand.nextDouble(); // vygenerujeme prob. sancu
        double sum = 0;
        for (int i = 0; i < this.dists.length; i++) {
            sum += this.dists[i].getProbability();

            if (sum > prob) {
                return this.generators[i].sample();
            }
        }
        throw new RuntimeException();
    }

    public String test() {
        double prob = this.probRand.nextDouble(); // vygenerujeme prob. sancu
        double sum = 0;
        for (int i = 0; i < this.dists.length; i++) {
            sum += this.dists[i].getProbability();

            if (sum > prob) {
                return this.generators[i].sample() + "\t" + prob;
            }
        }
        throw new RuntimeException();
    }
}

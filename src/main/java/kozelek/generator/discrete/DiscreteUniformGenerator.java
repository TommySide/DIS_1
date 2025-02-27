package kozelek.generator.discrete;

import kozelek.generator.IGenerator;
import kozelek.generator.SeedGenerator;

import java.util.Random;

public class DiscreteUniformGenerator implements IGenerator<Integer> {
    private int min, max;
    private Random rand;
    public DiscreteUniformGenerator(int min, int max, SeedGenerator seedGenerator) {
        this.min = min;
        this.max = max;
        this.rand = new Random(seedGenerator.sample());
    }

    @Override
    public Integer sample() {
        return this.rand.nextInt(min, max);
    }
}

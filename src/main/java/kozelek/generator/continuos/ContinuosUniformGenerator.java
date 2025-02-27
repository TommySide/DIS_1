package kozelek.generator.continuos;

import kozelek.generator.IGenerator;
import kozelek.generator.SeedGenerator;

import java.util.Random;

public class ContinuosUniformGenerator implements IGenerator<Double> {
    private double min, max;
    private Random random;

    public ContinuosUniformGenerator(double min, double max, SeedGenerator seedGenerator) {
        this.min = min;
        this.max = max;
        this.random = new Random(seedGenerator.sample());
    }

    @Override
    public Double sample() {
        return this.random.nextDouble(min, max);
    }
}

package kozelek.generator.continuos;

import kozelek.generator.IGenerator;

import java.util.Random;

public class ContinuosUniformGenerator implements IGenerator<Double> {
    private double min, max;
    private Random random;

    public ContinuosUniformGenerator(double min, double max) {
        this.min = min;
        this.max = max;
        this.random = new Random();
    }

    @Override
    public Double sample() {
        return this.random.nextDouble(min, max);
    }
}

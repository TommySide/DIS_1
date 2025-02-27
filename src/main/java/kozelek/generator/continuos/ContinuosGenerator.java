package kozelek.generator.continuos;

import kozelek.generator.IGenerator;

import java.util.Random;

public class ContinuosGenerator implements IGenerator<Double> {
    private Double min, max;
    private Random rand;

    public ContinuosGenerator(Double min, Double max) {
        this.min = min;
        this.max = max;
        this.rand = new Random();
    }

    @Override
    public Double sample() {
        return min + (max - min) * this.rand.nextDouble();
    }
}

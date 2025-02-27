package kozelek.generator.discrete;

import kozelek.generator.IGenerator;

import java.util.Random;

public class DiscreteUniformGenerator implements IGenerator<Integer> {
    private int min, max;
    private Random random;
    public DiscreteUniformGenerator(int min, int max) {
        this.min = min;
        this.max = max;

        this.random = new Random();
    }

    @Override
    public Integer sample() {
        return this.random.nextInt(min, max);
    }
}

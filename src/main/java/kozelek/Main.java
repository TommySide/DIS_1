package kozelek;

import kozelek.generator.SeedGenerator;

public class Main {
    public static void main(String[] args) {
        SeedGenerator seedGenerator = new SeedGenerator();
        for (int i = 0; i < 10; i++) {
            System.out.println(seedGenerator.sample());
        }
    }
}
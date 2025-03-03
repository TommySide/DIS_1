package mc.strategies;

public class StrategyA extends Strategy {
    @Override
    public void run() {
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
}

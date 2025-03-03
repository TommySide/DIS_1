package mc.strategies;

public class StrategyC extends Strategy {
    @Override
    public void run() {
        double deliveryChance = 0.0;
        double chance = 0.0;
        for (int i = 0; i < 30; i++) {
            // parny
            if (i % 2 == 0) {
                // dod 2
                deliveryChance = this.sampleDodavatel2(i);
                chance = this.randDodavatel2.nextDouble();
            } else {
                deliveryChance = this.sampleDodavatel1(i);
                chance = this.randDodavatel1.nextDouble();
            }
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

package kozelek.mc.strategies;

import kozelek.gui.interfaces.ChartDailyUpdateListener;

public class StrategyB extends Strategy {

    @Override
    public void run() {
        double deliveryChance = 0.0;
        for (int i = 0; i < 30; i++) {
            deliveryChance = this.sampleDodavatel2(i);
            double chance = this.randDodavatel2.nextDouble();
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
        day = true;
    }
}

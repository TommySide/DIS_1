package kozelek.mc.strategies;

import kozelek.config.Constants;
import kozelek.gui.interfaces.ChartDailyUpdateListener;

public class StrategyE extends Strategy {
    // 10 tyz. dod 1
    // 11 - 15 dod 1
    // 15 -> dod 1?
    @Override
    public void run() {
        double deliveryChance = 0.0;
        double chance = 0.0;
        for (int i = 0; i < 30; i++) {
            // dod 1
            deliveryChance = this.sampleDodavatel1(i);
            chance = this.randDodavatel1.nextDouble();

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

    public void delivery() {
        int avgTlmice = (Constants.TLMICE_MIN + Constants.TLMICE_MAX) / 2;
        int avgBrzdy = (Constants.BRZDY_MIN + Constants.BRZDY_MAX) / 2;
        int avgSvetla = 91;

        int thresholdTlmice = avgTlmice / 2;
        int topThresholdTlmice = (int) (avgTlmice * 1.5);

        int thresholdBrzdy = avgBrzdy / 2;
        int topThresholdBrzdy = (int) (avgBrzdy * 1.5);

        int thresholdSvetla = avgSvetla / 2;
        int topThresholdSvetla = (int) (avgSvetla * 1.5);

        int orderTlmice = Constants.TLMICE_COUNT;
        int orderBrzdy = Constants.BRZDY_COUNT;
        int orderSvetla = Constants.SVETLA_COUNT;

        if (skladTlmice < thresholdTlmice) {
            orderTlmice = (int) (Constants.TLMICE_COUNT * Constants.PERCENTAGE_EXTRA_BUY);
        } else if (skladTlmice > topThresholdTlmice) {
            orderTlmice = (int) (Constants.TLMICE_COUNT * Constants.PERCENTAGE_SMALLER_BUY);
        }

        if (skladBrzd < thresholdBrzdy) {
            orderBrzdy = (int) (Constants.BRZDY_COUNT * Constants.PERCENTAGE_EXTRA_BUY);
        } else if (skladBrzd > topThresholdBrzdy) {
            orderBrzdy = (int) (Constants.BRZDY_COUNT * Constants.PERCENTAGE_SMALLER_BUY);
        }

        if (skladSvetla < thresholdSvetla) {
            orderSvetla = (int) (Constants.SVETLA_COUNT * Constants.PERCENTAGE_EXTRA_BUY);
        } else if (skladSvetla > topThresholdSvetla) {
            orderSvetla = (int) (Constants.SVETLA_COUNT * Constants.PERCENTAGE_SMALLER_BUY);
        }

        skladTlmice += orderTlmice;
        skladBrzd += orderBrzdy;
        skladSvetla += orderSvetla;
    }


}

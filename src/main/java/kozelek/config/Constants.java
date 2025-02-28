package kozelek.config;

import kozelek.generator.Distribution;

public class Constants {
    public static final int TLMICE_COUNT = 100;
    public static final int BRZDY_COUNT = 200;
    public static final int SVETLA_COUNT = 150;

    public static final double TLMICE_PRICE_PD = 0.2;
    public static final double BRZDY_PRICE_PD = 0.3;
    public static final double SVETLA_PRICE_PD = 0.25;

    public static final double POKUTA_KS = 0.3;

    public static final int TLMICE_MIN = 50;
    public static final int TLMICE_MAX = 100;

    public static final int BRZDY_MIN = 60;
    public static final int BRZDY_MAX = 250;

    public static final Distribution[] DISTRIBUTIONS_SVETLO = new Distribution[]{
            new Distribution(30, 60, 0.2),
            new Distribution(60, 100, 0.4),
            new Distribution(100, 140, 0.3),
            new Distribution(140, 160, 0.1),
    };

    // dodavatelia

    public static final int DODAVATEL1_10_MIN = 10;
    public static final int DODAVATEL1_10_MAX = 70;

    public static final int DODAVATEL1_11_MIN = 30;
    public static final int DODAVATEL1_11_MAX = 95;

    public static final Distribution[] DISTRIBUTIONS_DODAVATEL2_15 = new Distribution[]{
            new Distribution(5, 10, 0.4),
            new Distribution(10, 50, 0.3),
            new Distribution(50, 70, 0.2),
            new Distribution(70, 80, 0.06),
            new Distribution(80, 95, 0.04),
    };

    public static final Distribution[] DISTRIBUTIONS_DODAVATEL2_16 = new Distribution[]{
            new Distribution(5, 10, 0.2),
            new Distribution(10, 50, 0.4),
            new Distribution(50, 70, 0.3),
            new Distribution(70, 80, 0.06),
            new Distribution(80, 95, 0.04),
    };
}

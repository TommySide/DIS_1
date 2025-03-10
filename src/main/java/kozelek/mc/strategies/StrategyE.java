package kozelek.mc.strategies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StrategyE extends Strategy {
    private ArrayList<Integer> dodavatelia;
    private ArrayList<Integer> tlmiceList ;
    private ArrayList<Integer> brzdyList;
    private ArrayList<Integer> svetlaList;

    /// CSV should be formatted like:
    ///
    /// dodavatel;tlmice;brzdy;svetla
    public StrategyE(String filename) {
        this.loadDataFromCSV(filename);
    }

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            // dod 1
            int dodavatel = (i < dodavatelia.size()) ? dodavatelia.get(i) : dodavatelia.getLast();
            int orderTlmice = (i < tlmiceList.size()) ? tlmiceList.get(i) : tlmiceList.getLast();
            int orderBrzdy = (i < brzdyList.size()) ? brzdyList.get(i) : brzdyList.getLast();
            int orderSvetla = (i < svetlaList.size()) ? svetlaList.get(i) : svetlaList.getLast();

            double deliveryChance = dodavatel == 1 ? this.sampleDodavatel1(i) : this.sampleDodavatel2(i);
            double chance = this.randDodavatel1.nextDouble();

            if (chance < deliveryChance) {
                this.delivery(orderTlmice, orderBrzdy, orderSvetla);
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

    public void loadDataFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            dodavatelia = new ArrayList<>();
            tlmiceList = new ArrayList<>();
            brzdyList = new ArrayList<>();
            svetlaList = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values.length == 4) {
                    dodavatelia.add(Integer.parseInt(values[0]));
                    tlmiceList.add(Integer.parseInt(values[1]));
                    brzdyList.add(Integer.parseInt(values[2]));
                    svetlaList.add(Integer.parseInt(values[3]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public void delivery(int orderTlmice, int orderBrzdy, int orderSvetla) {
        skladTlmice += orderTlmice;
        skladBrzd += orderBrzdy;
        skladSvetla += orderSvetla;
    }


}

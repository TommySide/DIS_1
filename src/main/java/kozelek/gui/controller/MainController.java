package kozelek.gui.controller;

import kozelek.config.Constants;
import kozelek.gui.ChartUpdateListener;
import kozelek.gui.view.MainWindow;
import kozelek.mc.MonteCarlo;
import kozelek.mc.strategies.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class MainController implements ChartUpdateListener {
    private final MainWindow view;
    private int repCount;
    private MonteCarlo monteCarlo;

    private JFreeChart[] charts;
    private JLabel[] labels;

    private final Strategy a = new StrategyA();
    private final Strategy b = new StrategyB();
    private final Strategy c = new StrategyC();
    private final Strategy d = new StrategyD();
    private final Strategy e = new StrategyE();
    private final Strategy[] strategies = new Strategy[]{a, b, c, d, e};

    public MainController(MainWindow view) {
        this.view = view;
        this.view.setVisible(true);

        this.view.getStartButton().addActionListener(e -> startSimulation());
        this.view.getKoniecButton().addActionListener(e -> stopSimulation());

        charts = this.view.getCharts();
        labels = this.view.getLabels();
    }

    private void stopSimulation() {
        this.monteCarlo.stopSimulation();
    }

    private void startSimulation() {
        this.view.resetCharts();

        String text = this.view.getFieldRepCount().getText();
        if (!text.isEmpty()) {
            this.repCount = Integer.parseInt(text);
            this.monteCarlo = new MonteCarlo(repCount, null, strategies, MainController.this);
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    monteCarlo.simuluj();
                    return null;
                }

            };
            worker.execute();
        }
    }

    @Override
    public void updateChart(double[] totalCosts, int rep) {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < charts.length; i++) {
                XYSeries series = ((XYSeriesCollection) charts[i].getXYPlot().getDataset()).getSeries(0);

                series.add(rep, totalCosts[i] / rep);
                double offsetFactor = Constants.OFFSET_FACTOR;

                this.view.updateRange(charts[i], series, offsetFactor);
                this.labels[i].setText(String.format("%.2f", totalCosts[i] / rep));
                this.charts[i].fireChartChanged();
            }
        });
    }
}

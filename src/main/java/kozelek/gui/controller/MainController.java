package kozelek.gui.controller;

import kozelek.config.Constants;
import kozelek.gui.interfaces.ChartUpdateListener;
import kozelek.gui.view.DayWindow;
import kozelek.gui.view.MainWindow;
import kozelek.mc.MonteCarlo;
import kozelek.mc.strategies.*;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;

public class MainController implements ChartUpdateListener {
    private final MainWindow view;
    private MonteCarlo monteCarlo;

    private final JFreeChart[] charts;
    private final JLabel[] labels;

    private final Strategy a = new StrategyA();
    private final Strategy b = new StrategyB();
    private final Strategy c = new StrategyC();
    private final Strategy d = new StrategyD();
    private final StrategyE e = new StrategyE("str.csv");
    private final Strategy[] strategies = new Strategy[]{a, b, c, d, e};

    public MainController(MainWindow view) {
        this.view = view;
        this.view.setVisible(true);

        this.view.getStartButton().addActionListener(_ -> startSimulation());
        this.view.getKoniecButton().addActionListener(_ -> stopSimulation());
        this.view.getFileButton().addActionListener(_ -> getSelectedFile());

        charts = this.view.getCharts();
        labels = this.view.getLabels();
        ChartPanel[] panels = this.view.getChartPanels();
        for (ChartPanel panel : panels) {
            panel.addChartMouseListener(new ChartMouseListener() {
                @Override
                public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {
                    JFreeChart clicked = chartMouseEvent.getChart();
                    showDailyChart(clicked.getID());
                }

                @Override
                public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {
                }
            });
        }
    }

    private void getSelectedFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "CSV"));

        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            e.loadDataFromCSV(file.getAbsolutePath());
        }
    }

    private void showDailyChart(String id) {
        System.out.println(id);
        ArrayList<Double> data;
        switch (id) {
            case "A" -> data = a.getNakladyPerRun();
            case "B" -> data = b.getNakladyPerRun();
            case "C" -> data = c.getNakladyPerRun();
            case "D" -> data = d.getNakladyPerRun();
            case "E" -> data = e.getNakladyPerRun();
            default -> data = null;
        }
        if (data != null) {
            DayWindow dw = new DayWindow(data);
            dw.setVisible(true);
//            for (Double d : data) {
//                System.out.println(d.toString());
//            }
        }
    }

    private void stopSimulation() {
        this.monteCarlo.stopSimulation();
    }

    private void startSimulation() {
        this.view.resetCharts();

        String text = this.view.getFieldRepCount().getText();
        if (!text.isEmpty()) {
            int repCount = Integer.parseInt(text);
            this.monteCarlo = new MonteCarlo(repCount, null, strategies, MainController.this);
            for (Strategy strategy : strategies) {
                strategy.setDay(false);
            }
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

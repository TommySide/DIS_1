package kozelek.gui.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JTextField fieldRepCount;
    private JButton startButton;
    private JButton koniecButton;
    private ChartPanel chartPanel1;
    private ChartPanel chartPanel2;
    private ChartPanel chartPanel3;
    private ChartPanel chartPanel4;
    private JLabel labelA;
    private JLabel labelB;
    private JLabel labelC;
    private JLabel labelD;
    private ChartPanel chartPanel5;
    private JLabel labelE;
    private JPanel panel;
    private JFreeChart chartA, chartB, chartC, chartD, chartE;

    public MainWindow() {
        setTitle("Main Window");
        setMinimumSize(new Dimension(1500, 900));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
    }

    private void createUIComponents() {
        this.chartA = createChart("Str. A");
        this.chartB = createChart("Str. B");
        this.chartC = createChart("Str. C");
        this.chartD = createChart("Str. D");
        this.chartE = createChart("Str. E");

        chartPanel1 = new ChartPanel(chartA);
        chartPanel2 = new ChartPanel(chartB);
        chartPanel3 = new ChartPanel(chartC);
        chartPanel4 = new ChartPanel(chartD);
        chartPanel5 = new ChartPanel(chartE);
    }

    public JFreeChart createChart(String title) {
        XYSeries series = new XYSeries("Price");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Replikácie",
                "Náklady",
                dataset
        );
        chart.getXYPlot().getRangeAxis().setAutoRange(false);
        return chart;
    }

    public void resetCharts() {
        resetChart(chartA);
        resetChart(chartB);
        resetChart(chartC);
        resetChart(chartD);
        resetChart(chartE);

        labelA.setText("");
        labelB.setText("");
        labelC.setText("");
        labelD.setText("");
        labelE.setText("");
    }

    public void updateRange(JFreeChart chart, XYSeries series, double offset) {
        double min = series.getMinY();
        double max = series.getMaxY();
        double range = max - min;
        if (range > 0)
            chart.getXYPlot().getRangeAxis().setRange(min - (range * offset), max + (range * offset));
    }

    private void resetChart(JFreeChart chart) {
        XYSeriesCollection dataset = (XYSeriesCollection) chart.getXYPlot().getDataset();
        dataset.removeAllSeries();

        XYSeries series = new XYSeries("Price");
        dataset.addSeries(series);

        chart.getXYPlot().getRangeAxis().setAutoRange(true);
    }

    public JFreeChart[] getCharts() {
        return new JFreeChart[]{chartA, chartB, chartC, chartD, chartE};
    }

    public JLabel[] getLabels() {
        return new JLabel[]{labelA, labelB, labelC, labelD, labelE};
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getKoniecButton() {
        return koniecButton;
    }

    public JTextField getFieldRepCount() {
        return fieldRepCount;
    }
}

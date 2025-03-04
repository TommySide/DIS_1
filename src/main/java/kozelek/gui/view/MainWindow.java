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
    private JPanel panel1;
    private JFreeChart chartA, chartB, chartC, chartD;

    public MainWindow() {
        setTitle("Start Window");
        setSize(1600, 800);
        setMinimumSize(new Dimension(1600, 800));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel1);
    }

    private void createUIComponents() {
        this.chartA = createChart("A");
        this.chartB = createChart("B");
        this.chartC = createChart("C");
        this.chartD = createChart("C");

        chartPanel1 = new ChartPanel(chartA);
        chartPanel2 = new ChartPanel(chartB);
        chartPanel3 = new ChartPanel(chartC);
        chartPanel4 = new ChartPanel(chartD);
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

        labelA.setText("");
        labelB.setText("");
        labelC.setText("");
        labelD.setText("");
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

    public JLabel getLabelA() {
        return labelA;
    }

    public JLabel getLabelB() {
        return labelB;
    }

    public JLabel getLabelC() {
        return labelC;
    }

    public JLabel getLabelD() {
        return labelD;
    }

    public JFreeChart getChartA() {
        return chartA;
    }

    public JFreeChart getChartB() {
        return chartB;
    }

    public JFreeChart getChartC() {
        return chartC;
    }

    public JFreeChart getChartD() {
        return chartD;
    }

    public JFreeChart[] getCharts() {
        return new JFreeChart[]{chartA, chartB, chartC, chartD};
    }

    public JLabel[] getLabels() {
        return new JLabel[]{labelA, labelB, labelC, labelD};
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

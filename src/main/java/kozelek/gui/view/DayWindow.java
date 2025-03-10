package kozelek.gui.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DayWindow extends JFrame {
    private ChartPanel chartPanel1;
    private JPanel panel1;
    private JButton goBackButton;
    private JFreeChart chart;
    private ArrayList<Double> data;

    public DayWindow(ArrayList<Double> dayData) {
        setTitle("One day Window");
        setMinimumSize(new Dimension(1500, 900));
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        goBackButton.addActionListener(_ -> close());

        data = dayData;
        this.displayData();
        add(panel1);
    }

    private void close() {
        this.dispose();
    }

    private void displayData() {
        XYSeries series = new XYSeries("Price");

        for (int i = 0; i < data.size(); i++) {
            series.add(i + 1, data.get(i));
        }

        XYSeriesCollection dataset = (XYSeriesCollection) chart.getXYPlot().getDataset();
        dataset.removeAllSeries();
        dataset.addSeries(series);

        updateRange(chart, series, 0.05);
    }

    private void createUIComponents() {
        chart = createChart("Day");

        chartPanel1 = new ChartPanel(chart);
    }

    public JFreeChart createChart(String title) {
        XYSeries series = new XYSeries("Price");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Deň",
                "Náklady",
                dataset
        );
        chart.getXYPlot().getRangeAxis().setAutoRange(false);
        return chart;
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
}

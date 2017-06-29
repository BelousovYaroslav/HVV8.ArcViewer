/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hvv_arcviewer;

import java.awt.Color;
import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author yaroslav
 */
public class PanelGraph extends javax.swing.JPanel {

    private final HVV_ArchiveViewer theApp;
    private TimeSeriesCollection dataset;
    
    final JFreeChart m_chart;
    ChartPanel m_chartPanel;
    /**
     * Creates new form PanelGraph
     */
    public PanelGraph( HVV_ArchiveViewer app, TimeSeries serie) {
        initComponents();
        theApp = app;
        
        dataset = new TimeSeriesCollection( serie);
        
        m_chart = createChart( dataset);
        
        m_chartPanel = new ChartPanel( m_chart);
        m_chart.removeLegend();
        
        //m_chartPanel.setPreferredSize( new Dimension( 100, 200));
        add( m_chartPanel);
    }

    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            null,
            "Время",
            "Пока неясно",
            dataset,
            true,
            true,
            false
        );
        final XYPlot plot = result.getXYPlot();
        
        plot.setBackgroundPaint( Color.BLACK);
        
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        //axis.setFixedAutoRange(60000.0);  // 60 seconds
        
        //plot.getRendererForDataset( dataset).setSeriesPaint( 0, new Color( 0,   0,  32));
        //plot.getRendererForDataset( dataset).setSeriesPaint( 1, new Color( 0,   32, 0));
        //plot.getRendererForDataset( dataset).setSeriesPaint( 2, new Color( 32,  0,  0));
        
        //DateFormat dtFmt = new SimpleDateFormat();
        //DateAxis dtAxis = ( DateAxis) axis;
        //dtAxis.setDateFormatOverride( dtFmt);
        
        axis = plot.getRangeAxis();
        axis.setAutoRange(true);
        
        return result;
    }
    
    public void setBoundsO( int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        m_chartPanel.setBounds( 0, 0, width, height);
        m_chartPanel.setPreferredSize( new Dimension( width, height));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

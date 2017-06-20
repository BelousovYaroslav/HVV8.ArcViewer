package hvv_arcviewer;

import hvv_devices.HVV_HvDevices;
import hvv_devices.HVV_VacuumDevice;
import hvv_devices.HVV_VacuumDevices;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.DefaultComboBoxModel;
import org.apache.log4j.Logger;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yaroslav
 */
public class HVV_AV_MainFrame extends javax.swing.JFrame {
    GregorianCalendar m_gdtmStartDate;
    GregorianCalendar m_gdtmStopDate;
    private final HVV_ArchiveViewer theApp;

    private final PanelGraph m_panelGraph1;
    private final PanelGraph m_panelGraph2;
    private final PanelGraph m_panelGraph3;
    private final PanelGraph m_panelGraph4;
    
    final private DefaultComboBoxModel cmbObjectModelG1;
    final private DefaultComboBoxModel cmbObjectModelG2;
    final private DefaultComboBoxModel cmbObjectModelG3;
    final private DefaultComboBoxModel cmbObjectModelG4;
    
    static Logger logger = Logger.getLogger( HVV_AV_MainFrame.class);
    /**
     * Creates new form HVV_AV_MainFrame
     * @param app
     * application instance
     */
    public HVV_AV_MainFrame( HVV_ArchiveViewer app) {
        
        initComponents();
        theApp = app;
        setTitle( "Модуль просмотра архивных данных, v.1.0.0.0 (2017.06.15 16:20), (C) ФЛАВТ 2017.");
        m_gdtmStartDate = ( GregorianCalendar) Calendar.getInstance();
        m_gdtmStopDate = ( GregorianCalendar) m_gdtmStartDate.clone();
        m_gdtmStopDate.add( Calendar.DAY_OF_MONTH, -1);
        m_gdtmStopDate.add( Calendar.HOUR, 2);
        
        m_gdtmStartDate.set( Calendar.DAY_OF_MONTH, 3);
        m_gdtmStartDate.set( Calendar.MONTH, Calendar.SEPTEMBER);
        m_gdtmStartDate.set( Calendar.YEAR, 2016);
        
        m_gdtmStopDate.set( Calendar.DAY_OF_MONTH, 3);
        m_gdtmStopDate.set( Calendar.MONTH, Calendar.SEPTEMBER);
        m_gdtmStopDate.set( Calendar.YEAR, 2016);
        
        updateDate();
        
        btnLayout1x1.setText(""); btnLayout1x1.setIcon( theApp.GetResources().getIconLayout1x1());
        btnLayout1x2.setText(""); btnLayout1x2.setIcon( theApp.GetResources().getIconLayout1x2());
        btnLayout2x1.setText(""); btnLayout2x1.setIcon( theApp.GetResources().getIconLayout2x1());
        btnLayout2x2.setText(""); btnLayout2x2.setIcon( theApp.GetResources().getIconLayout2x2());
        
        m_panelGraph1 = new PanelGraph( theApp, theApp.series_g1);
        pnlGraph1.add( m_panelGraph1);
        m_panelGraph1.setVisible( true);
        m_panelGraph1.setBoundsO( 0, 0, 580, 380);
        
        m_panelGraph2 = new PanelGraph( theApp, theApp.series_g2);
        pnlGraph2.add( m_panelGraph2);
        m_panelGraph2.setVisible( true);
        m_panelGraph2.setBoundsO( 0, 0, 580, 380);
        
        m_panelGraph3 = new PanelGraph( theApp, theApp.series_g3);
        pnlGraph3.add( m_panelGraph3);
        m_panelGraph3.setVisible( true);
        m_panelGraph3.setBoundsO( 0, 0, 580, 380);
        
        m_panelGraph4 = new PanelGraph( theApp, theApp.series_g4);
        pnlGraph4.add( m_panelGraph4);
        m_panelGraph4.setVisible( true);
        m_panelGraph4.setBoundsO( 0, 0, 580, 380);
        
        cmbObjectModelG1 = new DefaultComboBoxModel();
        HVV_VacuumDevices.getInstance().fillComboGraph( cmbObjectModelG1, true);
        HVV_HvDevices.getInstance().fillComboGraph( cmbObjectModelG1, false);
        cmbGraph1.setModel( cmbObjectModelG1);
        
        cmbObjectModelG2 = new DefaultComboBoxModel();
        HVV_VacuumDevices.getInstance().fillComboGraph( cmbObjectModelG2, true);
        HVV_HvDevices.getInstance().fillComboGraph( cmbObjectModelG2, false);
        cmbGraph2.setModel( cmbObjectModelG2);
        
        cmbObjectModelG3 = new DefaultComboBoxModel();
        HVV_VacuumDevices.getInstance().fillComboGraph( cmbObjectModelG3, true);
        HVV_HvDevices.getInstance().fillComboGraph( cmbObjectModelG3, false);
        cmbGraph3.setModel( cmbObjectModelG3);
        
        cmbObjectModelG4 = new DefaultComboBoxModel();
        HVV_VacuumDevices.getInstance().fillComboGraph( cmbObjectModelG4, true);
        HVV_HvDevices.getInstance().fillComboGraph( cmbObjectModelG4, false);
        cmbGraph4.setModel( cmbObjectModelG4);        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbGraph1 = new javax.swing.JComboBox();
        cmbGraph2 = new javax.swing.JComboBox();
        cmbGraph3 = new javax.swing.JComboBox();
        cmbGraph4 = new javax.swing.JComboBox();
        lblPoint1 = new javax.swing.JLabel();
        lblStartDay = new javax.swing.JLabel();
        lblYear = new javax.swing.JLabel();
        lblPoint2 = new javax.swing.JLabel();
        lblStartMonth = new javax.swing.JLabel();
        lblHour = new javax.swing.JLabel();
        lblDblPoint = new javax.swing.JLabel();
        lblMinutes = new javax.swing.JLabel();
        pnlGraph1 = new javax.swing.JPanel();
        pnlGraph2 = new javax.swing.JPanel();
        pnlGraph3 = new javax.swing.JPanel();
        pnlGraph4 = new javax.swing.JPanel();
        lblPoint3 = new javax.swing.JLabel();
        lblStopDay = new javax.swing.JLabel();
        lblStopYear = new javax.swing.JLabel();
        lblPoint4 = new javax.swing.JLabel();
        lblStopMonth = new javax.swing.JLabel();
        lblStopHour = new javax.swing.JLabel();
        lblDblPoint1 = new javax.swing.JLabel();
        lblStopMinutes = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnLayout1x1 = new javax.swing.JButton();
        btnLayout1x2 = new javax.swing.JButton();
        btnLayout2x1 = new javax.swing.JButton();
        btnLayout2x2 = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 1000));
        setResizable(false);
        getContentPane().setLayout(null);

        cmbGraph1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "VC.00А.Контур контроля форвакуума.Состояние", "VC.001.Насос Edwards.Состояние", "VC.04A.Датчик.Показание", "VC.16A.РРГ (О2).Состояние", "VC.16A.РРГ (О2).Уставка", "VC.16A.РРГ (О2).Показание", "HV.Рубильник.Состояние", "HV.ЛГ1.Анод.Состояние", "HV.ЛГ1.Анод.Ток", "HV.ЛГ1.Анод.Напряжение", "HV.ЛГ1.Штенгель.Состояние", "HV.ЛГ1.Штенгель.Ток", "HV.ЛГ1.Штенгель.Напряжение", "HV.ЛГ2.Анод.Состояние", "HV.ЛГ2.Анод.Ток", "HV.ЛГ2.Анод.Напряжение", "HV.ЛГ2.Штенгель.Ток", "HV.ЛГ2.Штенгель.Напряжение", "HV.ЛГ3.Анод.Ток", "HV.ЛГ3.Анод.Напряжение", "HV.ЛГ3.Штенгель.Ток", "HV.ЛГ3.Штенгель.Напряжение", "HV.ЛГ4.Анод.Ток", "HV.ЛГ4.Анод.Напряжение", "HV.ЛГ4.Штенгель.Ток", "HV.ЛГ4.Штенгель.Напряжение", "HV.ЛГ5.Анод.Ток", "HV.ЛГ5.Анод.Напряжение", "HV.ЛГ5.Штенгель.Ток", "HV.ЛГ5.Штенгель.Напряжение", "HV.ЛГ6.Анод.Ток", "HV.ЛГ6.Анод.Напряжение", "HV.ЛГ6.Штенгель.Ток", "HV.ЛГ6.Штенгель.Напряжение", "HV.ЛГ7.Анод.Ток", "HV.ЛГ7.Анод.Напряжение", "HV.ЛГ7.Штенгель.Ток", "HV.ЛГ7.Штенгель.Напряжение", "HV.ЛГ8.Анод.Ток", "HV.ЛГ8.Анод.Напряжение", "HV.ЛГ8.Штенгель.Ток", "HV.ЛГ8.Штенгель.Напряжение" }));
        cmbGraph1.setMaximumSize(new java.awt.Dimension(580, 25));
        cmbGraph1.setMinimumSize(new java.awt.Dimension(580, 25));
        cmbGraph1.setPreferredSize(new java.awt.Dimension(580, 25));
        getContentPane().add(cmbGraph1);
        cmbGraph1.setBounds(10, 10, 580, 25);

        cmbGraph2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "VC.00А.Контур контроля форвакуума.Состояние", "VC.001.Насос Edwards.Состояние", "VC.04A.Датчик.Показание", "VC.16A.РРГ (О2).Состояние", "VC.16A.РРГ (О2).Уставка", "VC.16A.РРГ (О2).Показание", "HV.Рубильник.Состояние", "HV.ЛГ1.Анод.Состояние", "HV.ЛГ1.Анод.Ток", "HV.ЛГ1.Анод.Напряжение", "HV.ЛГ1.Штенгель.Состояние", "HV.ЛГ1.Штенгель.Ток", "HV.ЛГ1.Штенгель.Напряжение", "HV.ЛГ2.Анод.Состояние", "HV.ЛГ2.Анод.Ток", "HV.ЛГ2.Анод.Напряжение", "HV.ЛГ2.Штенгель.Ток", "HV.ЛГ2.Штенгель.Напряжение", "HV.ЛГ3.Анод.Ток", "HV.ЛГ3.Анод.Напряжение", "HV.ЛГ3.Штенгель.Ток", "HV.ЛГ3.Штенгель.Напряжение", "HV.ЛГ4.Анод.Ток", "HV.ЛГ4.Анод.Напряжение", "HV.ЛГ4.Штенгель.Ток", "HV.ЛГ4.Штенгель.Напряжение", "HV.ЛГ5.Анод.Ток", "HV.ЛГ5.Анод.Напряжение", "HV.ЛГ5.Штенгель.Ток", "HV.ЛГ5.Штенгель.Напряжение", "HV.ЛГ6.Анод.Ток", "HV.ЛГ6.Анод.Напряжение", "HV.ЛГ6.Штенгель.Ток", "HV.ЛГ6.Штенгель.Напряжение", "HV.ЛГ7.Анод.Ток", "HV.ЛГ7.Анод.Напряжение", "HV.ЛГ7.Штенгель.Ток", "HV.ЛГ7.Штенгель.Напряжение", "HV.ЛГ8.Анод.Ток", "HV.ЛГ8.Анод.Напряжение", "HV.ЛГ8.Штенгель.Ток", "HV.ЛГ8.Штенгель.Напряжение" }));
        cmbGraph2.setMaximumSize(new java.awt.Dimension(580, 25));
        cmbGraph2.setMinimumSize(new java.awt.Dimension(580, 25));
        cmbGraph2.setPreferredSize(new java.awt.Dimension(580, 25));
        getContentPane().add(cmbGraph2);
        cmbGraph2.setBounds(600, 10, 580, 25);

        cmbGraph3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "VC.00А.Контур контроля форвакуума.Состояние", "VC.001.Насос Edwards.Состояние", "VC.04A.Датчик.Показание", "VC.16A.РРГ (О2).Состояние", "VC.16A.РРГ (О2).Уставка", "VC.16A.РРГ (О2).Показание", "HV.Рубильник.Состояние", "HV.ЛГ1.Анод.Состояние", "HV.ЛГ1.Анод.Ток", "HV.ЛГ1.Анод.Напряжение", "HV.ЛГ1.Штенгель.Состояние", "HV.ЛГ1.Штенгель.Ток", "HV.ЛГ1.Штенгель.Напряжение", "HV.ЛГ2.Анод.Состояние", "HV.ЛГ2.Анод.Ток", "HV.ЛГ2.Анод.Напряжение", "HV.ЛГ2.Штенгель.Ток", "HV.ЛГ2.Штенгель.Напряжение", "HV.ЛГ3.Анод.Ток", "HV.ЛГ3.Анод.Напряжение", "HV.ЛГ3.Штенгель.Ток", "HV.ЛГ3.Штенгель.Напряжение", "HV.ЛГ4.Анод.Ток", "HV.ЛГ4.Анод.Напряжение", "HV.ЛГ4.Штенгель.Ток", "HV.ЛГ4.Штенгель.Напряжение", "HV.ЛГ5.Анод.Ток", "HV.ЛГ5.Анод.Напряжение", "HV.ЛГ5.Штенгель.Ток", "HV.ЛГ5.Штенгель.Напряжение", "HV.ЛГ6.Анод.Ток", "HV.ЛГ6.Анод.Напряжение", "HV.ЛГ6.Штенгель.Ток", "HV.ЛГ6.Штенгель.Напряжение", "HV.ЛГ7.Анод.Ток", "HV.ЛГ7.Анод.Напряжение", "HV.ЛГ7.Штенгель.Ток", "HV.ЛГ7.Штенгель.Напряжение", "HV.ЛГ8.Анод.Ток", "HV.ЛГ8.Анод.Напряжение", "HV.ЛГ8.Штенгель.Ток", "HV.ЛГ8.Штенгель.Напряжение" }));
        cmbGraph3.setMaximumSize(new java.awt.Dimension(580, 25));
        cmbGraph3.setMinimumSize(new java.awt.Dimension(580, 25));
        cmbGraph3.setPreferredSize(new java.awt.Dimension(580, 25));
        getContentPane().add(cmbGraph3);
        cmbGraph3.setBounds(10, 430, 580, 25);

        cmbGraph4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "VC.00А.Контур контроля форвакуума.Состояние", "VC.001.Насос Edwards.Состояние", "VC.04A.Датчик.Показание", "VC.16A.РРГ (О2).Состояние", "VC.16A.РРГ (О2).Уставка", "VC.16A.РРГ (О2).Показание", "HV.Рубильник.Состояние", "HV.ЛГ1.Анод.Состояние", "HV.ЛГ1.Анод.Ток", "HV.ЛГ1.Анод.Напряжение", "HV.ЛГ1.Штенгель.Состояние", "HV.ЛГ1.Штенгель.Ток", "HV.ЛГ1.Штенгель.Напряжение", "HV.ЛГ2.Анод.Состояние", "HV.ЛГ2.Анод.Ток", "HV.ЛГ2.Анод.Напряжение", "HV.ЛГ2.Штенгель.Ток", "HV.ЛГ2.Штенгель.Напряжение", "HV.ЛГ3.Анод.Ток", "HV.ЛГ3.Анод.Напряжение", "HV.ЛГ3.Штенгель.Ток", "HV.ЛГ3.Штенгель.Напряжение", "HV.ЛГ4.Анод.Ток", "HV.ЛГ4.Анод.Напряжение", "HV.ЛГ4.Штенгель.Ток", "HV.ЛГ4.Штенгель.Напряжение", "HV.ЛГ5.Анод.Ток", "HV.ЛГ5.Анод.Напряжение", "HV.ЛГ5.Штенгель.Ток", "HV.ЛГ5.Штенгель.Напряжение", "HV.ЛГ6.Анод.Ток", "HV.ЛГ6.Анод.Напряжение", "HV.ЛГ6.Штенгель.Ток", "HV.ЛГ6.Штенгель.Напряжение", "HV.ЛГ7.Анод.Ток", "HV.ЛГ7.Анод.Напряжение", "HV.ЛГ7.Штенгель.Ток", "HV.ЛГ7.Штенгель.Напряжение", "HV.ЛГ8.Анод.Ток", "HV.ЛГ8.Анод.Напряжение", "HV.ЛГ8.Штенгель.Ток", "HV.ЛГ8.Штенгель.Напряжение" }));
        cmbGraph4.setMaximumSize(new java.awt.Dimension(580, 25));
        cmbGraph4.setMinimumSize(new java.awt.Dimension(580, 25));
        cmbGraph4.setPreferredSize(new java.awt.Dimension(580, 25));
        getContentPane().add(cmbGraph4);
        cmbGraph4.setBounds(600, 430, 580, 25);

        lblPoint1.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblPoint1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPoint1.setText(".");
        getContentPane().add(lblPoint1);
        lblPoint1.setBounds(60, 860, 10, 40);

        lblStartDay.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStartDay.setText("30");
        lblStartDay.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStartDayMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStartDay);
        lblStartDay.setBounds(10, 860, 50, 40);

        lblYear.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblYear.setText("2015");
        lblYear.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblYearMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblYear);
        lblYear.setBounds(130, 860, 100, 40);

        lblPoint2.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblPoint2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPoint2.setText(".");
        getContentPane().add(lblPoint2);
        lblPoint2.setBounds(120, 860, 10, 40);

        lblStartMonth.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStartMonth.setText("12");
        lblStartMonth.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStartMonthMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStartMonth);
        lblStartMonth.setBounds(70, 860, 50, 40);

        lblHour.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblHour.setText("20");
        lblHour.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblHourMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblHour);
        lblHour.setBounds(240, 860, 50, 40);

        lblDblPoint.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblDblPoint.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDblPoint.setText(":");
        getContentPane().add(lblDblPoint);
        lblDblPoint.setBounds(290, 860, 10, 40);

        lblMinutes.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblMinutes.setText("12");
        lblMinutes.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblMinutesMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblMinutes);
        lblMinutes.setBounds(300, 860, 50, 40);

        pnlGraph1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnlGraph1Layout = new javax.swing.GroupLayout(pnlGraph1);
        pnlGraph1.setLayout(pnlGraph1Layout);
        pnlGraph1Layout.setHorizontalGroup(
            pnlGraph1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 578, Short.MAX_VALUE)
        );
        pnlGraph1Layout.setVerticalGroup(
            pnlGraph1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );

        getContentPane().add(pnlGraph1);
        pnlGraph1.setBounds(10, 35, 580, 380);

        pnlGraph2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnlGraph2Layout = new javax.swing.GroupLayout(pnlGraph2);
        pnlGraph2.setLayout(pnlGraph2Layout);
        pnlGraph2Layout.setHorizontalGroup(
            pnlGraph2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 578, Short.MAX_VALUE)
        );
        pnlGraph2Layout.setVerticalGroup(
            pnlGraph2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );

        getContentPane().add(pnlGraph2);
        pnlGraph2.setBounds(600, 35, 580, 380);

        pnlGraph3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnlGraph3Layout = new javax.swing.GroupLayout(pnlGraph3);
        pnlGraph3.setLayout(pnlGraph3Layout);
        pnlGraph3Layout.setHorizontalGroup(
            pnlGraph3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 578, Short.MAX_VALUE)
        );
        pnlGraph3Layout.setVerticalGroup(
            pnlGraph3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );

        getContentPane().add(pnlGraph3);
        pnlGraph3.setBounds(10, 455, 580, 380);

        pnlGraph4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnlGraph4Layout = new javax.swing.GroupLayout(pnlGraph4);
        pnlGraph4.setLayout(pnlGraph4Layout);
        pnlGraph4Layout.setHorizontalGroup(
            pnlGraph4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 578, Short.MAX_VALUE)
        );
        pnlGraph4Layout.setVerticalGroup(
            pnlGraph4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );

        getContentPane().add(pnlGraph4);
        pnlGraph4.setBounds(600, 455, 580, 380);

        lblPoint3.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblPoint3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPoint3.setText(".");
        getContentPane().add(lblPoint3);
        lblPoint3.setBounds(480, 860, 10, 40);

        lblStopDay.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStopDay.setText("30");
        lblStopDay.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStopDayMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStopDay);
        lblStopDay.setBounds(430, 860, 50, 40);

        lblStopYear.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStopYear.setText("2015");
        lblStopYear.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStopYearMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStopYear);
        lblStopYear.setBounds(550, 860, 100, 40);

        lblPoint4.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblPoint4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPoint4.setText(".");
        getContentPane().add(lblPoint4);
        lblPoint4.setBounds(540, 860, 10, 40);

        lblStopMonth.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStopMonth.setText("12");
        lblStopMonth.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStopMonthMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStopMonth);
        lblStopMonth.setBounds(490, 860, 50, 40);

        lblStopHour.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStopHour.setText("20");
        lblStopHour.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStopHourMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStopHour);
        lblStopHour.setBounds(660, 860, 50, 40);

        lblDblPoint1.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblDblPoint1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDblPoint1.setText(":");
        getContentPane().add(lblDblPoint1);
        lblDblPoint1.setBounds(710, 860, 10, 40);

        lblStopMinutes.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStopMinutes.setText("12");
        lblStopMinutes.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStopMinutesMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStopMinutes);
        lblStopMinutes.setBounds(720, 860, 50, 40);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(968, 10, 220, 0);

        btnLayout1x1.setText("1x1");
        btnLayout1x1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLayout1x1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnLayout1x1);
        btnLayout1x1.setBounds(20, 920, 50, 40);

        btnLayout1x2.setText("1x2");
        btnLayout1x2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLayout1x2ActionPerformed(evt);
            }
        });
        getContentPane().add(btnLayout1x2);
        btnLayout1x2.setBounds(80, 920, 50, 40);

        btnLayout2x1.setText("2x1");
        btnLayout2x1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLayout2x1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnLayout2x1);
        btnLayout2x1.setBounds(140, 920, 50, 40);

        btnLayout2x2.setText("2x2");
        btnLayout2x2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLayout2x2ActionPerformed(evt);
            }
        });
        getContentPane().add(btnLayout2x2);
        btnLayout2x2.setBounds(200, 920, 50, 40);

        btnRefresh.setText("Отобразить");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(btnRefresh);
        btnRefresh.setBounds(840, 860, 350, 50);

        btnExit.setText("Выход");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        getContentPane().add(btnExit);
        btnExit.setBounds(840, 920, 350, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblHourMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_lblHourMouseWheelMoved
        m_gdtmStartDate.add( Calendar.HOUR, -1 * evt.getWheelRotation());
        updateDate();
    }//GEN-LAST:event_lblHourMouseWheelMoved

    private void lblMinutesMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_lblMinutesMouseWheelMoved
        m_gdtmStartDate.add( Calendar.MINUTE, -1 * evt.getWheelRotation());
        updateDate();
    }//GEN-LAST:event_lblMinutesMouseWheelMoved

    private void lblYearMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_lblYearMouseWheelMoved
        m_gdtmStartDate.add( Calendar.YEAR, -1 * evt.getWheelRotation());
        updateDate();
    }//GEN-LAST:event_lblYearMouseWheelMoved

    private void lblStartMonthMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_lblStartMonthMouseWheelMoved
        m_gdtmStartDate.add( Calendar.MONTH, -1 * evt.getWheelRotation());
        updateDate();
    }//GEN-LAST:event_lblStartMonthMouseWheelMoved

    private void lblStartDayMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_lblStartDayMouseWheelMoved
        m_gdtmStartDate.add( Calendar.DAY_OF_MONTH, -1 * evt.getWheelRotation());
        updateDate();
    }//GEN-LAST:event_lblStartDayMouseWheelMoved

    private void lblStopDayMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_lblStopDayMouseWheelMoved
        m_gdtmStopDate.add( Calendar.DAY_OF_MONTH, -1 * evt.getWheelRotation());
        updateDate();
    }//GEN-LAST:event_lblStopDayMouseWheelMoved

    private void lblStopYearMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_lblStopYearMouseWheelMoved
        m_gdtmStopDate.add( Calendar.YEAR, -1 * evt.getWheelRotation());
        updateDate();
    }//GEN-LAST:event_lblStopYearMouseWheelMoved

    private void lblStopMonthMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_lblStopMonthMouseWheelMoved
        m_gdtmStopDate.add( Calendar.MONTH, -1 * evt.getWheelRotation());
        updateDate();
    }//GEN-LAST:event_lblStopMonthMouseWheelMoved

    private void lblStopHourMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_lblStopHourMouseWheelMoved
        m_gdtmStopDate.add( Calendar.HOUR, -1 * evt.getWheelRotation());
        updateDate();
    }//GEN-LAST:event_lblStopHourMouseWheelMoved

    private void lblStopMinutesMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_lblStopMinutesMouseWheelMoved
        m_gdtmStopDate.add( Calendar.MINUTE, -1 * evt.getWheelRotation());
        updateDate();
    }//GEN-LAST:event_lblStopMinutesMouseWheelMoved

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        //theApp.series_g1.add(new Millisecond(), 100. + Math.random() * 15.);
        //return;
        
        theApp.series_g1.clear();

        GregorianCalendar gdt = (GregorianCalendar) m_gdtmStartDate.clone();
        boolean bRunningDates = true;
        
        int nCounter = 0;
        do {    
            String strFilename =  theApp.GetAMSRoot() + "/data/" + gdt.get( Calendar.YEAR);

            int nMonth = gdt.get( Calendar.MONTH) + 1;
            if( nMonth < 10)
                strFilename += ".0" + nMonth;
            else
                strFilename += "." + nMonth;
            
            int nDay = gdt.get( Calendar.DAY_OF_MONTH);
            if( nDay < 10)
                strFilename += ".0" + nDay;
            else
                strFilename += "." + nDay;
            
            cmbGraph1.getSelectedIndex();
            int nIndex = cmbGraph1.getSelectedIndex();
            String strSelection = ( String) cmbGraph1.getModel().getElementAt(nIndex);
            
            int nPoint1 = strSelection.indexOf( ".", 0);
            int nPoint2 = strSelection.indexOf( ".", nPoint1+1);
            int nPoint3 = strSelection.indexOf( ".", nPoint2+1);
            
            if( Character.isDigit( strSelection.charAt( 0)) == true) {
                //VAC.param
                strFilename += ".VAC";
                strFilename += "." + strSelection.substring( 0, nPoint1);
                strFilename += "." + strSelection.substring( nPoint2 + 1, nPoint3);
            }
            else {
                //HV.param
                strFilename += ".HV";
                strFilename += "." + strSelection.substring( 0, nPoint1);
                strFilename += "." + strSelection.substring( nPoint2 + 1, nPoint3);
            }
            
            strFilename += ".csv";
            
            logger.info( strFilename);
            //check if file exists
        
        
            TimeSeries seria = new TimeSeries( "G1",  Millisecond.class);

            FileInputStream fin = null;
            try {
                fin = new FileInputStream( strFilename);
                fin.close();

                BufferedReader br = new BufferedReader( new FileReader( strFilename));
                String line;
                while ( ( line = br.readLine()) != null) {
                    int indx1 = line.indexOf( ';');
                    String strDate = line.substring( 0, indx1);

                    String strValue = line.substring( indx1+1);
                    strValue = strValue.substring( 0, strValue.length()-1);

                    double dblValue = Double.parseDouble( strValue);
                    //logger.info( "strDate='" + strDate + "'");
                    //logger.info( "strValue='" + strValue + "'");

                    SimpleDateFormat df = new SimpleDateFormat( "dd.MM.yyyy HH:mm:ss.SSS");
                    Date dt = df.parse( strDate);

                    seria.addOrUpdate( new Millisecond(dt), dblValue);
                    if( ++nCounter > 3000) break;
                    
                }

                theApp.series_g1.addAndOrUpdate(seria);

            }
            catch( ParseException ex) {
                logger.error( "ParseException caught!", ex);
            }
            catch( FileNotFoundException ex) {
                logger.error( "FileNotFoundException caught!");//, ex);
            }
            catch( IOException ex) {
                logger.error( "IOException caught!", ex);
            }
            //catch( CloneNotSupportedException ex) {
            //    logger.error( "CloneNotSupportedException caught!", ex);
            //}
            
            gdt.add( Calendar.DAY_OF_MONTH, 1);
            if( gdt.after( m_gdtmStopDate))
                bRunningDates = false;
            
        } while( bRunningDates);
        
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnLayout1x1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayout1x1ActionPerformed
        pnlGraph1.setVisible( true);    cmbGraph1.setVisible( true);
        pnlGraph2.setVisible( false);   cmbGraph2.setVisible( false);
        pnlGraph3.setVisible( false);   cmbGraph3.setVisible( false);
        pnlGraph4.setVisible( false);   cmbGraph4.setVisible( false);
        
        cmbGraph1.setBounds( 10, 10, 1180, 25);     pnlGraph1.setBounds( 10, 35, 1180, 805);    m_panelGraph1.setBoundsO( 0, 0, 1180, 805);
    }//GEN-LAST:event_btnLayout1x1ActionPerformed

    private void btnLayout1x2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayout1x2ActionPerformed
        pnlGraph1.setVisible( true);    cmbGraph1.setVisible( true);
        pnlGraph2.setVisible( false);   cmbGraph2.setVisible( false);
        pnlGraph3.setVisible( true);    cmbGraph3.setVisible( true);
        pnlGraph4.setVisible( false);   cmbGraph4.setVisible( false);
        
        cmbGraph1.setBounds( 10, 10,  1180, 25);    pnlGraph1.setBounds( 10, 35,  1180, 380);   m_panelGraph1.setBoundsO( 0, 0, 1180, 380);
        cmbGraph3.setBounds( 10, 430, 1180, 25);    pnlGraph3.setBounds( 10, 455, 1180, 380);   m_panelGraph3.setBoundsO( 0, 0, 1180, 380);
    }//GEN-LAST:event_btnLayout1x2ActionPerformed

    private void btnLayout2x1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayout2x1ActionPerformed
        pnlGraph1.setVisible( true);    cmbGraph1.setVisible( true);
        pnlGraph2.setVisible( true);    cmbGraph2.setVisible( true);
        pnlGraph3.setVisible( false);   cmbGraph3.setVisible( false);
        pnlGraph4.setVisible( false);   cmbGraph4.setVisible( false);
        
        cmbGraph1.setBounds( 10,  10, 580, 25);    pnlGraph1.setBounds( 10,  35, 580, 805);     m_panelGraph1.setBoundsO( 0, 0, 580, 805);
        cmbGraph2.setBounds( 600, 10, 580, 25);    pnlGraph2.setBounds( 600, 35, 580, 805);     m_panelGraph2.setBoundsO( 0, 0, 580, 805);
    }//GEN-LAST:event_btnLayout2x1ActionPerformed

    private void btnLayout2x2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayout2x2ActionPerformed
        pnlGraph1.setVisible( true);    cmbGraph1.setVisible( true);
        pnlGraph2.setVisible( true);    cmbGraph2.setVisible( true);
        pnlGraph3.setVisible( true);    cmbGraph3.setVisible( true);
        pnlGraph4.setVisible( true);    cmbGraph4.setVisible( true);
        
        cmbGraph1.setBounds( 10,  10, 580, 25);    pnlGraph1.setBounds( 10,  35,  580, 380);    m_panelGraph1.setBoundsO( 0, 0, 580, 380);
        cmbGraph2.setBounds( 600, 10, 580, 25);    pnlGraph2.setBounds( 600, 35,  580, 380);    m_panelGraph2.setBoundsO( 0, 0, 580, 380);
        cmbGraph3.setBounds( 10,  430, 580, 25);   pnlGraph3.setBounds( 10,  455, 580, 380);    m_panelGraph3.setBoundsO( 0, 0, 580, 380);
        cmbGraph4.setBounds( 600, 430, 580, 25);   pnlGraph4.setBounds( 600, 455, 580, 380);    m_panelGraph4.setBoundsO( 0, 0, 580, 380);
    }//GEN-LAST:event_btnLayout2x2ActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        System.exit( 0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void updateDate() {
        
        lblStartDay.setText( ( m_gdtmStartDate.get( Calendar.DAY_OF_MONTH) < 10 ? "0" : "") +
                        m_gdtmStartDate.get( Calendar.DAY_OF_MONTH));
        
        lblStartMonth.setText(( m_gdtmStartDate.get( Calendar.MONTH) + 1 < 10 ? "0" : "") +
                          ( m_gdtmStartDate.get( Calendar.MONTH) + 1));
        
        lblYear.setText(( m_gdtmStartDate.get( Calendar.YEAR) < 10 ? "0" : "") +
                         m_gdtmStartDate.get( Calendar.YEAR));
        
        lblHour.setText(( m_gdtmStartDate.get( Calendar.HOUR_OF_DAY) < 10 ? "0" : "") +
                         m_gdtmStartDate.get( Calendar.HOUR_OF_DAY));
        
        lblMinutes.setText(( m_gdtmStartDate.get( Calendar.MINUTE) < 10 ? "0" : "") +
                            m_gdtmStartDate.get( Calendar.MINUTE));
        
        
        
        lblStopDay.setText( ( m_gdtmStopDate.get( Calendar.DAY_OF_MONTH) < 10 ? "0" : "") +
                        m_gdtmStopDate.get( Calendar.DAY_OF_MONTH));
        
        lblStopMonth.setText(( m_gdtmStopDate.get( Calendar.MONTH) + 1 < 10 ? "0" : "") +
                          ( m_gdtmStopDate.get( Calendar.MONTH) + 1));
        
        lblStopYear.setText(( m_gdtmStopDate.get( Calendar.YEAR) < 10 ? "0" : "") +
                         m_gdtmStopDate.get( Calendar.YEAR));
        
        lblStopHour.setText(( m_gdtmStopDate.get( Calendar.HOUR_OF_DAY) < 10 ? "0" : "") +
                         m_gdtmStopDate.get( Calendar.HOUR_OF_DAY));
        
        lblStopMinutes.setText(( m_gdtmStopDate.get( Calendar.MINUTE) < 10 ? "0" : "") +
                            m_gdtmStopDate.get( Calendar.MINUTE));
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HVV_AV_MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HVV_AV_MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HVV_AV_MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HVV_AV_MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HVV_AV_MainFrame( null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLayout1x1;
    private javax.swing.JButton btnLayout1x2;
    private javax.swing.JButton btnLayout2x1;
    private javax.swing.JButton btnLayout2x2;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox cmbGraph1;
    private javax.swing.JComboBox cmbGraph2;
    private javax.swing.JComboBox cmbGraph3;
    private javax.swing.JComboBox cmbGraph4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDblPoint;
    private javax.swing.JLabel lblDblPoint1;
    private javax.swing.JLabel lblHour;
    private javax.swing.JLabel lblMinutes;
    private javax.swing.JLabel lblPoint1;
    private javax.swing.JLabel lblPoint2;
    private javax.swing.JLabel lblPoint3;
    private javax.swing.JLabel lblPoint4;
    private javax.swing.JLabel lblStartDay;
    private javax.swing.JLabel lblStartMonth;
    private javax.swing.JLabel lblStopDay;
    private javax.swing.JLabel lblStopHour;
    private javax.swing.JLabel lblStopMinutes;
    private javax.swing.JLabel lblStopMonth;
    private javax.swing.JLabel lblStopYear;
    private javax.swing.JLabel lblYear;
    private javax.swing.JPanel pnlGraph1;
    private javax.swing.JPanel pnlGraph2;
    private javax.swing.JPanel pnlGraph3;
    private javax.swing.JPanel pnlGraph4;
    // End of variables declaration//GEN-END:variables
}

package hvv_arcviewer;

import hvv_devices.HVV_HvDevice;
import hvv_devices.HVV_HvDevices;
import hvv_devices.HVV_VacuumDevice;
import hvv_devices.HVV_VacuumDevices;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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
    
    private int m_nGraphs;
            
    static Logger logger = Logger.getLogger( HVV_AV_MainFrame.class);
    /**
     * Creates new form HVV_AV_MainFrame
     * @param app
     * application instance
     */
    public HVV_AV_MainFrame( HVV_ArchiveViewer app) {
        
        initComponents();
        
        btnStartDtDayUp.setIcon(    app.GetResources().getIconUp());
        btnStartDtMonUp.setIcon(    app.GetResources().getIconUp());
        btnStartDtYearUp.setIcon(   app.GetResources().getIconUp());
        btnStartDtHourUp.setIcon(   app.GetResources().getIconUp());
        btnStartDtMinUp.setIcon(    app.GetResources().getIconUp());
        
        btnStartDtDayDown.setIcon(  app.GetResources().getIconDown());
        btnStartDtMonDown.setIcon(  app.GetResources().getIconDown());
        btnStartDtYearDown.setIcon( app.GetResources().getIconDown());
        btnStartDtHourDown.setIcon( app.GetResources().getIconDown());
        btnStartDtMinDown.setIcon(  app.GetResources().getIconDown());
        
        btnStopDtDayUp.setIcon(     app.GetResources().getIconUp());
        btnStopDtMonUp.setIcon(     app.GetResources().getIconUp());
        btnStopDtYearUp.setIcon(    app.GetResources().getIconUp());
        btnStopDtHourUp.setIcon(    app.GetResources().getIconUp());
        btnStopDtMinUp.setIcon(     app.GetResources().getIconUp());
        
        btnStopDtDayDown.setIcon(   app.GetResources().getIconDown());
        btnStopDtMonDown.setIcon(   app.GetResources().getIconDown());
        btnStopDtYearDown.setIcon(  app.GetResources().getIconDown());
        btnStopDtHourDown.setIcon(  app.GetResources().getIconDown());
        btnStopDtMinDown.setIcon(   app.GetResources().getIconDown());
        
        
        theApp = app;
        setTitle( "Модуль просмотра архивных данных, 2018.02.13 12:00, (C) ФЛАВТ 2018.");
        m_gdtmStartDate = ( GregorianCalendar) theApp.GetSettings().GetStartDtm().clone();
        m_gdtmStopDate = ( GregorianCalendar) theApp.GetSettings().GetStopDtm().clone();

        switch( theApp.GetSettings().GetNaNProcessing()) {
            case 0: radNaNProc1.setSelected( true); break;
            case 1: radNaNProc2.setSelected( true); break;
            case 2: radNaNProc3.setSelected( true); break;
        }
        edtNaNProc.setText( String.format( "%.3f", theApp.GetSettings().GetNaNProcessingReplacement()).replace( ',', '.'));
        jProgressBar1.setVisible(false);
        
        /*
        m_gdtmStartDate.set( Calendar.DAY_OF_MONTH, 06);
        m_gdtmStartDate.set( Calendar.MONTH, Calendar.JUNE);
        m_gdtmStartDate.set( Calendar.YEAR, 2017);
        
        m_gdtmStopDate.set( Calendar.DAY_OF_MONTH, 06);
        m_gdtmStopDate.set( Calendar.MONTH, Calendar.JUNE);
        m_gdtmStopDate.set( Calendar.YEAR, 2017);
        */
        
        updateDate();
        
        btnLayout1x1.setText(""); btnLayout1x1.setIcon( theApp.GetResources().getIconLayout1x1());
        btnLayout1x2.setText(""); btnLayout1x2.setIcon( theApp.GetResources().getIconLayout1x2());
        btnLayout2x1.setText(""); btnLayout2x1.setIcon( theApp.GetResources().getIconLayout2x1());
        btnLayout2x2.setText(""); btnLayout2x2.setIcon( theApp.GetResources().getIconLayout2x2());
        
        m_nGraphs = theApp.GetSettings().Get_nGraphs();
        
                
        m_panelGraph1 = new PanelGraph( theApp, theApp.series_g1);
        pnlGraph1.add( m_panelGraph1);
        //m_panelGraph1.setVisible( true);
        //m_panelGraph1.setBoundsO( 0, 0, 580, 380);
        
        m_panelGraph2 = new PanelGraph( theApp, theApp.series_g2);
        pnlGraph2.add( m_panelGraph2);
        //m_panelGraph2.setVisible( true);
        //m_panelGraph2.setBoundsO( 0, 0, 580, 380);
        
        m_panelGraph3 = new PanelGraph( theApp, theApp.series_g3);
        pnlGraph3.add( m_panelGraph3);
        //m_panelGraph3.setVisible( true);
        //m_panelGraph3.setBoundsO( 0, 0, 580, 380);
        
        m_panelGraph4 = new PanelGraph( theApp, theApp.series_g4);
        pnlGraph4.add( m_panelGraph4);
        //m_panelGraph4.setVisible( true);
        //m_panelGraph4.setBoundsO( 0, 0, 580, 380);
        
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
        
        SetGraphsViewStates();
        
        cmbGraph1.setSelectedIndex( theApp.GetSettings().Get_Graph1ViewParam());
        cmbGraph2.setSelectedIndex( theApp.GetSettings().Get_Graph2ViewParam());
        cmbGraph3.setSelectedIndex( theApp.GetSettings().Get_Graph3ViewParam());
        cmbGraph4.setSelectedIndex( theApp.GetSettings().Get_Graph4ViewParam());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        cmbGraph1 = new javax.swing.JComboBox();
        cmbGraph2 = new javax.swing.JComboBox();
        cmbGraph3 = new javax.swing.JComboBox();
        cmbGraph4 = new javax.swing.JComboBox();
        pnlGraph1 = new javax.swing.JPanel();
        pnlGraph2 = new javax.swing.JPanel();
        pnlGraph3 = new javax.swing.JPanel();
        pnlGraph4 = new javax.swing.JPanel();
        btnStartDtDayUp = new javax.swing.JButton();
        lblStartDay = new javax.swing.JLabel();
        btnStartDtDayDown = new javax.swing.JButton();
        lblPoint1 = new javax.swing.JLabel();
        btnStartDtMonUp = new javax.swing.JButton();
        lblStartMonth = new javax.swing.JLabel();
        btnStartDtMonDown = new javax.swing.JButton();
        lblPoint2 = new javax.swing.JLabel();
        btnStartDtYearUp = new javax.swing.JButton();
        lblYear = new javax.swing.JLabel();
        btnStartDtYearDown = new javax.swing.JButton();
        btnStartDtHourUp = new javax.swing.JButton();
        lblHour = new javax.swing.JLabel();
        btnStartDtHourDown = new javax.swing.JButton();
        lblDblPoint = new javax.swing.JLabel();
        btnStartDtMinUp = new javax.swing.JButton();
        lblMinutes = new javax.swing.JLabel();
        btnStartDtMinDown = new javax.swing.JButton();
        btnStopDtDayUp = new javax.swing.JButton();
        lblStopDay = new javax.swing.JLabel();
        btnStopDtDayDown = new javax.swing.JButton();
        lblPoint3 = new javax.swing.JLabel();
        btnStopDtMonUp = new javax.swing.JButton();
        lblStopMonth = new javax.swing.JLabel();
        btnStopDtMonDown = new javax.swing.JButton();
        lblPoint4 = new javax.swing.JLabel();
        btnStopDtYearUp = new javax.swing.JButton();
        lblStopYear = new javax.swing.JLabel();
        btnStopDtYearDown = new javax.swing.JButton();
        btnStopDtHourUp = new javax.swing.JButton();
        lblStopHour = new javax.swing.JLabel();
        btnStopDtHourDown = new javax.swing.JButton();
        lblDblPoint1 = new javax.swing.JLabel();
        btnStopDtMinUp = new javax.swing.JButton();
        lblStopMinutes = new javax.swing.JLabel();
        btnStopDtMinDown = new javax.swing.JButton();
        btnLayout1x1 = new javax.swing.JButton();
        btnLayout1x2 = new javax.swing.JButton();
        btnLayout2x1 = new javax.swing.JButton();
        btnLayout2x2 = new javax.swing.JButton();
        lblNaNProcTitle = new javax.swing.JLabel();
        radNaNProc3 = new javax.swing.JRadioButton();
        radNaNProc1 = new javax.swing.JRadioButton();
        radNaNProc2 = new javax.swing.JRadioButton();
        edtNaNProc = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 1020));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
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

        btnStartDtDayUp.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/up.gif")); // NOI18N
        btnStartDtDayUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartDtDayUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnStartDtDayUp);
        btnStartDtDayUp.setBounds(10, 840, 50, 20);

        lblStartDay.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStartDay.setText("30");
        lblStartDay.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStartDayMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStartDay);
        lblStartDay.setBounds(10, 860, 50, 40);

        btnStartDtDayDown.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/down.gif")); // NOI18N
        btnStartDtDayDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartDtDayDownActionPerformed(evt);
            }
        });
        getContentPane().add(btnStartDtDayDown);
        btnStartDtDayDown.setBounds(10, 900, 50, 20);

        lblPoint1.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblPoint1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPoint1.setText(".");
        getContentPane().add(lblPoint1);
        lblPoint1.setBounds(60, 860, 10, 40);

        btnStartDtMonUp.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/up.gif")); // NOI18N
        btnStartDtMonUp.setToolTipText("");
        btnStartDtMonUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartDtMonUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnStartDtMonUp);
        btnStartDtMonUp.setBounds(70, 840, 50, 20);

        lblStartMonth.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStartMonth.setText("12");
        lblStartMonth.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStartMonthMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStartMonth);
        lblStartMonth.setBounds(70, 860, 50, 40);

        btnStartDtMonDown.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/down.gif")); // NOI18N
        btnStartDtMonDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartDtMonDownActionPerformed(evt);
            }
        });
        getContentPane().add(btnStartDtMonDown);
        btnStartDtMonDown.setBounds(70, 900, 50, 20);

        lblPoint2.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblPoint2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPoint2.setText(".");
        getContentPane().add(lblPoint2);
        lblPoint2.setBounds(120, 860, 10, 40);

        btnStartDtYearUp.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/up.gif")); // NOI18N
        btnStartDtYearUp.setToolTipText("");
        btnStartDtYearUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartDtYearUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnStartDtYearUp);
        btnStartDtYearUp.setBounds(130, 840, 100, 20);

        lblYear.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblYear.setText("2015");
        lblYear.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblYearMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblYear);
        lblYear.setBounds(130, 860, 100, 40);

        btnStartDtYearDown.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/down.gif")); // NOI18N
        btnStartDtYearDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartDtYearDownActionPerformed(evt);
            }
        });
        getContentPane().add(btnStartDtYearDown);
        btnStartDtYearDown.setBounds(130, 900, 100, 20);

        btnStartDtHourUp.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/up.gif")); // NOI18N
        btnStartDtHourUp.setToolTipText("");
        btnStartDtHourUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartDtHourUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnStartDtHourUp);
        btnStartDtHourUp.setBounds(240, 840, 50, 20);

        lblHour.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblHour.setText("20");
        lblHour.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblHourMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblHour);
        lblHour.setBounds(240, 860, 50, 40);

        btnStartDtHourDown.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/down.gif")); // NOI18N
        btnStartDtHourDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartDtHourDownActionPerformed(evt);
            }
        });
        getContentPane().add(btnStartDtHourDown);
        btnStartDtHourDown.setBounds(240, 900, 50, 20);

        lblDblPoint.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblDblPoint.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDblPoint.setText(":");
        getContentPane().add(lblDblPoint);
        lblDblPoint.setBounds(290, 860, 10, 40);

        btnStartDtMinUp.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/up.gif")); // NOI18N
        btnStartDtMinUp.setToolTipText("");
        btnStartDtMinUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartDtMinUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnStartDtMinUp);
        btnStartDtMinUp.setBounds(300, 840, 50, 20);

        lblMinutes.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblMinutes.setText("12");
        lblMinutes.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblMinutesMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblMinutes);
        lblMinutes.setBounds(300, 860, 50, 40);

        btnStartDtMinDown.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/down.gif")); // NOI18N
        btnStartDtMinDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartDtMinDownActionPerformed(evt);
            }
        });
        getContentPane().add(btnStartDtMinDown);
        btnStartDtMinDown.setBounds(300, 900, 50, 20);

        btnStopDtDayUp.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/up.gif")); // NOI18N
        btnStopDtDayUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopDtDayUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnStopDtDayUp);
        btnStopDtDayUp.setBounds(430, 840, 50, 20);

        lblStopDay.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStopDay.setText("30");
        lblStopDay.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStopDayMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStopDay);
        lblStopDay.setBounds(430, 860, 50, 40);

        btnStopDtDayDown.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/down.gif")); // NOI18N
        btnStopDtDayDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopDtDayDownActionPerformed(evt);
            }
        });
        getContentPane().add(btnStopDtDayDown);
        btnStopDtDayDown.setBounds(430, 900, 50, 20);

        lblPoint3.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblPoint3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPoint3.setText(".");
        getContentPane().add(lblPoint3);
        lblPoint3.setBounds(480, 860, 10, 40);

        btnStopDtMonUp.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/up.gif")); // NOI18N
        btnStopDtMonUp.setToolTipText("");
        btnStopDtMonUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopDtMonUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnStopDtMonUp);
        btnStopDtMonUp.setBounds(490, 840, 50, 20);

        lblStopMonth.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStopMonth.setText("12");
        lblStopMonth.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStopMonthMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStopMonth);
        lblStopMonth.setBounds(490, 860, 50, 40);

        btnStopDtMonDown.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/down.gif")); // NOI18N
        btnStopDtMonDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopDtMonDownActionPerformed(evt);
            }
        });
        getContentPane().add(btnStopDtMonDown);
        btnStopDtMonDown.setBounds(490, 900, 50, 20);

        lblPoint4.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblPoint4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPoint4.setText(".");
        getContentPane().add(lblPoint4);
        lblPoint4.setBounds(540, 860, 10, 40);

        btnStopDtYearUp.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/up.gif")); // NOI18N
        btnStopDtYearUp.setToolTipText("");
        btnStopDtYearUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopDtYearUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnStopDtYearUp);
        btnStopDtYearUp.setBounds(550, 840, 100, 20);

        lblStopYear.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStopYear.setText("2015");
        lblStopYear.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStopYearMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStopYear);
        lblStopYear.setBounds(550, 860, 100, 40);

        btnStopDtYearDown.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/down.gif")); // NOI18N
        btnStopDtYearDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopDtYearDownActionPerformed(evt);
            }
        });
        getContentPane().add(btnStopDtYearDown);
        btnStopDtYearDown.setBounds(550, 900, 100, 20);

        btnStopDtHourUp.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/up.gif")); // NOI18N
        btnStopDtHourUp.setToolTipText("");
        btnStopDtHourUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopDtHourUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnStopDtHourUp);
        btnStopDtHourUp.setBounds(660, 840, 50, 20);

        lblStopHour.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStopHour.setText("20");
        lblStopHour.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStopHourMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStopHour);
        lblStopHour.setBounds(660, 860, 50, 40);

        btnStopDtHourDown.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/down.gif")); // NOI18N
        btnStopDtHourDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopDtHourDownActionPerformed(evt);
            }
        });
        getContentPane().add(btnStopDtHourDown);
        btnStopDtHourDown.setBounds(660, 900, 50, 20);

        lblDblPoint1.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblDblPoint1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDblPoint1.setText(":");
        getContentPane().add(lblDblPoint1);
        lblDblPoint1.setBounds(710, 860, 10, 40);

        btnStopDtMinUp.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/up.gif")); // NOI18N
        btnStopDtMinUp.setToolTipText("");
        btnStopDtMinUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopDtMinUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnStopDtMinUp);
        btnStopDtMinUp.setBounds(720, 840, 50, 20);

        lblStopMinutes.setFont(new java.awt.Font("Cantarell", 0, 36)); // NOI18N
        lblStopMinutes.setText("12");
        lblStopMinutes.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                lblStopMinutesMouseWheelMoved(evt);
            }
        });
        getContentPane().add(lblStopMinutes);
        lblStopMinutes.setBounds(720, 860, 50, 40);

        btnStopDtMinDown.setIcon(new javax.swing.ImageIcon("/home/yaroslav/HVV_HOME/res/images/down.gif")); // NOI18N
        btnStopDtMinDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopDtMinDownActionPerformed(evt);
            }
        });
        getContentPane().add(btnStopDtMinDown);
        btnStopDtMinDown.setBounds(720, 900, 50, 20);

        btnLayout1x1.setText("1x1");
        btnLayout1x1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLayout1x1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnLayout1x1);
        btnLayout1x1.setBounds(20, 930, 50, 40);

        btnLayout1x2.setText("1x2");
        btnLayout1x2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLayout1x2ActionPerformed(evt);
            }
        });
        getContentPane().add(btnLayout1x2);
        btnLayout1x2.setBounds(80, 930, 50, 40);

        btnLayout2x1.setText("2x1");
        btnLayout2x1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLayout2x1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnLayout2x1);
        btnLayout2x1.setBounds(140, 930, 50, 40);

        btnLayout2x2.setText("2x2");
        btnLayout2x2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLayout2x2ActionPerformed(evt);
            }
        });
        getContentPane().add(btnLayout2x2);
        btnLayout2x2.setBounds(200, 930, 50, 40);

        lblNaNProcTitle.setText("Как интерпретировать NaN в данных");
        getContentPane().add(lblNaNProcTitle);
        lblNaNProcTitle.setBounds(370, 920, 380, 20);

        buttonGroup1.add(radNaNProc3);
        radNaNProc3.setText("как число");
        getContentPane().add(radNaNProc3);
        radNaNProc3.setBounds(630, 940, 100, 30);

        buttonGroup1.add(radNaNProc1);
        radNaNProc1.setSelected(true);
        radNaNProc1.setText("Как и раньше");
        getContentPane().add(radNaNProc1);
        radNaNProc1.setBounds(370, 940, 113, 30);

        buttonGroup1.add(radNaNProc2);
        radNaNProc2.setText("пропускать");
        getContentPane().add(radNaNProc2);
        radNaNProc2.setBounds(500, 940, 125, 30);

        edtNaNProc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(edtNaNProc);
        edtNaNProc.setBounds(730, 940, 80, 30);

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
        getContentPane().add(jProgressBar1);
        jProgressBar1.setBounds(0, 980, 1190, 20);

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
        btnRefresh.setEnabled( false);
        btnExit.setEnabled( false);
        
        theApp.series_g1.clear();
        theApp.series_g2.clear();
        theApp.series_g3.clear();
        theApp.series_g4.clear();
        
        boolean bRunningDates = true;
        
        long lMillisDuration = m_gdtmStopDate.getTimeInMillis() - m_gdtmStartDate.getTimeInMillis();
        double dblRunningTimeStepMillis = 0;
        if( lMillisDuration > 1000000)
            dblRunningTimeStepMillis = ( double) lMillisDuration / 1000.;
        
        String strAxisLabel;
        for( int nGraph=0; nGraph<4; nGraph++) {
            strAxisLabel = "";
        
            if( nGraph == 1 && pnlGraph2.isVisible() == false) continue;
            if( nGraph == 2 && pnlGraph3.isVisible() == false) continue;
            if( nGraph == 3 && pnlGraph4.isVisible() == false) continue;
            
            SimpleDateFormat df = new SimpleDateFormat( "dd.MM.yyyy HH:mm:ss.SSS");
            GregorianCalendar gdt = (GregorianCalendar) m_gdtmStartDate.clone();
            logger.info( "STARTING gdt=" + df.format( gdt.getTime()));
            
            TimeSeries seria;
            switch( nGraph) {
                case 0:     seria = new TimeSeries( "G1",  Millisecond.class); break;
                case 1:     seria = new TimeSeries( "G2",  Millisecond.class); break;
                case 2:     seria = new TimeSeries( "G3",  Millisecond.class); break;
                case 3:     seria = new TimeSeries( "G4",  Millisecond.class); break;
                default:    seria = new TimeSeries( "G1",  Millisecond.class); break;
            }

            
            bRunningDates = true;
            do {    
                String strCsvFilename =  theApp.GetAMSRoot() + "/data/" + gdt.get( Calendar.YEAR);
                String strYearPackedDirectoryZipFile = theApp.GetAMSRoot() + "/data/" + gdt.get( Calendar.YEAR);
                String strMonthPackedDirectoryZipFile = strYearPackedDirectoryZipFile;
                
                int nMonth = gdt.get( Calendar.MONTH) + 1;
                if( nMonth < 10) {
                    strCsvFilename += ".0" + nMonth;
                    strMonthPackedDirectoryZipFile += ".0" + nMonth + "/" + gdt.get( Calendar.YEAR) + ".0" + nMonth;
                    strYearPackedDirectoryZipFile += "/" + gdt.get( Calendar.YEAR) + ".0" + nMonth;
                }
                else {
                    strCsvFilename += "." + nMonth;
                    strMonthPackedDirectoryZipFile += "." + nMonth + "/" + gdt.get( Calendar.YEAR) + "." + nMonth;
                    strYearPackedDirectoryZipFile += "/" + gdt.get( Calendar.YEAR) + nMonth;
                }
                
                int nDay = gdt.get( Calendar.DAY_OF_MONTH);
                if( nDay < 10) {
                    strCsvFilename += ".0" + nDay;
                    strMonthPackedDirectoryZipFile += ".0" + nDay;
                    strYearPackedDirectoryZipFile  += ".0" + nDay;
                }
                else {
                    strCsvFilename += "." + nDay;
                    strMonthPackedDirectoryZipFile += "." + nDay;
                    strYearPackedDirectoryZipFile  += "." + nDay;
                }
                
                strMonthPackedDirectoryZipFile += ".zip";
                strYearPackedDirectoryZipFile += ".zip";
                String strZipFilename = strCsvFilename + ".zip";
                
                
                JComboBox cmb;
                switch( nGraph) {
                    case 0:     cmb = cmbGraph1; break;
                    case 1:     cmb = cmbGraph2; break;
                    case 2:     cmb = cmbGraph3; break;
                    case 3:     cmb = cmbGraph4; break;
                    default:    cmb = cmbGraph1; break;
                }

                int nIndex = cmb.getSelectedIndex();
                String strSelection = ( String) cmb.getModel().getElementAt(nIndex);

                int nPoint1 = strSelection.indexOf( ".", 0);
                int nPoint2 = strSelection.indexOf( ".", nPoint1+1);
                int nPoint3 = strSelection.indexOf( ".", nPoint2+1);
                int nPoint4 = strSelection.indexOf( ".", nPoint3+1);

                if( Character.isDigit( strSelection.charAt( 0)) == true) {
                    //VAC.param
                    strCsvFilename += ".VAC";
                    
                    String strDevIndex = strSelection.substring( 0, nPoint1);
                    strCsvFilename += "." + strDevIndex;
                    
                    String strDevSubIndex;
                    if( strSelection.charAt( 0) == '2')
                        strDevSubIndex = strSelection.substring( nPoint3 + 1, nPoint4);
                    else
                        strDevSubIndex = strSelection.substring( nPoint2 + 1, nPoint3);
                    strCsvFilename += "." + strDevSubIndex;
                    
                    HVV_VacuumDevice dev = ( HVV_VacuumDevice) HVV_VacuumDevices.getInstance().m_devices.get( strDevIndex);
                    strAxisLabel = ( String) dev.m_mapParametersUnits.get( strDevSubIndex);
                }
                else {
                    //HV.param
                    strCsvFilename += ".HV";
                    strCsvFilename += "." + strSelection.substring( 0, nPoint1);
                    strCsvFilename += "." + strSelection.substring( nPoint3 + 1, nPoint4);
                    
                    HVV_HvDevice dev = ( HVV_HvDevice) HVV_HvDevices.getInstance().m_devices.get( strSelection.substring( 0, nPoint1));
                    strAxisLabel = ( String) dev.m_mapParametersUnits.get( strSelection.substring( nPoint3 + 1, nPoint4));
                }

                strCsvFilename += ".csv";

                logger.info( "CSV file: '" + strCsvFilename + "'");
                logger.info( "ZIP file: '" + strZipFilename + "'");
                logger.info("ZIP monthly-folder-packed-file: '" + strMonthPackedDirectoryZipFile + "'");
                logger.info( "ZIP  yearly-folder-packed-file: '" + strYearPackedDirectoryZipFile + "'");
                
                //check if file exists
                File f = new File( strCsvFilename);
                if( f.exists() == false) { 
                    
                    //check if ZIP File exists
                    f = new File( strZipFilename);
                    
                    if( !f.exists()) {
                        //давайте попробуем вытащить ZIP-архив из папки архивов под названием "ГОД.МЕСЯЦ"
                        File f_arcFolded = new File( strMonthPackedDirectoryZipFile);
                        if( f_arcFolded.exists()) {
                            Path src = FileSystems.getDefault().getPath(strMonthPackedDirectoryZipFile);
                            Path dst = FileSystems.getDefault().getPath( strZipFilename);
                            try {
                                Files.move( src, dst, REPLACE_EXISTING);
                            } catch( IOException ex) {
                                logger.error( "IOException caught!", ex);
                            }
                        }
                        
                        //check again if ZIP File exists in /data folder ;)
                        f = new File( strZipFilename);
                    }
                    
                    if( !f.exists()) {
                        //CSV файла нет, ZIP файла в папке ГОД.МЕСЯЦ нет, давайте попробуем вытащить ZIP-файл из папки ГОД
                        File f_arcFolded = new File( strYearPackedDirectoryZipFile);
                        if( f_arcFolded.exists()) {
                            Path src = FileSystems.getDefault().getPath( strYearPackedDirectoryZipFile);
                            Path dst = FileSystems.getDefault().getPath( strZipFilename);
                            try {
                                Files.move( src, dst, REPLACE_EXISTING);
                            } catch( IOException ex) {
                                logger.error( "IOException caught!", ex);
                            }
                        }
                        
                        //check again if ZIP File exists in /data folder ;)
                        f = new File( strZipFilename);
                    }
                    
                    if( f.exists()) {
                        String outputFolder = theApp.GetAMSRoot() + "/data";
                        byte[] buffer = new byte[1024];
                        
                        try {

                            /*
                            //create output directory is not exists
                            File folder = new File(OUTPUT_FOLDER);
                            if(!folder.exists()){
                                    folder.mkdir();
                            }
                            */

                            //get the zip file content
                            ZipInputStream zis =
                                new ZipInputStream(new FileInputStream( strZipFilename));
                            
                            //get the zipped file list entry
                            ZipEntry ze = zis.getNextEntry();
                            while( ze != null) {
                                
                                String fileName = ze.getName();
                                File newFile = new File(outputFolder + File.separator + fileName);
                                logger.info("UNZIPPING '" + strZipFilename + "': file: "+ newFile.getAbsoluteFile());

                                //create all non exists folders
                                //else you will hit FileNotFoundException for compressed folder
                                new File( newFile.getParent()).mkdirs();

                                FileOutputStream fos = new FileOutputStream(newFile);
                                int len;
                                while( ( len = zis.read( buffer)) > 0) {
                                    fos.write( buffer, 0, len);
                                }

                                fos.close();
                                ze = zis.getNextEntry();
                            }

                            zis.closeEntry();
                            zis.close();

                            logger.info("UNZIPPING Done");
                            
                            logger.info("Removing ZIP-file");
                            f.delete();
                            
                        } catch( IOException ex) {
                            logger.error( "IOExceiption caught:", ex);
                        }
                        
                        
                    }
                    else {
                        logger.warn( "ZIP file is also absent. Skipping the date");
                        
                        //файл от текущего дня не найден - перейдём к следующему
                        gdt.add( Calendar.DAY_OF_MONTH, 1);
                        gdt.set( Calendar.HOUR_OF_DAY, 0);
                        gdt.set( Calendar.MINUTE, 0);


                        logger.info( "gdt=" + df.format( gdt.getTime()));
                        logger.info( "m_gdtmStopDate=" + df.format( m_gdtmStopDate.getTime()));
                        logger.info( "gdt.after( m_gdtmStopDate)=" + gdt.after( m_gdtmStopDate));
                        logger.info( "***");
                        if( gdt.after( m_gdtmStopDate))
                            bRunningDates = false;

                        if( bRunningDates) {
                            logger.info( "Next day");
                            continue;
                        }
                        else {
                            logger.info( "Stops");
                            break;
                        }
                    }
                    
                }

                //File fl = new File( strFilename);
                //ZipInputStream zis;


                
                long lRunningDate = m_gdtmStartDate.getTimeInMillis() + ( long) dblRunningTimeStepMillis;

                jProgressBar1.setMinimum( 0);
                jProgressBar1.setMaximum( ( int) ( m_gdtmStopDate.getTimeInMillis() - m_gdtmStartDate.getTimeInMillis()));
                jProgressBar1.setValue( ( int) ( lRunningDate - m_gdtmStartDate.getTimeInMillis()));
                
                double dblReplaceValue = 0;
                try {
                    dblReplaceValue = Double.parseDouble( edtNaNProc.getText().replace( ',', '.'));
                }
                catch( NumberFormatException ex) {
                    logger.warn( ex);
                }
                
                FileInputStream fin = null;
                try {
                    fin = new FileInputStream( strCsvFilename);
                    fin.close();

                    //файл 1
                    BufferedReader br = new BufferedReader( new FileReader( strCsvFilename));

                    String strLine;
                    double dblSumm = 0.;
                    int nC = 0;

                    //и пока читаются строки из файла, читаем
                    while ( ( strLine = br.readLine()) != null) {

                        //ищем разделитель
                        int indx1 = strLine.indexOf( ';');

                        //раздербаниваем - откусываем дату
                        String strDate = strLine.substring( 0, indx1);
                        Date dt = df.parse( strDate);

                        if( dt.after( m_gdtmStopDate.getTime())) {
                            logger.info( "OVER");
                            break;
                        }

                        if( dt.after( m_gdtmStartDate.getTime())) {
                            //раздербаниваем дальше - откусываем значение
                            String strValue = strLine.substring( indx1+1);
                            strValue = strValue.substring( 0, strValue.length()-1);
                            double dblValue = Double.parseDouble( strValue);

                            //logger.info( "line='" + strLine + "'");
                            //logger.info( "strDate='" + strDate + "'");
                            //logger.info( "strValue='" + strValue + "'");

                            dblSumm += dblValue;
                            nC++;

                            if( dblRunningTimeStepMillis > 0.) {
                                //добавляем в сумматор для среднего
                                if( dt.getTime() > lRunningDate) {
                                    double val = dblSumm / ( double) nC;
                                    
                                    //logger.info( "GRAPH" + nGraph + " ADDING (" + val+ ")!");
                                    lRunningDate += ( long) dblRunningTimeStepMillis;
                                    jProgressBar1.setValue( ( int) ( lRunningDate - m_gdtmStartDate.getTimeInMillis()));
                                    
                                    if( Double.isNaN( val)) {
                                        if( radNaNProc1.isSelected()) {
                                            //as it is
                                            seria.addOrUpdate( new Millisecond(dt), val);
                                        }
                                        else if( radNaNProc2.isSelected()) {
                                            //skipping
                                        }
                                        else if( radNaNProc3.isSelected()) {
                                            //replacing with number
                                            seria.addOrUpdate( new Millisecond(dt), dblReplaceValue);
                                        }
                                    }
                                    else
                                        seria.addOrUpdate( new Millisecond(dt), val);
                                    
                                    dblSumm = 0.; nC = 0;

                                }
                            }
                            else {
                                //добавляем в серию наживую 
                                //logger.info( "GRAPH" + nGraph + " ADDING 1:1!");
                                seria.addOrUpdate( new Millisecond(dt), dblValue);
                            }
                        }

                    }



                }
                catch( ParseException ex) {
                    logger.error( "ParseException caught!", ex);
                }
                catch( FileNotFoundException ex) {
                    logger.error( "FileNotFoundException caught!", ex);
                }
                catch( IOException ex) {
                    logger.error( "IOException caught!", ex);
                }
                //catch( CloneNotSupportedException ex) {
                //    logger.error( "CloneNotSupportedException caught!", ex);
                //}

                //файл от текущего дня закончился - перейдём к следующему
                gdt.add( Calendar.DAY_OF_MONTH, 1);
                gdt.set( Calendar.HOUR_OF_DAY, 0);
                gdt.set( Calendar.MINUTE, 0);
                
                
                logger.info( "gdt=" + df.format( gdt.getTime()));
                logger.info( "m_gdtmStopDate=" + df.format( m_gdtmStopDate.getTime()));
                logger.info( "gdt.after( m_gdtmStopDate)=" + gdt.after( m_gdtmStopDate));
                logger.info( "***");
                if( gdt.after( m_gdtmStopDate))
                    bRunningDates = false;

                if( bRunningDates)
                    logger.info( "Next day");
                else
                    logger.info( "Stops");
                
                //break;

            } while( bRunningDates);

            logger.info( "starting redraw" + nGraph);
            switch( nGraph) {
                case 0:
                    theApp.series_g1.addAndOrUpdate( seria);
                    m_panelGraph1.m_chart.getXYPlot().getRangeAxis().setLabel( strAxisLabel);
                break;
                    
                case 1:
                    theApp.series_g2.addAndOrUpdate( seria);
                    m_panelGraph2.m_chart.getXYPlot().getRangeAxis().setLabel( strAxisLabel);
                break;
                    
                case 2:
                    theApp.series_g3.addAndOrUpdate( seria);
                    m_panelGraph3.m_chart.getXYPlot().getRangeAxis().setLabel( strAxisLabel);
                break;
                    
                case 3:
                    theApp.series_g4.addAndOrUpdate( seria);
                    m_panelGraph4.m_chart.getXYPlot().getRangeAxis().setLabel( strAxisLabel);
                break;
                    
                default:
                    theApp.series_g1.addAndOrUpdate( seria);
                    m_panelGraph1.m_chart.getXYPlot().getRangeAxis().setLabel( strAxisLabel);
                break;
            }
            logger.info( "go to next graph");
        }
        
        
        btnRefresh.setEnabled( true);
        btnExit.setEnabled( true);
        
        logger.info( "out");
            
    }//GEN-LAST:event_btnRefreshActionPerformed

    public void SetGraphsViewStates() {
        switch( m_nGraphs) {
            case 2:
                pnlGraph1.setVisible( true);    cmbGraph1.setVisible( true);
                pnlGraph2.setVisible( false);   cmbGraph2.setVisible( false);
                pnlGraph3.setVisible( true);    cmbGraph3.setVisible( true);
                pnlGraph4.setVisible( false);   cmbGraph4.setVisible( false);

                cmbGraph1.setBounds( 10, 10,  1180, 25);    pnlGraph1.setBounds( 10, 35,  1180, 380);   m_panelGraph1.setBoundsO( 0, 0, 1180, 380);
                cmbGraph3.setBounds( 10, 430, 1180, 25);    pnlGraph3.setBounds( 10, 455, 1180, 380);   m_panelGraph3.setBoundsO( 0, 0, 1180, 380);
            break;
                
            case 3:
                pnlGraph1.setVisible( true);    cmbGraph1.setVisible( true);
                pnlGraph2.setVisible( true);    cmbGraph2.setVisible( true);
                pnlGraph3.setVisible( false);   cmbGraph3.setVisible( false);
                pnlGraph4.setVisible( false);   cmbGraph4.setVisible( false);

                cmbGraph1.setBounds( 10,  10, 580, 25);    pnlGraph1.setBounds( 10,  35, 580, 805);     m_panelGraph1.setBoundsO( 0, 0, 580, 805);
                cmbGraph2.setBounds( 600, 10, 580, 25);    pnlGraph2.setBounds( 600, 35, 580, 805);     m_panelGraph2.setBoundsO( 0, 0, 580, 805);
            break;
                
            case 4:
                pnlGraph1.setVisible( true);    cmbGraph1.setVisible( true);
                pnlGraph2.setVisible( true);    cmbGraph2.setVisible( true);
                pnlGraph3.setVisible( true);    cmbGraph3.setVisible( true);
                pnlGraph4.setVisible( true);    cmbGraph4.setVisible( true);

                cmbGraph1.setBounds( 10,  10, 580, 25);    pnlGraph1.setBounds( 10,  35,  580, 380);    m_panelGraph1.setBoundsO( 0, 0, 580, 380);
                cmbGraph2.setBounds( 600, 10, 580, 25);    pnlGraph2.setBounds( 600, 35,  580, 380);    m_panelGraph2.setBoundsO( 0, 0, 580, 380);
                cmbGraph3.setBounds( 10,  430, 580, 25);   pnlGraph3.setBounds( 10,  455, 580, 380);    m_panelGraph3.setBoundsO( 0, 0, 580, 380);
                cmbGraph4.setBounds( 600, 430, 580, 25);   pnlGraph4.setBounds( 600, 455, 580, 380);    m_panelGraph4.setBoundsO( 0, 0, 580, 380);
            break;
                
            default:
                pnlGraph1.setVisible( true);    cmbGraph1.setVisible( true);
                pnlGraph2.setVisible( false);   cmbGraph2.setVisible( false);
                pnlGraph3.setVisible( false);   cmbGraph3.setVisible( false);
                pnlGraph4.setVisible( false);   cmbGraph4.setVisible( false);

                cmbGraph1.setBounds( 10, 10, 1180, 25);     pnlGraph1.setBounds( 10, 35, 1180, 805);    m_panelGraph1.setBoundsO( 0, 0, 1180, 805);
            break;
        }
    }
    private void btnLayout1x1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayout1x1ActionPerformed
        m_nGraphs = 1;
        SetGraphsViewStates();
    }//GEN-LAST:event_btnLayout1x1ActionPerformed

    private void btnLayout1x2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayout1x2ActionPerformed
        m_nGraphs = 2;
        SetGraphsViewStates();        
    }//GEN-LAST:event_btnLayout1x2ActionPerformed

    private void btnLayout2x1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayout2x1ActionPerformed
        m_nGraphs = 3;
        SetGraphsViewStates();
    }//GEN-LAST:event_btnLayout2x1ActionPerformed

    private void btnLayout2x2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayout2x2ActionPerformed
        m_nGraphs = 4;
        SetGraphsViewStates();
    }//GEN-LAST:event_btnLayout2x2ActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        //theApp.GetSettings().Set_nGraphs( m_nGraphs);
        //theApp.GetSettings().SaveSettings();
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        theApp.GetSettings().Set_nGraphs( m_nGraphs);
        
        theApp.GetSettings().Set_Graph1ViewParam( cmbGraph1.getSelectedIndex());
        theApp.GetSettings().Set_Graph2ViewParam( cmbGraph2.getSelectedIndex());
        theApp.GetSettings().Set_Graph3ViewParam( cmbGraph3.getSelectedIndex());
        theApp.GetSettings().Set_Graph4ViewParam( cmbGraph4.getSelectedIndex());
        
        theApp.GetSettings().SetStartDtm( m_gdtmStartDate);
        theApp.GetSettings().SetStopDtm( m_gdtmStopDate);
        
        if( radNaNProc1.isSelected()) theApp.GetSettings().SetNaNProcessing( 0);
        else if( radNaNProc2.isSelected()) theApp.GetSettings().SetNaNProcessing( 1);
        else if( radNaNProc3.isSelected()) theApp.GetSettings().SetNaNProcessing( 2);
        
        double dblRepl = 0.;
        try {
            dblRepl = Double.parseDouble( edtNaNProc.getText().replace( ',', '.'));
        }
        catch( NumberFormatException ex) {
            logger.warn( ex);
        }
        theApp.GetSettings().SetNaNProcessingReplacement( dblRepl);
        
        theApp.GetSettings().SaveSettings();
    }//GEN-LAST:event_formWindowClosed

    private void btnStartDtYearUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartDtYearUpActionPerformed
        m_gdtmStartDate.add( Calendar.YEAR, 1); updateDate();
    }//GEN-LAST:event_btnStartDtYearUpActionPerformed

    private void btnStartDtDayDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartDtDayDownActionPerformed
        m_gdtmStartDate.add( Calendar.DAY_OF_MONTH, -1); updateDate();
    }//GEN-LAST:event_btnStartDtDayDownActionPerformed

    private void btnStartDtMinUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartDtMinUpActionPerformed
        m_gdtmStartDate.add( Calendar.MINUTE, 1); updateDate();
    }//GEN-LAST:event_btnStartDtMinUpActionPerformed

    private void btnStartDtMonUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartDtMonUpActionPerformed
        m_gdtmStartDate.add( Calendar.MONTH, 1); updateDate();
    }//GEN-LAST:event_btnStartDtMonUpActionPerformed

    private void btnStartDtHourUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartDtHourUpActionPerformed
        m_gdtmStartDate.add( Calendar.HOUR, 1); updateDate();
    }//GEN-LAST:event_btnStartDtHourUpActionPerformed

    private void btnStartDtDayUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartDtDayUpActionPerformed
        m_gdtmStartDate.add( Calendar.DAY_OF_MONTH, 1); updateDate();
    }//GEN-LAST:event_btnStartDtDayUpActionPerformed

    private void btnStartDtMinDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartDtMinDownActionPerformed
        m_gdtmStartDate.add( Calendar.MINUTE, -1); updateDate();
    }//GEN-LAST:event_btnStartDtMinDownActionPerformed

    private void btnStartDtHourDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartDtHourDownActionPerformed
        m_gdtmStartDate.add( Calendar.HOUR, -1); updateDate();
    }//GEN-LAST:event_btnStartDtHourDownActionPerformed

    private void btnStartDtYearDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartDtYearDownActionPerformed
        m_gdtmStartDate.add( Calendar.YEAR, -1); updateDate();
    }//GEN-LAST:event_btnStartDtYearDownActionPerformed

    private void btnStartDtMonDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartDtMonDownActionPerformed
        m_gdtmStartDate.add( Calendar.MONTH, -1); updateDate();
    }//GEN-LAST:event_btnStartDtMonDownActionPerformed

    private void btnStopDtHourDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopDtHourDownActionPerformed
        m_gdtmStopDate.add( Calendar.HOUR, -1); updateDate();
    }//GEN-LAST:event_btnStopDtHourDownActionPerformed

    private void btnStopDtMonDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopDtMonDownActionPerformed
        m_gdtmStopDate.add( Calendar.MONTH, -1); updateDate();
    }//GEN-LAST:event_btnStopDtMonDownActionPerformed

    private void btnStopDtYearDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopDtYearDownActionPerformed
        m_gdtmStopDate.add( Calendar.YEAR, -1); updateDate();
    }//GEN-LAST:event_btnStopDtYearDownActionPerformed

    private void btnStopDtMinDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopDtMinDownActionPerformed
        m_gdtmStopDate.add( Calendar.MINUTE, -1); updateDate();
    }//GEN-LAST:event_btnStopDtMinDownActionPerformed

    private void btnStopDtDayDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopDtDayDownActionPerformed
        m_gdtmStopDate.add( Calendar.DAY_OF_MONTH, -1); updateDate();
    }//GEN-LAST:event_btnStopDtDayDownActionPerformed

    private void btnStopDtHourUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopDtHourUpActionPerformed
        m_gdtmStopDate.add( Calendar.HOUR, 1); updateDate();
    }//GEN-LAST:event_btnStopDtHourUpActionPerformed

    private void btnStopDtMonUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopDtMonUpActionPerformed
        m_gdtmStopDate.add( Calendar.MONTH, 1); updateDate();
    }//GEN-LAST:event_btnStopDtMonUpActionPerformed

    private void btnStopDtYearUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopDtYearUpActionPerformed
        m_gdtmStopDate.add( Calendar.YEAR, 1); updateDate();
    }//GEN-LAST:event_btnStopDtYearUpActionPerformed

    private void btnStopDtMinUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopDtMinUpActionPerformed
        m_gdtmStopDate.add( Calendar.MINUTE, 1); updateDate();
    }//GEN-LAST:event_btnStopDtMinUpActionPerformed

    private void btnStopDtDayUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopDtDayUpActionPerformed
        m_gdtmStopDate.add( Calendar.DAY_OF_MONTH, 1); updateDate();
    }//GEN-LAST:event_btnStopDtDayUpActionPerformed

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
        
        
        if( m_gdtmStopDate.before( m_gdtmStartDate)) {
            m_gdtmStopDate = ( GregorianCalendar) m_gdtmStartDate.clone();
            m_gdtmStopDate.add( Calendar.MINUTE, 1);
        }
        
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
    private javax.swing.JButton btnStartDtDayDown;
    private javax.swing.JButton btnStartDtDayUp;
    private javax.swing.JButton btnStartDtHourDown;
    private javax.swing.JButton btnStartDtHourUp;
    private javax.swing.JButton btnStartDtMinDown;
    private javax.swing.JButton btnStartDtMinUp;
    private javax.swing.JButton btnStartDtMonDown;
    private javax.swing.JButton btnStartDtMonUp;
    private javax.swing.JButton btnStartDtYearDown;
    private javax.swing.JButton btnStartDtYearUp;
    private javax.swing.JButton btnStopDtDayDown;
    private javax.swing.JButton btnStopDtDayUp;
    private javax.swing.JButton btnStopDtHourDown;
    private javax.swing.JButton btnStopDtHourUp;
    private javax.swing.JButton btnStopDtMinDown;
    private javax.swing.JButton btnStopDtMinUp;
    private javax.swing.JButton btnStopDtMonDown;
    private javax.swing.JButton btnStopDtMonUp;
    private javax.swing.JButton btnStopDtYearDown;
    private javax.swing.JButton btnStopDtYearUp;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbGraph1;
    private javax.swing.JComboBox cmbGraph2;
    private javax.swing.JComboBox cmbGraph3;
    private javax.swing.JComboBox cmbGraph4;
    private javax.swing.JTextField edtNaNProc;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lblDblPoint;
    private javax.swing.JLabel lblDblPoint1;
    private javax.swing.JLabel lblHour;
    private javax.swing.JLabel lblMinutes;
    private javax.swing.JLabel lblNaNProcTitle;
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
    private javax.swing.JRadioButton radNaNProc1;
    private javax.swing.JRadioButton radNaNProc2;
    private javax.swing.JRadioButton radNaNProc3;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hvv_arcviewer;

import hvv_resources.HVV_Resources;
import java.io.File;
import java.net.ServerSocket;
import javax.swing.JOptionPane;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;



/**
 *
 * @author yaroslav
 */
public class HVV_ArchiveViewer {
    public HVV_AV_MainFrame m_pMainWnd;
    
    private final String m_strAMSrootEnvVar;
    public String GetAMSRoot() { return m_strAMSrootEnvVar; }
    
    static Logger logger = Logger.getLogger( HVV_ArchiveViewer.class);
    public static final org.apache.log4j.Level LOG_LEVEL = org.apache.log4j.Level.DEBUG;
    
    private final HVV_ArcViewerSettings m_pSettings;
    public HVV_ArcViewerSettings GetSettings() { return m_pSettings;}
    
    private final HVV_Resources m_pResources;
    public HVV_Resources GetResources() { return m_pResources;}
    
    private ServerSocket m_pSingleInstanceSocketServer;
    
    public TimeSeries series_g1;
    public TimeSeries series_g2;
    public TimeSeries series_g3;
    public TimeSeries series_g4;
    
    public HVV_ArchiveViewer() {
        m_strAMSrootEnvVar = System.getenv( "AMS_ROOT");
        
        //SETTINGS
        m_pSettings = new HVV_ArcViewerSettings( m_strAMSrootEnvVar);
        
        //ПРОВЕРКА ОДНОВРЕМЕННОГО ЗАПУСКА ТОЛЬКО ОДНОЙ КОПИИ ПРОГРАММЫ
        try {
            m_pSingleInstanceSocketServer = new ServerSocket( GetSettings().GetSingleInstanceSocketServerPort());
        }
        catch( Exception ex) {
            MessageBoxError( "Модуль сбора данных уже запущен.\nПоищите на других \"экранах\".", "Модуль сбора данных");
            logger.error( "Не смогли открыть сокет для проверки запуска только одной копии программы! Программа уже запущена?", ex);
            m_pSingleInstanceSocketServer = null;
            m_pResources = null;
            return;
        }
        
        //RESOURCES
        m_pResources = HVV_Resources.getInstance();
        
        series_g1 =  new TimeSeries( "G1",  Millisecond.class);
        series_g2 =  new TimeSeries( "G2",  Millisecond.class);
        series_g3 =  new TimeSeries( "G3",  Millisecond.class);
        series_g4 =  new TimeSeries( "G4",  Millisecond.class);
    }
    
    
    
    public void start() {
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
            java.util.logging.Logger.getLogger( HVV_AV_MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger( HVV_AV_MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger( HVV_AV_MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger( HVV_AV_MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        m_pMainWnd = new HVV_AV_MainFrame( this);
        java.awt.EventQueue.invokeLater( new Runnable() {
            public void run() {
                m_pMainWnd.setVisible( true);
            }
        });
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //BasicConfigurator.configure();
        //logger.setLevel( LOG_LEVEL);
        
        
        //главная переменная окружения
        String strAMSrootEnvVar = System.getenv( "AMS_ROOT");
        if( strAMSrootEnvVar == null) {
            MessageBoxError( "Не задана переменная окружения AMS_ROOT!", "HVV_Poller");
            return;
        }
        
        //настройка логгера
        String strlog4jPropertiesFile = strAMSrootEnvVar + "/etc/log4j.arcviewer.properties";
        File file = new File( strlog4jPropertiesFile);
        if(!file.exists())
            System.out.println("It is not possible to load the given log4j properties file :" + file.getAbsolutePath());
        else
            PropertyConfigurator.configure( file.getAbsolutePath());
        
        new HVV_ArchiveViewer().start();
        
        logger.info( "ArcViewerApp::main(): in. Start point!");
    }
    
    /**
     * Функция для сообщения пользователю информационного сообщения
     * @param strMessage сообщение
     * @param strTitleBar заголовок
     */
    public static void MessageBoxInfo( String strMessage, String strTitleBar)
    {
        JOptionPane.showMessageDialog( null, strMessage, strTitleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Функция для сообщения пользователю сообщения об ошибке
     * @param strMessage сообщение
     * @param strTitleBar заголовок
     */
    public static void MessageBoxError( String strMessage, String strTitleBar)
    {
        JOptionPane.showMessageDialog( null, strMessage, strTitleBar, JOptionPane.ERROR_MESSAGE);
    }
}

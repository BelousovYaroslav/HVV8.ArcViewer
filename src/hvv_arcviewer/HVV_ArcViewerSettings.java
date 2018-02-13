/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hvv_arcviewer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author yaroslav
 */
public class HVV_ArcViewerSettings {
    static Logger logger = Logger.getLogger( HVV_ArcViewerSettings.class);
    
    private int m_nSingleInstanceSocketServerPort;
    public int GetSingleInstanceSocketServerPort() { return m_nSingleInstanceSocketServerPort;}
    
    private int m_nGraphs;
    public int Get_nGraphs() { return m_nGraphs;}
    public void Set_nGraphs( int nNewValue) { m_nGraphs = nNewValue;}
    
    private int m_nGraph1ViewParam;
    public int Get_Graph1ViewParam() { return m_nGraph1ViewParam;}
    public void Set_Graph1ViewParam( int nNewValue) { m_nGraph1ViewParam = nNewValue;}
    
    private int m_nGraph2ViewParam;
    public int Get_Graph2ViewParam() { return m_nGraph2ViewParam;}
    public void Set_Graph2ViewParam( int nNewValue) { m_nGraph2ViewParam = nNewValue;}
    
    private int m_nGraph3ViewParam;
    public int Get_Graph3ViewParam() { return m_nGraph3ViewParam;}
    public void Set_Graph3ViewParam( int nNewValue) { m_nGraph3ViewParam = nNewValue;}
    
    private int m_nGraph4ViewParam;
    public int Get_Graph4ViewParam() { return m_nGraph4ViewParam;}
    public void Set_Graph4ViewParam( int nNewValue) { m_nGraph4ViewParam = nNewValue;}
    
    private int m_nTimeZoneShift;
    public int GetTimeZoneShift() { return m_nTimeZoneShift;}
    
    private GregorianCalendar m_gclndStart;
    public GregorianCalendar GetStartDtm() { return m_gclndStart; }
    public void SetStartDtm( GregorianCalendar gclndNewDtm) { m_gclndStart = ( GregorianCalendar) gclndNewDtm.clone();}
    
    private GregorianCalendar m_gclndStop;
    public GregorianCalendar GetStopDtm() { return m_gclndStop; }
    public void SetStopDtm( GregorianCalendar gclndNewDtm) { m_gclndStop = ( GregorianCalendar) gclndNewDtm.clone();}
    
    private int m_nNaNProcessing;
    public int GetNaNProcessing() { return m_nNaNProcessing;}
    public void SetNaNProcessing( int nNewValue) { m_nNaNProcessing = nNewValue;}
    
    private double m_dblNaNProcessingReplacement;
    public double GetNaNProcessingReplacement() { return m_dblNaNProcessingReplacement;}
    public void SetNaNProcessingReplacement( double dblNewValue) { m_dblNaNProcessingReplacement = dblNewValue;}
    
    public HVV_ArcViewerSettings( String strAMSRoot) {
        m_nSingleInstanceSocketServerPort = 10005;
        
        m_nGraphs = 1;
        
        m_nGraph1ViewParam = 1;
        m_nGraph2ViewParam = 2;
        m_nGraph3ViewParam = 3;
        m_nGraph4ViewParam = 4;
        
        m_nTimeZoneShift = 0;

        m_gclndStart = null;
        m_gclndStop = null;
        
        m_nNaNProcessing = 0;
        m_dblNaNProcessingReplacement = 0.;
        
        ReadSettings();
    }
    
    private boolean ReadSettings() {
        boolean bResOk = true;
        try {
            SAXReader reader = new SAXReader();
            
            String strSettingsFilePathName = System.getenv( "AMS_ROOT") + File.separator +
                                            "etc" + File.separator +
                                            "settings.arcviewer.r.xml";
            URL url = ( new java.io.File( strSettingsFilePathName)).toURI().toURL();
            
            Document document = reader.read( url);
            
            Element root = document.getRootElement();

            // iterate through child elements of root
            for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element element = ( Element) i.next();
                String name = element.getName();
                String value = element.getText();
                
                //logger.debug( "Pairs: [" + name + " : " + value + "]");
                
                if( "singleInstancePort.ArcViewer".equals( name)) m_nSingleInstanceSocketServerPort = Integer.parseInt( value);
                
                if( "nGraphs".equals( name)) m_nGraphs = Integer.parseInt( value);
                
                if( "Graph1ViewParam".equals( name)) m_nGraph1ViewParam = Integer.parseInt( value);
                if( "Graph2ViewParam".equals( name)) m_nGraph2ViewParam = Integer.parseInt( value);
                if( "Graph3ViewParam".equals( name)) m_nGraph3ViewParam = Integer.parseInt( value);
                if( "Graph4ViewParam".equals( name)) m_nGraph4ViewParam = Integer.parseInt( value);
                
                SimpleDateFormat formatter = new SimpleDateFormat( "yyyy.MM.dd HH:mm:ss");
                try {
                    if( "gclndrStart".equals( name)) { m_gclndStart = new GregorianCalendar(); m_gclndStart.setTime( formatter.parse( value));}
                    if( "gclndrStop".equals( name))  { m_gclndStop  = new GregorianCalendar();  m_gclndStop.setTime( formatter.parse( value));}
                }
                catch( ParseException ex) {
                    logger.warn( ex);
                }
                        
                if( "timezone".equals( name)) m_nTimeZoneShift = Integer.parseInt( value);
                
                if( "NaN.Processing".equals( name)) m_nNaNProcessing = Integer.parseInt( value);
                if( "NaN.Processing.replace".equals( name)) m_dblNaNProcessingReplacement = Double.parseDouble( value.replace( ',', '.'));
            }
            
        } catch( MalformedURLException ex) {
            logger.error( "MalformedURLException caught while loading settings!", ex);
            bResOk = false;
        } catch( DocumentException ex) {
            logger.error( "DocumentException caught while loading settings!", ex);
            bResOk = false;
        }
        
        if( bResOk) {
            try {
                SAXReader reader = new SAXReader();

                String strSettingsFilePathName = System.getenv( "AMS_ROOT") + File.separator +
                                                    "etc" + File.separator +
                                                    "settings.arcviewer.rw.xml";
                File flCheckExistence = new File( strSettingsFilePathName);
                if( flCheckExistence.exists()) {
                
                    URL url = ( new java.io.File( strSettingsFilePathName)).toURI().toURL();

                    Document document = reader.read( url);

                    Element root = document.getRootElement();

                    // iterate through child elements of root
                    for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
                        Element element = ( Element) i.next();
                        String name = element.getName();
                        String value = element.getText();

                        //logger.debug( "Pairs: [" + name + " : " + value + "]");

                        if( "nGraphs".equals( name)) m_nGraphs = Integer.parseInt( value);

                        if( "Graph1ViewParam".equals( name)) m_nGraph1ViewParam = Integer.parseInt( value);
                        if( "Graph2ViewParam".equals( name)) m_nGraph2ViewParam = Integer.parseInt( value);
                        if( "Graph3ViewParam".equals( name)) m_nGraph3ViewParam = Integer.parseInt( value);
                        if( "Graph4ViewParam".equals( name)) m_nGraph4ViewParam = Integer.parseInt( value);
                        
                        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy.MM.dd HH:mm:ss");
                        try {
                            if( "gclndrStart".equals( name)) { m_gclndStart = new GregorianCalendar(); m_gclndStart.setTime( formatter.parse( value));}
                            if( "gclndrStop".equals( name))  { m_gclndStop  = new GregorianCalendar(); m_gclndStop.setTime(  formatter.parse( value));}
                        }
                        catch( ParseException ex) {
                            logger.warn( ex);
                        }
                        
                        if( "timezone".equals( name)) m_nTimeZoneShift = Integer.parseInt( value);
                        
                        if( "NaN.Processing".equals( name)) m_nNaNProcessing = Integer.parseInt( value);
                        if( "NaN.Processing.replace".equals( name)) {
                            try {
                                m_dblNaNProcessingReplacement = Double.parseDouble( value.replace(',', '.'));
                            }
                            catch( NumberFormatException ex) {
                                logger.warn( ex);
                            }
                        }
                    }
                }

            } catch( MalformedURLException ex) {
                logger.error( "MalformedURLException caught while loading settings!", ex);
                bResOk = false;
            } catch( DocumentException ex) {
                logger.error( "DocumentException caught while loading settings!", ex);
                bResOk = false;
            }
        }
        
        if( m_gclndStart == null) {
            m_gclndStart = new GregorianCalendar();
            m_gclndStart.setTime( new Date( System.currentTimeMillis() - 1000 * 60 * 60 * m_nTimeZoneShift));
            m_gclndStart.add( Calendar.DAY_OF_MONTH, -1);
        }
        
        if( m_gclndStop == null) {
            m_gclndStop = new GregorianCalendar();
            m_gclndStop.setTime( new Date( System.currentTimeMillis() - 1000 * 60 * 60 * m_nTimeZoneShift));
        }
        
        return bResOk;
    }
    
    /**
     * Функция сохранения настроек в .xml файл
     */
    public void SaveSettings() {
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement( "Settings" );
            
            
            root.addElement( "nGraphs").addText( "" + m_nGraphs);
            
            root.addElement( "Graph1ViewParam").addText( "" + m_nGraph1ViewParam);
            root.addElement( "Graph2ViewParam").addText( "" + m_nGraph2ViewParam);
            root.addElement( "Graph3ViewParam").addText( "" + m_nGraph3ViewParam);
            root.addElement( "Graph4ViewParam").addText( "" + m_nGraph4ViewParam);
            
            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy.MM.dd HH:mm:ss");
            root.addElement( "gclndrStart").addText( formatter.format( m_gclndStart.getTime()));
            root.addElement( "gclndrStop").addText( formatter.format( m_gclndStop.getTime()));
            
            root.addElement( "NaN.Processing").addText( "" + m_nNaNProcessing);
            root.addElement( "NaN.Processing.replace").addText( String.format( "%.03f", m_dblNaNProcessingReplacement).replace( ',', '.'));
                
            OutputFormat format = OutputFormat.createPrettyPrint();
            
            String strSettingsFilePathName = System.getenv( "AMS_ROOT") + File.separator +
                                                    "etc" + File.separator +
                                                    "settings.arcviewer.rw.xml";
            
            XMLWriter writer = new XMLWriter( new FileWriter( strSettingsFilePathName), format);
            
            writer.write( document );
            writer.close();
        } catch (IOException ex) {
            logger.error( "IOException caught while saving settings!", ex);
        }
    }
}

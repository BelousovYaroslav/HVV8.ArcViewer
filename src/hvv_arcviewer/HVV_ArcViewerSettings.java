/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hvv_arcviewer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author yaroslav
 */
public class HVV_ArcViewerSettings {
    static Logger logger = Logger.getLogger( HVV_ArcViewerSettings.class);
    
    private int m_nSingleInstanceSocketServerPort;
    public int GetSingleInstanceSocketServerPort() { return m_nSingleInstanceSocketServerPort;}
    
    public HVV_ArcViewerSettings( String strAMSRoot) {
        m_nSingleInstanceSocketServerPort = 10005;
        
        ReadSettings();
    }
    
    private boolean ReadSettings() {
        boolean bResOk = true;
        try {
            SAXReader reader = new SAXReader();
            
            String strSettingsFilePathName = System.getenv( "AMS_ROOT") + "/etc/settings.arcviewer.xml";
            URL url = ( new java.io.File( strSettingsFilePathName)).toURI().toURL();
            
            Document document = reader.read( url);
            
            Element root = document.getRootElement();

            // iterate through child elements of root
            for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element element = ( Element) i.next();
                String name = element.getName();
                String value = element.getText();
                
                //logger.debug( "Pairs: [" + name + " : " + value + "]");
                
                if( "singleInstancePort".equals( name)) m_nSingleInstanceSocketServerPort = Integer.parseInt( value);
                
                //if( "timezone".equals( name)) m_nTimeZoneShift = Integer.parseInt( value);
            }
            
        } catch( MalformedURLException ex) {
            logger.error( "MalformedURLException caught while loading settings!", ex);
            bResOk = false;
        } catch( DocumentException ex) {
            logger.error( "DocumentException caught while loading settings!", ex);
            bResOk = false;
        }
        
        return bResOk;
    }
}

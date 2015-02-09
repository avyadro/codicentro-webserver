/*
 * @author: Alexander Villalobos Yadró
 * @user: avillalobos
 * @email: avyadro@yahoo.com.mx
 * @created: Jun 11, 2014 at 11:20:09 AM
 * @place: Ciudad de México, México
 * @company: Planet Media México
 * @web: http://www.planetmedia.com.mx
 * @className: Webserver.java
 * @purpose:
 * Revisions:
 * Ver        Date               Author                                      Description
 * ---------  ---------------  -----------------------------------  ------------------------------------
 **/
package net.codicentro.web.server;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

public class Webserver {

    /**
     * Set your PATH to your WebServer target directory ->
     * pathToWebServer/target. Either by adding to the system enviroment Ej.
     * #adding to ~/.bash_profile export WEBSERVER_HOME = pathToWebServer/target
     *
     * Or modifyng the property WEBSERVER_HOME = "pathToWebServer/target".
     */
    private static final String WEBSERVER_HOME = "pathToWebServer/target";
    // private static final Logger logger = LoggerFactory.getLogger("/home/data/workspace/codicentro-webserver/target/conf/log4j.properties");
    
    /**
     * Path to Jetty Config. It can be jetty8-http.xml or jetty9-http.xml
     */
    private static final String JETTY_CONF_VERSION ="/conf/jetty8-http.xml";
    
    // private static final Logger logger = LoggerFactory.getLogger("/home/data/workspace/codicentro-webserver/target/conf/log4j.properties");
    /**
     * The main method
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        for (String property : System.getProperties().stringPropertyNames()) {
//            System.out.println(property + "=" + System.getProperty(property));
//        }

        //System.out.println("JAVA_HOME=" + System.getenv("JAVA_HOME"));
        String WEBSERVER_HOME = System.getenv("WEBSERVER_HOME");
        WEBSERVER_HOME = StringUtils.isBlank(WEBSERVER_HOME) ? Webserver.WEBSERVER_HOME : WEBSERVER_HOME;
        System.out.println("WEBSERVER_HOME=" + WEBSERVER_HOME);

        Resource resource = Resource.newResource(WEBSERVER_HOME + Webserver.JETTY_CONF_VERSION);
        XmlConfiguration config = new XmlConfiguration(resource.getInputStream());
        Server server = (Server) config.configure();
        HandlerList handlers = new HandlerList();
        for (String arg : args) {
            resource = Resource.newResource(WEBSERVER_HOME + "/conf/jetty-web-" + arg + ".xml");
            config = new XmlConfiguration(resource.getInputStream());
            WebAppContext wac = (WebAppContext) config.configure();
            wac.setContextPath("/" + arg);
            wac.setResourceAlias("/WEB-INF/classes/", "/classes/");
            wac.setWelcomeFiles(new String[]{"mvc/index", "index.jsp", "index.html"});
            handlers.addHandler(wac);
        }
        server.setHandler(handlers);
        server.setStopAtShutdown(true);
        server.start();
        server.join();
    }
}

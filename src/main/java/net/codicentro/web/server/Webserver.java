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

import java.nio.file.Path;
import java.nio.file.Paths;
import net.codicentro.core.TypeCast;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Webserver {

    // private static final Logger logger = LoggerFactory.getLogger("/home/data/workspace/codicentro-webserver/target/conf/log4j.properties");
    public static void main(String[] args) throws Exception {
//        for (String property : System.getProperties().stringPropertyNames()) {
//            System.out.println(property + "=" + System.getProperty(property));
//        }

        System.out.println("JAVA_HOME=" + System.getenv("JAVA_HOME"));
//        String WEBSERVER_HOME = TypeCast.isBlank(System.getenv("WEBSERVER_HOME")) ? System.getProperty("java.class.path") : System.getenv("WEBSERVER_HOME");
        String WEBSERVER_HOME = System.getenv("WEBSERVER_HOME");
        System.out.println("WEBSERVER_HOME=" + WEBSERVER_HOME);

        Resource resource = Resource.newResource(WEBSERVER_HOME + "/conf/jetty8-http.xml");
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

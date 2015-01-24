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

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

public class Webserver {

    public static void main(String[] args) throws Exception {
        Resource resource = Resource.newSystemResource("/jetty8-http.xml");
        XmlConfiguration config = new XmlConfiguration(resource.getInputStream());
        Server server = (Server) config.configure();
        HandlerList handlers = new HandlerList();
        for (String arg : args) {
            resource = Resource.newSystemResource("/jetty-web-" + arg + ".xml");
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

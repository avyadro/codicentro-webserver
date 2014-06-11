/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.codicentro.web.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

/**
 *
 * @author avillalobos
 */
public class Webserver {

    public static void main(String[] args) throws Exception {
        Resource resource = Resource.newSystemResource("/jetty-http.xml");
        XmlConfiguration config = new XmlConfiguration(resource.getInputStream());
        Server server = (Server) config.configure();
        resource = Resource.newSystemResource("/jetty-web-" + args[0] + ".xml");
        config = new XmlConfiguration(resource.getInputStream());
        WebAppContext wac = (WebAppContext) config.configure();
        wac.setContextPath("/" + args[0]);
        wac.setResourceAlias("/WEB-INF/classes/", "/classes/");
        server.setHandler(wac);
        server.setStopAtShutdown(true);
        server.start();
        server.join();
    }
}

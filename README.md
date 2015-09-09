# Downloding #

git clone https://code.google.com/p/codicentro-webserver/


# Configuring #

This is a maven project.

Go to resources folder.
Copy and Paste jetty-web-template.xml and change the name of the file with your appName.
EJ.

**jetty-web-myApp.xml**

Edit this file setting where your .war file is. (.war of the app which will be deploy)


&lt;Set name="war"&gt;

/path/name.war

&lt;/Set&gt;



Set your PATH to your `WebServer` target directory -> pathToWebServer/target.
Either by adding to the system enviroment Ej. <br />
add to ~/.bash\_profile <br />
**export WEBSERVER\_HOME = pathToWebServer/target** <br />

Or modify the property  in `net.codicentro.web.server.WebServer.java class` <br />
**WEBSERVER\_HOME = "pathToWebServer/target"**. <br />

# Compiling #

`mvn clean package`

# Running #

`java -Dwebserver.home=pathToWebServer/target -jar pathToWebServer/target/webserver-1.0.2-SNAPSHOT.jar myApp`

**Note**: myApp is the name on jetty-web-**myApp**.xml

Go to your browser **localhost:8001/myApp**






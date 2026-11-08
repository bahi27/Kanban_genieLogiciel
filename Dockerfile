FROM jetty:11-jre17
COPY projest-app/target/projest-app.war /var/lib/jetty/webapps/ROOT.war

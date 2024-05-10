FROM tomcat:latest

WORKDIR /usr/local/tomcat

COPY ./build/libs/tienda.war ./webapps/ROOT.war
COPY ./ConfiguracionTomcat/server.xml ./conf/server.xml
COPY wait-for-it.sh wait-for-it.sh

RUN chmod +x wait-for-it.sh

EXPOSE 80
EXPOSE 8080

CMD ["catalina.sh", "run"]
FROM tomcat:latest

COPY ./build/libs /usr/local/tomcat/webapps

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
CMD ["catalina.sh", "run"]
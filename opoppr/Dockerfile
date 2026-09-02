FROM maven:3.9.12-eclipse-temurin-25 AS build
WORKDIR /tmp
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ src/
RUN mvn -q package

FROM tomcat:11-jdk25
#RUN apt-get update && apt-get install -y default-mysql-client zip sendmail sendmail-cf m4
RUN apt-get update && apt-get install -y default-mysql-client zip
COPY opoppr-scripts/ /opoppr-scripts/
COPY entrypoint.sh /entrypoint.sh

RUN rm -rf $CATALINA_HOME/webapps/ROOT/
COPY configs/container.xml $CATALINA_HOME/conf/Catalina/localhost/ROOT.xml
COPY --from=build /tmp/target/opoppr-2.0-SNAPSHOT.war $CATALINA_HOME/webapps/ROOT.war
EXPOSE 8080
ENTRYPOINT [ "/entrypoint.sh" ]
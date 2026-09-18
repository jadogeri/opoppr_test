# FROM maven:3.9.12-eclipse-temurin-25 AS build
# WORKDIR /tmp
# COPY pom.xml .
# RUN mvn dependency:go-offline
# COPY src/ src/
# RUN mvn -q package

# FROM tomcat:11-jdk25
# #RUN apt-get update && apt-get install -y default-mysql-client zip sendmail sendmail-cf m4
# RUN apt-get update && apt-get install -y default-mysql-client zip
# COPY opoppr-scripts/ /opoppr-scripts/
# COPY entrypoint.sh /entrypoint.sh

# RUN rm -rf $CATALINA_HOME/webapps/ROOT/
# COPY configs/container.xml $CATALINA_HOME/conf/Catalina/localhost/ROOT.xml
# COPY --from=build /tmp/target/opoppr-2.0-SNAPSHOT.war $CATALINA_HOME/webapps/ROOT.war
# EXPOSE 8080
# ENTRYPOINT [ "/entrypoint.sh" ]
FROM maven:3.9.12-eclipse-temurin-25 AS build
WORKDIR /tmp
COPY pom.xml .

# 1. Force the offline plugin down directly using global HTTP fallback mirroring
RUN mvn org.apache.maven.plugins:maven-dependency-plugin:3.6.1:go-offline \
    -Dmaven.resolver.transport=wagon \
    -Dmaven.wagon.http.ssl.insecure=true \
    -Dmaven.wagon.http.ssl.allowall=true \
    -Dmaven.wagon.http.ssl.ignore.validity.dates=true \
    -Dmaven.repo.local=/root/.m2/repository \
    -Dmaven.mirror.all=http://apache.org

COPY src/ src/

# 2. Use the exact same bypass mirror flags for the application package phase
RUN mvn -q package \
    -Dmaven.resolver.transport=wagon \
    -Dmaven.wagon.http.ssl.insecure=true \
    -Dmaven.wagon.http.ssl.allowall=true \
    -Dmaven.wagon.http.ssl.ignore.validity.dates=true \
    -Dmaven.mirror.all=http://apache.org

FROM tomcat:11-jdk25
RUN apt-get update && apt-get install -y default-mysql-client zip
COPY opoppr-scripts/ /opoppr-scripts/
COPY entrypoint.sh /entrypoint.sh

RUN rm -rf $CATALINA_HOME/webapps/ROOT/
COPY configs/container.xml $CATALINA_HOME/conf/Catalina/localhost/ROOT.xml
COPY --from=build /tmp/target/opoppr-2.0-SNAPSHOT.war $CATALINA_HOME/webapps/ROOT.war
EXPOSE 8080
ENTRYPOINT [ "/entrypoint.sh" ]

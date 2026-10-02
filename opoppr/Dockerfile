FROM maven:3.9.12-eclipse-temurin-25 AS build
WORKDIR /tmp

# Force Maven globally to bypass SSL constraints and force legacy Wagon if required
ENV MAVEN_OPTS="-Dmaven.resolver.transport=wagon \
                -Dmaven.wagon.http.ssl.insecure=true \
                -Dmaven.wagon.http.ssl.allowall=true \
                -Dmaven.wagon.http.ssl.ignore.validity.dates=true \
                -Dmaven.resolver.transport.http.sslInsecure=true \
                -Dmaven.resolver.transport.http.sslAllowAll=true"

# Copy only the pom.xml first to take advantage of Docker layer caching
COPY pom.xml .

# Download dependencies properly (flags are automatically picked up from MAVEN_OPTS)
RUN mvn dependency:go-offline

# Copy the source code after dependencies are cached
COPY src/ src/

# Build the application using the cached offline dependencies
RUN mvn -q package -o

# Final runtime stage
FROM tomcat:11-jdk25

# Combine apt-get update and install, then clean up to reduce image size
RUN apt-get update && apt-get install -y --no-install-recommends \
    default-mysql-client \
    zip \
    && rm -rf /var/lib/apt/lists/*

COPY opoppr-scripts/ /opoppr-scripts/
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Deploy application to Tomcat
RUN rm -rf $CATALINA_HOME/webapps/ROOT/
COPY configs/container.xml $CATALINA_HOME/conf/Catalina/localhost/ROOT.xml
COPY --from=build /tmp/target/opoppr-2.0-SNAPSHOT.war $CATALINA_HOME/webapps/ROOT.war

EXPOSE 8080
ENTRYPOINT [ "/entrypoint.sh" ]

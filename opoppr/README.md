# OPOPPR
This folder contains the OPOPPR application. The application has only been certified to run on apache tomcat 9.x server on JDK 17 and MySQL 8.x. Since these platforms are dated, a docker configuration is included to more easily stand up a running environment. The Dockerfile implements a multi-stage build which will compile and package the war file before building the opoppr image.

## Locally running OPOPPR
The docker-compose.yml defines two services; opoppr and db. opoppr is the application and db is the databse. The compose file also defines a volume on which the database will be held so that data can survive the db container's termination. Starting up the stack for the first time or after making a change to pom.xml will take longer since all of the dependencies are downloaded from maven.

The application is accessed at http://localhost:8080. Enter OPAADMIN/123456 at the Tax Bill Number and PIN prompts to access the Administration menu. Use the **Initialize Database** option to load data from a Master File provided by the Orleans Parish Assessors office. Look in the ACCOUNT table for other Tax Bill Number/PIN combinations to log in as a Tax Preprarer.

### Running OPOPPR
To run OPOPPR in the background.

``` bash
docker compose up -d
```

### Viewing logs
To view the OPOPOR application logs.

```bash
docker compose logs -f opoppr
```

To view the OPOPPR database logs.
```bash
docker compose logs -f db
```

### Stopping OPOPPR
To stop OPOPPR without deleting the containers.

``` bash
docker compose stop
```

### Removing OPOPPR
To remove the OPOPPR app and database containers. The opoppr and mysql images will not be deleted.

``` bash
docker compose down
```

To remove the OPOPPR app and database containers as well as the database volume.

```bash
docker compose down -v
```

### Rebuilding a service
To rebuild a service.

```bash
docker compose build <service-name>
```

where <service-name> is either opoppr or db. Use this command to rebuild and application container (opoppr) whenver you make a change to the code.

## Building OPOPPR for Elastic Beanstalk
To deploy OPOPPR to AWS Elastic Beanstalk, a zip file is built which contains all of the sourcecode and configuration files needed to build and deploy the application in a Docker container that runs in an AWS EC2 instance. Use the following command to create the zip file.

```bash
./package.sh <version-string>
```

where <version-string> is a unique version number that ElasticBeanstalk uses to uniquely identify versions of the application.

The application is deployed using the Elastic Beanstalk Upload function in AWS Console.

TODO: step by step.
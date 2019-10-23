# simpleague
- Clone this repository.
- I chose to download and install NetBeans and Glassfish (see [Java EE 7 Jakarta EE 8 Upgrade](https://github.com/davidmontaine/simpleague/wiki/Java-EE-7---Jakarta-EE-8-Upgrade)) therefore, domain.xml can be used.

  Server is configured to start in Debug Mode.  Set java-config attribute debug-enabled to false to turn off.

  SMTP needs to be configured (see mail-resource).
  
  If you don't run server in Docker container, change database host from host.docker.internal to localhost (or appropriate host name).
- mail.properties toAddressTesting needs to be set.
- Integration tests are configured to run.  To turn off, set maven-failsafe-plugin attribute skipITs to false in pom.xml.
- I chose MySQL as the database (actually MariaDB).

  Run src\db\user.sql first followed by league.sql.
- To run server in Docker container, run build-run.cmd in project folder or open file to see and run docker commands manually.  

  RUN asadmin in Dockerfile enables Glassfish Administration Console to run.  This is really not necessary as domain.xml supplied (and this prolongs startup time) and can be commented out.
  
  Database does not yet run in a Docker container therefore its host is set to host.docker.internal.
- In src\loadtest is simpleague.jmx to run JMeter load tests.

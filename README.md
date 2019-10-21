# simpleague
- Clone this repository.
- I chose to download and install NetBeans and Glassfish (see [Java EE 7 Jakarta EE 8 Upgrade](https://github.com/davidmontaine/simpleague/wiki/Java-EE-7---Jakarta-EE-8-Upgrade)) therefore, domain.xml can be used.

  Server is configured to start in Debug Mode.  Set java-config attribute debug-enabled to false to turn off.

  SMTP needs to be configured (mail-resource).

- mail.properties toAddressTesting needs to be set.
- Integration tests are configured to run.  To turn off, set maven-failsafe-plugin attribute skipITs to false in pom.xml.
- To run server in Docker container, run build-run.cmd in project folder or open file and run docker commands manually.
- I chose MySQL as the database (actually MariaDB).

  Run src/db/user.sql first followed by league.sql.

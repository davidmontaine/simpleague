# simpleague
- Clone this repository.
- I chose to download and install NetBeans and Glassfish therefore, domain.xml can be used.

Server is configured to start in Debug Mode.  Set java-config attribute debug-enabled to false to turn off.

SMTP needs to be configured (mail-resource).

- mail.properties toAddressTesting needs to be set.
- Integration tests are configured to run.  To turn off, set maven-failsafe-plugin attribute skipITs to false in pom.xml.

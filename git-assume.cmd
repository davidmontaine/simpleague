SET action=no-assume-unchanged

IF "%1"=="true" (
    SET action=assume-unchanged
)
git update-index --%action% build-run.cmd
git update-index --%action% cacerts.jks
git update-index --%action% Dockerfile
git update-index --%action% domain.xml
git update-index --%action% git-assume.cmd
git update-index --%action% src\main\resources\mail.properties
git update-index --%action% pom.xml

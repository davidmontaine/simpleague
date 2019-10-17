FROM airhacks/glassfish
ENV DOMAIN_DIR ${GLASSFISH_HOME}/domains/domain1
RUN curl -o ${DOMAIN_DIR}/lib/ext/mariadb-java-client-2.4.1.jar -L https://downloads.mariadb.com/Connectors/java/connector-java-2.4.1/mariadb-java-client-2.4.1.jar
COPY domain.xml ${DOMAIN_DIR}/config
RUN asadmin --user=admin stop-domain && \
    echo "AS_ADMIN_PASSWORD=" > /tmp/glassfishpwd && \
    echo "AS_ADMIN_NEWPASSWORD=admin123" >> /tmp/glassfishpwd  && \
    asadmin --user=admin --passwordfile=/tmp/glassfishpwd change-admin-password && \
    asadmin start-domain && \
    echo "AS_ADMIN_PASSWORD=admin123" > /tmp/glassfishpwd && \
    asadmin --user=admin --passwordfile=/tmp/glassfishpwd enable-secure-admin && \
    asadmin --user=admin stop-domain && \
    rm /tmp/glassfishpwd
COPY ./target/simpleague.war ${DEPLOYMENT_DIR}
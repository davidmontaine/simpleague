::docker image ls
::docker image rm -f $(docker image ls -aq)
::docker container ls -a
::docker container rm -f $(docker container ls -aq)
::docker container logs simpleague
::docker container inspect simpleague
::docker exec -it simpleague cat /opt/glassfish5/glassfish/domains/domain1/config/domain.xml
::docker exec -it simpleague ls /opt/glassfish5/glassfish/domains/domain1/autodeploy -lsa

::docker network create -d bridge --subnet 192.168.0.0/24 --gateway 192.168.0.1 mynet
::docker network ls
::docker network rm mynet

docker container rm -f simpleague
call mvn clean package
docker image build -t simpleague .
docker container run -d --name simpleague -p 8080:8080 -p 4848:4848 -p 9009:9009 -e "TZ=America/New_York" simpleague

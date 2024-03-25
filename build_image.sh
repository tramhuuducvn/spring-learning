docker stop spring_app_ctn
docker rm spring_app_ctn
docker rmi spring_app

mvn clean package -DskipTests

docker build -t spring_app .
docker run --name spring_app_ctn -p 8080:8080 spring_app

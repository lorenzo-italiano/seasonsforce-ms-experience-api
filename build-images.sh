mvn clean install

mv target/seasonsforce-ms-experience-api-1.0-SNAPSHOT.jar api-image/seasonsforce-ms-experience-api-1.0-SNAPSHOT.jar

cd api-image

docker build -t experience-api .

cd ../postgres-image

docker build -t experience-db .
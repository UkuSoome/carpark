# carpark



### Quickstart guide:

1) Clone the repo
```
git clone https://github.com/UkuSoome/carpark.git
```
2) Go into the directory
```
cd carpark
```
3) Start the application, runs on port 8080 by default, can be changed at /src/main/resources/application.properties
```
./gradlew bootRun or gradlew.bat bootRun
```
4) Run tests
```
./gradlew test or gradlew.bat test
```


### Main endpoints used:

1) Make data.json is a json file in the format 
```
{
  "weight": 351,
  "height": 50
}
```
2) Saves a new car
```
curl -X POST -H 'Content-Type: application/json' -d @data.json localhost:8080/cars
```
3) Gets a list of all cars.
```
curl localhost:8080/cars
```
4) Gets details about a specific car
```
curl localhost:8080/cars/{carId}
```
5) Delete a car
```
curl -X DELETE localhost:8080/cars/{carId}
```

### Database:

Uses embedded H2 database. 

1) Access at localhost:8080/h2console
```
url = jdbc:h2:mem:carparkdb
username = sa
password = Empty
```
2) Tables are created by /src/main/resources/schema-lab.sql file.
3) Initial parking floors table data is created by /src/main/resources/data-lab.sql file.

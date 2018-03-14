#### Json Feed Aggregator ####

Developed using Jdk 1.8, Spring Boot and embedded MongoDb. 

Available endpoints:

#### http://localhost:8080/news ####
`GET` Return all the news from the remote resources.

#### http://localhost:8080/news/{source} ####

`GET` Return all the news from the remote resources filtered by `{source}`.

Avaiable sources are:

* Hacker;
* NyTimes;
* Bbc.
	 
#### http://localhost:8080/save ####

`GET` Store the news into MongoDb.

#### http://localhost:8080/list ####

`GET` Retrieve all the news from MongoDb.

#### http://localhost:8080/list/{word} ####

`GET` Retrieve the news filtered by {word} from MongoDb.

#### Compile and Running the application ####
Compile: mvn clean install

Running: java -jar feednews-0.0.1-SNAPSHOT.jar

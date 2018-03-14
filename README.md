#### Json Feed Aggregator ####

Developed using Jdk 1.8, Spring Boot and embedded MongoDb. 

Available endpoints:

host
#### /news ####
`GET` Return all the news from the remote resources.

[/news/{source}]
====
`GET` Return all the news from the remote resources filtered by `{source}`.
Avaiable sources are:
	* Hacker;
	* NyTimes;
	* Bbc.
	 
==== 
[/save]
====
`GET` Store the news feed into MongoDb.
==== 
[/list]
====
`GET` Retrieve the news feed from MongoDb.
==== 

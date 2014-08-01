vndsservice
===========

* Require
	MasterSlave library. https://github.com/hunghoang/masterslave

* You need maven to build this project
	To build project without test

		mvn clean install -DskipTests

	To build project with test, you must start zookeeper (default address: 127.0.0.1:2181) and restful service:
	
		mvn clean install
		
* Guide

	Check 
	
		host:port/vndsService/rest/hello/masterorslave 

	to see if it's a master or slave.


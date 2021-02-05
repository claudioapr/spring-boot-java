
##Task Plan Generator Using Springboot REST

Plan Generator
In order to inform borrowers about the final repayment schedule, we need to have pre-calculated repayment plans throughout the lifetime of a loan.

## DEV Considerations

For developing the application was chose springboot due the rapidly develop that it allows.


## run    

For running the application is necessary to have:
- Maven installed and configured in the PATH VARIABLE(or replace the command to use the mvn location).
- Java 8 or above

Note: it was tested using linux ubuntu terminal, windows 10 PRO using git bash(git terminal), and macos(using apple terminal) 


#### running test through maven 

	mvn clean test



#### running the server  
	
	sh ./run-server.sh 

That will bring up the webservice in the port 8080 and you can test it using any  tool such as postman or by running curl command. 

The endpoint available is :
	
	http://localhost:8080/generate-plan

Note: if the port 8080 is already in use in your OS please edit the file sh ./run-server.sh replacing the valuer of the arg

	server.port=8080 

for example

	server.port=40000 

remember to replace as well in the URLs and in the test script(api-test-curl.sh) mentioned bellow if you are using it 	
	
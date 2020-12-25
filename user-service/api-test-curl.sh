#!/bin/sh
###############################
##Claudio Resende 30/07/2020###
###############################

echo 
echo -e "It will register the user, the response is a json with the registered user"
echo -e "excuting POST http://localhost:8080/userservice/register "
echo -e "reponse:"
curl -s \
	-d '{"firstName": "Test Name","lastName": "Test Last Name","userName": "admin","password": "admin1234"}' \
	-H "Content-Type: application/json" \
	-X POST http://localhost:8080/userservice/register
echo
echo
echo -e "It will try to register the same user again, the response is a json informing the user already exists "
echo -e "excuting POST http://localhost:8080/userservice/register "
echo -e "reponse:"
curl -s \
	-d '{"firstName": "Test Name","lastName": "Test Last Name","userName": "admin","password": "admin1234"}' \
	-H "Content-Type: application/json" \
	-X POST http://localhost:8080/userservice/register
echo
echo
echo -e "It will login using a existing user with the right password "
echo -e "excuting POST http://localhost:8080/userservice/login "
echo -e "reponse:"
curl -s \
     -d '{"userName": "admin","password": "admin1234"}' \
	 -H "Content-Type: application/json" \
	 -X POST http://localhost:8080/userservice/login
echo
echo
echo -e "It will login using a existing user with the right password "
echo -e "excuting POST http://localhost:8080/userservice/login "
echo -e "reponse:"
curl -s \
     -d '{"userName": "admin","password": "admin123"}' \
	 -H "Content-Type: application/json" \
	 -X POST http://localhost:8080/userservice/login
	 
$SHELL	 
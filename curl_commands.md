**GetAll**

curl -H "Accept:application/json" http://localhost:8080/topjava/rest/meals

**Get**

curl -H "Accept:application/json" http://localhost:8080/topjava/rest/meals/100002

**Between**

curl -H "Accept:application/json" http://localhost:8080/topjava/rest/meals/between?startDate=2015-05-30&startTime=8:00:00&endDate=2015-05-30&endTime=19:00:00

**Delete**

curl -X DELETE http://localhost:8080/topjava/rest/meals/100006

**Create**

curl -X POST -H "Content-Type:application/json;Content-Encoding:UTF-8" http://localhost:8080/topjava/rest/meals -d '{"dateTime":"2015-05-30T15:00:00","description":"второй завтрак","calories":5000}'

**Update**

curl -X PUT -H "Content-Type:application/json;Content-Encoding:UTF-8" -d '{"id":100002, "dateTime":"2015-05-30T19:00:00","description":"ужин","calories":400}' http://localhost:8080/topjava/rest/meals/100002
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" type="text/css" href="meal.css">
</head>
<body>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form action="meals" method="POST" accept-charset="UTF-8">
    <input type="hidden" name="id" value="${meal.id}">
    <input type="text" name="description" value="${meal.description}"/>
    <input type="text" name="calories" value="${meal.calories}"/>
    <input id="date" type="datetime-local" name="date" value="${meal.dateTime}">
    <input type="submit" value="edit" name="ACTION"/>
</form>

</body>
</html>
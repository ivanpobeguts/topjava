<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
    <table style="width:100%">
        <tr>
            <th>Description</th>
            <th>Calories</th>
            <th>Time</th>
        </tr>
    <c:forEach items="${requestScope.mealList}" var="meal">
        <tr>
            <td><c:out value="${meal.getDescription()}"/></td>
            <td><c:out value="${meal.getCalories()}"/></td>
            <td><c:out value="${meal.getDateTime()}"/></td>
        </tr>
    </c:forEach>
    </table>

</body>
</html>

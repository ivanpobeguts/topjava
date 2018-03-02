<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" type="text/css" href="meal.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
    <table id="meals">
        <tr>
            <th>Description</th>
            <th>Calories</th>
            <th>Time</th>
        </tr>
    <c:forEach items="${requestScope.mealList}" var="meal">
        <c:choose>
            <c:when test="${not meal.isExceed()}">
                <tr bgcolor="#7EE25D">
                    <td><c:out value="${meal.getDescription()}"/></td>
                    <td><c:out value="${meal.getCalories()}"/></td>
                    <td><c:out value="${meal.getDateTime().format(requestScope.formatter)}"/></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr bgcolor="#F45B5B">
                    <td><c:out value="${meal.getDescription()}"/></td>
                    <td><c:out value="${meal.getCalories()}"/></td>
                    <td><c:out value="${meal.getDateTime().format(requestScope.formatter)}"/></td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    </table>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta keywords="!!keywords!!">
    <meta author="Rostislav Pekhovsky">

    <title>RS&T</title>
    
    <link rel="stylesheet" href="${context}/static/css/header.css"/>
    <link rel="stylesheet" href="${context}/static/css/block.css"/>
    <link rel="stylesheet" href="${context}/static/css/bottom.css"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="block">
    <div class="sign">
        <br/>
        <h2>Start Page</h2>
        <br/>
    </div>
</div>
<jsp:include page="bottom.jsp"/>
</body>
</html>
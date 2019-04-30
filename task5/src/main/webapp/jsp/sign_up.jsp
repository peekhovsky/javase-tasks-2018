<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html>
<head>
    <title>RS&T</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
    </script>



    <link rel="stylesheet" href="${context}/static/css/header.css"/>
    <link rel="stylesheet" href="${context}/static/css/block.css"/>
    <link rel="stylesheet" href="${context}/static/css/bottom.css"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="block">
    <div class="sign">
        <form action="sign_up" method="post">
            <br/>
            <p>Enter your personal data into fields:</p>
            <input type="text" placeholder="First name" name="first_name"
                   id="first_name"/>
            <input type="text" placeholder="Last name" name="last_name"
                   id="last_name"/>
            <input type="text" placeholder="Login" name="login" id="login"/>
            <label>
                <input type="password" placeholder="Password"
                       name="password" id="password"/>
            </label>
            <label>
                <div class="message" id="message"></div>
                <input type="password" placeholder="Repeat password"
                       name="confirm_password" id="confirm_password"/>
            </label>

            <label>Select a photo: </label>
            <input type="file" multiple accept="image/*">
            <input type="submit" name="submit" value="Sign up">
        </form>
        <br/>
        <br/>
    </div>
</div>

<jsp:include page="bottom.jsp"/>
</body>

<script src="${context}/static/js/sign-up.js"></script>
</html>

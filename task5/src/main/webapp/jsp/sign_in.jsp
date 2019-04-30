<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="text"/>

<!DOCTYPE html>
<html>
<head>

  <meta charset="UTF-8">
  <title><fmt:message key="sign_in.title"/></title>
  <link rel="stylesheet" href="${context}/static/css/header.css"/>
  <link rel="stylesheet" href="${context}/static/css/block.css"/>
  <link rel="stylesheet" href="${context}/static/css/bottom.css"/>

  <script>
      let errorMessage = "${requestScope.errorMessage}";
  </script>

</head>
<body>

<jsp:include page="header.jsp"/>
<div class="block">
  <div class="sign">
    <p><fmt:message key="sign_in.inv_to_sign_in"/></p>
    <h5 id="error-message"></h5>
    <form action="" method="post">
      <label>
        <input type="text" placeholder=
        <fmt:message key="sign_in.label.username"/> name="login" id="login"/>
      </label>
      <br/>
      <label>
        <input type="password" placeholder=
        <fmt:message key="sign_in.label.password"/> name="password"
               id="password"/>
      </label>
      <button type="submit"><fmt:message key="sign_in.label.sign_in_button"/></button>
      <br/>
    </form>
    <br/>
    <p><fmt:message key="sign_in.inv_to_reg"/></p>
    <form action="${context}/sign_up" method="get">
      <button type="submit"><fmt:message key="sign_in.label.sign_up_button"/></button>
    </form>
    <br/>
    <br/>
    <br/>
    <br/>
  </div>
</div>
<jsp:include page="bottom.jsp"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="${context}/static/js/sign-in.js"></script>

</body>
</html>

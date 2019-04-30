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
  <title><fmt:message key="sign_in.title"/> | <fmt:message key="app.name"/></title>
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
  <div class="admin-block">
    <p><fmt:message key="date.checkout_message"/></p>
    <form action="date" method="post">
      <input type="hidden" name="action" value="CHARGE">
      <button type="submit" id="checkout-button"><fmt:message key="date.checkout_button"/></button>
    </form>
  </div>
</div>

<div class="block">
  <div class="admin-block">
    <p><fmt:message key="date.block_message"/></p>
    <form action="date" method="post">
      <input type="hidden" name="action" value="BLOCK">
      <button type="submit" id="block-button"><fmt:message key="date.block_button"/></button>
    </form>
  </div>
</div>

<jsp:include page="bottom.jsp"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="${context}/static/js/sign-in.js"></script>

</body>
</html>

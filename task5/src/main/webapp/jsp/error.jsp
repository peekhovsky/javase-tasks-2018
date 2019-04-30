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
  <link rel="stylesheet" href="${context}/static/css/header.css"/>
  <link rel="stylesheet" href="${context}/static/css/block.css"/>
  <link rel="stylesheet" href="${context}/static/css/bottom.css"/>

  <script>
      let errorCode = "${requestScope.errorCode}";
      let errorMessage = " ";
      <c:if test="${not empty requestScope.errorMessage}">
      errorMessage = "${requestScope.errorMessage}";
      </c:if>
  </script>
</head>

<body>

<jsp:include page="header.jsp"/>

<div class="block">
  <div class="sign">
    <br/>
    <h2 id="error-message"></h2>
    <br/>
  </div>
</div>

<jsp:include page="bottom.jsp"/>

</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="${context}/static/js/error.js" type="text/javascript"></script>
</html>

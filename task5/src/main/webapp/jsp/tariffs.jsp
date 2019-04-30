<%--@elvariable id="user" type="java"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="text"/>

<html>
<head>
  <title>RS&T</title>
  <link rel="stylesheet" href="${context}/static/css/header.css"/>
  <link rel="stylesheet" href="${context}/static/css/block.css"/>
  <link rel="stylesheet" href="${context}/static/css/bottom.css"/>
</head>

<body>

<jsp:include page="header.jsp"/>

<div id="admin-block" class="block">
  <div class="admin-block">
    <a href="${context}/add_tariff">
      <button type="button">
        <fmt:message key="tariff.add_new.title"/>
      </button>
    </a>
  </div>
</div>

<c:forEach items="${requestScope.tariffs}" var="tariff">
  <div class="block">
    <div class="tariff-block">
      <h4>${tariff.name}</h4>
      <p>${tariff.description}</p>
      <c:if test="${sessionScope.user_type == 'ADMIN'}">
        <c:if test="${tariff.status == 'NO_NEW_USERS'}">
          <p class="red">${tariff.status}</p>
        </c:if>
        <c:if test="${tariff.status == 'ACTIVE'}">
          <p class="green">${tariff.status}</p>
        </c:if>
      </c:if>

      <a href="${context}/tariffs?id=${tariff.id}"><fmt:message key="tariff.link.more_info"/></a>
    </div>
  </div>
</c:forEach>

<jsp:include page="bottom.jsp"/>

<script src="${context}/static/js/tariffs.js"></script>
</body>
</html>

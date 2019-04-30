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

<div class="block">
  <div class="discount-block">
    <h4><fmt:message key="discounts.page_name"/></h4>
    <c:forEach items="${requestScope.discounts}" var="discount">
      <div class="grid-discount">
        <p>Tariff: <a href="${context}/tariffs?id=${discount.tariffId}">${discount.tariffName}</a></p>
        <p>Starts: ${discount.startDate}</p>
        <p>Ends: ${discount.finishDate}</p>
        <p>Value: ${discount.percent}%</p>
        <c:if test="${sessionScope.user_type == 'CLIENT'}">
          <form action="discounts" method="post">
            <input type="hidden" name="discountId" value="${discount.id}">
            <input type="hidden" name="userId" value="${sessionScope.user_id}">
            <button type="submit"><fmt:message key="discounts.submit_button"/></button>
          </form>
        </c:if>
      </div>
    </c:forEach>
  </div>
</div>


<jsp:include page="bottom.jsp"/>

<script src="${context}/static/js/tariffs.js"></script>
</body>
</html>

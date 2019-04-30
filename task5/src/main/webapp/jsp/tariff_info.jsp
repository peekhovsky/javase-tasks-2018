<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="user" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="text"/>

<jsp:useBean id="tariff" scope="request" type="by.epam.provider.model.Tariff"/>

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
        <fmt:message key="tariff.info.add_new"/>
      </button>
    </a>
    <c:if test="${tariff.status != 'ACTIVE'}">
      <a href="${context}/block_tariff?id=${tariff.id}&action=UNBLOCK">
        <button type="submit">
          <fmt:message key="tariff.info.unblock"/>
        </button>
      </a>
    </c:if>
    <c:if test="${tariff.status == 'ACTIVE'}">
      <a href="${context}/block_tariff?id=${tariff.id}&action=BLOCK">
        <button type="submit">
          <fmt:message key="tariff.info.block"/>
        </button>
      </a>
    </c:if>
    <a href="${context}/add_discount?id=${tariff.id}">
      <button type="submit">
        <fmt:message key="tariff.info.discount"/>
      </button>
    </a>
  </div>
</div>

<div class="block">
  <div class="tariff-block">
    <h4>${tariff.name}</h4>
    <p>${tariff.description}</p>
    <table>
      <tr>
        <td>
          <label><fmt:message key="tariff.info.free_mb_per_month"/></label>
        </td>
        <td>
          ${tariff.freeMbPerMonth} MB
        </td>
      </tr>
      <tr>
        <td>
          <label><fmt:message key="tariff.info.speed"/></label>
        </td>
        <td>
          ${tariff.speed} MB/s
        </td>
      </tr>
      <tr>
        <td>
          <label><fmt:message key="tariff.info.speed_over_traffic"/></label>
        </td>
        <td>
          ${tariff.speedOverTraffic} MB/s
        </td>
      </tr>
      <tr>
        <td>
          <label><fmt:message key="tariff.info.day_bill"/></label>
        </td>
        <td>
          $ ${tariff.dayBill / 100}
        </td>
      </tr>
      <tr>
        <td>
          <label><fmt:message key="tariff.info.month_bill"/></label>
        </td>
        <td>
          $ ${(tariff.dayBill * 30.0) / 100}
        </td>
      </tr>
    </table>
    <form method="post" action="set_tariff">
      <input type="hidden" name="id" value="${tariff.id}"/>
      <button type="submit"><fmt:message key="tariff.link.apply"/></button>
    </form>
  </div>
</div>

<jsp:include page="bottom.jsp"/>

</body>
</html>

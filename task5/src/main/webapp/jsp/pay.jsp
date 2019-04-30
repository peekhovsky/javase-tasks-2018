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
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="block">
  <div class="pay-form">
    <h4><fmt:message key="pay.form.name"/></h4>
    <img src="${context}/static/img/visa-logo.png" height="50" alt=""/>
    <img src="${context}/static/img/mastercard-logo.jpg" height="50" alt=""/>
    <form id="pay-form" method="post" action="pay">
      <ul>
        <li>
          <input type="number" min="0" id="money" name="money"
                 placeholder="<fmt:message key="pay.form.money"/>">
        </li>
        <li>
          <input type="number" min="0" id="card-number" name="card_number"
                 placeholder="<fmt:message key="pay.form.card_number"/>" disabled>
        </li>
        <li>
          <input type="month" pattern="dd-MM" id="expire-date" name="expire_date"
                 placeholder="<fmt:message key="pay.form.expire_date"/>" disabled>
        </li>
        <li><input type="number" pattern="dd-MM" id="cvv-code" name="cvv_code"
                   placeholder="<fmt:message key="pay.form.cvv_code"/>" disabled>
        </li>
      </ul>
      <button type="submit" id="submit-button"><fmt:message key="pay.form.confirm_button"/></button>
      <button type="button" id="back-button"><fmt:message key="pay.form.back_button"/></button>
    </form>
    <br/>
  </div>
</div>

<jsp:include page="bottom.jsp"/>

<script src="${context}/static/js/pay.js"></script>
</body>
</html>

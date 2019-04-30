<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="text"/>

<jsp:useBean id="tariff" scope="request" type="by.epam.provider.model.Tariff"/>

<!DOCTYPE html>
<html>
<head>
  <title><fmt:message key="discount.add_new.title"/> | <fmt:message key="app.name"/></title>
  <link rel="stylesheet" href="${context}/static/css/header.css"/>
  <link rel="stylesheet" href="${context}/static/css/block.css"/>
  <link rel="stylesheet" href="${context}/static/css/bottom.css"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="block">
  <div class="add-form">
    <div class="add-discount-form">
      <h4><fmt:message key="discount.add_new.name"/></h4>
      <form id="add-discount-form" action="${context}/add_discount" method="post">

        <h5>Tariff: ${tariff.name}</h5>

        <input type="hidden" name="id" value="${tariff.id}">

        <label><fmt:message key="discount.add_new.start_date"/><br/>
          <input type="date" name="start_date" id="start-date"/>
        </label>

        <label><fmt:message key="discount.add_new.finish_date"/><br/>
          <input type="date" name="finish_date" id="finish-date"/>
        </label>
        <label><fmt:message key="discount.add_new.percent"/><br/>
          <input type="number" name="percent" id="percent"
                 min="0.01" max="100"
                 placeholder="<fmt:message key="discount.add_new.percent"/>"/>
        </label>
        <button type="submit">
          <fmt:message key="discount.add_new.submit_button"/>
        </button>

        <button id="back-button" type="button">
          <fmt:message key="discount.add_new.back_button"/>
        </button>

      </form>
    </div>
  </div>
</div>

<jsp:include page="bottom.jsp"/>

</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.js"></script>
<script src="${context}/static/js/add-discount.js"></script>
</html>

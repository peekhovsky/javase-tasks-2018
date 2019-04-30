<%--@elvariable id="user" type="java"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="text"/>

<html>
<head>
  <title><fmt:message key="tariff.add_new.title"/> | <fmt:message key="app.name"/></title>
  <link rel="stylesheet" href="${context}/static/css/header.css"/>
  <link rel="stylesheet" href="${context}/static/css/block.css"/>
  <link rel="stylesheet" href="${context}/static/css/bottom.css"/>
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="block">
  <div class="add-form">
    <form id="add-tariff-form" action="${context}/add_tariff" method="post">
      <h4><fmt:message key="tariff.add_new.title"/></h4>
      <ul>
        <li>
          <input type="text" name="tariff" id="tariff-name"
                 placeholder="<fmt:message key="tariff.form.name"/>"
                 required/>
        </li>
        <li>
          <textarea name="description" id="tariff-desc"
                    placeholder="<fmt:message key="tariff.form.description"/>"
                    required></textarea>
        </li>
        <li>
          <input type="number" name="free_mb_per_month" id="tariff-md-per-month" min="0"
                 placeholder="<fmt:message key="tariff.form.free_mb_per_month"/>"/>
        </li>
        <li>
          <input type="number" name="speed" id="tariff-speed" min="0"
                 placeholder="<fmt:message key="tariff.form.speed"/>"/>
        </li>
        <li>
          <input type="number" name="speed_over_traffic" id="tariff-speed-over" min="0"
                 placeholder="<fmt:message key="tariff.form.speed_over_traffic"/>"/>
        </li>
        <li>
          <input type="number" name="tariff_day_bill" id="tariff-day-bill" min="0"
                 placeholder="<fmt:message key="tariff.form.day_bill"/>"
          />
        </li>
        <li>
          <label><input type="radio" name="status" value="ACTIVE" checked>
            <fmt:message key="tariff.form.status.active"/>
          </label>
          <label><input type="radio" name="status" value="NO_NEW_USERS">
            <fmt:message key="tariff.form.status.no_new_users"/>
          </label>
          <label><input type="radio" name="status" value="DELETED">
            <fmt:message key="tariff.form.status.disabled"/>
          </label>
        </li>
        <li>
          <button name="enter" type="submit"><fmt:message key="tariff.form.button"/></button>
        </li>
      </ul>
    </form>
  </div>
</div>

<jsp:include page="bottom.jsp"/>

</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.js"></script>
<script src="${context}/static/js/add-tariff.js"></script>
</html>

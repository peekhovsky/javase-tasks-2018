<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="text"/>

<jsp:useBean id="user_dto" scope="request" type="by.epam.provider.model.dto.UserDto"/>
<fmt:parseDate value="${user_dto.registrationDate}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>

<!DOCTYPE html>
<html>
<head>
  <title>RS&T</title>
  <link rel="stylesheet" href="${context}/static/css/header.css"/>
  <link rel="stylesheet" href="${context}/static/css/block.css"/>
  <link rel="stylesheet" href="${context}/static/css/bottom.css"/>

  <script>
      let clientUserStatus = "${user_dto.clientUserStatus}";
      let login = "${user_dto.login}";
      let firstName = "${user_dto.firstName}";
      let lastName = "${user_dto.lastName}";
  </script>
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="block">
  <div class="user-status">
    <h4><fmt:message key="user_info.label.balance"/> $<fmt:formatNumber type="number" maxFractionDigits="2"
                                                                        value="${user_dto.balanceInDollars}"/></h4>
    <h4><fmt:message key="user_info.label.status"/> <label id="client-user-status">${user_dto.clientUserStatus}</label>
    </h4>
    <button onclick="location.href='${context}/pay'" type="button">
      <fmt:message key="user_info.button.pay"/>
    </button>
    <button onclick="location.href='${context}/bills?id=${user_dto.id}'" type="button">
      <fmt:message key="user_info.button.bills"/>
    </button>
  </div>
</div>
<div class="block">
  <div class="discount-block">
    <h4><fmt:message key="user_info.label.discounts"/></h4>

    <c:forEach items="${requestScope.discounts}" var="discount">
      <div class="grid-discount">
        <p>Tariff: <a href="${context}/tariffs?id=${user_dto.tariffId}">${user_dto.tariffName}</a></p>
        <p>Starts: ${discount.startDate}</p>
        <p>Ends: ${discount.finishDate}</p>
        <p>Value: ${discount.percent}%</p>
      </div>
    </c:forEach>

  </div>
</div>

<div class="block">
  <div class="user-desc">
    <div class="change-img">
      <h4><fmt:message key="user_info.label.profile_photo"/></h4>
      <form id="change-img-form" action="user_info" method="post" enctype="multipart/form-data">
        <input type="file" name="photo" multiple accept="image/*,image/jpeg" required/>
        <input type="hidden" name="is_photo" value="true"/>
        <input type="hidden" name="id" value="${user_dto.id}"/>
        <button type="submit"><fmt:message key="user_info.label.submit_photo"/></button>
      </form>
    </div>
    <div class="avatar">
      <img src="${context}/static/img/${user_dto.iconReference}" width="200" alt="avatar">
    </div>

    <h4><fmt:message key="user_info.label.profile_info"/></h4>
    <form id="edit-user-form" action="user_info" method="post">
      <ul>
        <li>
          <label>Login:<br/>
            <input type="text" id="login" name="login" value="${user_dto.login}"/>
          </label>
        </li>
        <li>
          <label>First name:<br/>
            <input type="text" id="first-name" name="first_name" value="${user_dto.firstName}"/>
          </label>
        </li>
        <li>
          <label>Last name:<br/>
            <input type="text" id="last-name" name="last_name" value="${user_dto.lastName}"/>
          </label>
        </li>
        <li>
          <label>Registration date: </label><br/>
          <fmt:formatDate value="${parsedDate}" type="date" pattern="yyyy-MM-dd"/>
        </li>
        <li>
          <label>Status: </label><br/>
          ${user_dto.clientUserStatus}
        </li>
        <li>
          <label>Tariff: </label><br/>
          <a href="${context}/tariffs?id=${user_dto.tariffId}">${user_dto.tariffName}</a>
        </li>
        <li>
          <button type="button" id="edit-button">Edit</button>
          <button type="submit" id="submit-button">Save</button>
          <button type="button" id="back-button">Back</button>
        </li>
      </ul>
    </form>
  </div>
</div>
<jsp:include page="bottom.jsp"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/additional-methods.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/additional-methods.min.js"></script>
<script src="${context}/static/js/user-info.js"></script>

</body>
</html>

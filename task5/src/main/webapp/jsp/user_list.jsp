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
      let pageNum = "${requestScope.page_num}";
      let pagesNum = "${requestScope.pages_num}";
  </script>
</head>
<body>
<jsp:include page="header.jsp"/>

<c:forEach items="${requestScope.users}" var="user">
  <div class="block">
    <div class="user-list-desc">
      <h5>${user.login}</h5>
      <p>Type: ${user.type}</p>
      <c:if test="${user.status == 'ACTIVE'}">
        <p class="green">Status: ${user.status}</p>
        <a href="${context}/ban_user?id=${user.id}&action=BAN">
          <button type="submit">
            <fmt:message key="user_list.button.ban"/>
          </button>
        </a>
      </c:if>
      <c:if test="${user.status == 'BANNED'}">
        <p class="red">Status: ${user.status}</p>
        <a href="${context}/ban_user?id=${user.id}&action=UNBAN">
          <button type="submit">
            <fmt:message key="user_list.button.unban"/>
          </button>
        </a>
      </c:if>
      <a href="${context}/user_info?id=${user.id}">
        <button type="submit">
          <fmt:message key="user_list.button.user_info"/>
        </button>
      </a>
    </div>
  </div>
</c:forEach>

<div class="block">
  <div class="page-numbers">
    <c:forEach var="i" begin="${0}" end="${requestScope.pages_num}">
      <a id="page-link${i}" href="${context}/users_list?page_num=${i}">${i + 1}</a>
    </c:forEach>
  </div>
</div>

<jsp:include page="bottom.jsp"/>
<script src="${context}/static/js/user-list.js"></script>
</body>
</html>

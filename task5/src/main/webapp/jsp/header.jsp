<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="text"/>

<c:set var="userType" value="${sessionScope.user_type}"/>
<c:set var="userLogin" value="${sessionScope.user_login}"/>
<c:set var="contentPath" value="${context}"/>

<script>
    let userType = "${sessionScope.user_type}";
    let userLogin = "${sessionScope.user_login}";
    let contentPath = "${context}";
    let signInText = "<fmt:message key="header.block.sign_in"/>";
    let logOutText = "<fmt:message key="header.block.log_out"/>";
    let currentLocation = location.href
</script>

<header>
  <div class="left">
    <h2>RS&T</h2>
  </div>

  <div class="center">
    <h3 id="userLoginLabel"></h3>
  </div>
  <c:if test="${not empty sessionScope.avatar}">
    <div class="header-avatar">
      <img src="${context}/static/img/${sessionScope.avatar}" alt="avatar">
    </div>
  </c:if>
  <div class="right">
    <div class="navigation-signing">
      <ul>
        <li id="li-sign-in-out">
          <a id="a-sign-in-out"></a>
        </li>
      </ul>
    </div>
  </div>
  <div class="language-select">
    <a id="a-en">EN</a>
    <a id="a-ru">RU</a>
  </div>
</header>


<nav id="navigation-menu">
  <ul>
    <li>
      <a id="a-tariff" href="${context}/tariffs">
        <fmt:message key="header.nav.tariff"/>
      </a>
    </li>
    <li>
      <a id="a-discount" href="${context}/discounts">
        <fmt:message key="header.nav.discounts"/>
      </a>
    </li>
    <c:choose>
      <c:when test="${sessionScope.user_type == 'CLIENT'}">
        <li>
          <a id="a-client-info" href="${context}/user_info">
            <fmt:message key="header.nav.client_info"/>
          </a>
        </li>
        <li>
          <a id="a-bills" href="${context}/bills?id=${sessionScope.user_id}">
            <fmt:message key="header.nav.bills"/>
          </a>
        </li>
      </c:when>
      <c:when test="${sessionScope.user_type == 'ADMIN'}">
        <li>
          <a id="a-admin-clients-info" href="${context}/users_list?page_num=0">
            <fmt:message key="header.nav.client_info_admin"/>
          </a>
        </li>
        <li>
          <a id="a-admin-date" href="${context}/date">
            <fmt:message key="header.nav.date"/>
          </a>
        </li>
      </c:when>
    </c:choose>
  </ul>
</nav>

<c:if test="${not empty requestScope.message}">
  <div class="block">
    <div class="message-block">
      <p>${requestScope.message}</p>
    </div>
  </div>
</c:if>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="${context}/static/js/header.js" type="text/javascript"></script>

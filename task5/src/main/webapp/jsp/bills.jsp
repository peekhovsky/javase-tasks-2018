<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

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
  <div class="bills">
    <h4><fmt:message key="bills.page_name"/></h4>
    <table>
      <tr>
        <th class="bills-table-1">
          Date:
        </th>
        <th class="bills-table-2">
          Sum:
        </th>
        <th class="bills-table-3">
          Tariff:
        </th>

      </tr>
      <c:forEach items="${requestScope.bills}" var="bill">
        <tr>
          <td class="bills-table-1">
            <fmt:parseDate value="${bill.date}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both"/>
            <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm"/>
          </td>
          <td class="bills-table-2">
            $<fmt:formatNumber type="number" maxFractionDigits="2"
                               value="${bill.sumInDollars}"/>
          </td>
          <td class="bills-table-3">
            <a href="${context}/tariffs?id=${bill.tariffId}">${bill.tariffName}</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</div>
<div class="bottom">
  <h4>By Rostislav Pekhovsky 2019 ©</h4>
</div>
</body>
</html>


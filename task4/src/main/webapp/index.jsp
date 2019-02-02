<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="medicines" scope="request" type="java.util.List"/>
<html>
<head>
    <style>
        <%@include file="css/style.css" %>
    </style>
    <title>Medicines</title>
</head>
<body>
<div class="head">
    <table align="center" class="blue-table">
        <tr>
            <td>
                <h2 class="head">Parser type: ${parserType}</h2>
            </td>
            <td>
                <form method="get" action="">
                    <label>SAX
                        <c:choose>
                            <c:when test="${parserType == 'sax'}">
                                <input type="radio" name="parserType"
                                       value="sax" checked>
                            </c:when>
                            <c:otherwise>
                                <input type="radio" name="parserType"
                                       value="sax">
                            </c:otherwise>
                        </c:choose>
                    </label><br/>
                    <label>StAX
                        <c:choose>
                            <c:when test="${parserType == 'stax'}">
                                <input type="radio" name="parserType"
                                       value="stax" checked>
                            </c:when>
                            <c:otherwise>
                                <input type="radio" name="parserType"
                                       value="stax">
                            </c:otherwise>
                        </c:choose>
                    </label><br/>
                    <label>DOM
                        <c:choose>
                            <c:when test="${parserType == 'dom'}">
                                <input type="radio" name="parserType"
                                       value="dom" checked>
                            </c:when>
                            <c:otherwise>
                                <input type="radio" name="parserType"
                                       value="dom">
                            </c:otherwise>
                        </c:choose>

                    </label><br/>
                    <input type="submit">
                </form>
            </td>
        </tr>
    </table>
</div>


<div class="main">
    <table align="center" class="blue-table">
        <tr>
            <th>Medicine</th>
            <th>Analogues</th>
            <th>Versions</th>
        </tr>

        <c:forEach items="${medicines}" var="medicine">
            <tr>
                <td>${medicine.id}</td>
                <td>${medicine.name}</td>
                <td>
                    <c:if test="${not empty medicine.analogs.analogList}">
                        <c:forEach items="${medicine.analogs.analogList}"
                                   var="analog">
                            ${analog} <br/>
                        </c:forEach>
                    </c:if>
                </td>
                <td>
                    <ul>
                        <c:forEach items="${medicine.versions.versionList}"
                                   var="version">
                            <li>
                                <h3>${version.type}</h3>

                                <div class="inner">
                                    <table class="blue-table">
                                        <caption>Companies</caption>
                                        <tr>
                                            <th>Name</th>
                                            <th>Certificate number</th>
                                            <th>Issue date</th>
                                            <th>Expire date</th>
                                            <th>Reg organisation</th>
                                        </tr>
                                        <c:forEach
                                                items="${version.companyList}"
                                                var="company">
                                            <tr>
                                                <td>${company.name}</td>
                                                <td>${company.certificate.num}</td>
                                                <td><fmt:formatDate
                                                        value="${company.certificate.issueDate}"
                                                        pattern="yyyy-MM-dd"/></td>
                                                <td><fmt:formatDate
                                                        value="${company.certificate.expireDate}"
                                                        pattern="yyyy-MM-dd"/></td>
                                                <td>${company.certificate.regOrganisation}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </li>

                        </c:forEach>

                    </ul>
                </td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
</html>

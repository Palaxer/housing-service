<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="d" uri="/WEB-INF/date-format.tld"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />
<%@ page isELIgnored="false" contentType="text/html" pageEncoding="UTF-8" language="java" %>

<html>
<head>
    <title><fmt:message key="text.title.view.work-type"/></title>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
<header id="header" class="clear">
    <div id="hgroup">
        <h1><fmt:message key="text.housing-service"/></h1>
    </div>
</header>
<div class="clear"></div>

<div id="container">
    <aside id="left_column">
        <h2 class="title"><fmt:message key="text.tenant"/></h2>
        <nav>
            <ul>
                <li><a href="<c:url value="/?command=tenantbid"/>"><fmt:message key="text.title.view.bid"/></a></li>
                <li><a href="<c:url value="/?command=redirect&to=addbid"/>"><fmt:message key="text.title.add.bid"/></a></li>
            </ul>
        </nav>
        <c:if test="${'ADVISOR' == sessionScope.user.role.roleType ||
                          'ADMIN' == sessionScope.user.role.roleType}">
            <h2 class="title"><fmt:message key="text.advisor"/></h2>
            <nav>
                <ul>
                    <li><a href="<c:url value="/?command=advisorbid&status=all"/>"><fmt:message key="text.title.view.bid"/></a></li>
                    <li><a href="<c:url value="/?command=advisorworkplane"/>"><fmt:message key="text.title.view.work-plane"/></a></li>
                </ul>
            </nav>
        </c:if>
        <c:if test="${'CREW' == sessionScope.user.role.roleType}">
            <h2 class="title"><fmt:message key="text.crew"/></h2>
            <nav>
                <ul>
                    <li><a href="/?command=crewworkplane"><fmt:message key="text.title.view.work-plane"/></a></li>
                    <li><a href="/?command=brigadeinfo"><fmt:message key="text.title.view.brigade-info"/></a></li>
                </ul>
            </nav>
        </c:if>
        <c:if test="${'ADMIN' == sessionScope.user.role.roleType}">
            <h2 class="title"><fmt:message key="text.admin"/></h2>
            <nav>
                <ul>
                    <li><a class="selected" href="/?command=getworktype"><fmt:message key="text.title.view.work-type"/></a></li>
                    <li><a href="/?command=getbrigade"><fmt:message key="text.title.view.brigade"/></a></li>
                    <li><a href="/?command=getalluser"><fmt:message key="text.title.view.all-account"/></a></li>
                </ul>
            </nav>
        </c:if>
        <h2 class="title"><fmt:message key="text.setting"/></h2>
        <nav>
            <ul>
                <li><a href="<c:url value="/?command=redirect&to=profile"/>"><fmt:message key="text.profile"/></a></li>
                <li><a href=<c:url value="/?command=logout"/>><fmt:message key="text.logout"/></a></li>
            </ul>
        </nav>
    </aside>
    <!-- main content -->
    <div id="content">
        <!-- section 1 -->
        <section>
            <!-- article 1 -->
            <article>
                <c:if test="${not empty successUpdate}">
                    <h3 id="success"><fmt:message key="page.message.success-update"/></h3>
                </c:if>
                <c:if test="${not empty invalidData}">
                    <h3><fmt:message key="text.error.invalid-data"/></h3>
                </c:if>
                <h2 class="capitalize"><fmt:message key="text.add-work-type"/></h2>
                <form method="POST" action="<c:url value="/"/>">
                    <input type="hidden" name="command" value="addworktype">
                    <table class="table-fill">
                        <thead>
                        <tr>
                            <th class="text-left"><fmt:message key="text.work-type.name"/></th>
                            <th class="text-left"><fmt:message key="text.operation"/></th>
                        </tr>
                        </thead>
                        <tbody class="table-hover">
                            <tr>
                                    <input type="hidden" name="command" value="updateworktype">
                                    <td class="text-left">
                                        <input type="text" name="typeName" value="${typeName}" placeholder="<fmt:message key="text.work-type.name"/>">
                                    </td>
                                    <td class="text-left">
                                        <input type="submit" value="<fmt:message key="text.button.add"/>">
                                    </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
                </br>
                </br>
                <h2 class="capitalize"><fmt:message key="text.work-types"/></h2>
                <table class="table-fill">
                    <thead>
                    <tr>
                        <th class="text-left"><fmt:message key="text.id"/></th>
                        <th class="text-left"><fmt:message key="text.work-type.name"/></th>
                        <th class="text-left"><fmt:message key="text.operation"/></th>
                    </tr>
                    </thead>
                    <tbody class="table-hover">
                    <c:forEach items="${workTypeList}" var="workType">
                        <tr>
                            <form method="POST" action="<c:url value="/"/>">
                                <input type="hidden" name="command" value="updateworktype">
                                <td class="text-left">${workType.workTypeId}</td>
                                <td class="text-left"><input type="text" name="typeName" value="${workType.typeName}"></td>
                                <td class="text-left">
                                    <input type="hidden" name="index" value="${workType.workTypeId}">
                                    <input type="submit" value="<fmt:message key="text.button.change"/>">
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </article>
        </section>
    </div>
    <div class="clear"></div>
</div>
<!-- footer -->
<footer id="footer">
    <div class="clear"></div>
</footer>
</body>

</html>

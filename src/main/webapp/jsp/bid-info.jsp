<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="d" uri="/WEB-INF/date-format.tld"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />
<%@ page isELIgnored="false" contentType="text/html" pageEncoding="UTF-8" language="java" %>

<html>
<head>
    <title><fmt:message key="text.title.view.bid-info"/></title>
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
                    <li><a href="/?command=getworktype"><fmt:message key="text.title.view.work-type"/></a></li>
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
                <table class="table-fill">
                    <thead>
                    <tr>
                        <th class="text-left"><fmt:message key="text.user.login"/></th>
                        <th class="text-left"><fmt:message key="text.name"/></th>
                        <th class="text-left"><fmt:message key="text.user.street"/></th>
                        <th class="text-left"><fmt:message key="text.user.house-number"/></th>
                        <th class="text-left"><fmt:message key="text.user.apartment"/></th>
                        <th class="text-left"><fmt:message key="text.user.city"/></th>
                        <th class="text-left"><fmt:message key="text.user.phone-number"/></th>
                    </tr>
                    </thead>
                    <tbody class="table-hover">
                        <tr>
                            <td class="text-left">${bid.userTenant.login}</td>
                            <td class="text-left">${bid.userTenant.firstName} ${bid.userTenant.lastName}</td>
                            <td class="text-left">${bid.userTenant.street}</td>
                            <td class="text-left">${bid.userTenant.houseNumber}</td>
                            <td class="text-left">${bid.userTenant.apartment}</td>
                            <td class="text-left">${bid.userTenant.city}</td>
                            <td class="text-left">${bid.userTenant.phoneNumber}</td>
                        </tr>
                    </tbody>
                </table>
                </br>
                <table class="table-fill">
                    <thead>
                    <tr>
                        <th class="text-left"><fmt:message key="text.bid.id"/></th>
                        <th class="text-left"><fmt:message key="text.bid.work-type"/></th>
                        <th class="text-left"><fmt:message key="text.bid.work-scope"/></th>
                        <th class="text-left"><fmt:message key="text.bid.lead-time"/></th>
                        <th class="text-left"><fmt:message key="text.status"/></th>
                        <th class="text-left"><fmt:message key="text.bid.desc"/></th>
                        <th class="text-left"><fmt:message key="text.bid.time"/></th>
                        <c:if test="${'NEW' == bid.status}">
                            <th class="text-left"><fmt:message key="text.operation"/></th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody class="table-hover">
                    <tr>
                        <td class="text-left">${bid.bidId}</td>
                        <td class="text-left">${bid.workType.typeName}</td>
                        <td class="text-left">${bid.workScope}</td>
                        <td class="text-left"><d:dateFrm date="${bid.leadTime}" local="${language}"/></td>
                        <td class="text-left">${bid.status}</td>
                        <td class="text-left">${bid.description}</td>
                        <td class="text-left"><d:dateFrm date="${bid.bidTime}" local="${language}"/></td>
                        <c:if test="${'NEW' == bid.status}">
                            <td class="text-left">
                                <form method="POST" action="<c:url value="/"/>">
                                    <input type="hidden" name="command" value="closeBid">
                                    <input type="hidden" name="index" value="${bid.bidId}">
                                    <input type="submit" value="<fmt:message key="text.button.close"/>">
                                </form>
                            </td>
                        </c:if>
                    </tr>
                    </tbody>
                </table>
                </br>
                <c:if test="${'NEW' == bid.status && (user.role.roleType == 'ADVISOR' || user.role.roleType == 'ADMIN')}">
                    <form method="POST" action="<c:url value="/"/>">
                        <input type="hidden" name="command" value="setbrigade">
                        <input type="hidden" name="index" value="${bid.bidId}">
                        <table class="table-fill">
                            <thead>
                            <tr>
                                <th class="text-left"><fmt:message key="text.brigade.name"/></th>
                                <th class="text-left"><fmt:message key="text.work-plane.work-time"/></th>
                                <th class="text-left"><fmt:message key="text.operation"/></th>
                            </tr>
                            </thead>
                            <tbody class="table-hover">
                            <tr>
                                <td class="text-left">
                                    <select name="brigadeId">
                                        <c:forEach items="${brigadeList}" var="brigade">
                                            <option value="${brigade.brigadeId}">${brigade.brigadeName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td class="text-left">
                                    <input type="datetime-local" lang="${language}" name="leadTime" value="${time}">
                                </td>
                                <td class="text-left">
                                    <input type="submit" value="<fmt:message key="text.button.set"/>">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </c:if>
                <c:if test="${!('NEW' == bid.status || 'CLOSE' == bid.status)}">
                    <table class="table-fill">
                        <thead>
                        <tr>
                            <th class="text-left"><fmt:message key="text.brigade.name"/></th>
                            <th class="text-left"><fmt:message key="text.work-plane.work-time"/></th>
                            <th class="text-left"><fmt:message key="text.work-plane.complete-time"/></th>
                        </tr>
                        </thead>
                        <tbody class="table-hover">
                        <tr>
                            <td class="text-left">${workPlane.brigade.brigadeName}</td>
                            <td class="text-left">${workPlane.workTime}</td>
                            <td class="text-left"><d:dateFrm date="${workPlane.completeTime}" local="${language}"/></td>
                        </tr>
                        </tbody>
                    </table>
                </c:if>
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

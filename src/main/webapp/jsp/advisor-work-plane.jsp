<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="d" uri="/WEB-INF/date-format.tld"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />
<%@ page isELIgnored="false" contentType="text/html" pageEncoding="UTF-8" language="java" %>

<html>
<head>
    <title><fmt:message key="text.title.view.work-plane"/></title>
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
                        <th class="text-left"><fmt:message key="text.work-plane.id"/></th>
                        <th class="text-left"><fmt:message key="text.advisor"/></th>
                        <th class="text-left"><fmt:message key="text.brigade.name"/></th>
                        <th class="text-left"><fmt:message key="text.bid.id"/></th>
                        <th class="text-left"><fmt:message key="text.status"/></th>
                        <th class="text-left"><fmt:message key="text.work-plane.work-time"/></th>
                        <th class="text-left"><fmt:message key="text.work-plane.complete-time"/></th>
                        <th class="text-left"><fmt:message key="text.operation"/></th>
                    </tr>
                    </thead>
                    <tbody class="table-hover">
                    <c:forEach items="${workPlaneList}" var="workPlane">
                        <tr>
                            <td class="text-left">${workPlane.workPlaneId}</td>
                            <td class="text-left">${workPlane.userAdvisor.firstName} ${workPlane.userAdvisor.lastName}</td>
                            <td class="text-left">${workPlane.brigade.brigadeName}</td>
                            <td class="text-left">${workPlane.bid.bidId}</td>
                            <td class="text-left">${workPlane.status}</td>
                            <td class="text-left"><d:dateFrm date="${workPlane.workTime}" local="${language}"/></td>
                            <td class="text-left"><d:dateFrm date="${workPlane.completeTime}" local="${language}"/></td>
                            <td class="text-left">
                                <form action="<c:url value="/"/>">
                                    <input type="hidden" name="command" value="workplaneinfo">
                                    <input type="hidden" name="index" value="${workPlane.workPlaneId}">
                                    <input type="submit" value="<fmt:message key="text.button.info"/>">
                                </form>
                            </td>
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

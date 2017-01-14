<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="d" uri="/WEB-INF/date-format.tld"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />
<%@ page isELIgnored="false" contentType="text/html" pageEncoding="UTF-8" language="java" %>

<html>
<head>
    <title><fmt:message key="text.title.view.all-bid"/></title>
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
                    <li><a class="selected" href="<c:url value="/?command=advisorbid&status=all"/>"><fmt:message key="text.title.view.bid"/></a></li>
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
                <div id="padding">
                    <form action="/">
                        <input type="hidden" name="command" value="advisorbid">
                        <c:choose>
                            <c:when test="${status == 'all'}">
                                <input checked type="radio" name="status" value="all"> <fmt:message key="text.status.all"/>
                            </c:when>
                            <c:otherwise>
                                <input type="radio" name="status" value="all"> <fmt:message key="text.status.all"/>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${status == 'new'}">
                                <input checked type="radio" name="status" value="new"> <fmt:message key="text.status.new"/>
                            </c:when>
                            <c:otherwise>
                                <input type="radio" name="status" value="new"> <fmt:message key="text.status.new"/>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${status == 'in work'}">
                                <input checked type="radio" name="status" value="in work"> <fmt:message key="text.status.in-work"/>
                            </c:when>
                            <c:otherwise>
                                <input type="radio" name="status" value="in work"> <fmt:message key="text.status.in-work"/>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${status == 'complete'}">
                                <input checked type="radio" name="status" value="complete"> <fmt:message key="text.status.complete"/>
                            </c:when>
                            <c:otherwise>
                                <input type="radio" name="status" value="complete"> <fmt:message key="text.status.complete"/>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${status == 'close'}">
                                <input checked type="radio" name="status" value="close"> <fmt:message key="text.status.close"/>
                            </c:when>
                            <c:otherwise>
                                <input type="radio" name="status" value="close"> <fmt:message key="text.status.close"/>
                            </c:otherwise>
                        </c:choose>
                        <input type="submit" value="<fmt:message key="text.button.get"/>">
                    </form>
                </div>
                <table class="table-fill">
                    <thead>
                    <tr>
                        <th class="text-left"><fmt:message key="text.bid.work-type"/></th>
                        <th class="text-left"><fmt:message key="text.bid.work-scope"/></th>
                        <th class="text-left"><fmt:message key="text.bid.lead-time"/></th>
                        <th class="text-left"><fmt:message key="text.name"/></th>
                        <th class="text-left"><fmt:message key="text.status"/></th>
                        <th class="text-left"><fmt:message key="text.bid.desc"/></th>
                        <th class="text-left"><fmt:message key="text.bid.time"/></th>
                        <th class="text-left"><fmt:message key="text.operation"/></th>
                    </tr>
                    </thead>
                    <tbody class="table-hover">
                    <c:forEach items="${bidList}" var="bid">
                        <tr>
                            <td class="text-left">${bid.workType.typeName}</td>
                            <td class="text-left">${bid.workScope}</td>
                            <td class="text-left"><d:dateFrm date="${bid.leadTime}" local="${language}"/></td>
                            <td class="text-left">${bid.userTenant.firstName} ${bid.userTenant.lastName}</td>
                            <td class="text-left">${bid.status}</td>
                            <td class="text-left">${bid.description}</td>
                            <td class="text-left"><d:dateFrm date="${bid.bidTime}" local="${language}"/></td>
                            <td class="text-left">
                                    <form action="<c:url value="/"/>">
                                        <input type="hidden" name="command" value="bidInfo">
                                        <input type="hidden" name="index" value="${bid.bidId}">
                                        <c:choose>
                                            <c:when test="${'NEW' == bid.status}">
                                                <input type="submit" value="<fmt:message key="text.button.set"/>">
                                            </c:when>
                                            <c:otherwise>
                                                <input type="submit" value="<fmt:message key="text.button.info"/>">
                                            </c:otherwise>
                                        </c:choose>
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

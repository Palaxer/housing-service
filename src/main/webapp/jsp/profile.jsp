<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />
<%@ page isELIgnored="false" contentType="text/html" pageEncoding="UTF-8" language="java" %>

<html>
<head>
    <title><fmt:message key="text.profile"/></title>
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
                <li><a class="selected" href="<c:url value="/?command=redirect&to=profile"/>"><fmt:message key="text.profile"/></a></li>
                <li><a href=<c:url value="/?command=logout"/>><fmt:message key="text.logout"/></a></li>
            </ul>
        </nav>
    </aside>
    <!-- main content -->
    <div id="content">
        <!-- section 1 -->
        <section>
            <!-- article 1 -->
            <article id="padding">
                <h2><fmt:message key="text.profile.local"/></h2>
                <a class="uppercase" href="<c:url value="/?command=setlocale&language=ru"/>"><fmt:message key="text.lang.ru"/></a>
                <a class="uppercase" href="<c:url value="/?command=setlocale&language=en"/>"><fmt:message key="text.lang.en"/></a>
                <h2><fmt:message key="text.profile.passwd"/></h2>
                <c:if test="${not empty invalidDataPasswd}">
                    <h3 id="error"><fmt:message key="text.error.invalid-data"/></h3>
                </c:if>
                <c:if test="${not empty successUpdatePasswd}">
                    <h3 id="success"><fmt:message key="page.message.success-update"/></h3>
                </c:if>
                <c:if test="${not empty invalidUpdatePasswd}">
                    <h3 id="error"><fmt:message key="text.error.invalid-update"/></h3>
                </c:if>
                <form method="POST" action="<c:url value="/command=chengepasswd"/>">
                    <input type="hidden" name="command" value="changepasswd">
                    <input type="password" name="passwd" value="" placeholder=<fmt:message key="text.user.passwd"/>>
                    <input type="password" name="passwd1" value="" placeholder=<fmt:message key="text.user.passwd"/>>

                    <input class="submit" type="submit" value="<fmt:message key="text.button.change"/>">
                </form>
                <h2><fmt:message key="text.profile.user"/></h2>
                <c:if test="${not empty invalidData}">
                    <h3 id="error"><fmt:message key="text.error.invalid-data"/></h3>
                </c:if>
                <c:if test="${not empty successUpdate}">
                    <h3 id="success"><fmt:message key="page.message.success-update"/></h3>
                </c:if>
                <c:if test="${not empty invalidUpdate}">
                    <h3 id="error"><fmt:message key="text.error.invalid-update"/></h3>
                </c:if>
                <form method="POST" action="<c:url value="/command=updateuser"/>">
                    <input type="hidden" name="command" value="updateuser">
                    <input class="${invalidFirstName}" type="text" name="first-name" value="${user.firstName}" title="<fmt:message key="text.user.first-name"/>" placeholder=<fmt:message key="text.user.first-name"/>>
                    <input class="${invalidLastName}" type="text" name="last-name" value="${user.lastName}" title="<fmt:message key="text.user.last-name"/>" placeholder=<fmt:message key="text.user.last-name"/>>
                    <input class="${invalidStreet}" type="text" name="street" value="${user.street}" title="<fmt:message key="text.user.street"/>" placeholder=<fmt:message key="text.user.street"/>>
                    <input class="${invalidHouse}" type="text" name="house-number" value="${user.houseNumber}" title="<fmt:message key="text.user.house-number"/>" placeholder=<fmt:message key="text.user.house-number"/>>
                    <input class="${invalidApartment}" type="number" name="apartment" value="${user.apartment}" title="<fmt:message key="text.user.apartment"/>" placeholder=<fmt:message key="text.user.apartment"/>>
                    <input class="${invalidCity}" type="text" name="city" value="${user.city}" title="<fmt:message key="text.user.city"/>" placeholder=<fmt:message key="text.user.city"/>>
                    <input class="${invalidPhoneNumber}" type="text" name="phone-number" value="${user.phoneNumber}" placeholder=<fmt:message key="text.user.phone-number"/>>

                    <input class="submit" type="submit" value="<fmt:message key="text.button.update"/>">
                </form>
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

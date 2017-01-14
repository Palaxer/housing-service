<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />
<%@ page isELIgnored="false" contentType="text/html" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="text.title.login"/></title>
    <link href="<c:url value="/css/container.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="container">
        <a class="uppercase" href="<c:url value="/?language=en"/>"><fmt:message key="text.lang.en"/></a>
        <a class="uppercase" href="<c:url value="/?language=ru"/>"><fmt:message key="text.lang.ru"/></a>
        <h1 class="uppercase"><fmt:message key="text.title.login"/></h1>
        <c:if test="${not empty loginError}">
            <h2><fmt:message key="text.error.login"/></h2>
        </c:if>
        <form name="login" method="POST" action="login">
            <input type="hidden" name="command" value="login">
            <input type="text" name="login" value="${login}" placeholder=<fmt:message key="text.user.login"/>>
            <input type="password" name="passwd" placeholder=<fmt:message key="text.user.passwd"/>>

            <input class="submit" type="submit" name="login" value=<fmt:message key="text.button.submit"/>>
        </form>
        <form name="registration" action="<c:url value="/"/>">
            <input type="hidden" name="command" value="redirect">
            <input type="hidden" name="to" value="registration">
            <input class="submit" type="submit" value=<fmt:message key="text.button.registration"/>>
        </form>
    </div>
</body>
</html>

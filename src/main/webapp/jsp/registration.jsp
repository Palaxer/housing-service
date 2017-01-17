<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="text.title.registration"/></title>
    <link href="<c:url value="/css/container.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="container">
        <h1 class="uppercase"><fmt:message key="text.title.registration"/></h1>
        <c:if test="${not empty loginExist}">
            <h2><fmt:message key="text.error.login.exist"/></h2>
        </c:if>
        <c:if test="${not empty invalidData}">
            <h2><fmt:message key="text.error.invalid-data"/></h2>
        </c:if>
        <form name="registration" method="POST" action="<c:url value="/command=redirect&to=registration"/>">
            <input type="hidden" name="command" value="REGISTRATION">
            <input class="${null != invalidData.invalidLogin}" type="text" name="login" value="${user.login}" placeholder=<fmt:message key="text.user.login"/>>
            <input type="password" name="passwd" value="${user.password}" placeholder=<fmt:message key="text.user.passwd"/>>

            <input class="${invalidData.invalidFirstName}" type="text" name="first-name" value="${user.firstName}" placeholder=<fmt:message key="text.user.first-name"/>>
            <input class="${invalidData.invalidLastName}" type="text" name="last-name" value="${user.lastName}" placeholder=<fmt:message key="text.user.last-name"/>>
            <input class="${invalidData.invalidStreet}" type="text" name="street" value="${user.street}" placeholder=<fmt:message key="text.user.street"/>>
            <input class="${invalidData.invalidHouse}" type="text" name="house-number" value="${user.houseNumber}" placeholder=<fmt:message key="text.user.house-number"/>>
            <input class="${invalidData.invalidApartment}" type="number" name="apartment" value="${user.apartment}" placeholder=<fmt:message key="text.user.apartment"/>>
            <input class="${invalidData.invalidCity}" type="text" name="city" value="${user.city}" placeholder=<fmt:message key="text.user.city"/>>
            <input class="${invalidData.invalidPhoneNumber}" type="text" name="phone-number" value="${user.phoneNumber}" placeholder=<fmt:message key="text.user.phone-number"/>>

            <input type="submit" value=<fmt:message key="text.button.registration"/> class="submit">
        </form>
    </div>
</body>
</html>
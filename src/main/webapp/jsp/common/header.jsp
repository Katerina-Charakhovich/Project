<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <title><fmt:message key="Label.Header"/></title>
</head>
<nav class="navbar fixed-top navbar-expand-lg navbar navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="controller?command=init_start_page">Home <span
                        class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>
<c:choose>
    <c:when test="${role == 'admin'}">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
        role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
        <fmt:message key="Label.Admin"/>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="controller?command=admin_page"><fmt:message key="Page.Users"/></a>
                    <a class="dropdown-item" href="controller?command=admin_page_films"><fmt:message key="Page.Films"/></a>

                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="#">Something else here</a>
                </div>
            </li>
    </c:when>
</c:choose>
            <li class="nav-item">
                <a class="nav-link disabled" href="#">Disabled</a>
            </li>
        </ul>
        <c:choose>
            <c:when test="${role != null}">
                <form class="form-inline my-2 my-lg-0" id="profileForm" >
                    <input class="btn btn-outline-secondary" type="submit" method="post" action="controller"
                           value="<fmt:message key="Label.Profile"/>">
                    <input type="hidden" name="command" value="profile"/>
                </form>
                <div class="logout" id="headerForm">
                    <a href="controller?command=Logout" class="btn btn-outline-secondary" role="button"
                       aria-pressed="true"><fmt:message key="Label.Logout"/></a>
                </div>
            </c:when>
            <c:otherwise>
                <form class="form-inline my-2 my-lg-0" id="headerForm">
                    <input class="btn btn-outline-secondary" type="submit" method="post" action="controller"
                           value="<fmt:message key="Label.Sign"/>">
                    <input type="hidden" name="command" value="forward"/>
                    <input type="hidden" name="page" value="path.page.login"/>
                </form>
            </c:otherwise>
        </c:choose>
        <form class="form-inline my-2 my-lg-0" method="post" action="controller">
            <input type="hidden" name="command" value="language"/>
            <%--<c:set var="processCommand" value="profile" scope="session"/>--%>
            <input type="hidden" name="langChangeProcessCommand" value="${langChangeProcessCommand}"/>
            <input class="btn btn-outline-secondary" type="submit" name="currentNavLocale" value="${language}">
        </form>
    </div>
</nav>
</html>

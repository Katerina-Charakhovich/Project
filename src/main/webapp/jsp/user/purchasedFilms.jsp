<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<c:set var="lang" value="${language}"/>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title><fmt:message key="Label.ProfileCard"/></title>
    <style>
        <%@include file="/css/style.css" %>
    </style>
</head>
<c:import url="/jsp/common/header.jsp"/>
<div class="startPage">
    <div class="container-sm">
        <a onclick="javascript:history.back();return false;">
            <svg width="4em" height="4em" viewBox="0 0 16 16" class="bi bi-arrow-left-square text-light"
                 fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd"
                      d="M14 1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
                <path fill-rule="evenodd"
                      d="M12 8a.5.5 0 0 1-.5.5H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5a.5.5 0 0 1 .5.5z"/>
            </svg>
        </a>
        <c:choose>
            <c:when test="${role == 'USER'}">
                <fieldset disabled class="sign_in">
                    <legend><fmt:message key="Label.MyFilms"/></legend>
                </fieldset>
            </c:when>
            <c:otherwise>
                <fieldset disabled class="sign_in">
                    <legend><fmt:message key="Label.ViewFilms"/></legend>
                </fieldset>
            </c:otherwise>
        </c:choose>
        <div class="row row-cols-4">
            <c:forEach items="${films}" var="film">
                <a href="controller?command=film&filmId=${film.filmId}&filmName=${film.filmName}&filmAvatar=${film.filmAvatar}">
                    <div class="col-10">
                        <br/>
                        <div><img src="pictures_for_project/posters_${fn:toLowerCase(lang)}/${film.filmAvatar}" alt=""
                                  width="200">
                        </div>
                        <span class="font-weight-bold text-info"><c:out value="${film.filmName}"/></span>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
    <c:import url="/jsp/common/footer.jsp"/>
</div>
</html>

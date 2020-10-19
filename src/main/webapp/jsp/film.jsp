<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title><fmt:message key="Label.FilmTitle"/></title>
</head>
<style>
    <%@include file="/css/style.css" %>
</style>
<c:import url="/jsp/common/header.jsp"/>
<div class="film">
<div class="container-sm">
    <div class="row">
        <div class="col">
            <div><span class="font-weight-bold text-uppercase text-info"><fmt:message key="${filmName}"/></span>
            </div>
            <br/>
            <div><img src="pictures_for_project/posters_${language}/${realName}" alt="" width="250">
            </div>
            <br/>
            <div><span class="font-weight-normal text-secondary">${yearOfCreation}</span>
            </div>
            <div id="genre"><span class="font-weight-normal text-secondary"><fmt:message key="${genre}"/></span>
            </div>
        </div>
        <div class="col-7" id="trailer">
            <br/>
            <br/>
            <div class="embed-responsive embed-responsive-16by9">
                <iframe class="embed-responsive-item" src="<fmt:message key="${link}"/>" allowfullscreen></iframe>
            </div>
            <br/>
            <br/>
            <div> <span class="font-weight-bold text-info"><fmt:message key="${description}"/></span>
            </div>
        </div>
    </div>
</div>
<c:import url="/jsp/common/footer.jsp"/>
</div>
</html>

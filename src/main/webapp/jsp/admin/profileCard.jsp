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
    <title><fmt:message key="Label.ProfileCard"/></title>
    <style>
        <%@include file="/css/style.css" %>
    </style>
</head>
<c:import url="/jsp/common/header.jsp"/>
<div class="profileCard">
    <div class="container-sm">
        <fieldset disabled class="sign_in">
            <legend><fmt:message key="Label.Profile"/>:</legend>
        </fieldset>
            <div class="card" style="width: 18rem;">
                <img src="pictures_for_project/picturesForAvatar/${avatar}" class="card-img-top"
                     alt="...">
                <div class="card-body">
                    <h5 class="card-title">${name}</h5>
                    <p class="card-text">${about_me}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">${gender}</li>
                    <li class="list-group-item">${country}</li>
                </ul>
            </div>
    </div>
<br/>
<br/>
<c:import url="/jsp/common/footer.jsp"/>
<br/>
</div>
</html>

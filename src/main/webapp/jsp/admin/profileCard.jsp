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
        <a onclick="javascript:history.back();return false;">
            <svg width="4em" height="4em" viewBox="0 0 16 16" class="bi bi-arrow-left-square text-light"
                 fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd"
                      d="M14 1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
                <path fill-rule="evenodd"
                      d="M12 8a.5.5 0 0 1-.5.5H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5a.5.5 0 0 1 .5.5z"/>
            </svg>
        </a>
        <fieldset disabled class="sign_in">
            <legend><fmt:message key="Label.Profile"/>:</legend>
        </fieldset>
        <div class="card" style="width: 18rem;">
            <img src="pictures_for_project/picturesForAvatar/<c:out value="${avatar}"/>" class="card-img-top"
                 alt="...">
            <div class="card-body">
                <h5 class="card-title"><c:out value="${name}"/></h5>
                <p class="card-text"><c:out value="${about_me}"/></p>
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><c:out value="${gender}"/></li>
                <li class="list-group-item"><c:out value="${country}"/></li>
            </ul>
        </div>
        <br>
        <br>
        <div class="row">
            <div class="col-4">
            </div>
            <div class="col-4">
                <div class="Sub_films" id="myFilms">
                    <form class="form-inline my-2 my-lg-0" method="post"
                          action="${request.getContextPath()}/TaskWebLogin_war/controller">
                        <input class="btn btn-primary btn-lg btn-block" type="submit"
                               value="<fmt:message key="Label.ViewFilms"/>">
                        <input type="hidden" name="command" value="view_purchased_film"/>
                        <input type="hidden" name="command" value="view_purchased_film"/>
                    </form>
                </div>
            </div>
            <div class="col-4">
            </div>
        </div>
    </div>
    <br/>
    <br/>
    <c:import url="/jsp/common/footer.jsp"/>
    <br/>
</div>
</html>

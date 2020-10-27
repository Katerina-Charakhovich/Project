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
    <title><fmt:message key="Label.Admin"/></title>
    <style>
        <%@include file="/css/style.css" %>
    </style>
</head>
<c:import url="/jsp/common/header.jsp"/>
<div class="container-sm">
    <div class="row">
        <div class="col">
        </div>
        <div class="col-6">
            <form name="filmCreatorForm" method="post"
                  action="${request.getContextPath()}/TaskWebLogin_war/controller">
                <input type="hidden" name="command" value="create_film_info_ru"/>
                <label for="descriptionOfFilmRussian" class="validationTooltip"><fmt:message
                        key="Label.filmDescriptionRussian"/></label>
                <input type="text" name="descriptionRu"
                       maxlength="500" value="" id="descriptionOfFilmRussian"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <label for="nameForPropertyGenreRussian" class="validationTooltip"><fmt:message
                        key="Label.filmGenreRussian"/></label>
                <input type="text" name="genreOfFilmRussian"
                       maxlength="300" value="" id="nameForPropertyGenreRussian"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <label for="nameForPropertyLinkRussian" class="validationTooltip"><fmt:message
                        key="Label.filmLinkRussian"/></label>
                <input type="text" name="linkOfFilmRussian"
                       maxlength="300" value="" id="nameForPropertyLinkRussian"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <div class="submit-login">
                    <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="Submit.Submit"/>">
                </div>
            </form>
        </div>
        <div class="col">
        </div>
    </div>
</div>
    <c:import url="/jsp/common/footer.jsp"/>
</html>
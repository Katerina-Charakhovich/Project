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
<div class="filmCreator">
    <div class="container-sm">
        <div class="row">
            <div class="col-3">
            </div>
            <div class="col-6">
                <fieldset disabled class="sign_in">
                    <legend><fmt:message key="Label.createFilm"/></legend>
                </fieldset>
            </div>
            <div class="col-3">
            </div>
        </div>
        <div class="row">
            <div class="col-3">
            </div>
            <div class="col-6">
                <fieldset disabled class="sign_in">
                    <legend><fmt:message key="Label.createFilmInEnglish"/></legend>
                </fieldset>
                <form name="filmCreatorForm" method="post"
                      action="${request.getContextPath()}/TaskWebLogin_war/controller">
                    <input type="hidden" name="command" value="film_creator"/>
                    <label for="nameForProperty" class="validationTooltip"><fmt:message
                            key="Label.filmName"/></label>
                    <input type="text" name="filmName"
                           pattern="^[a-zA-Z0-9\s?!,.:'\-]+$"
                           maxlength="50" minlength="0" value="${filmName}" id="nameForProperty"
                           class="form-control"
                           aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-large" required>
                    <label for="descriptionEn" class="validationTooltip"><fmt:message
                            key="Label.filmDescription"/></label>
                    <textarea type="text" name="description" maxlength="1000" class="form-control"
                              minlength="0" aria-label="Sizing example input"
                              aria-describedby="inputGroup-sizing-default"
                              id="descriptionEn">${description}</textarea>
                    <label for="nameForPropertyGenreEnglish" class="validationTooltip"><fmt:message
                            key="Label.filmGenre"/></label>
                    <input type="text" name="genre"
                           pattern="^[a-zA-Z0-9\s?!,.:'\-]+$"
                           maxlength="50" value="${genre}" id="nameForPropertyGenreEnglish"
                           class="form-control"
                           aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-large">
                    <label for="nameForPropertyGenreEnglish" class="validationTooltip"><fmt:message
                            key="Label.filmLink"/></label>
                    <input type="text" name="link"
                           maxlength="300" minlength="0"
                           value="${link}" id="nameForPropertyLinkEnglish"
                           class="form-control"
                           pattern="(https?://)?(www\.)?(yotu\.be/|youtube\.com/)?((.+/)?(watch(\?v=|.+&v=))?(v=)?)([\w_-]{11})(&.+)?"
                           aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-large">
                    <label for="FormYearOfCreation" class="validationTooltip"><fmt:message
                            key="Film.YearOfCreation"/></label>
                    <input type="number" name="yearOfCreation"
                           maxlength="4" minlength="0"
                           value="${yearOfCreation}" id="FormYearOfCreation"
                           class="form-control"
                           pattern="^\d{0,4}$"
                           aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-large">
                    <br>
                    <br>
                    <c:if test="${errorData == true}">
                        <div class="validation"><fmt:message key="${field}"/>: <fmt:message key="Message.data"/></div>
                    </c:if>
                    <div class="submit-create">
                        <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="Submit.Save"/>">
                    </div>
                </form>
                <div>
                    <div class="col">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:import url="/jsp/common/footer.jsp"/>
</div>
</html>

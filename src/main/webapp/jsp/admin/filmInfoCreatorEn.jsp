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
        <div class="col-6">
            <form action="${request.getContextPath()}/TaskWebLogin_war/upload" enctype="multipart/form-data"
                  method="post">
                <div class="custom-file">
                    <input type="file" class="custom-file-input" name="content" id="validatedCustomFile"
                           required>
                    <label class="custom-file-label" for="validatedCustomFile"><fmt:message
                            key="Film.ChooseEnglishPhoto"/></label>
                </div>
                <div class="button-save">
                    <button class="btn btn-outline-primary " type="submit"><fmt:message
                            key="Submit.Save"/></button>
                </div>
            </form>
        </div>
        <div class="col-6">
            <div class="upload text-info">
                <form action="upload" enctype="multipart/form-data" method="post">
                    <div class="custom-file">
                        <input type="file" class="custom-file-input" name="content" id="validatedCustomFile2"
                               required>
                        <label class="custom-file-label" for="validatedCustomFile2"><fmt:message
                                key="Film.ChooseRussianPhoto"/></label>
                    </div>
                    <div class="button-save">
                        <button class="btn btn-outline-primary " type="submit"><fmt:message
                                key="Submit.Save"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col">
        </div>
        <div class="col">
            <form name="filmCreatorForm" method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller">
                <input type="hidden" name="command" value="create_film_info_en"/>
                <input type="text" name="description"
                       maxlength="50" value="Description." id="descriptionOfFilm"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <label for="nameForProperty" class="validationTooltip"><fmt:message
                        key="Label.filmDescriptionEnglish"/></label>
                <input type="text" name="descriptionEn"
                       maxlength="300" value="" id="nameForProperty"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <input type="text" name="genre"
                       maxlength="50" value="Genre." id="genreOfFilm"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <label for="nameForPropertyGenreEnglish" class="validationTooltip"><fmt:message
                        key="Label.filmGenreEnglish"/></label>
                <input type="text" name="genreOfFilmEnglish"
                       maxlength="500" value="" id="nameForPropertyGenreEnglish"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <input type="text" name="link"
                       maxlength="50" value="Link." id="linkOfFilm"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <label for="nameForPropertyGenreEnglish" class="validationTooltip"><fmt:message
                        key="Label.filmLinkEnglish"/></label>
                <input type="text" name="linkOfFilmEnglish"
                       maxlength="300" value="" id="nameForPropertyLinkEnglish"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <label for="FormYearOfCreation" class="validationTooltip"><fmt:message
                        key="Film.YearOfCreation"/></label>
                <input type="number" name="yearOfCreation"
                       maxlength="4" value="" id="FormYearOfCreation"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <div class="submit-login">
                    <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="Submit.Submit"/>">
                </div>
            </form>
            <form name="registrationSubmit" method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller"
                  class="register-button">
                <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="Submit.Next"/>">
                <input type="hidden" name="command" value="forward"/>
                <input type="hidden" name="page" value="/jsp/admin/filmInfoCreatorRu.jsp"/>
            </form>
        </div>
        <div class="col">
        </div>
    </div>
</div>
<c:import url="/jsp/common/footer.jsp"/>
</html>


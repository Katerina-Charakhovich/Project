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
        <div class="col-3">
        </div>
        <div class="col-6">
            <fieldset disabled class="sign_in">
                <legend><fmt:message key="Label.createFilm"/></legend>
            </fieldset>
            <form name="filmCreatorForm" method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller">
                <input type="hidden" name="command" value="film_creator"/>
                <input type="text" name="filmName"
                       maxlength="50" value="Film." id="nameForProfile"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <label for="nameForProperty" class="validationTooltip"><fmt:message
                        key="Label.filmNameEnglish"/></label>
                <input type="text" name="filmNameForPropEn"
                       maxlength="50" value="" id="nameForProperty"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <label for="nameForPropertyRu" class="validationTooltip"><fmt:message
                        key="Label.filmNameRussian"/></label>
                <input type="text" name="filmNameForPropRu"
                       maxlength="50" value="" id="nameForPropertyRu"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <br>
                <div class="submit-login">
                    <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="Submit.Submit"/>">
                </div>

            </form>
            <form name="registrationSubmit" method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller"
                  class="register-button">
                <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="Submit.Next"/>">
                <input type="hidden" name="command" value="forward"/>
                <input type="hidden" name="page" value="/jsp/admin/filmInfoCreatorEn.jsp"/>
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

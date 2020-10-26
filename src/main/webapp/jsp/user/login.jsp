<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <title><fmt:message key="Label.Login"/></title>
    <style>
        <%@include file="/css/style.css" %>
    </style>
</head>
<c:import url="../common/header.jsp"/>
<div class="login">
    <div class="container-sm">
        <div class="row">
            <div class="col">
            </div>
            <div class="col-4">
                <form name="loginForm" method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller"
                      class="needs-validation" novalidate>
                    <fieldset disabled class="sign_in">
                        <legend><fmt:message key="Label.Sign"/></legend>
                    </fieldset>
                    <input type="hidden" name="command" value="login"/>
                    <div class="mb-3">
                        <div class="form-group">
                            <label for="validationTooltip01" class="validationTooltip"><fmt:message
                                    key="Label.Login"/></label>
                            <input type="email" name="email"
                                   class="form-control"
                                   id="validationTooltip01"
                                   pattern="^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
                                   placeholder="<fmt:message key="Label.PlaceholderEmail"/>"
                                   required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <div class="form-group">
                            <label for="validationTooltip02" class="validationTooltip"><fmt:message
                                    key="Label.Password"/></label>
                            <input type="password" name="password"
                                   class="form-control"
                                   id="validationTooltip02"
                                   pattern="^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,20}$"
                                   maxlength="20" minlength="6"
                                   placeholder="<fmt:message key="Label.PlaceholderPassword"/>"
                                   required>
                        </div>
                    </div>
                    <c:if test="${errorLoginPassMessage == false}">
                    <div class="validation"> <fmt:message key="Message.loginerror"/></div>
            </c:if>
            <c:if test="${UserIsLocked == false}">
            <div class="validation"> <fmt:message key="Message.locked"/></div>
        </c:if>
<br/><br/>
<div class="submit-login">
    <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="Submit.Login"/>">
</div>
</form>
<script>
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>
<form name="registrationSubmit" method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller"
      class="register-button">
    <input class="btn btn-outline-primary" type="submit"
           value="<fmt:message key="Submit.Registration"/>">
    <input type="hidden" name="command" value="forward"/>
    <input type="hidden" name="page" value="/jsp/user/registration.jsp"/>
</form>
</div>
<div class="col">
</div>
</div>
</div>
<br/>
<br/>
<br/>
<br/>
<br/>
<c:import url="../common/footer.jsp"/>
</div>
</html>
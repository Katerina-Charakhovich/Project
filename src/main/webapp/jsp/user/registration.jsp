<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>

<html>

<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <title><fmt:message key="Label.Registration"/></title>
</head>
<c:import url="../common/header.jsp"/>
<style>
    <%@include file="/css/style.css" %>
</style>
${language}
<div class="registration">
    <div class="container-sm_registration">
        <div class="row">
            <div class="col">
            </div>
            <div class="col-4">
                <form name="registrationForm" method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller"
                      class="needs-validation" novalidate>
                    <fieldset disabled class="create_your_account">
                        <legend><fmt:message key="Label.Registration"/></legend>
                    </fieldset>
                    <input type="hidden" name="command" value="registration"/>
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
                                   pattern="^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА_Я]).{6,20}$"
                                   placeholder="<fmt:message key="Label.PlaceholderPassword"/>"
                                   required>
                            <small id="passwordHelp" class="form-text text-muted">
                                <fmt:message key="Label.PasswordHelp"/></small>
                        </div>
                    </div>
                    <div class="mb-3">
                        <div class="form-group">
                            <label for="validationTooltip03" class="validationTooltip"><fmt:message
                                    key="Label.Confirm"/></label>
                            <input type="password" name="repeat password"
                                   class="form-control"
                                   id="validationTooltip03"
                                   pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$"
                                   placeholder="<fmt:message key="Label.PlaceholderRepeatPassword"/>"
                                   required>
                        </div>
                    </div>
                    <c:if test="${registrationErrorSymbols == true}">
                        <div class="validation"> <fmt:message key="Message.incorrectLoginAndPassword"/></div>
                    </c:if>
                    <c:if test="${registrationError == true}">
                        <div class="validation"> <fmt:message key="Message.thisUserExists"/></div>
                    </c:if>
                    <c:if test="${registrationErrorPasswords == true}">
                        <div class="validation"> <fmt:message key="Message.passwordsDoNotMatch"/></div>
                    </c:if>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <div class="submit-registration">
                        <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="Submit.Submit"/>">
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
            </div>
            <div class="col">
            </div>
        </div>
    </div>
    <c:import url="../common/footer.jsp"/>
</div>
</html>

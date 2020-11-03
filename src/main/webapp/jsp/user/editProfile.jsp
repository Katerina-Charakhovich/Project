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
    <title><fmt:message key="Label.Edit_profile"/></title>
    <style>
        <%@include file="/css/style.css" %>
    </style>
</head>
<c:import url="/jsp/common/header.jsp"/>
<div class="profile_edit">
    <div class="container-sm">
        <div class="row">
            <div class="col-4">
            </div>
            <div class="col-4">
                <form name="editProfileForm" method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller">
                    <fieldset disabled class="edit_profile">
                        <legend><fmt:message key="Label.Edit_profile"/></legend>
                    </fieldset>
                    <input type="hidden" name="command" value="edit_profile"/>
                    <div class="mb-3">
                        <div class="form-group">
                            <label for="nameForProfile" class="profileType"><fmt:message
                                    key="Label.User_name"/></label>
                            <input type="text" name="name"
                                   pattern="[A-Za-zА-Яа-яЁё]+(\s+[A-Za-zА-Яа-яЁё]+)?"
                                   maxlength="50" value="${name}" id="nameForProfile"
                                   class="form-control"
                                   aria-label="Sizing example input"
                                   aria-describedby="inputGroup-sizing-large">
                        </div>
                    </div>
                    <c:if test="${errorData == true}">
                        <div class="validation"> <fmt:message key="Message.data"/></div>
                    </c:if>
                    <div class="mb-3">
                        <div class="form-group">
                            <label for="inputGroupSelect02" class="profileType"><fmt:message
                                    key="Label.Sex"/></label>
                            <select class="custom-select" name="gender" id="inputGroupSelect02">
                                <option selected>${gender}</option>
                                <option value="<fmt:message key="Select.Male"/>"><fmt:message
                                        key="Select.Male"/></option>
                                <option value="<fmt:message key="Select.Female"/>"><fmt:message
                                        key="Select.Female"/></option>
                            </select>
                        </div>
                    </div>
                    <div class="mb-3">
                        <div class="form-group">
                            <label for="inputGroupSelect01" class="profileType"><fmt:message
                                    key="Label.Country"/></label>
                            <select class="custom-select" name="country" id="inputGroupSelect01">
                                <option selected>${country}</option>
                                <option value="<fmt:message key="Select.ARMENIA"/>"><fmt:message
                                        key="Select.ARMENIA"/></option>
                                <option value="<fmt:message key="Select.AUSTRIA"/>"><fmt:message
                                        key="Select.AUSTRIA"/></option>
                                <option value="<fmt:message key="Select.BELARUS"/>"><fmt:message
                                        key="Select.BELARUS"/></option>
                                <option value="<fmt:message key="Select.BELGIUM"/>"><fmt:message
                                        key="Select.BELGIUM"/></option>
                                <option value="<fmt:message key="Select.BULGARIA"/>"><fmt:message
                                        key="Select.BULGARIA"/></option>
                                <option value="<fmt:message key="Select.CANADA"/>"><fmt:message
                                        key="Select.CANADA"/></option>
                                <option value="<fmt:message key="Select.CZECH_REPUBLIC"/>"><fmt:message
                                        key="Select.CZECH_REPUBLIC"/></option>
                                <option value="<fmt:message key="Select.DENMARK"/>"><fmt:message
                                        key="Select.DENMARK"/></option>
                                <option value="<fmt:message key="Select.ESTONIA"/>"><fmt:message
                                        key="Select.ESTONIA"/></option>
                                <option value="<fmt:message key="Select.FRANCE"/>"><fmt:message
                                        key="Select.FRANCE"/></option>
                                <option value="<fmt:message key="Select.GEORGIA"/>"><fmt:message
                                        key="Select.GEORGIA"/></option>
                                <option value="<fmt:message key="Select.GERMANY"/>"><fmt:message
                                        key="Select.GERMANY"/></option>
                                <option value="<fmt:message key="Select.HUNGARY"/>"><fmt:message
                                        key="Select.HUNGARY"/></option>
                                <option value="<fmt:message key="Select.ITALY"/>"><fmt:message
                                        key="Select.ITALY"/></option>
                                <option value="<fmt:message key="Select.LATVIA"/>"><fmt:message
                                        key="Select.LATVIA"/></option>
                                <option value="<fmt:message key="Select.LITHUANIA"/>"><fmt:message
                                        key="Select.LITHUANIA"/></option>
                                <option value="<fmt:message key="Select.NORWAY"/>"><fmt:message
                                        key="Select.NORWAY"/></option>
                                <option value="<fmt:message key="Select.POLAND"/>"><fmt:message
                                        key="Select.POLAND"/></option>
                                <option value="<fmt:message key="Select.RUSSIAN_FEDERATION"/>"><fmt:message
                                        key="Select.RUSSIAN_FEDERATION"/></option>
                                <option value="<fmt:message key="Select.SPAIN"/>"><fmt:message
                                        key="Select.SPAIN"/></option>
                                <option value="<fmt:message key="Select.SWEDEN"/>"><fmt:message
                                        key="Select.SWEDEN"/></option>
                                <option value="<fmt:message key="Select.SWITZERLAND"/>"><fmt:message
                                        key="Select.SWITZERLAND"/></option>
                                <option value="<fmt:message key="Select.UKRAINE"/>"><fmt:message
                                        key="Select.UKRAINE"/></option>
                                <option value="<fmt:message key="Select.UNITED_KINGDOM"/>"><fmt:message
                                        key="Select.UNITED_KINGDOM"/></option>
                                <option value="<fmt:message key="Select.UNITED_STATES"/>"><fmt:message
                                        key="Select.UNITED_STATES"/></option>
                            </select>
                        </div>
                    </div>
                    <div class="mb-3">
                        <div class="form-group">
                            <label for="exampleFormControlTextarea1" class="profileType"><fmt:message
                                    key="Label.About_me"/></label>
                            <textarea type="text" name="about_me" maxlength="300" class="form-control"
                                      aria-label="Sizing example input"
                                      aria-describedby="inputGroup-sizing-default"
                                      id="exampleFormControlTextarea1"><c:out value="${about_me}"/></textarea>
                        </div>
                        <br>
                        <br>
                        <c:if test="${errorData == true}">
                            <div class="validation"> <fmt:message key="Message.data"/></div>
                        </c:if>
                    </div>
                    <div class="submit-edit">
                        <input class="btn btn-outline-primary btn-lg" type="submit" name="Submit_edit"
                               value="<fmt:message key="Submit.Save"/>">
                    </div>
                </form>
                <div class="col">
                </div>
            </div>
        </div>
        <br/>
        <br/>
        <br/>
        <c:import url="/jsp/common/footer.jsp"/>
    </div>
</div>
</html>

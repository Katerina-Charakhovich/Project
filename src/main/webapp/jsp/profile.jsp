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
    <title><fmt:message key="Label.Profile"/></title>
    <style>
        <%@include file="/css/style.css" %>
    </style>
</head>
<c:import url="/jsp/common/header.jsp"/>
<div class="Profile">
    <div class="container-sm">
        <div class="row">
            <br/>
            <br/>
            <div class="col">
                <p class="font-weight-bold text-info">${name}</p>
                <div><img src="pictures_for_project/picturesForAvatar/${avatar}"
                          alt="" width="200"/></div>
                <br/>
                <br/>
                <div class="upload text-info">
                    <fmt:message key="Label.SelectFileUpload"/><br/>
                                    <form action="upload" enctype="multipart/form-data" method="POST">
                                        <INPUT type="file" name="content" height="130">
                        <div class="button-save">
                            <button class="btn btn-outline-primary " type="submit"><fmt:message key="Submit.Save"/></button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-6">
                <fieldset disabled class="sign_in">
                    <legend><fmt:message key="Label.YourProfile"/></legend>
                </fieldset>
                <table class="table table-th-block">
                    <tbody>
                    <tr>
                        <td class="active"><strong><fmt:message key="Label.Sex"/></strong></td>
                        <td>${gender}</td>
                    </tr>
                    <tr>
                        <td class="active"><strong><fmt:message key="Label.Country"/></strong></td>
                        <td>${country}</td>
                    </tr>
                    <tr>
                        <td class="active"><strong><fmt:message key="Label.About_me"/></strong></td>
                        <td>${about_me}</td>
                    </tr>
                    </tbody>
                </table>
                <form class="form-inline my-2 my-lg-0">
                    <div class="Sub_edit" id="edit">
                        <input class="btn btn-outline-primary" type="submit" method="post" action="controller"
                               value="<fmt:message key="Label.Edit"/>">
                        <input type="hidden" name="command" value="forward_to_edit_profile"/>
                    </div>
                </form>
            </div>
            <div class="col">
            </div>
        </div>
    </div>
    <br/>
    <c:import url="/jsp/common/footer.jsp"/>
    <br/>
</div>
</html>

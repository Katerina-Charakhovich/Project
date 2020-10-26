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
<div class="container-sm">
    <div class="row">
        <div class="col">
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
            <form name="filmCreatorForm" method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller">
                <input type="hidden" name="command" value="film_creator"/>
                <input type="text" name="filmName" pattern="^[a-zA-Z0-9]+$"
                       maxlength="50" value="Film." id="nameForProfile"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
                <input type="text" name="filmNameForPropEn" pattern="^[a-zA-Z0-9]+$"
                       maxlength="50" value=" " id="nameForProperty"
                       class="form-control"
                       aria-label="Sizing example input"
                       aria-describedby="inputGroup-sizing-large">
            </form>
            <div>
                <div class="col">
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
                    <form name="filmCreatorForm" method="post"
                          action="${request.getContextPath()}/TaskWebLogin_war/controller">
                        <input type="hidden" name="command" value="film_creator"/>
                        <input type="text" name="filmNameForPropRu" pattern="^[a-zA-Z0-9]+$"
                               maxlength="50" value=" " id="nameForPropertyRu"
                               class="form-control"
                               aria-label="Sizing example input"
                               aria-describedby="inputGroup-sizing-large">
                    </form>
                </div>
            </div>
        </div>
    </div>
    <c:import url="/jsp/common/footer.jsp"/>
</div>
</html>

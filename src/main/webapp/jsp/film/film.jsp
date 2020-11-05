<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="prop.pagecontent"/>
<c:set var="lang" value="${language}"/>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title><fmt:message key="Label.FilmTitle"/></title>
</head>
<style>
    <%@include file="/css/style.css" %>
</style>
<c:import url="/jsp/common/header.jsp"/>
<div class="film">
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
        <br/>
        <br/>
        <div class="row">
            <div class="col">
                <div><span class="font-weight-bold text-uppercase text-info"><c:out value="${filmName}"/></span>
                </div>
                <br/>
                <div><img src="pictures_for_project/posters_${fn:toLowerCase(lang)}/${filmAvatar}" alt="" width="250">
                </div>
                <br/>
                <div><span class="font-weight-normal text-secondary"><c:out value="${yearOfCreation}"/></span>
                </div>
                <div id="genre"><span class="font-weight-normal text-secondary"><c:out value="${genre}"/></span>
                </div>
            </div>
            <div class="col-7" id="trailer">
                <br/>
                <br/>
                <div class="embed-responsive embed-responsive-16by9">
                    <iframe class="embed-responsive-item" src="${link}" allowfullscreen></iframe>
                </div>
                <br/>
                <br/>
                <div><span class="font-weight-bold text-info"><c:out value="${description}"/></span>
                </div>
            </div>
        </div>
        <br>
        <br>
        <div class="row">
            <div class="col-4">
                ${buyFilm}
                <c:if test="${role == 'USER' && buyFilm == 'false'}">
                    <form name="BuyForm" method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller">
                        <button type="submit" name="buyFilm" value="true" class="btn btn-warning btn-lg btn-block">
                            <fmt:message key="Label.Buy"/></button>
                        <input type="hidden" name="command" value="purchase_film"/>
                        <input type="hidden" name="filmName" value="${filmName}"/>
                        <input type="hidden" name="filmAvatar" value="${filmAvatar}"/>
                        <input type="hidden" name="yearOfCreation" value="${yearOfCreation}"/>
                        <input type="hidden" name="genre" value="${genre}"/>
                        <input type="hidden" name="link" value="${link}"/>
                        <input type="hidden" name="description" value="${description}"/>
                    </form>
                </c:if>
                <c:if test="${role == 'USER' && buyFilm == 'true'}">
                    <button type="button" name="buyFilm" value="true" class="btn btn-outline-warning btn-lg btn-block">
                        <fmt:message key="Label.Purchased"/></button>
                </c:if>
            </div>
            <div class="col-4">
            </div>
            <div class="col-4">
            </div>
        </div>
    </div>
    <c:import url="/jsp/common/footer.jsp"/>
</div>
</html>

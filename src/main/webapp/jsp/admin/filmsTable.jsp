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
<div class="Admin_Page">
    <div class="container-sm">
        <fieldset disabled class="sign_in">
            <legend><fmt:message key="Label.films"/></legend>
        </fieldset>
        <div class="row">
            <div class="dropdown">
                <div class="input-group-prepend">
                    <label class="input-group-text" for="dropDownGroup"><fmt:message key="Label.choose"/></label>
                </div>
                <button class="btn btn-secondary dropdown-toggle" id="dropDownGroup" type="button"
                        data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                    ${filmsOnPage}
                </button>
                <form method="post" action="controller">
                    <input type="hidden" name="command" value="admin_page"/>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                        <button class="dropdown-item" type="input" name="filmsOnPage" value="5">5</button>
                        <button class="dropdown-item" type="input" name="filmsOnPage" value="10">10</button>
                        <button class="dropdown-item" type="input" name="filmsOnPage" value="15">15</button>
                        <button class="dropdown-item" type="input" name="filmsOnPage" value="20">20</button>
                    </div>
                </form>
            </div>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col"><fmt:message key="Label.FilmName"/></th>
                    <th scope="col"><fmt:message key="Film.Description"/></th>
                    <th scope="col"><fmt:message key="Film.YearOfCreation"/></th>
                    <th scope="col"><fmt:message key="Film.genre"/></th>
                    <th scope="col"><fmt:message key="Label.Action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${films}" var="film">
<%--                    <c:forEach items="${filmsInfo}" var="filmInfo">--%>
                    <tr>
                        <td>${film.id}</td>
                        <td>${film.filmName}</td>
                        <td><fmt:message key="${film.filmInfo.description}"/></td>
                        <td>${filmInfo.yearOfCreation}</td>
                        <td><fmt:message key="${filmInfo.genre}"/></td>
                        <td>
                            <div class="dropdown">
                                <a class="btn btn-info dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    ...
                                </a>

                                <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                    <a class="dropdown-item" href="controller?command=view_film_page&id=${film.id}&filmName=${film.filmName}&realName=${film.realName}">
                                        <fmt:message key="View.film"/>
                                    </a>
                                    <a class="dropdown-item" href="controller?command=view_profile&email=${user.email}">
                                        <fmt:message key="View.profile"/>
                                    </a>
                                    <a class="dropdown-item" href="#">Something else here</a>
                                </div>
                            </div>
                        </td>
                    </tr>
<%--                    </c:forEach>--%>
                </c:forEach>
                </tbody>
            </table>
            <nav aria-label="Page navigation example">
                <ul class="pagination pg-blue">
                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <li class="page-item active"><span class="page-link">
                                    ${i} <span class="sr-only">(current)</span>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         href="controller?command=admin_page_films&filmsOnPage=${filmsOnPage}&currentPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?command=admin_page_films&filmsOnPage=${filmsOnPage}&currentPage=${currentPage+1}"><fmt:message
                                key="Label.next"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
    <c:import url="/jsp/common/footer.jsp"/>
</div>
</html>


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
        <div class="row">
            <div class="col-4">
            </div>
            <div class="col-4">
            </div>
            <div class="col-4">
                <br>
                <br>
                <form name="creatorSubmit" method="post"
                      action="${request.getContextPath()}/TaskWebLogin_war/controller"
                      class="register-button">
                    <button type="submit" class="btn btn-primary btn-circle"><i class="fas fa-map"></i>+</button>
                    <input type="hidden" name="command" value="forward"/>
                    <input type="hidden" name="page" value="/jsp/admin/filmCreator.jsp"/>
                </form>
            </div>
        </div>
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
                <form method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller">
                    <input type="hidden" name="command" value="admin_page_films"/>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                        <button class="dropdown-item" type="input" name="filmsOnPage" value="4">4</button>
                        <button class="dropdown-item" type="input" name="filmsOnPage" value="8">8</button>
                        <button class="dropdown-item" type="input" name="filmsOnPage" value="12">12</button>
                        <button class="dropdown-item" type="input" name="filmsOnPage" value="16">16</button>
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
                    <th scope="col"><fmt:message key="Film.active"/></th>
                    <th scope="col"><fmt:message key="Label.Action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${films}" var="film">
                    <tr>
                        <td><c:out value="${film.filmId}"/></td>
                        <td><c:out value="${film.filmName}"/></td>
                        <td><c:out value="${film.filmInfo.description}"/></td>
                        <td><c:out value="${film.filmInfo.yearOfCreation}"/></td>
                        <td><c:out value="${film.filmInfo.genre}"/></td>
                        <td><c:out value="${film.active}"/></td>
                        <td>
                            <div class="dropdown">
                                <a class="btn btn-primary dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    ...
                                </a>

                                <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                    <a class="dropdown-item"
                                       href="controller?command=view_film_page&filmId=${film.filmId}&filmName=${film.filmName}&filmAvatar=${film.filmAvatar}">
                                        <fmt:message key="View.film"/>
                                    </a>
                                    <a class="dropdown-item"
                                       href="controller?command=forward_to_edit_film&filmId=${film.filmId}&currentFilmLang=en&yearOfCreation=${film.filmInfo.yearOfCreation}&active=${film.active}">
                                        <fmt:message key="Label.Edit_film"/>
                                    </a>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <nav aria-label="Page navigation example">
                <ul class="pagination pg-blue">
                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentFilmPage eq i}">
                                <li class="page-item active"><span class="page-link">
                                    ${i} <span class="sr-only">(current)</span>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         href="controller?command=admin_page_films&filmsOnPage=${filmsOnPage}&currentFilmPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentFilmPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?command=admin_page_films&filmsOnPage=${filmsOnPage}&currentFilmPage=${currentFilmPage+1}"><fmt:message
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


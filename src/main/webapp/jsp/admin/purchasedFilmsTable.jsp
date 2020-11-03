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
    <title><fmt:message key="Label.PurchasedFilms"/></title>
    <style>
        <%@include file="/css/style.css" %>
    </style>
</head>
<c:import url="/jsp/common/header.jsp"/>
<div class="Admin_Page">
    <div class="container-sm">
        <fieldset disabled class="sign_in">
            <legend><fmt:message key="Label.PurchasedFilms"/></legend>
        </fieldset>
        <div class="row">
            <div class="dropdown">
                <div class="input-group-prepend">
                    <label class="input-group-text" for="dropDownGroup"><fmt:message key="Label.chooseUsers"/></label>
                </div>
                <button class="btn btn-secondary dropdown-toggle" id="dropDownGroup" type="button"
                        data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                    ${purchasedFilmsOnPage}
                </button>
                <form method="post" action="controller">
                    <input type="hidden" name="command" value="purchased_film_table"/>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                        <button class="dropdown-item" type="input" name="purchasedFilmsOnPage" value="4">4</button>
                        <button class="dropdown-item" type="input" name="purchasedFilmsOnPage" value="8">8</button>
                        <button class="dropdown-item" type="input" name="purchasedFilmsOnPage" value="12">12</button>
                        <button class="dropdown-item" type="input" name="purchasedFilmsOnPage" value="16">16</button>
                    </div>
                </form>
            </div>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Email</th>
                    <th scope="col"><fmt:message key="Label.User_name"/></th>
                    <th scope="col"><fmt:message key="Label.Sex"/></th>
                    <th scope="col"><fmt:message key="Label.Country"/></th>
                    <th scope="col"><fmt:message key="Label.FilmName"/></th>
                    <th scope="col"><fmt:message key="Film.Description"/></th>
                    <th scope="col"><fmt:message key="Film.YearOfCreation"/></th>
                    <th scope="col"><fmt:message key="Film.genre"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${purchasedFilms}" var="purchasedFilm">
                    <tr>
                        <td><c:out value="${purchasedFilm.key.email}"/></td>
                        <td><c:out value="${purchasedFilm.key.name}"/></td>
                        <td><c:out value="${purchasedFilm.key.userGender}"/></td>
                        <td><c:out value="${purchasedFilm.key.country}"/></td>
                        <td><c:out value="${purchasedFilm.value.filmName}"/></td>
                        <td><c:out value="${purchasedFilm.value.filmInfo.description}"/></td>
                        <td><c:out value="${purchasedFilm.value.filmInfo.yearOfCreation}"/></td>
                        <td><c:out value="${purchasedFilm.value.filmInfo.genre}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <nav aria-label="Page navigation example">
                <ul class="pagination pg-blue">
                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPurchasedFilmPage eq i}">
                                <li class="page-item active"><span class="page-link">
                                    ${i} <span class="sr-only">(current)</span>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         href="controller?command=purchased_film_table&purchasedFilmsOnPage=${purchasedFilmsOnPage}&currentPurchasedFilmPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPurchasedFilmPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?command=purchased_film_table&purchasedFilmsOnPage=${purchasedFilmsOnPage}&currentPurchasedFilmPage=${currentPurchasedFilmPage+1}">
                            <fmt:message key="Label.next"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
    <c:import url="/jsp/common/footer.jsp"/>
</div>
</html>

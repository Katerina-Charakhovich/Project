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
    <title><fmt:message key="Label.Welcome"/></title>
    <style>
        <%@include file="/css/style.css" %>
    </style>
</head>
<c:import url="/jsp/common/header.jsp"/>

<div class="startPage">
    <div class="start_container">
        <div class="container-sm">
            <div class="row">
                <div class="col-8">
                    <div id="carouselExampleFade" class="carousel slide carousel-fade" data-ride="carousel">
                        <div class="carousel-inner">
                            <div class="carousel-item active" data-interval="2000">
                                <img src="pictures_for_project/jocker.jpg" class="d-block w-100" alt="...">
                            </div>
                            <div class="carousel-item" data-interval="2000">
                                <img src="pictures_for_project/vedmak.jpg" class="d-block w-100" alt="...">
                            </div>
                            <div class="carousel-item" data-interval="2000">
                                <img src="pictures_for_project/ironMan.jpg" class="d-block w-100" alt="...">
                            </div>
                        </div>
                        <a class="carousel-control-prev" href="#carouselExampleFade" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#carouselExampleFade" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                    <script>
                        $(function () {
                            $('#carouselExampleFade').carousel({
                                interval: 10000,
                                keyboard: false,
                                pause: 'hover',
                                ride: 'carousel',
                                wrap: false
                            });
                        });
                    </script>
                </div>
                <div class="col">
                    <div class="row" id="poster-row1">
                        <div class="col"><img src="pictures_for_project/posters_en/inception.jpg" alt=""
                                              width="131">
                        </div>
                        <div class="col"><img src="pictures_for_project/posters_ru/john_wick2.jpg" alt=""
                                              width="131">
                        </div>
                        <div class="col-3"></div>
                    </div>
                    <div class="row">
                        <div class="col"><img
                                src="pictures_for_project/posters_ru/three_billboards_outside_ebbing_missouri.jpg"
                                alt="" width="131"></div>
                        <div class="col"><img src="pictures_for_project/posters_ru/Tenet.jpg" alt="" width="131">
                        </div>
                        <div class="col-3"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container-sm">
        <div class="dropdown">
            <div class="input-group-prepend">
                <label class="input-group-text" for="dropDownGroup"><fmt:message key="Label.choose"/></label>
            </div>
            <button class="btn btn-secondary dropdown-toggle" id="dropDownGroup" type="button" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                ${filmsOnPage}
            </button>
            <form method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller">
                <input type="hidden" name="command" value="init_start_page"/>
                <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                    <button class="dropdown-item" type="input" name="filmsOnPage" value="4">4</button>
                    <button class="dropdown-item" type="input" name="filmsOnPage" value="8">8</button>
                    <button class="dropdown-item" type="input" name="filmsOnPage" value="12">12</button>
                    <button class="dropdown-item" type="input" name="filmsOnPage" value="16">16</button>
                    <button class="dropdown-item" type="input" name="filmsOnPage" value="20">20</button>
                </div>
            </form>
        </div>
        <div class="row row-cols-4">
            <c:forEach items="${films}" var="film">
                <a href="controller?command=film&filmId=${film.filmId}&filmName=${film.filmName}&filmAvatar=${film.filmAvatar}">
                    <div class="col-10">
                        <br/>
                        <div><img src="pictures_for_project/posters_${fn:toLowerCase(lang)}/${film.filmAvatar}" alt=""
                                  width="200">
                        </div>
                        <span class="font-weight-bold text-info"><c:out value="${film.filmName}"/></span>
                    </div>
                </a>
            </c:forEach>
        </div>
        <br>
        <br>
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
                                                     href="controller?command=init_start_page&filmsOnPage=${filmsOnPage}&currentFilmPage=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentFilmPage lt noOfPages}">
                    <li class="page-item"><a class="page-link"
                                             href="controller?command=init_start_page&filmsOnPage=${filmsOnPage}&currentFilmPage=${currentFilmPage+1}"><fmt:message
                            key="Label.next"/></a>
                    </li>
                </c:if>
            </ul>
        </nav>
        <c:import url="/jsp/common/footer.jsp"/>
    </div>
</div>

</html>

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
        <fieldset disabled class="sign_in">
            <c:choose>
                <c:when test="${currentFilmLang == 'en'}">
                    <legend><fmt:message key="Label.Edit_film_en"/></legend>
                </c:when>
                <c:otherwise>
                    <legend><fmt:message key="Label.Edit_film_ru"/></legend>
                </c:otherwise>
            </c:choose>
        </fieldset>
        <div class="row">
            <div class="col-4">
            </div>
            <div class="col-4">
            </div>
            <div class="col-4">
                <br>
                <br>
                <div class="row">
                    <div class="col-4">
                    </div>
                    <div class="col-4">
                        <c:choose>
                            <c:when test="${currentFilmLang == 'en'}">
                                <form name="creatorSubmit" method="post"
                                      action="${request.getContextPath()}/TaskWebLogin_war/controller"
                                      class="register-button">
                                    <button type="submit" class="btn btn-primary"><i class="fas fa-map"></i>
                                        <fmt:message key="Label.TranslateFilmRu"/>
                                    </button>
                                    <input type="hidden" name="command" value="forward_to_edit_film"/>
                                    <input type="hidden" name="currentFilmLang" value="ru"/>
                                    <input type="hidden" name="filmId" value="${filmId}"/>
                                    <input type="hidden" name="yearOfCreation" value="${yearOfCreation}"/>
                                </form>
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="col-4">
            </div>
                </div>
        </div>
        </div>
        <div class="row">
            <div class="col-4">
                <div class="upload text-info">
                    <div><img src="pictures_for_project/posters_${currentFilmLang}/${filmAvatar}"
                              alt="" width="200"/></div>
                </div>
                <br>
                <br>
                    <form action="${request.getContextPath()}/TaskWebLogin_war/upload?avatar=film_${currentFilmLang}"
                          enctype="multipart/form-data"
                          method="post">
                        <div class="custom-file">
                            <input type="file" size="30px" class="custom-file-input" name="content" id="validatedCustomFile"
                                   required>
                            <label class="custom-file-label"  for="validatedCustomFile"><fmt:message
                                    key="Film.ChoosePhoto"/></label>
                        </div>
                        <div class="butt-save">
                            <button class="btn btn-outline-primary " type="submit"><fmt:message
                                    key="Submit.Save"/></button>
                        </div>
                    </form>
                </div>
            <div class="col-3">
            </div>
            <div class="col-5">
                <form name="filmCreatorForm" method="post"
                      action="${request.getContextPath()}/TaskWebLogin_war/controller">
                    <input type="hidden" name="command" value="edit_film"/>
                    <div class="custom-control custom-switch col-form-label-lg">
                        <c:choose>
                            <c:when test="${active == 'true'}">
                                <input type="checkbox" name="active" value="true" class="custom-control-input"
                                       id="customSwitch1" checked>
                                <input type="hidden" name="command" value="edit_film"/>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" name="active" value="false" class="custom-control-input"
                                       id="customSwitch1" >
                                <input type="hidden" name="command" value="edit_film"/>
                            </c:otherwise>
                        </c:choose>
                        <label class="custom-control-label text-light"
                               for="customSwitch1"><strong><fmt:message
                                key="Film.active"/></strong></label>
                    </div>
                    <label for="nameForProperty" class="validationTooltip"><fmt:message
                            key="Label.filmName"/></label>
                    <c:choose>
                        <c:when test="${currentFilmLang == 'en'}">
                            <input type="text" name="filmName"
                                   maxlength="50" minlength="0"
                                   value="${filmName}"
                                   pattern="^[a-zA-Z0-9\s?!,.:'\-]+$" id="nameForProperty"
                                   class="form-control"
                                   aria-label="Sizing example input"
                                   aria-describedby="inputGroup-sizing-large" required>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="filmName"
                                   maxlength="50" value="${filmName}"
                                   minlength="0"
                                   pattern="^[а-яА-ЯёЁ0-9\s?!,.:'\-]+$" id="nameForProperty"
                                   class="form-control"
                                   aria-label="Sizing example input"
                                   aria-describedby="inputGroup-sizing-large" required>
                        </c:otherwise>
                    </c:choose>

                    <label for="exampleFormControlTextarea1" class="profileType"><fmt:message
                            key="Label.filmDescription"/></label>
                    <textarea type="text" name="description" maxlength="1000"
                              minlength="0" class="form-control"
                              aria-label="Sizing example input"
                              aria-describedby="inputGroup-sizing-default"
                              id="exampleFormControlTextarea1">${description}</textarea>
                    <label for="nameForPropertyGenreEnglish" class="validationTooltip"><fmt:message
                            key="Label.filmGenre"/></label>
                    <c:choose>
                        <c:when test="${currentFilmLang == 'en'}">
                            <input type="text" name="genre"
                                   maxlength="50" value="${genre}" id="nameForPropertyGenreEnglish"
                                   minlength="0"
                                   pattern="^[a-zA-Z0-9\s?!,.:'\-]+$"
                                   class="form-control"
                                   aria-label="Sizing example input"
                                   aria-describedby="inputGroup-sizing-large">
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="genre"
                                   maxlength="50" value="${genre}" id="nameForPropertyGenreEnglish"
                                   minlength="0"
                                   pattern="^[а-яА-ЯёЁ0-9\s?!,.:'\-]+$"
                                   class="form-control"
                                   aria-label="Sizing example input"
                                   aria-describedby="inputGroup-sizing-large">
                        </c:otherwise>
                    </c:choose>
                    <label for="nameForPropertyGenreEnglish" class="validationTooltip"><fmt:message
                            key="Label.filmLink"/></label>
                    <input type="text" name="link"
                           maxlength="300" minlength="0"
                           pattern="(https?://)?(www\.)?(yotu\.be/|youtube\.com/)?((.+/)?(watch(\?v=|.+&v=))?(v=)?)([\w_-]{11})(&.+)?"
                           value="${link}" id="nameForPropertyLinkEnglish"
                           class="form-control"
                           aria-label="Sizing example input"
                           aria-describedby="inputGroup-sizing-large">
                    <div class="row">
                        <div class="col-5">
                            <c:choose>
                                <c:when test="${currentFilmLang == 'en'}">
                                    <label for="FormYearOfCreation" class="validationTooltip"><fmt:message
                                            key="Film.YearOfCreation"/></label>
                                    <input type="number" minlength="0" name="yearOfCreation"
                                           maxlength="4" value="${yearOfCreation}" id="FormYearOfCreation"
                                           class="form-control"
                                           pattern="^\d{0,4}$"
                                           aria-label="Sizing example input"
                                           aria-describedby="inputGroup-sizing-large">
                                </c:when>
                            </c:choose>
                        </div>
                        <div class="col-1">
                        </div>
                        <div class="col-5">
                        </div>
                    </div>
                    <c:if test="${errorData == true}">
                        <div class="validation"><fmt:message key="${field}"/>: <fmt:message key="Message.data"/></div>
                    </c:if>
                    <div class="submit-next">
                        <input class="btn btn-outline-primary" type="submit"
                               value="<fmt:message key="Submit.Save"/>">
                        <input type="hidden" name="currentFilmLang" value="${currentFilmLang}"/>
                        <input type="hidden" name="yearOfCreation" value="${yearOfCreation}"/>
                        <input type="hidden" name="filmId" value="${filmId}"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <c:import url="/jsp/common/footer.jsp"/>
</div>
</html>

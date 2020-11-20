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
            <legend><fmt:message key="Label.users"/></legend>
        </fieldset>
        <div class="row">
            <div class="dropdown">
                <div class="input-group-prepend">
                    <label class="input-group-text" for="dropDownGroup"><fmt:message key="Label.chooseUsers"/></label>
                </div>
                <button class="btn btn-secondary dropdown-toggle" id="dropDownGroup" type="button"
                        data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                    ${usersOnPage}
                </button>
                <form method="post" action="${request.getContextPath()}/TaskWebLogin_war/controller">
                    <input type="hidden" name="command" value="admin_page"/>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                        <button class="dropdown-item" type="input" name="usersOnPage" value="4">4</button>
                        <button class="dropdown-item" type="input" name="usersOnPage" value="8">8</button>
                        <button class="dropdown-item" type="input" name="usersOnPage" value="12">12</button>
                        <button class="dropdown-item" type="input" name="usersOnPage" value="16">16</button>
                    </div>
                </form>
            </div>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Email</th>
                    <th scope="col"><fmt:message key="Label.User_name"/></th>
                    <th scope="col"><fmt:message key="Label.Sex"/></th>
                    <th scope="col"><fmt:message key="Label.Country"/></th>
                    <th scope="col"><fmt:message key="Label.Role"/></th>
                    <th scope="col"><fmt:message key="Label.Locked"/></th>
                    <th scope="col"><fmt:message key="Label.Action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td><c:out value="${user.userId}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.userGender}"/></td>
                        <td><c:out value="${user.country}"/></td>
                        <td><c:out value="${user.userRole}"/></td>
                        <c:choose>
                            <c:when test="${user.locked == true}">
                                <td><fmt:message key="Label.Yes"/></td>
                            </c:when>
                            <c:otherwise>
                                <td><fmt:message key="Label.No"/></td>
                            </c:otherwise>
                        </c:choose>
                        <td>
                            <div class="dropdown">
                                <a class="btn btn-primary dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    ...
                                </a>

                                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuLink">
                                    <a class="dropdown-item"
                                       href="controller?command=admin_page&lock=${user.locked}&email=${user.email}&currentUsersPage=${currentUsersPage}&usersOnPage=${usersOnPage}">
                                        <c:choose>
                                            <c:when test="${user.locked eq 'false'}">
                                                <fmt:message key="Locked.Lock"/>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:message key="Locked.Unlock"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                    <a class="dropdown-item" href="controller?command=view_profile&email=${user.email}">
                                        <fmt:message key="View.profile"/>
                                    </a>
                                    <a class="dropdown-item"
                                       href="controller?command=admin_page&make_admin=${make_admin}&email=${user.email}&currentUsersPage=${currentUsersPage}&usersOnPage=${usersOnPage}">
                                        <fmt:message key="Label.Appoint_admin"/>
                                    </a>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <nav class="app-pagination" aria-label="Page navigation example">
                <ul class="pagination pg-blue">
                    <c:forEach begin="1" end="${noOfPagesUserTable}" var="i">
                        <c:choose>
                            <c:when test="${currentUsersPage eq i}">
                                <li class="page-item active"><span class="page-link">
                                    ${i} <span class="sr-only">(current)</span>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         href="controller?command=admin_page&usersOnPage=${usersOnPage}&currentUsersPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentUsersPage lt noOfPagesUserTable}">
                        <li class="page-item"><a class="page-link"
                                                 href="controller?command=admin_page&usersOnPage=${usersOnPage}&currentUsersPage=${currentUsersPage+1}">
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

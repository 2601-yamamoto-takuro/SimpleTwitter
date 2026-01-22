<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>簡易Twitter</title>
		<link href="./css/style.css" rel="stylesheet" type="text/css">
    </head>
	<body>
		<div class="main-contents">
			<c:if test="${ not empty loginUser }">
			    <div class="profile">
			        <div class="name"><h2><c:out value="${loginUser.name}" /></h2></div>
			        <div class="account">@<c:out value="${loginUser.account}" /></div>
			        <div class="description"><c:out value="${loginUser.description}" /></div>
			    </div>
			</c:if>

			<c:if test="${ not empty errorMessages }">
			    <div class="errorMessages">
			        <ul>
			            <c:forEach items="${errorMessages}" var="errorMessage">
			                <li><c:out value="${errorMessage}" />
			            </c:forEach>
			        </ul>
			    </div>
			    <c:remove var="errorMessages" scope="session" />
			</c:if>

		    <div class="form-area">
			    <form action="edit" method="post">
				    <input type="hidden" name="messageid" value="${message.id}">
				    <textarea name="text">${message.text}</textarea>
				    <br />
				    <button type="submit">更新</button>
				</form>
			</div>
			<a href="./">戻る</a>
			<div class="copyright"> Copyright(c)YourName</div>
		</div>
    </body>
</html>
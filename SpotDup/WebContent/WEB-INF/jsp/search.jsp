<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
<style>
.login {
	width: 300px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
</head>
<body>
	<div class="login">
	<h1>Search Page</h1>
	<i>${message}</i>
	<br />
	<form:form method="POST" commandName="employee"
		action="${pageContext.request.contextPath}/searchResults">
		<table>
			<tbody>
				<tr>
					<td>Employee name:</td>
					<td><form:input path="name" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Search" /></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</form:form>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<a href="${pageContext.request.contextPath}/add">Add Employee</a>
	</sec:authorize>
	
	User : ${pageContext.request.userPrincipal.name} | 	<c:url var="url" value="/j_spring_security_logout"></c:url><b> <a href="${url}">Logout</a> </b>
	
</div>
</body>
</html>
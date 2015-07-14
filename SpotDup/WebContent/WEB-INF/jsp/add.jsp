<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add</title>
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
	<h1>Add Page</h1>
	<form:form method="POST" commandName="employee"
		action="${pageContext.request.contextPath}/addEmployee">
		<table id="table">
			<tbody>
				<tr>
					<td>Employee Name:</td>
					<td><form:input path="name" /></td>
					<td><form:errors path="name"></form:errors></td>
				</tr>
				<tr>
					<td>Employee Job Title:</td>
					<td><form:input path="jobTitle" /></td>
					<td><form:errors path="jobTitle"></form:errors></td>
				</tr>
				<tr>
					<td>Employee E-Mail:</td>
					<td><form:input path="email" /></td>
					<td><form:errors path="email"></form:errors></td>
				</tr>
				<tr>
					<td>Employee Phone Number:</td>
					<td><form:input path="phoneNumber" /></td>
					<td><form:errors path="phoneNumber"></form:errors></td>
				</tr>
				<tr>
					<td>Employee CellPhone Number:</td>
					<td><form:input path="cellphoneNumber" /></td>
					<td><form:errors path="cellphoneNumber"></form:errors></td>
				</tr>

				<tr>
					<td><input type="submit" value="Add" /></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</form:form>
	<a href="search">Search again</a>
	User : ${pageContext.request.userPrincipal.name} | 	<c:url var="url" value="/j_spring_security_logout"></c:url><b> <a href="${url}">Logout</a> </b>
</div>
</body>
</html>
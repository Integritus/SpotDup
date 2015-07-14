<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
<style>
.login {
	width: 450px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
table, td, th {
    border: 1px solid black;
}

td {
    padding: 5px;
}
table {
    border-collapse: collapse;
}
</style>
</head>
<body>
	<div class="login">
	<h1>Employees</h1>
	<table>
		<tbody>

			<c:choose>
				<c:when test="${empty employee}">
					<tr>
						<td colspan=5>No employees by that search string.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td>Id</td>
						<td>Name</td>
						<td>Job Title</td>
						<td>Email</td>
						<td>Phone number</td>
						<td>CellPhone number</td>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<td>Edit/Delete</td>
						</sec:authorize>

					</tr>

				</c:otherwise>
			</c:choose>
			<c:forEach var="e" items="${employee}">
				<tr>
					<td>${e.id}</td>
					<td>${e.name}</td>
					<td>${e.jobTitle}</td>
					<td>${e.email}</td>
					<td>${e.phoneNumber}</td>
					<td>${e.cellphoneNumber}</td>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<td><a href="${pageContext.request.contextPath}/edit/${e.id}">Edit</a>/
							<a id="delete${e.id}" onClick="myFunction(${e.id})" href="${pageContext.request.contextPath}/delete/${e.id}">Delete</a></td>
					</sec:authorize>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="search">Search again</a>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<a href="${pageContext.request.contextPath}/add">Add Employee</a>
	</sec:authorize>
	User : ${pageContext.request.userPrincipal.name} |
	<c:url var="url" value="/j_spring_security_logout"></c:url>
	<b> <a href="${url}">Logout</a>
	</b>
</div>
<script>
function myFunction(id) {
    var a = document.getElementById("delete" + id);
    if (confirm("Press a button! " + id) == true) {
    	a.setAttribute("href", "${pageContext.request.contextPath}/delete/" + id);
    } else {
    	a.setAttribute("href", "#");
    }
    
}
</script>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Playlists</title>
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
	<h1>Playlists</h1>
	<table>
		<tbody>

			<c:choose>
				<c:when test="${empty playlists}">
					<tr>
						<td colspan=5>No playlists</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td>Id</td>
						<td>Name</td>

					</tr>

				</c:otherwise>
			</c:choose>
			<c:forEach var="p" items="${playlists}">
				<tr>
					<td>${p.id}</td>
					<td>${p.name}</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>	
</div>

</body>
</html>

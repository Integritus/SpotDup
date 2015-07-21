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
	<h1>Duplicates</h1>
	<table>
		<tbody>

			<c:choose>
				<c:when test="${empty tracks}">
					<tr>
						<td colspan=5>No duplicates</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td>Name</td>						
						<td>Artists</td>
						<td>Album</td>
					</tr>

				</c:otherwise>
			</c:choose>
			<c:forEach var="t" items="${tracks}">
				<tr>					
					<td>${t.name}</td>
					<td><c:forEach var="a" items="${t.artists}">
						${a.name}<br>
					</c:forEach>
					</td>
					<td>${t.album.name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
</div>

</body>
</html>

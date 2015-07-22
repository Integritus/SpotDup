<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>SpotDup - A Web App to remove duplicates on your Spotify
	playlists</title>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<style type="text/css">
#login, #loggedin {
	display: none;
}

.text-overflow {
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 500px;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<ul class="nav nav-pills">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#">Profile</a></li>
					<li class="disabled"><a href="#">Messages</a></li>
					<li class="dropdown pull-right"><a href="#"
						data-toggle="dropdown" class="dropdown-toggle">Dropdown<strong
							class="caret"></strong></a>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<h3 class="text-center text-primary">Tracks that where removed</h3>
				<table class="table">
					<thead>
						<c:choose>
							<c:when test="${empty tracks}">
								<tr>
									<th colspan=2>No Duplicates</th>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<th>Name</th>
									<th>Artists</th>
									<th>Album</th>
								</tr>

							</c:otherwise>
						</c:choose>

					</thead>
					<tbody>
						<c:forEach var="t" items="${tracks}">
							<tr>
								<td>${t.name}</td>
								<td><c:forEach var="a" items="${t.artists}">${a.name}<br>
									</c:forEach></td>
								<td>${t.album.name}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="row">
					<div class="col-md-6 text-center">
					
						<a href="${pageContext.request.contextPath}/showPlaylists"
					class="btn btn-primary">Back</a>
					</div>
					<div class="col-md-6 text-center">

						<a href="${pageContext.request.contextPath}/logout"
					class="btn btn-primary disabled">Logout</a>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>

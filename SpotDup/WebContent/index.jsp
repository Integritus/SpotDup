<!doctype html>
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
			<div class="col-md-12 text-center">
				<h3 class="text-primary">SpotDup - A Web App to
					remove duplicates on your Spotify Playlists</h3>
				<a href="${pageContext.request.contextPath}/login"
					class="btn btn-primary">Log in with Spotify</a>
			</div>
		</div>
	</div>
</body>

</html>


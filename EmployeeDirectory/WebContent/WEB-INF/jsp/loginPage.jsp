<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<title>Login Page</title>
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

<body onload='document.loginForm.username.focus();'>
  <div class="login">
	
 
		<h1>Login</h1>
 
		<c:if test="${not empty error}">
			${error}
		</c:if>
		<c:if test="${not empty msg}">
			${msg}
		</c:if>
 
		<form name='loginForm'
		  action="<c:url value='/login' />" method='POST'>

		<table>
			<tr>
				<td>User</td>
				<td><input type='text' name='username'></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
				  value="submit" /></td>
			</tr>
		  </table>

		</form>
	
  </div>
</body>
</html>
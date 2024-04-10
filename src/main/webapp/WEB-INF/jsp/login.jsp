<html>
<head>
<title>First Web Application</title>
</head>
<body>
    <!-- start header -->
    	<div id="header">
    		<div id="menu">
    			<ul>
    				<li><a href="/register">Register</a></li>
    			</ul>
    		</div>
    	</div>
    	<!-- end header -->
    	<!-- start page -->
    	<div id="page">
    		<!-- start content -->
    		<div id="content">
    			<div class="post">
    				<h1 class="title">Welcome to Campus Portal - Login</h1>
    			</div>
    		</div>
    	</div>
    <!-- end page -->
    <!-- ======================================================================================================== -->

    <font color="red">${errorMessage}</font>
    <h3>Login</h3>
    		<div style="height: 242px; margin-left: 4px">
    			<form action="/login" method="post" name="form2" id="form2">
    				<table align="center" style="width: 38%; height: 118px;">
    					<tr>
    						<td class="auto-style5"><strong>User ID&nbsp;&nbsp;&nbsp; :</strong></td>
    						<td class="auto-style4">
    							<input id="uid" type="text" name="uid" />
    						</td>
    					</tr>
    					<tr>
    						<td class="auto-style5"><strong>Password&nbsp; :</strong></td>
    						<td class="auto-style4">
    							<input id="pwd" type="password" name="pwd" />
    						</td>
    					</tr>
    					<tr>
    						<td class="auto-style5"><strong>UserType :</strong></td>
    						<td class="auto-style4">
    							<select id="usertype" name="usertype">
    								<option value="1">Student</option>
    								<option value="2">Head of department</option>
    								<option value="3">Teacher</option>
    							</select>
    						</td>
    					</tr>
    				</table>
    				<br />
    				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    				<input id="Submit1" type="button" value="submit"
    					onclick="valid()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="Reset1" type="reset"
    					value="reset" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			</form>
            </div>
</body>
</html>
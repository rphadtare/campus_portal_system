<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Student Home</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="default.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!-- start page -->
<div id="page">
<!-- 	start content -->
	<div id="content">
		<div class="post">
			<h1 class="title">Welcome to Campus Portal</h1>
		</div>
	</div>
</div>
<!-- end page -->
<!-- ======================================================================================================== -->

<div style="height: 448px">
    	<h3>Welcome ${user_name} !!</h3>
        <iframe height="448px" width="200" src="frame1.jsp" name="frame1">
        </iframe>
       <iframe height="448" name="frame2" width="900">
        </iframe>
	   
    </div>
<!-- ======================================================================================================== -->
<div id="footer">
	<p id="legal">&copy;2023 Compaus Portal. All Rights Reserved. | Designed by Rutu</p>
</div>
</body>
</html>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
	<h1>Hello world!</h1>
	<button id="test">测试</button>
	<script>
		function escapeScript(s){
			s = s.replace(/</g,'&lt;');
			s = s.replace(/>/g,'&gt;');
			s = s.replace(/%3C/g,'&lt;');
			s = s.replace(/%3E/g,'&gt;');
			return s;
		}
		document.getElementById('test').onclick = function(){
			var url = location.href;
			alert(url);
			url = url.replace(/</g,'&lt;');
			url = url.replace(/>/g,'&gt;');
			url = url.replace(/%3C/g,'&lt;');
			url = url.replace(/%3E/g,'&gt;');
			alert(url);
			location.href = url;
		}
	</script>
</body>
</html>
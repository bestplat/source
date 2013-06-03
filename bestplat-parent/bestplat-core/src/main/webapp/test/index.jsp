<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Main Page Test(Compatible IE)</title>

<link rel="stylesheet" type="text/css" href="<c:url value="/static/js/jquery/plugins/bootstrap2/css/bootstrap.css"/>"/>

<!--[if lte IE 6]>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/js/jquery/plugins/bootstrap2/css/bootstrap-ie6.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/js/jquery/plugins/bootstrap2/css/ie.css"/>"/>
<![endif]-->
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="#">微思奇</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">开始<b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="#">动作</a></li>
                  <li><a href="#">其它动作</a></li>
                  <li><a href="#">其它事情</a></li>
                  <li class="divider"></li>
                  <li class="nav-header">分隔</li>
                  <li><a href="#">链接</a></li>
                  <li><a href="#">更多</a></li>
                </ul>
              </li>
              <li><a href="#">选购产品</a></li>
              <li><a href="#">联系我们</a></li>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">语言<b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="#">简体中文</a></li>
                  <li><a href="#">繁體中文</a></li>
                  <li><a href="#">English</a></li>
                </ul>
              </li>
              
            </ul>
            <form class="navbar-form pull-right">
              <input class="span2" id="email" type="text" placeholder="电子邮箱">
              <input class="span2" id="password" type="password" placeholder="密码">
              <button type="submit" class="btn">登陆</button>
              <button type="submit" class="btn">注册</button>
            </form>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="hero-unit">
        <h2>微思奇(VSSQ)，在线企业应用平台</h2>
        <p>我们的理念是简约，高效、专业和智能，专注于为中小企业提供简约高效且安全稳定的在线应用。目前引进最优秀班子，主力打造客户关系管理系统（CRM），誓做全球最好用的CRM系统。</p>
        <p><a href="#" class="btn btn-primary btn-large">了解更多 &raquo;</a></p>
      </div>

      <!-- Example row of columns -->
      <div class="row">
        <div class="span3">
          <h2>简约与定制</h2>
          <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
          <p><a class="btn" href="#">View details &raquo;</a></p>
        </div>
        <div class="span3">
          <h2>引导式操作</h2>
          <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
          <p><a class="btn" href="#">View details &raquo;</a></p>
       </div>
        <div class="span3">
          <h2>高数据质量</h2>
          <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
          <p><a class="btn" href="#">View details &raquo;</a></p>
        </div>
        <div class="span3">
          <h2>安全与稳定</h2>
          <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
          <p><a class="btn" href="#">View details &raquo;</a></p>
        </div>
      </div>

      <hr/>

      <footer>
        <p align="center">&copy;2013 微思奇科技有限公司 </p>
      </footer>

    </div> <!-- /container -->
    
    
    <script type="text/javascript" src="<c:url value="/static/js/jquery/jquery-1.10.1.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/static/js/jquery/plugins/bootstrap2/js/bootstrap.min.js"/>"></script>
	<!--[if lte IE 6]>
	<script type="text/javascript" src="<c:url value="/static/js/jquery/plugins/bootstrap2/js/bootstrap-ie.js"/>"></script>
	<![endif]-->
</body>
</html>
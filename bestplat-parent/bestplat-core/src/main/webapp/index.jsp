<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setAttribute("cp", request.getContextPath());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>首页 - 微思奇创新工作室</title>
<link rel="stylesheet" type="text/css" href="${cp}/static/css/main.css"/>
<link rel="stylesheet" type="text/css" href="${cp}/static/js/jquery/plugins/bootstrap2/css/bootstrap.css"/>
<!--[if lte IE 6]>
<link rel="stylesheet" type="text/css" href="${cp}/static/js/jquery/plugins/bootstrap2/css/bootstrap-ie6.css"/>
<link rel="stylesheet" type="text/css" href="${cp}/static/js/jquery/plugins/bootstrap2/css/ie.css"/>
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
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;微思奇是领先的企业云计算平台，致力于帮助企业以最低成本获取最大收益。CRM、BI、OA等企业经营所需的各种关键应用，微思奇都将提供。您和您的团队可随时随地、通过任意设备访问微思奇。微思奇还提供开放应用平台，邀请开发人员注册，您可以雇佣开发人员在微思奇上量身定做自己的应用。将企业应用运行在微思奇上，一切唾手可得！</p>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;现在注册，立即奉送价值24元的代金券，您可以通过此代金券购买并试用微思奇的服务。同时，参与推广微思奇的服务，还将获得3%的成交额的酬劳。</p>
        <p><a href="#" class="btn btn-primary btn-large">马上注册&raquo;</a></p>
      </div>

      <!-- Example row of columns -->
      <div class="row">
        <div class="span3">
          <h2>简约与定制</h2>
          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您是否对于其他一些企业应用软件，如CRM中繁杂鸡肋的功能忍无可忍？微思奇提供的企业应用将从常规企业应用中抽取其中的20%最常用最有效率的功能并做得尽善尽美，让您免于投入巨量的学习和适应成本就能发挥到最优。同时，微思奇平台还提供便利的功能扩展服务，允许您根据实际需要，购买现成组件或雇佣开发人员为您开发最适合您的那部分定制功能，使您和您团队的工作变得天衣无缝。</p>
        </div>
        <div class="span3">
          <h2>引导式操作</h2>
          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;常规的企业应用中，您是否对于冗长的使用说明书感到无从下手呢？微思奇首创世界领先的引导式操作模式，您操作的每一步，系统都将智能的感知并给出最好的建议和帮助，使您以最快的速度熟悉系统的业务流程；同时，系统还将学习和分析您的操作习惯，随着时间的积累，愈来愈精准的协助您快速完成业务流程。赶紧注册试用下吧。</p>
       </div>
        <div class="span3">
          <h2>高质量数据</h2>
          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;企业应用中，最有价值的是业务数据，但随着时间的积累，各种无效的、碎片化的数据愈积愈多，使得应用的效率越来越差（比如CRM中客户资料的过时和变更），进而影响您的业务呢？微思奇提供的应用，使用先进的数据监控、识别、清理和备份机制，实时保持数据的准确性，为您提供最佳效率。</p>
        </div>
        <div class="span3">
          <h2>安全与稳定</h2>
          <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;微思奇使用先进的安全保障机制，在通道上，全站使用256位的SSL加密（参见您的浏览器地址栏的https标识），并采用正向加密技术（ECDHE_RSA），确保无人能够截取您的业务数据；账户安全上，微思奇采用实名认证机制，确保您的账号的最高安全性和可收回性；至于稳定，微思奇平台专人24小时维护和监控服务器状态，采用集群方式部署，确保服务不中断。同时，微思奇还将保持运营企业应用的各个历史版本，您可以自由选择是否升级版本。</p>
        </div>
      </div>

      <hr/>

      <footer>
        <p align="center">&copy;2013 微思奇创新工作室 </p>
      </footer>

    </div> <!-- /container -->
    
    <script type="text/javascript" src="${cp}/static/js/modernizr.custom.js"></script>
    <script type="text/javascript" src="${cp}/static/js/jquery/jquery-1.10.1.min.js"></script>
	<script type="text/javascript" src="${cp}/static/js/jquery/plugins/bootstrap2/js/bootstrap.min.js"></script>
	<!--[if lte IE 6]>
	<script type="text/javascript" src="${cp}/static/js/jquery/plugins/bootstrap2/js/bootstrap-ie.js"></script>
	<![endif]-->
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>微信扫码登录</title>

</head>

<body>
<div  id="ma">
</div>
<script src="script/jquery.js" type="text/javascript" ></script>
<script src="script/wxLogin.js" type="text/javascript" ></script>
<script>
var test = 0;
    $(function(){
/* --------------------------展示微信登录二维码----------hxx--------------- */
        $.post("${pageContext.request.contextPath}/index/weixin",function(data){
         var ma = data.shorturl;
         var srcMa = "http://qr.topscan.com/api.php?text="+ma;
        var imgg = "<img class='h-img' alt='' src="+srcMa+">";
        $("#ma").empty().append(imgg);
        });
        $.post("<%=basePath%>index/type",{"a":0});
        panduan();
/* -------------展示微信登录二维码----------hxx------------ */
    })
/*-------------- 微信扫码是否成功的判断--------hxx---------- */
    function panduan(){
    $.post("<%=basePath%>index/successDL",function(data){
    var resp = eval('('+data+')');
        if(resp.type==1){
        alert('1')
            var userId = resp.userId;
            var h = $("#h").val();
            var chapterId = $("#chapterId").val();
            var chapterId = $("#chapterId").val();
            $.post("<%=basePath%>index/type",{"a":0});
            window.location.href='<%=basePath%>index/fangSession?userId='+userId+'&h='+h;
        <%--    window.location.href='<%=basePath%>index/showIndex'; --%>

        }else if(resp.type==2){
        alert('2')
            $.post("<%=basePath%>index/type",{"a":0});
        }else if(resp.type==0 && test!=200){
        alert('0')
            /* setInterval("panduan()",8000); */
            test = test+1;
            panduan();
        }else if(test==300){
        alert('300')
            alert("登录码已失效，请刷新页面更新验证码！");
            $.post("<%=basePath%>index/type",{"a":5});
        } else{
          alert('else')
        }
    });

}
    /*------------------------ 微信扫码是否成功的判断-------------hxx---------- */
</script>
</body>
</html>

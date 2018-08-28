<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.mtm2000.common.util.*"%>

<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	response.flushBuffer();

%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="0">
		<title><%=122122%></title>
		<link href="css/index.css" rel="stylesheet" type="text/css">
		<script src="script/jquery1/jquery.js">
</script>
		<script src="script/jquery.qrcode.js">
</script>
		<script src="script/qrcode.js">
</script>
		<SCRIPT type="text/javascript">
  var url = top.location.href;
  var isChanged = false;
  if (url.substr(url.length - 15) == 'frame/index.jsp') {
    //url = url.substr(0, url.length - 9) + 'index.jsp';
    isChanged = true;
  }
//  if (window.location.protocol == "http:") {
//    url = "https" + url.substring(4);
//    isChanged = true;
//  }

  if (top.location.href != location.href )
     top.location.href = '/nhis/index.jsp';
  
  function checkkey(event){
    if (event.keyCode==13) submitForm();
  }
  function resetForm(){
  	document.getElementById("user").reset();
  }
  function submitForm(){
    if (document.getElementById('userForm.loginName').value == '') {
      alert('用户名为空！');
      document.getElementById('userForm.loginName').focus();
    }
    else if(!checkLoginName()){
      alert('用户名格式有误!');
      document.getElementById('userForm.loginName').focus();
    }
    else if (document.getElementById('userForm.passWord').value == '') {
      alert('密码为空！');
      document.getElementById('userForm.passWord').focus();
    }
    else if(document.all.checkcode.value == ''){
       alert('验证码为空！');
       document.all.checkcode.focus();
    }
    else if(document.all.checkcode.value.length != 4){
       alert('验证码不对！');
       document.all.checkcode.focus();
    }
    else {
      if(document.getElementById("remember").checked){
        var loginName = document.getElementById("userForm.loginName").value;
        setCookie("username",loginName);
      }else{
        delCookie("username");
      }
      //if(confirm("正式系统已启动，请勿在试用系统录入正式数据。是否进入？")){
     document.user.submit();
     // }
      
    }
  }

  function getVerCode(){
    document.getElementById('verimg').src='GetImgNumber?newtime='+new Date().getTime();
  }
  
  //验证用户名
  function checkLoginName(){
    var idcard=document.getElementById('userForm.loginName').value;
    if(!checkIdcard(idcard)){
      if( (idcard.length==6&&(idcard.substr(0,2)=="sy" || idcard.substr(0,2)=="SY" || !isNaN(idcard))) ||  //6位数字胸牌号
          (idcard.length==8&&!isNaN(idcard.substr(0,7))&&(!isNaN(idcard.substr(7))||idcard.substr(7)=='X'||idcard.substr(7)=='x')) ){  //身份证后8位，7位数字+最后一位可为X/x
        return true;
      }
      return false;
    }
    return true;
  }
  
  //身份证验证
  function checkIdcard(idcard){
    var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
    var idcard,Y,JYM;
    var S,M;
    var idcard_array = new Array();
    idcard_array = idcard.split("");
    //地区检验
    if(area[parseInt(idcard.substr(0,2))]==null) return false;
    //身份号码位数及格式检验
    switch(idcard.length){
    case 15:
      if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
        ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性
      } else {
        ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性
      }
      if(ereg.test(idcard)) return true;
      else return false;
      break;
    case 18:
      //18位身份号码检测
      //出生日期的合法性检查
      if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
        ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式
      } else {
        ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式
      }
      if(ereg.test(idcard)){//测试出生日期的合法性
        //计算校验位
        S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
          + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
          + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
          + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
          + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
          + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
          + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
          + parseInt(idcard_array[7]) * 1
          + parseInt(idcard_array[8]) * 6
          + parseInt(idcard_array[9]) * 3 ;
        Y = S % 11;
        M = "F";
        JYM = "10X98765432";
        M = JYM.substr(Y,1);//判断校验位
        if (idcard_array[17] == 'x') idcard_array[17] = 'X';
        if(M == idcard_array[17]) return true; //检测ID的校验位
        else return false;
      }
      else return false;
      break;
    default:
      return false;
      break;
    }
  }
  
function init(){
	
  document.getElementById("userForm.loginName").focus();
  //调用函数
  var loginName = getCookie("username");
  if(loginName!=null){
    document.getElementById("userForm.loginName").value=loginName;
    document.getElementById("userForm.passWord").focus();
    document.getElementById("remember").checked=true;
  }
}
//两个参数，一个是cookie的名子，一个是值
function setCookie(name,value){
  var Days = 365; //此 cookie 将被保存 365 天
  var exp  = new Date();    //new Date("December 31, 9998");
  exp.setTime(exp.getTime() + Days*24*60*60*1000);
  document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

//取cookies函数
function getCookie(name){
  var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
  if(arr != null) return unescape(arr[2]); return null;
}

//删除cookie
function delCookie(name){
  var exp = new Date();
  exp.setTime(exp.getTime() - 1);
  var cval=getCookie(name);
  if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
//--------------------------------------------------------------------------------------
var t;
function getLoginCode(){
	$.ajax({
				type: "post",
				url:   "logincode!addLogincode.do" ,
				success: function(data) {
					$("#qrcodeImg").qrcode({render: "table",
						width:180,
						height:180,
						correctLevel:0,
						text:data,
						correctLevel:QRErrorCorrectLevel.H});
					$("#code-header").click();
					t=setInterval(function(){
					 	ajaxlogin(data);
					 },5000);
				  },
				 error:function(){
				 }
				});
	
}
function ajaxlogin(id){
	var a ="${header['host']}";
	$.ajax({
				type: "post",
				url:   "logincode!mobilecheck.do?id="+id ,
				dataType: "json",
				success: function(data) {
				var userId = data.map;
					if(userId=="2"){
						return null;
					}else if(userId=="3"){
						 clearInterval(t);
					}else{
						 $("#user").attr("action","logincode!login.do")
						 $("#userId").attr("value",userId);
						 $("#user").submit();
					 	//window.location.href="logincode!login.do?userId="+aa+"&url="+a;
					}				
				  },
				  error: function(userId){
				  	}
				});

}

$(function(){
	getVerCode();
})
</SCRIPT>
	</head>
	<body onload="init();getLoginCode();">
		<div id="header">
			<div
				style="margin-left: 25%; width: 1080px; float: left; white-space: nowrap;">
				<div style="width: 10px; float: left;">
					<img src="images/images_yn/login-headImg.png">
				</div>
				<div
					style="width: 950px; float: right; height: 100px; margin-top: 8%;">
					<span style="font-size: 52px; font-weight: bold; color: #FFFFFF;"><%=123414%></span>
				</div>
			</div>
		</div>
		<div style="height: 50px;"></div>
		<div id="content">
			<form name="user" id="user" action="frame!login.do" method="post">
				<table id="login">
					<tr>
						<td>
							<label for="userForm.loginName">
								<div class="labelimg">
									<img src="images/images_yn/login-username.png">
									&nbsp;
								</div>
								<div class="labelinfo">
									用户名&nbsp;&nbsp;&nbsp;
								</div>
							</label>
						</td>
						<td colspan="2" class="pad-right">
							<input class="formInfo" type="text" name="userForm.loginName"
								id="userForm.loginName">
						</td>
						<td rowspan="5" width="300px" id="qrcode" align="center">
							<p id="code-header">
								扫码登录，安全快捷
							</p>
							<br>
							<!--						<img id="qrcodeImg" src="images/images_yn/login-qrcodeImg.png">-->
							<div id="qrcodeImg"></div>
							<br>
							<br>
							<p class="font-small" id="code-footer">
								请使用移动执法系统&nbsp;&nbsp;"扫一扫"
							</p>
						</td>
					</tr>
					<tr>
						<td>
							<label for="userForm.passWord">
								<div class="labelimg">
									<img src="images/images_yn/login-password.png">
									&nbsp;
								</div>
								<div class="labelinfo">
									密 码&nbsp;&nbsp;&nbsp;
								</div>
							</label>
						</td>
						<td colspan="2" class="pad-right">
							<input class="formInfo" type="password" name="userForm.passWord"
								id="userForm.passWord">
							<input type="hidden" name="url" value="${header['host']}">
							<input type="hidden" name="userId" id="userId" />
						</td>
					</tr>
					<tr>
						<td width="140px">
							<label for="checkcode">
								<div class="labelimg">
									<img src="images/images_yn/login-code.png">
									&nbsp;
								</div>
								<div class="labelinfo">
									验证码&nbsp;&nbsp;&nbsp;
								</div>
							</label>
						</td>
						<td width="300px">
							<input class="formInfo" type="text" maxlength="4"
								onkeydown="checkkey(event)" name="checkcode"
								onkeydown="checkkey(event)" id="checkcode">
						</td>
						<td width="170px" class="pad-right">
							<img id="verimg" onclick="getVerCode()" name="verimg" border="0"
								style="cursor: pointer" src="GetImgNumber">
						</td>
					</tr>
					<tr>
						<td></td>
						<td class="font-small" align="left">
							<input name="remember" id="remember" type="checkbox">
							&nbsp;记住用户名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td align="center" class="pad-right">
							<a class="font-small" onclick="getVerCode()"
								style="cursor: pointer;">看不清，换一组</a>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<br>
							<input id="login-submit" onclick="submitForm()" type="button"
								value="登&nbsp;&nbsp;&nbsp;录">
						</td>
						<td></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="footer">
			<p>
				版权所有&nbsp;&nbsp;<%=122 %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;技术支持电话&nbsp;&nbsp;010-84351380&nbsp;&nbsp;010-84351380-6000 （传真）&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创意制作/技术支持&nbsp;&nbsp;北京梦天门科技股份有限公司
			</p>
		</div>
	</body>
</html>
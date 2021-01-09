<!DOCTYPE html>
<html lang="en" class="no-js">

    <head>

        <meta charset="utf-8">
        <title>Shiro Demo | 注册</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- CSS -->
        <#--link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'-->
		<link rel="stylesheet" href="${basePath}/css/login/reset.css"/>
        <link rel="stylesheet" href="${basePath}/css/login/supersized.css"/>
        <link rel="stylesheet" href="${basePath}/css/login/style.css"/>
		<style>
			#vcode >img{cursor:pointer;margin-bottom: -15px;border-radius:5px;}
		</style>
        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
        <script src=${basePath}"/js/common/html5shiv.js"></script>
        <![endif]-->
    </head>

    <body id="body">
        <div class="page-container" style="margin: 100px auto 0px;">
            <h1>Register</h1>
            <form id="_form" action="" method="post">
                <input type="text" name="nickname" id="nickname" class="username" placeholder="Nickname">
                <input type="text" name="email"  id="email" placeholder="Email Account">
                <input type="password" name="pswd" id="password" class="password" placeholder="Password">
                <input type="password" id="re_password"  placeholder="Repeat the password">
                <div style="text-align: left; margin-left: 10px;" id="vcode">
	                <input type="text" name="verifyCode"   placeholder="Verification code" style="width: 110px; margin-left: -8px; margin-right: 8px;">
                	<img src="${basePath}/u/getVerifyCode.shtml" />
                </div>
                <button type="button" class="register">Register</button>
                <button type="button" id="login" >Login</button>
                <div class="error"><span>+</span></div>
            </form>
        </div>

        <!-- Javascript -->
        <script  src="${basePath}/js/common/jquery/jquery1.8.3.min.js"></script>
        <script  src="${basePath}/js/common/MD5.js"></script>
        <script  src="${basePath}/js/common/supersized.3.2.7.min.js"></script>
        <script  src="${basePath}/js/common/supersized-init.js"></script>
		<script  src="${basePath}/js/common/layer/layer.js"></script>
        <script src="${basePath}/js/common/jquery.blockUI.js"></script>
        <script  src="${basePath}/js/common/common.js"></script>
        <script >
			jQuery(document).ready(function() {
				//验证码
				$("#vcode").on("click",'img',function(){
					/**动态验证码，改变地址，多次在火狐浏览器下，不会变化的BUG，故这样解决*/
					var i = new Image();
					i.src = '${basePath}/u/getVerifyCode.shtml?'  + Math.random();
					$(i).replaceAll(this);
					//$(this).clone(true).attr("src",'${basePath}/open/getGifCode.shtml?'  + Math.random()).replaceAll(this);
				});
			    $('.register').click(function(){
			    	var form = $('#_form');
			    	var error= form.find(".error");
			    	var tops = ['27px','96px','165px','235px','304px','372px'];
			    	var inputs = $("form :text,form :password");
			    	for(var i=0;i<inputs.length;i++){
			    		var self = $(inputs[i]);
			    		if(self.val() == ''){
			    			 error.fadeOut('fast', function(){
			               		 $(this).css('top', tops[i]);
				            });
				            error.fadeIn('fast', function(){
				               self.focus();
				            });
				            return !1;
			    		}
			    	}
			    	var re_password = $("#re_password").val();
			    	var password = $("#password").val();
			    	if(password !== re_password){
			    		return layer.msg('2次密码输出不一样！',function(){}),!1;
			    	}
                    let verifyCode = $('[name=verifyCode]').val()
			    	if(verifyCode.length !== 4){
			    		return layer.msg('验证码的长度为4位！',function(){}),!1;
			    	}
			    	$.operate.post("${basePath}/u/submitRegister.shtml",{
			    	    nickname: $('#nickname').val(), email: $('#email').val(),
                        pswd: password, verifyCode
                    } ,function(result){
			    		if(result && result.status === resp_status.SUCCESS){
			    		    setTimeout(function () {
                                window.location.href= "${basePath}/" + result.obj || "${basePath}/";
                            }, 2000)
			    		}
			    	});
			        
			    });
			    $("form :text,form :password").keyup(function(){
			        $(this).parent().find('.error').fadeOut('fast');
			    });
			    //跳转
			    $("#login").click(function(){
			    	window.location.href="login.shtml";
			    });
			    $("#register").click(function(){
			    	window.location.href="register.shtml";
			    });
			    
			
			});
        </script>
    </body>

</html>


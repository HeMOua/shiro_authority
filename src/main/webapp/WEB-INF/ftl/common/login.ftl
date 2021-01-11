<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>登录</title>

    <link rel="stylesheet" href="${basePath}/css/common/style.css">
    <link rel="stylesheet" href="${basePath}/css/login.css">
</head>
<body>

<#--背景-->
<div class="main">
    <#--登录框-->
    <div class="loginFrame">
        <#--标题-->
        <p class="loginTitle">Login</p>
        <#--用户名-->
        <div class="user">
            <label>
                <span>
                    <svg t="1609557860152" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1169" width="20" height="20"><path d="M959.68 921.024c-15.232-181.696-139.648-331.968-307.84-386.624 70.464-45.632 117.248-124.48 117.248-214.464C769.152 178.624 654.208 64 512.512 64 370.752 64 255.808 178.624 255.808 319.936c0 89.984 46.784 168.832 117.248 214.528-168.192 54.592-292.544 204.864-307.84 386.56-0.192 3.456-0.64 5.44 0 10.176C66.496 947.2 80.64 960 96.704 960c17.92 0 32.064-14.656 32.064-32 16.704-197.76 182.272-351.936 383.744-351.936 201.408 0 366.976 154.176 383.68 351.936 0 17.344 14.144 32 32.064 32 16.064 0 30.208-12.8 31.424-28.8C960.32 926.464 959.936 924.416 959.68 921.024zM320 319.936C320 213.952 406.208 128 512.512 128s192.448 85.952 192.448 191.936c0 106.048-86.144 192-192.448 192S320 425.984 320 319.936z" p-id="1170" fill="#ffffff"></path></svg>
                </span>
                <span>用户名</span>
                <input id="username" type="text" placeholder="UserName">
            </label>
        </div>
        <#--密码-->
        <div class="password">
            <label>
                <span>
                    <svg t="1609557900068" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2035" width="20" height="20"><path d="M864 1022H159.6c-52.9 0-96-43.1-96-96V512.2c0-52.9 43.1-96 96-96H864c52.9 0 96 43.1 96 96V926c0 52.9-43.1 96-96 96zM159.6 480.2c-17.6 0-32 14.4-32 32V926c0 17.6 14.4 32 32 32H864c17.6 0 32-14.4 32-32V512.2c0-17.6-14.4-32-32-32H159.6zM799.5 352.2c-17.7 0-32-14.3-32-32 0-140.5-114.3-254.7-254.7-254.7S258 179.8 258 320.2c0 17.7-14.3 32-32 32s-32-14.3-32-32c0-85.1 33.2-165.2 93.4-225.4C347.6 34.7 427.6 1.5 512.8 1.5S678 34.7 738.2 94.9c60.2 60.2 93.4 140.2 93.4 225.4-0.1 17.6-14.4 31.9-32.1 31.9z" p-id="2036" fill="#ffffff"></path><path d="M511.8 866c-81 0-147-65.9-147-147 0-81 65.9-147 147-147 81 0 147 65.9 147 147s-65.9 147-147 147z m0-229.9c-45.7 0-83 37.2-83 83 0 45.7 37.2 83 83 83 45.7 0 83-37.2 83-83s-37.2-83-83-83z" p-id="2037" fill="#ffffff"></path></svg>
                </span>
                <span>密码</span>
                <input id="password" type="password" placeholder="Password">
            </label>
        </div>
        <div class="rememberMe">
            <input type="checkbox" checked> 记住我
        </div>
        <#--登录按钮-->
        <div class="enterBtn">
            <span>登录</span>
        </div>
        <span class="register">
            <a href="${basePath}/u/register.shtml">还没有账号，立即注册</a>
        </span>
    </div>
</div>

</body>
<script src="${basePath}/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="${basePath}/js/common/jquery.blockUI.js"></script>
<script src="${basePath}/js/common/layer/layer.js"></script>
<script src="${basePath}/js/common/common.js"></script>
<script>
    $(function () {
        $('.enterBtn').on('click', function () {
            let username = $('#username').val();
            let password = $('#password').val();
            let check = $('input[type=checkbox]').is(':checked')
            if($.common.isEmpty(username) && $.common.isEmpty(password)){
                $.modal.msgError('用户名和密码不可为空！')
            }else if($.common.isEmpty(username) && !$.common.isEmpty(password)){
                $.modal.msgError('用户名不可为空！')
            }else if(!$.common.isEmpty(username) && $.common.isEmpty(password)){
                $.modal.msgError('密码不可为空！')
            }else{
                $.operate.post('${basePath}/u/submitLogin.shtml', {email: username, pswd: password, rememberMe: check}, function (result) {
                    if(result && result.status !== resp_status.SUCCESS){
                        $('#password').val('');
                    }else{
                        setTimeout(function(){
                            window.location.href= result.obj || "${basePath}/";
                        },1500)
                    }
                })
            }

        })
    })
</script>
</html>
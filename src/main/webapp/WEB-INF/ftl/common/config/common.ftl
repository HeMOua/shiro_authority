<#macro js>
    <script src="${basePath}/js/common/jquery/jquery1.8.3.min.js"></script>
    <script src="${basePath}/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="${basePath}/js/common/jquery.blockUI.js"></script>
    <script src="${basePath}/js/common/layer/layer.js"></script>
    <script src="${basePath}/js/common/common.js"></script>
    <script src="${basePath}/js/common/base.js" baseUrl="${basePath}"></script>
</#macro>

<#macro css>
    <link rel="shortcut icon" href="${basePath}/favicon.ico"/>
    <link href="${basePath}/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link href="${basePath}/css/common/style.css" rel="stylesheet">
</#macro>

<#macro top>
    <nav class="navbar navbar-blue navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand">shiro权限项目</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <@shiro.user>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                               aria-expanded="false">${token.nickname?default('侠名')} <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="${basePath}/user/index.shtml">个人资料</a></li>
                                <li><a href="${basePath}/role/mypermission.shtml">我的权限</a></li>
                                <li><a href="javascript:" onclick="logout();">退出登录</a></li>
                            </ul>
                        </li>
                    </@shiro.user>
                    <@shiro.guest>
                        <li><a href="javascript:void(0);" onclick="location.href='${basePath}'">登录</a></li>
                    </@shiro.guest>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</#macro>

<#macro left type index>
<#assign select = type + index/>
<div class="col-md-2">
    <div class="fix">
        <#--个人中心-->
        <div>
            <div class="side-title" data-toggle="collapse" data-target="#collapseUser">
                <a href="javascript:">个人中心 <span class="caret"></span></a>
            </div>
            <ul id="collapseUser" class="collapse ${(type=='user')?string('in', '')}">
                <li class="${(select=='user1')?string('active','')}"><a href="${basePath}/user/index.shtml">个人资料</a></li>
                <li class="${(select=='user2')?string('active','')}"><a href="${basePath}/user/updateSelf.shtml" >资料修改</a></li>
                <li class="${(select=='user3')?string('active','')}"><a href="${basePath}/user/updatePswd.shtml" >密码修改</a></li>
                <li class="${(select=='user4')?string('active','')}"><a href="${basePath}/role/mypermission.shtml">我的权限</a></li>
            </ul>
        </div>
        <#--用户中心-->
        <@shiro.hasAnyRoles name="admin,user">
        <div>
            <div class="side-title" data-toggle="collapse" data-target="#collapseMember">
                <a href="javascript:">用户管理 <span class="caret"></span></a>
            </div>
            <ul id="collapseMember" class="collapse ${(type=='member')?string('in', '')}">
                <@shiro.hasPermission name="user:list">
                <li class="${(select=='member1')?string('active','')}"><a href="${basePath}/member/list.shtml">用户列表</a></li>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="user:online">
                <li class="${(select=='member2')?string('active','')}"><a href="${basePath}/member/online.shtml">在线用户</a></li>
                </@shiro.hasPermission>
            </ul>
        </div>
        </@shiro.hasAnyRoles>
        <#--权限管理-->
        <@shiro.hasAnyRoles name="admin,perms">
        <div>
            <div class="side-title" data-toggle="collapse" data-target="#collapseAuth">
                <a href="javascript:">权限管理 <span class="caret"></span></a>
            </div>
            <ul id="collapseAuth" class="collapse ${(type=='auth')?string('in', '')}">
                <@shiro.hasPermission name="role:list">
                <li class="${(select=='auth1')?string('active','')}"><a href="${basePath}/role/index.shtml">角色列表</a></li>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="role:alloc">
                <li class="${(select=='auth2')?string('active','')}"><a href="${basePath}/role/allocation.shtml">角色分配</a></li>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="perms:list">
                <li class="${(select=='auth3')?string('active','')}"><a href="${basePath}/permission/index.shtml">权限列表</a></li>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="perms:alloc">
                <li class="${(select=='auth4')?string('active','')}"><a href="${basePath}/permission/allocation.shtml">权限分配</a></li>
                </@shiro.hasPermission>
            </ul>
        </div>
        </@shiro.hasAnyRoles>
    </div>
</div>
</#macro>


<#macro toNamesString list>
    <#list list! as item>
        ${item.name}<#if item_index != (list?size - 1)>&nbsp;,&nbsp;</#if>
    </#list>
</#macro>

<#macro toIdsString list>
    <#list list! as item>${item.id}<#if item_index != (list?size - 1)>,</#if></#list>
</#macro>
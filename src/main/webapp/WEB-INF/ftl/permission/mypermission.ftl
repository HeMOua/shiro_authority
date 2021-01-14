<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <title>我的权限 —个人中心</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
    <@common.css/>
    <@common.js/>
</head>
<body>
<@common.top/>
<div class="container">
    <div class="row">
        <@common.left 'user' 4 />
        <div class="col-md-10">
            <h2>我的权限</h2>
            <hr>
            <div id="getPermissionTree">

            </div>
        </div>
    </div>
    <#--/row-->
</div>

<script>
    $(function () {
        //加载 permission tree data
        var load = layer.load();
        $.post("getPermissionTree.shtml", {}, function (data) {
            layer.close(load);
            if (data && !data.length) {
                return $("#getPermissionTree").html('<code>您没有任何权限。只有默认的个人中心。</code>'), !1;
            }
            $('#getPermissionTree').treeview({
                levels: 1,//层级
                color: "#428bca",
                nodeIcon: "glyphicon glyphicon-user",
                showTags: true,//显示数量
                data: data//数据
            });
        }, 'json');
    });
</script>
</body>
</html>
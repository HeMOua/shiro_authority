<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <@common.css/>
    <@common.js/>
</head>
<body>
<div class="container">
    <form class="form-horizontal">
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">权限名称:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" placeholder="请输入角色名称">
            </div>
        </div>
        <div class="form-group">
            <label for="url" class="col-sm-2 control-label">权限URL地址:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="url" placeholder="请输入权限URL地址">
            </div>
        </div>
    </form>
</div>
<script>
    function submitHandler() {
        let name = $('#name').val()
        let url = $('#url').val()
        if($.common.isExistEmpty(name, url)){
            $.modal.msgWarning('请完整填写表单！')
            return
        }
        $.operate.save('${basePath}/permission/addPermission.shtml', {name, url})
    }
</script>
</body>
</html>
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
            <label for="name" class="col-sm-2 control-label">角色名称:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" placeholder="请输入角色名称">
            </div>
        </div>
        <div class="form-group">
            <label for="type" class="col-sm-2 control-label">角色类型:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="type" name="type"  placeholder="请输入角色类型  [字母 + 数字] 6位">
            </div>
        </div>
    </form>
</div>
<script>
    function submitHandler() {
        let name = $('#name').val()
        let type = $('#type').val()
        if($.common.isExistEmpty(name, type)){
            $.modal.msgWarning('请完整填写表单！')
            return
        }
        $.operate.save('${basePath}/role/addRole.shtml', {name, type})
    }
</script>

</body>
</html>
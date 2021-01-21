;(function ($) {
    $.extend({
        modal: {
            icon(type) {
                let icon;
                if (type === modal_status.WARNING) {
                    icon = 0;
                } else if (type === modal_status.SUCCESS) {
                    icon = 1;
                } else if (type === modal_status.ERROR) {
                    icon = 2;
                } else {
                    icon = 3;
                }
                return icon;
            },
            msg(content, type) {
                layer.msg(content, {icon: $.modal.icon(type), time: 1000})
            },
            msgError(content) {
                $.modal.msg(content, modal_status.ERROR)
            },
            msgSuccess(content) {
                $.modal.msg(content, modal_status.SUCCESS)
            },
            msgWarning(content) {
                $.modal.msg(content, modal_status.WARNING)
            },
            confirm(content, callback){
                layer.confirm(content, {
                    icon: 3,
                    title: "系统提示",
                    btn: ['确认', '取消']
                }, function (index) {
                    layer.close(index);
                    if (typeof callback == "function") {
                        callback()
                    }
                });
            },
            open: function (modalName, url, width, height) {
                let title = modalName
                if ($.common.isEmpty(modalName)) {
                    title = '系统弹窗'
                }
                if ($.common.isEmpty(url)) {
                    url = "/404.shtml"
                }
                if ($.common.isEmpty(width)) {
                    width = 800
                }
                if ($.common.isEmpty(height)) {
                    height = $(window).height() - 50
                }
                layer.open({
                    type: 2,
                    area: [width + 'px', height + 'px'],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: title,
                    content: url,
                    btn: ['确定', '关闭'],
                    // 弹层外区域关闭
                    shadeClose: true,
                    yes: function (index, layero) {
                        let iframeWin = layero.find('iframe')[0];
                        iframeWin.contentWindow.submitHandler(index, layero);
                    },
                    cancel: function(index) {
                        return true;
                    }
                })
            },
            // 打开遮罩层
            loading(message) {
                $.blockUI({message: '<div class="loading-box"><div class="loading"></div>&nbsp;&nbsp;' + message + '</div>'})
            },
            // 关闭遮罩层
            closeLoading() {
                setTimeout(function () {
                    $.unblockUI();
                }, 50);
            },
            // 关闭窗体
            close: function () {
                let index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            },
            // 关闭全部窗体
            closeAll() {
                layer.closeAll();
            },
            reload(){
                parent.location.reload()
            },
            delayReload(time=1000){
                setTimeout(function () {
                    $.modal.reload()
                }, time)
            }
        },
        operate: {
            prototype(url, type, dataType, data, before, callback) {
                let config = {
                    url: url,
                    type: type,
                    dataType: dataType,
                    data: data,
                    beforeSend: function () {
                        if (typeof before == "function") {
                            before()
                        }
                    },
                    success: function (result) {
                        if (typeof callback == "function") {
                            callback(result)
                        }
                    }
                };
                $.ajax(config)
            },
            submit(url, type, dataType, data, callback){
                $.operate.prototype(url, type, dataType, data, function () {
                    $.modal.loading("正在处理中，请稍后...");
                }, function (result) {
                    if (typeof callback == "function") {
                        callback(result)
                    }
                    $.operate.ajaxSuccess(result)
                })
            },
            // post请求传输
            post(url, data, callback) {
                $.operate.submit(url, "post", "json", data, callback)
            },
            // get请求传输
            get(url, callback) {
                $.operate.submit(url, "get", "json", "", callback)
            },
            // 添加信息
            add(modelName, url, height, width){
                $.modal.open('添加' + modelName, url, width, height)
            },
            // 保存信息
            save(url, data, callback){
                $.operate.prototype(url, 'post', 'json', data, function () {
                    $.modal.loading("正在处理中，请稍后...")
                }, function (result) {
                    if (typeof callback == "function") {
                        callback(result)
                    }
                    $.operate.successCallback(result)
                })
            },
            // 删除单个记录
            remove(url, id, callback){
                $.modal.confirm('确定删除该条信息吗？', function () {
                    $.operate.post(url, {ids: id}, function (result) {
                        if(typeof callback == 'function'){
                            callback(result)
                        }else {
                            if (result.status === resp_status.SUCCESS) {
                                $.modal.delayReload()
                            }
                        }
                    })
                })
            },
            removeAll(url, idList, callback){
                if(idList.length === 0){
                    $.modal.msgWarning('请先选择待删除用户！')
                    return
                }
                let title = '确定删除选中的' + idList.length + '条信息吗？'
                $.modal.confirm(title, function () {
                    $.operate.post(url, {ids: idList.join(',')}, function (result) {
                        if(typeof callback == 'function'){
                            callback(result)
                        }else{
                            if(result.status === resp_status.SUCCESS){
                                $.modal.delayReload()
                            }
                        }
                    })
                })
            },
            jumpToPage(url, data, basePath, callback){
                $.operate.prototype(url, 'post', 'json', data, function () {
                    $.modal.loading("正在处理中，请稍后...")
                }, function (result) {
                    $.modal.closeLoading()
                    if(typeof callback == 'function'){
                        callback(result)
                    }
                    if(result.status === resp_status.SUCCESS){
                        if($.common.isEmpty(result.msg)){
                            $.modal.msgSuccess('操作成功！')
                        }else{
                            $.modal.msgSuccess(result.msg)
                        }
                        setTimeout(function () {
                            if($.common.startWith(result.obj, '/')){
                                window.location.href = result.obj
                            }else{
                                window.location.href = `${basePath}/${result.obj}` || `${basePath}/`;
                            }
                        }, 1500)
                    }else if(result.status === resp_status.WARNING){
                        $.modal.msgWarning(result.msg)
                    }else{
                        $.modal.msgError(result.msg)
                    }
                })
            },
            ajaxSuccess(result) {
                $.modal.closeLoading()
                if(result.status === resp_status.SUCCESS){
                    if($.common.isEmpty(result.msg)){
                        $.modal.msgSuccess('操作成功！')
                    }else{
                        $.modal.msgSuccess(result.msg)
                    }
                    $.modal.delayReload()
                }else if(result.status === resp_status.WARNING){
                    $.modal.msgWarning(result.msg)
                }else{
                    $.modal.msgError(result.msg)
                    if(!$.common.isEmpty(result.obj)){
                        $.modal.delayReload()
                    }
                }
            },
            // 成功回调执行事件（父窗体）
            successCallback: function(result) {
                $.modal.closeLoading();
                if (result.status === resp_status.SUCCESS) {
                    let parent = window.parent;
                    $.modal.close();
                    if($.common.isEmpty(result.msg)){
                        parent.$.modal.msgSuccess('操作成功！')
                    }else{
                        parent.$.modal.msgSuccess(result.msg)
                    }
                    parent.$.modal.delayReload()
                } else if (result.status === resp_status.WARNING) {
                    $.modal.msgWarning(result.msg)
                }  else {
                    $.modal.msgError(result.msg);
                }
            }
        },
        common: {
            isEmpty(content) {
                return (content == null || this.trim(content) === '')
            },
            isExistEmpty(){
                let flag = false
                for(let i = 0; i < arguments.length; i++){
                    if($.common.isEmpty(arguments[i])){
                        flag = true
                        break
                    }
                }
                return flag
            },
            // 判断字符串是否是以start开头
            startWith: function(value, start) {
                let reg = new RegExp("^" + start);
                return reg.test(value)
            },
            // 判断字符串是否是以end结尾
            endWith: function(value, end) {
                let reg = new RegExp(end + "$");
                return reg.test(value)
            },
            trim: function (value) {
                if (value == null) {
                    return "";
                }
                return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
            }
        }
    })
})($)

/** 响应状态码 */
resp_status = {
    SUCCESS: 0,
    WARNING: 100,
    ERROR: 200
};

/** 弹窗状态码 */
modal_status = {
    SUCCESS: "success",
    ERROR: "error",
    WARNING: "warning"
};
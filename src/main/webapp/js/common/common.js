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
            // 关闭全部窗体
            closeAll() {
                layer.closeAll();
            },
            reload(){
                parent.location.reload()
            },
            delayReload(time=1500){
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
                        $.operate.ajaxSuccess(result)
                    }
                };
                $.ajax(config)
            },
            loadingBeforeSubmit(url, type, dataType, data, callback){
                $.operate.prototype(url, type, dataType, data, function () {
                    $.modal.loading("正在处理中，请稍后...");
                }, callback)
            },
            // post请求传输
            post(url, data, callback) {
                $.operate.loadingBeforeSubmit(url, "post", "json", data, callback)
            },
            // get请求传输
            get(url, callback) {
                $.operate.loadingBeforeSubmit(url, "get", "json", "", callback)
            },
            ajaxSuccess(result) {
                $.modal.closeLoading()
                if(result.status === resp_status.SUCCESS){
                    if($.common.isEmpty(result.msg)){
                        $.modal.msgSuccess('操作成功！')
                    }else{
                        $.modal.msgSuccess(result.msg)
                    }
                }else if(result.status === resp_status.WARNING){
                    $.modal.msgWarning(result.msg)
                }else{
                    $.modal.msgError(result.msg)
                }
            }
        },
        common: {
            isEmpty(content) {
                return (content == null || content.trim() === '')
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
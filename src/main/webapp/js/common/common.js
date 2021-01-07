;(function ($) {
    $.extend({
        modal: {
            icon(type) {
                let icon;
                switch (type) {
                    case modal_status.SUCCESS:
                        icon = 1;
                        break;
                    case modal_status.ERROR:
                        icon = 2;
                        break;
                    case modal_status.WARNING:
                        icon = 0;
                        break;
                    default:
                        icon = 3;
                }
                return icon;
            },
            msg(content, type) {
                if (type != undefined) {
                    layer.msg(content, {icon: $.modal.icon(type), time: 1000})
                } else {
                    layer.msg(content)
                }
            },
            msgError: function (content) {
                $.modal.msg(content, modal_status.ERROR)
            },
            msgSuccess: function (content) {
                $.modal.msg(content, modal_status.SUCCESS)
            },
            msgWarning: function (content) {
                $.modal.msg(content, modal_status.WARNING)
            },
            // 打开遮罩层
            loading(message) {
                $.blockUI({message: '<div class="loading-box"><div class="loading"></div> ' + message + '</div>'})
            },
            // 关闭遮罩层
            closeLoading: function () {
                setTimeout(function () {
                    $.unblockUI();
                }, 50);
            },
            // 关闭全部窗体
            closeAll: function () {
                layer.closeAll();
            },
        },
        operate: {
            submit(url, type, dataType, data, callback) {
                let config = {
                    url: url,
                    type: type,
                    dataType: dataType,
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在处理中，请稍后...");
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
            // post请求传输
            post(url, data, callback) {
                $.operate.submit(url, "post", "json", data, callback)
            },
            // get请求传输
            get(url, callback) {
                $.operate.submit(url, "get", "json", "", callback)
            },
            ajaxSuccess(result) {
                $.modal.closeLoading()
            }
        },
        common: {
            isEmpty(content) {
                return (content == null || content.trim() === '')
            }
        }
    })
})($)

/** 响应状态码 */
resp_status = {
    SUCCESS: 200,
    FAIL: 400,
    ERROR: 500
};

/** 弹窗状态码 */
modal_status = {
    SUCCESS: "success",
    ERROR: "error",
    WARNING: "warning"
};
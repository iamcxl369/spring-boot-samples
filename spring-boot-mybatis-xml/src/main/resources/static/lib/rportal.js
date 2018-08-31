/*
 * dashboard模块
 */
layui.define(['jquery','layer'],function(exports){ //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
    var $= layui.jquery;
    var layer = layui.layer;

    var obj = {
        request: function (url, method, params, msg) {// 向后台发送请求
            // loading层
            layer.msg(msg + '中', {
                icon: 16,
                shade: 0.01
            });

            $.ajax({
                url:url,
                type:method,
                dataType:'json',
                data: params,
                success:function(data){
                    if (data.code === 0) {
                        if (data) {
                            layer.msg(msg + "成功！");
                        } else {
                            layer.msg(msg + "失败！")
                        }
                    } else if (data.code === 1001) {
                        layer.msg(data.msg);
                    } else if (data.code === 1003) {
                        layer.msg(data.msg + "," + msg + "失败！");
                    }
                    layer.closeAll('loading');
                },
                error() {
                    layer.msg("后台接口发生错误，" + msg + "失败！");
                    layer.closeAll('loading');
                }
            });
        }
    };

    // 输出rportal接口
    exports('rportal', obj);
});

/**
 * 编辑区
 */
class Edit {
    constructor() {
        //图片区域
        this.img = $("#showImg");
        //编辑区域
        this.editPanl = $("#doc");
        //保存按钮
        this.saveBtn = $("#saveBtn");
        //隐藏域 存放文件路径
        this.filePathHidePanl = $("#fileName");
        //下载按钮
        this.downLoadBtn = $("#downLoadBtn");
    }


    /**
     * 初始化方法
     */
    init() {
        //隐藏图片div
        var _this = this;
        this.img.hide();
        //隐藏文本域
        this.editPanl.hide();
        //隐藏保存按钮
        this.saveBtn.hide();
        //给按钮添加事件
        this.downLoadBtn.addEvent("click",function(e){
            _this.downLoadFile();
        });
        this.saveBtn.addEvent("click",function(e){
            _this.save();
        });
    }
    /**
    * 根据文件类型 采取不同的处理方式
    * 如果是 js css html txt 调用editFile()
    * 如果是 png jpg 调用 executeImg()
    * 其他将不支持预览 调用 downLoadFile()
    * @param {*} _file 
    */
    executeFile(_file) {
        this.filePathHidePanl.val(_file.filePath);
        var s = _file.fileName;
        var type = s.split(".");
        if (type[1] == "png" || type[1] == "jpg") {
            //显示图片div
            this.img.show();
            //隐藏文本域
            this.editPanl.hide();
            //隐藏保存按钮
            this.saveBtn.hide();
            this.executeImg(_file);
        } else if (type[1] == "js" || type[1] == "css" || type[1] == "html" || type[1] == "txt" || type[1] == "xml") {
            //隐藏图片div
            this.img.hide();
            //隐藏文本域
            this.editPanl.show();
            //隐藏保存按钮
            this.saveBtn.show();
            this.editFile(_file);
        } else {
            //不支持类型 只能下载
            //隐藏图片div
            this.img.hide();
            //隐藏文本域
            this.editPanl.hide();
            //隐藏保存按钮
            this.saveBtn.hide();
            this.filePathHidePanl.html("不支持预览！");
        }
    }
    /** 
 * 处理文件 下载
 * @param {*} _file 文件
 */
    downLoadFile() {
        if (this.filePathHidePanl.val() == "") {
            alert("请选择要下载的文件");
            return;
        }
        var filePath = this.filePathHidePanl.val();
        var a = document.createElement("a");
        var url = "/file.action?from=downLoad&path=" + filePath;
        a.download = "";
        a.href = url;
        a.click();
    }
    /**
     * 处理图片的显示
     * @param {*} _file  文件对象
     */
    executeImg(_file) {
        var _this = this;
        this.filePathHidePanl.html(_file.fileName + "的预览");
        var url = "/file.action?from=downLoad&path=" + _file.filePath;
        var xhr = new XMLHttpRequest;
        xhr.open("GET", url, true);
        xhr.responseType = "blob";
        xhr.onload = function () {
            if (this.status == 200) {
                var blob = this.response;
                var img = document.createElement('img');
                var objectUrl = URL.createObjectURL(blob);
                img.style.maxHeight = "98%";
                img.style.maxWidth = "98%";
                img.onload = function (e) {
                    // 释放 url.
                    window.URL.revokeObjectURL(img.src);
                };
                img.src = objectUrl;
                _this.img.html(img);
            };
        }
        xhr.send();
    }
    /**
     * 编辑文件
     * @param {*} _file 文件对象
     */
    editFile(_file) {
        var _this = this;
        this.filePathHidePanl.html(_file.fileName + "   的预览");
        $.ajax({
            url: "/file.action",
            data: {
                "from": "editFile",
                "filePath": _file.filePath
            },
            success: function (result) {
                _this.editPanl.val(result);
                return;
            },
            error: function () {
                console.log("请求出错");
                return;
            }
        });
    }
    /**
     * 对文件进行保存
     */
    save() {
        var filePath = this.filePathHidePanl.val();
        if (filePath == "") {
            alert("请选择文件");
            return;
        }
        if (confirm("是否保存   " + this.filePathHidePanl.html())) {
            var str = this.editPanl.val();
            var msg = encodeURIComponent(str);
            $.ajax({
                url: "/file.action",
                data: {
                    "from": "saveFile",
                    "content": msg,
                    "filePath": filePath
                },
                success: function (result) {
                    if (result == "ok") {
                        alert("保存成功");
                        return;
                    }
                },
                error: function () {
                    console.log("请求出错");
                    return;
                }
            });
        } else {
            return;
        }

    }
}
/**
 * 树控件的命名空间
 * root 为 页面上ul的id 
 * 生成树 请指定 ul 并 调用Inti()
 */
var TREESPACE = TREESPACE || {};
//树的根节点 id 
TREESPACE.root = "";
//创建树模块对象
TREESPACE.tree = (function () {
    return function () {
        return new Tree(TREESPACE.fn);;
    }
})();

/**
 * 文件树
 */
class Tree {

    /**
     * 参数Fn 为外部方法接口,可以实现对TreeNode 进行处理  该参数在Tree 类中， 只传给TreeNode 没有其他地方引用
     * 如果有特殊处理 则要实现  TREESPACE.fn = function(){ do something }
     * @param {*} fn 
     */
    constructor(fn) {
        //节点的监听方法
        this.fn = fn;
    }

    /**
     * 初始化方法
     */
    init() {
        var service = new FileService();
        var ul = document.getElementById(TREESPACE.root);
        var _this = this;
        ul.setAttribute("class", "treeul");
        //根据后台conf.xml中设置的路径获取第一层目录
        service.initByPath(function (files) {
            for (var i = 0; i < files.length; i++) {
                var fileObj = new FileObj(files[i].fileName, files[i].size, files[i].isDir, files[i].filePath);
                var treeNode = new TreeNode(fileObj, _this.fn);
                var li = document.createElement("li");
                li.setAttribute("class", "treeli");
                //在li 中放入span 和 前置图片
                var span = document.createElement("span");
                span.innerText = files[i].fileName;
                span.setAttribute("class", "spanli");
                span.setAttribute("title", files[i].fileName);
                var img = document.createElement("img");
                //文件和目录 加上不同的图片前缀 目录的图片前缀可以点击
                if (fileObj.isDir == 1) {
                    //如果是目录
                    img.src = "../images/open.png";
                    img.treeNode = treeNode;
                    //图片添加 点击监听
                    img.addEventListener("click", function (event) {
                        this.treeNode.click(this);
                    });
                    li.appendChild(img);
                } else {
                    //是文件
                    img.src = "../images/file.png";
                    li.appendChild(img);
                }
                // 树节点 绑定dom元素
                span.treeNode = treeNode;
                //span 添加 点击监听
                span.addEventListener("click", function (event) {
                    this.treeNode.click(this);
                });
                li.appendChild(span);
                ul.appendChild(li);
            }
        });
    }

    /**
     * 获得节点 子节点
     * @param {*} file 
     * @param {*} _span 
     */
    creatNextLev(file, _span) {
        // 根据点击时获取的目录路径 获得子目录
        var service = new FileService();
        var _this = this;
        service.getInfoByFilePath(file, function (files) {
            var ul = document.createElement("ul");
            ul.setAttribute("class", "treeul");
            for (var i = 0; i < files.length; i++) {
                var fileObj = new FileObj(files[i].fileName, files[i].size, files[i].isDir, files[i].filePath);
                var treeNode = new TreeNode(fileObj, _this.fn);
                var li = document.createElement("li");
                li.setAttribute("class", "treeli");
                var span = document.createElement("span");
                span.innerText = files[i].fileName;
                span.setAttribute("class", "spanli");
                span.setAttribute("title", files[i].fileName);
                var img = document.createElement("img");
                //文件和目录 加上不同的图片前缀 目录的图片前缀可以点击
                if (fileObj.isDir == 1) {
                    //是目录
                    img.src = "../images/open.png";
                    img.treeNode = treeNode;
                    //图片前缀 增加点击监听
                    img.addEventListener("click", function (event) {
                        this.treeNode.click(this);
                    });
                    li.appendChild(img);
                } else {
                    //是文件
                    img.src = "../images/file.png";
                    li.appendChild(img);
                }
                // 树节点 绑定dom元素
                span.treeNode = treeNode;
                //span 增加点击监听
                span.addEventListener("click", function (event) {
                    this.treeNode.click(this);
                });

                li.appendChild(span);
                ul.appendChild(li);
            }
            _span.parentNode.appendChild(ul);
        });
    }

}
/**
 * 树节点
 */
class TreeNode {

    /**
     * 
     * @param {*} obj   文本对象
     * @param {*} fn 从Tree 中接收的 外部方法
     * @param {*} fristClick 是否第一次点击 默认参数
     */
    constructor(obj, fn, fristClick = 1) {
        //第一次点击
        this.fristClick = fristClick;
        //接收文本对象
        this.obj = obj;
        //接收Tree 中传过来的方法
        this.fn = fn;
    }

    //树的节点点击事件
    click(_span) {
        //如果不是图片则点击高亮显示
        if (_span.className != "") {
            this.setHighLight(_span);
        }
        //获得span的父元素
        var p = _span.parentNode;
        //获得 ul 标签
        var uls = p.getElementsByTagName("ul");
        //解决第一次生成元素后 点击不收缩问题
        if (uls.length > 0 && uls[0].style.display == "") {
            uls[0].style.display = "block";
        }
        if (this.obj.isDir == 1) {
            //是文件夹
            if (this.fristClick == 1) {
                //第一次点击 添加元素 调用树的creatNextLev
                TREESPACE.tree().creatNextLev(this.obj, _span);
                this.fristClick = 0;
                // //获得 img 标签
                var imgs = p.getElementsByTagName("img");
                //点击图片改变样式
                imgs[0].src = "../images/close.png"
            } else {
                if (uls.length == 0) {
                    return;
                }
                //获得 img 标签
                var imgs = p.getElementsByTagName("img");
                if (uls[0].style.display == "none" || uls[0].style.display == "") {
                    uls[0].style.display = "block";
                    //点击图片改变样式
                    imgs[0].src = "../images/close.png"
                }
                else if (uls[0].style.display = "block") {
                    uls[0].style.display = "none";
                    //点击图片改变样式
                    imgs[0].src = "../images/open.png"
                }
            }
        } else {
            // 是文件
            var evt = document.createEvent('Event');
            // 定义事件类型
            evt.initEvent('customEvent', true, true);
            // 在元素上监听事件
            this.addEvent(_span, this.obj);
            _span.dispatchEvent(evt);
        }
    }
    //自定义事件
    addEvent(_span, obj) {
        if (typeof (this.fn) == "function") {
            _span.addEventListener('customEvent', this.fn(obj), false);
        }
    }

    /**
     * 设置点击高亮
     * @param {*} _span 
     */
    setHighLight(_span) {
        var all = document.getElementById(TREESPACE.root);
        var spans = all.getElementsByTagName("span");
        for (var i = 0; i < spans.length; i++) {
            if (spans[i].className == "active") {
                spans[i].className = "spanli";
            }
        }
        _span.setAttribute("class", "active");
    }
}

/**
 * 文件对象
 */
class FileObj {

    /**
     * 文件对象
     * @param {*} fileName 文件名
     * @param {*} size 大小
     * @param {*} isDir 是否为目录 1是 0否
     * @param {*} filePath 文件完整路径
     */
    constructor(fileName, size, isDir, filePath) {
        this.fileName = fileName;
        this.size = size;
        this.isDir = isDir;
        this.filePath = filePath;
    }
}
/**
 * 获得目录信息类
 */
class FileService {
    constructor() {
    }

    /**
     * 初始化执行
     * @param {*} fn 回调函数
     */
    initByPath(fn) {
        $.ajax({
            url: "/file.action",
            data: {
                "from": "fileSystem"
            },
            success: function (result) {
                if (result == "fail") {
                    $("#tree").html("<h3>初始化失败，请检查conf.xml是否配置正确</h3>");
                    return;
                }
                var file = eval("(" + result + ")");
                if (file.length == 0) {
                    alert("空文件夹");
                    return;
                }
                fn(file);
            },
            error: function () {
                console.log("请求错误");
                return;
            }
        });

    }


    /**
     * 获得子目录
     * @param {*} fileObj 文件节点
     * @param {*} fn 回调函数
     */
    getInfoByFilePath(fileObj, fn) {
        $.ajax({
            url: "/file.action",
            data: {
                "from": "getFile",
                "filePath": fileObj.filePath
            },
            success: function (result) {
                var file = eval("(" + result + ")");
                if (file.length == 0) {
                    alert("空文件夹");
                    return;
                }
                fn(file);
            },
            error: function () {
                console.log("请求出错");
                return;
            }
        });
    }
}


/**
 * onLoad
 */
$(function () {

    /**
     * 外部方法 对树的节点进行处理
     * @param {*} obj 树中的 节点对象
     */
    TREESPACE.fn = function (obj) {
        new Edit().executeFile(obj);
    }
    // 指定根节点 id
    TREESPACE.root = "fileTree";
    //初始化树模块
    TREESPACE.tree().init();
    //初始化 编辑模块
    new Edit().init();
});



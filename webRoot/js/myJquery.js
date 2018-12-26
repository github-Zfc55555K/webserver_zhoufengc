/**
 * 入口
 * @param {*} args 
 */
function $(args) {
    return new jQuery(args);
}
/**
 * 为对象添加事件
 * @param {*} obj 对象
 * @param {*} e 事件
 * @param {*} fn function
 */
function addEvent(obj, e, fn) {
    obj.addEventListener(e, fn, false);
}
/**
 * 获得页面上所有使用 .class的元素
 * @param {*} _class .class名称
 */
function getClass(_class) {
    var classes = document.getElementsByTagName("*");
    var result = [];
    for (var i = 0; i < classes.length; i++) {
        if (classes[i].className == _class) {
            result.push(classes[i]);
        }
    }
    return result;
}

/**
 * 选择器
 * @param {*} args 
 */
function jQuery(args) {
    this.elements = [];
    switch (typeof args) {
        case 'function':
            addEvent(window, 'load', args);
            break;
        case 'string':
            switch (args.charAt(0)) {
                case '#':
                    var obj = document.getElementById(args.substring(1));
                    this.elements.push(obj);
                    break;
                case '.':
                    this.elements = getClass(args.substring(1));
                    break;
                default:
                    this.elements = document.getElementsByTagName(args);

            }
        case 'object':
            this.elements.push(args);
    }
}
/**
 * ajax 异步请求
 */
$.ajax = function (options) {
    options.dateType = "json";
    var params = formatParams(options.data);
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            var status = xhr.status;
            if (status >= 200 && status < 300) {
                options.success && options.success(xhr.responseText);
            } else {
                options.fail && options.error(status);
            }
        }
    }
    xhr.open("GET", options.url + "?" + params, true);
    xhr.send(null);
}

function formatParams(data) {
    var arr = [];
    for (var name in data) {
        arr.push(encodeURIComponent(name) + "=" + encodeURIComponent(data[name]));
    }
    return arr.join("&");
}

jQuery.prototype.addEvent = function (e, fn) {
    this.elements[0].addEventListener(e, fn, false);
}

jQuery.prototype.html = function () {
    if (arguments.length == 1) {
        if (typeof arguments[0] == "object") {
            this.elements[0].innerHTML = "";
            this.elements[0].appendChild(arguments[0]);
        } else {
            this.elements[0].innerHTML = arguments[0];
        }
    } else {
        return this.elements[0].innerHTML;
    }
}
jQuery.prototype.val = function () {
    if (arguments.length == 1) {
        this.elements[0].value = arguments[0];
    } else {
        return this.elements[0].value;
    }
}

jQuery.prototype.hide = function () {
    if (arguments.length == 0) {
        this.elements[0].style.display = 'none';
    }
}

jQuery.prototype.show = function () {
    if (arguments.length == 0) {
        this.elements[0].style.display = '';
    }
}
本Demo分为两部分 前后端未使用任何框架 
第一部分是 类似tomcat的简易web服务器，并且简单实现了servlet：
使用到了java的 IO流，反射，socket等

第二部分是 一个基于b/s的文件管理系统，实现了类似于windows资源管理器的功能，可以对文件进行1查看2编辑3下载

使用：
在conf.xml中可以配置 端口号，资源目录，线程池大小等
在web.xml中可以配置默认首页，servlet映射
运行：
webserver/server.java为程序主函数
在浏览器中输入 localhost:端口号 即可运行

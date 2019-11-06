# gmall1031 本地修改版本

=====================================================================
使用window连接linux上的zookeeper时一定要将虚拟机的防火墙关闭才可以连接
关闭防火墙命令:service  iptables stop
=====================================================================

gamll-user-service 用户服务的service层8070
gamll-user-web用户服务的web层8080

gmall-manage-service 后台管理service层 8071
gmall-manage-web 后台管理用户service层 8081


=======================================================================
前后端跨域问题：
    端口号不同，ip地址不同都是跨域问题
    
 1.前端127.0.0.1:8888   
 2.后端127.0.0.1:8080
 前端和后端因为来自不同的网域，所以在http的安全协议策略下，不信任
 3.解决方案:
    a.在前端请求添加跨域协议
    b.在SpringMvc的控制层加入@CrossOrigin跨域访问的注解
==================================================================

引入分布式文件系统 fastdfs
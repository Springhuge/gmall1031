//gmall 1031 本地修改版本

==============================================================

使用window连接linux上的zookeeper时一定要将虚拟机的防火墙关闭才可以连接
关闭防火墙命令:service  iptables stop

===============================================================

gamll-user-service 用户服务的service层8070
gamll-user-web用户服务的web层8080

gmall-manage-service 后台管理service层 8071
gmall-manage-web 后台管理用户service层 8081

//gmall-item-service 前台的商品详情服务 8072 直接调用manage-service服务
gmall-item-web 前台的商品详情展示 8082

gmall-redisson-test redissson测试项目

gmall-web-service-test webservice 服务端学习项目
gmall-web-service-client-test webservice 客户端学习项目
=================================================================================


前后端跨域问题：
    端口号不同，ip地址不同都是跨域问题

 1.前端127.0.0.1:8888   
 2.后端127.0.0.1:8080
 前端和后端因为来自不同的网域，所以在http的安全协议策略下，不信任
 3.解决方案:
    a.在前端请求添加跨域协议
    b.在SpringMvc的控制层加入@CrossOrigin跨域访问的注解
=================================================================================

引入分布式文件系统 fastdfs
引入thymeleaf模板技术
引入redis


增加一个redis工具类，用来初始化redis的池子
增加一个redis配置类，将redis的配置写入spring的容器中

=================================================================================

redis 常见问题：

    1.缓存在高并发和安全压力下的一些问题
        a.缓存击穿:对一些设置了过期时间的key，如果这些key可能会在某些时间点被高并发的访问，是一种非常"热点"的数据。这个时候，需要考虑一个
     问题:如果这个key在大量请求同时进来前正好失效，那么所有对这个key的数据查询都落到db，我们成为缓存击穿。
        和缓存雪崩的区别:
            击穿是一个热点key失效
            雪崩是很多key集体失效
         解决:
        b.缓存穿透:是指查询一个一定不存在的数据，由于缓存时不命中，将去查询数据库，但是数据库也无此记录，并且处于容错考虑，我们没有将这次
    查询的null值写入缓存，这将导致这个不存在的的数据每次请求都要到存储层去查询，失去了缓存的意义。在流量大时，可能DB就挂掉了，要是有人利
    用这个不存在的key频繁攻击我们的应用，这就是漏洞。
        解决:空结果进行缓存，但它的过期时间会很短，最长不超过五分钟。    
        c.缓存雪崩:缓存雪崩式指我们在设置缓存时采用了相同的过期时间，导致缓存在某一时刻同时失效，请求全部转发到DB，DB瞬时压力过重雪崩。
            解决:原有的失效时间基础上增加一个随机值，比如1-5分钟随机，这样每一个缓存的福哦其时间的重复率就会降低，就很难引发集体失效
        的事件。  
        
        穿透：利用不存在的key去攻击mysql数据库。
        雪崩：缓存中的很多key失效，导致数据库负载过重宕机。
        击穿：在正常的访问下，如果缓存失效，如何保护mysql，重启服务。
    2.如何解决缓存的问题
        使用redis数据库的分布式锁，解决mysql的访问压力问题
        
        第一种分布式锁：redis自带一个分布式锁，set px nx
        					Key          Value    过期时间  分布式锁参数只有不存在才能参数成功
        			  set sku:108:lock    1  px    60000                  nx  


        			  问题1：如果在reids中的锁已经过期了，然后锁过期的那个请求又执行完毕，回来删		锁，删除了其他线程的锁，怎么办？
        			  在加锁的时候给在给v的值加一个随机的值，在删锁的时候先去获取这个锁的值，当值存	 在且值于之前的token相同的时候，在去删除锁。


        问题2：如果碰巧在查询redis锁还没有删除的时候，正在网络传输时，锁过期了，怎么办？
        可以用lua脚本，在查询到key的同时删除该key，防止高并发下的意外的发生
        
        String script = "if redis.call('get',key[1])==ARGV[1] then return redis.call('del',key[1]) else return 0 end";
        
        jedis.eval(script,Collections.singletonList("lock"),Collections.singletonList(token));
        	
        第二种分布式锁：redisoon框架，一个redis的juc的实现（既有jedis的功能，又有juc的功能）


​      


=================================================================================    



使用apache工具httpb对redis进行压力测试，端口为9999

压测命令:ab -c 200 -n 1000 http:nginx
ab -c 200 -n 1000 http://127.0.0.1/testRedisson

解决端口被占用问题

修改httpd端口

默认httpd端口为80，现在改成800

修改两个地方：

1.修改配置文件httpd.conf

listen 80

把80改成需要的端口

2.修改配置文件**httpd-vhosts.conf**

```
<VirtualHost *:80>
    DocumentRoot "E:/wamp/www/test"
    ServerName test.cm
    ServerAlias test.cm
    ErrorLog "logs/dummy-host.example.com-error.log"
    CustomLog "logs/dummy-host.example.com-access.log" common
</VirtualHost>

<VirtualHost *:80>
    DocumentRoot "E:/wamp/www/test"
    ServerName test.cm
    ServerAlias test.cm
    ErrorLog "logs/dummy-host.example.com-error.log"
    CustomLog "logs/dummy-host.example.com-access.log" common
</VirtualHost>
```

把80改成需要的端口

 

如果是xampp在启动项vi xampp

if testport 80
then
$GETTEXT -s "fail."
echo "XAMPP: " $($GETTEXT 'Another web server is already running.')
return 1

把80改成需要的端口







搜索引擎

 elasticSearch6 （和elasticSearch5的区别在于，root用户权限、一个库能否建立多个表）



进行文本搜索（以空间换算时间算法）文本比对文本速度就会快很多。

同类产品solr、ElaticSearch、Hermes（腾讯）（实时检索分析）

ElaticSearch和solr一样都是基于lucene（apache），默认以集群方式工作。

​	

​	Lucene是一个开放源代码的全文检索引擎工具包，但它不是一个完整的全文检索引擎，而是一个全文检索引擎的架构，提供了完整的查询引擎和索引引擎，部分文本分析引擎，搜索引擎产品简介。





​	搜索引擎（以百度和goole为例）的工作原理是什么？

​	1.爬虫

​	2.分析

​	3.查询

​	

​	elasticSearch（搜索引擎算法）的算法

​	倒排索引（在内容上建立索引，用内容区匹配索引）

​	Btree（balance tree b-tree）

​	B+tree



​	es的配置

​	需要改成其他非root用户启动

​	es使用的jvm默认内存大小要大于2G才可以使用

​	Exception in thread "main"      java.nio.file.AccessDeniedException:/opt/es/elasticsearch/elasticsearch-6.

​	在elasticSearch中的config目录下修改jvm.option配置能够使用jvm内存大小，建议使用1到4G

​	在elasticSearch.yml（集群配置文件）配置es的host地址，配成本机地址，外面才可以访问



es的概念

1.通过（9200端口）http协议进行交互

GＥＴ／_cat/indeices?v

http的get请求，_cat下划线的关键字，indeices索引

<http://XXXX:9200/_cat/indices?v>

2.基本概念

Index 库

Type 表

Document 行（一条数据）

Field 字段

3.第一命令

​	建立索引



​	增删改查命令

​	PUT

​	DELETE

​	POST

​	GET
#1.先提供两个filter对消费者和提供者的RpcContext里的信息进行处理
#2.配置filter到META-INF中
* /src/main/resources/META-INFO/dubbo/下新建文件：com.alibaba.dubbo.rpc.Filter
dubboProviderContextFilter=com.king.dubbo.TestProviderContextFilter
dubboConsumerContextFilter=com.king.dubbo.TestConsumerContextFilter

- 在application.properties中增加dubbo的filter配置
dubbo.provider.filter = dubboProviderContextFilter
dubbo.consumer.filter = dubboConsumerContextFilter
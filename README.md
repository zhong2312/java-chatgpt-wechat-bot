## 简介
 java版消息自动回复机器人、支持多种消息接收、回复、发送渠道。<br>
 通过Bot可以构建多种不同的组合。<br>
 目前已实现的有：<br>
 ChatGPT+微信(支持连续语境对话)<br>
 OpenAI(gpt3)+微信<br>
 ChatGPT+控制台(一般用于测试。使用控制台作为消息接收和发送的渠道)<br>
 
## 使用

application.yml<br>
配置bot.appkey 、bot.botName、bot.wechat.qrcode.path 启动BotStarter.java即可。<br>
二维码存放路径需要自己新建一下文件夹<br>

groupWhiteList.txt 群聊白名单配置，不配置默认不处理全部群消息<br>
userWhiteList.txt  私聊白名单配置，不配置默认回复所有私聊消息<br>

如果打包为jar启动，则可以把配置文件复制到任意目录，然后在启动参数中指定根目录如：
java  -Dbot.appKey=xxx -DrootConfigPath=D:\botConfig -jar bot.jar

mvn package appassembler:assemble -Dmaven.test.skip=true

```
Bot.buildChatGPTWechatBot().start(); //使用ChatGPT
Bot.buildOpenAIWechatBot().start(); //使用openAI(GPT3)
Bot.buildChatGPTConsoleBot().start();//使用控制台作为消息输入输出(目前只支持Eclipse)
Bot.buildChatGPTAutoBot().start();//创建一个自言自语的机器人

API被墙了，需要使用代理，在application.yml文件配置代理：
#代理设置
proxy.enable: true
proxy.host: 127.0.0.1
proxy.port: 33211

```
## 架构说明
```
原理
微信消息接收线程-> 预处理消息队列
预处理线程 -> 预处理消息出队列，进行敏感词检查，白名单检查，对话频率检查，入待回复队列。
Openai线程 -> 待回复队列出队列，请求openai，失败入队列并等待10秒后重试。成功入队列待发送队列。
微信消息发送线程 -> 待发送队列出队列，发送消息，随机停顿5-20秒。

直接引入代码的开源包：
itchat4j 增加了一些基本属性：群名称、发送用户名称等。

使用SpringBoot是因为后续计划支持web界面操作：
1.支持多个机器人实例
2.支持多节点调度
3.支持在线停启
4.支持对话调度等。
```

## Console模式截图

![image](https://github.com/zhong2312/java-chatgpt-wechat-bot/blob/main/img/console.jpg)

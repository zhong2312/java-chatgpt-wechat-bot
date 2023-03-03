## 简介
 java版chatGPT微信机器人。
 
## 使用

application.yml
配置bot.appkey 、bot.botName、bot.wechat.qrcode.path 启动BotStarter.java即可。
二维码存放路径需要自己新建一下文件夹

groupWhiteList.txt 群聊白名单配置，不配置默认不处理全部群消息
userWhiteList.txt  私聊白名单配置，不配置默认回复所有私聊消息

```
Bot.buildChatGPTWechatBot().start(); //使用ChatGPT
Bot.buildOpenAIWechatBot().start(); //使用openAI(GPT3)
Bot.buildChatGPTConsoleBot().start();//使用控制台作为消息输入输出(目前只支持Eclipse)

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


```

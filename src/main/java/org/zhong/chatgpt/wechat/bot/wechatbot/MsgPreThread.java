package org.zhong.chatgpt.wechat.bot.wechatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhong.chatgpt.wechat.bot.model.BotMsg;
import org.zhong.chatgpt.wechat.bot.model.WehchatMsgQueue;
import org.zhong.chatgpt.wechat.bot.msgprocess.MsgProcessor;

import com.alibaba.fastjson.JSON;

public class MsgPreThread implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(MsgPreThread.class);
	
	private MsgProcessor msgProcessor;
	
	public MsgPreThread(MsgProcessor msgProcessor) {
		super();
		this.msgProcessor = msgProcessor;
	}

	@Override
	public void run() {
		for(;;) {
			BotMsg botMsg = WehchatMsgQueue.popPreMsg();
			if(botMsg == null) {
				logger.debug("从preMsgs获取消息为空");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				logger.debug("从preMsgs获取消息:", JSON.toJSONString(botMsg));
				msgProcessor.process(botMsg);
			}
		}
	}

	public static void start(MsgProcessor msgProcessor) {
		logger.info("消息预处理线程启动");
		new Thread(new MsgPreThread(msgProcessor)).start();
	}
}

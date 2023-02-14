package org.zhong.chatgpt.wechat.bot.wechatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhong.chatgpt.wechat.bot.model.BotMsg;
import org.zhong.chatgpt.wechat.bot.model.WehchatMsgQueue;
import org.zhong.chatgpt.wechat.bot.msgprocess.MsgProcessor;

import com.alibaba.fastjson.JSON;

public class MsgReplyThread implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(MsgReplyThread.class);
	
	private MsgProcessor msgProcessor;
	
	public MsgReplyThread(MsgProcessor msgProcessor) {
		super();
		this.msgProcessor = msgProcessor;
	}

	@Override
	public void run() {
		for(;;) {
			BotMsg botMsg = WehchatMsgQueue.popReplyMsg();
			if(botMsg == null) {
				logger.debug("从waitReplyMsgs获取消息为空");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				logger.debug("从waitReplyMsgs获取消息:{}", JSON.toJSONString(botMsg));
				msgProcessor.process(botMsg);
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		 
	}

	public static void start(MsgProcessor msgProcessor) {
		logger.info("消息回复线程启动");
		new Thread(new MsgReplyThread(msgProcessor)).start();
	}
}

package org.zhong.chatgpt.wechat.bot.wechatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhong.chatgpt.wechat.bot.msgprocess.WechatMsgHandler;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;

public class WechatAcceptThread {
	
	private static Logger logger = LoggerFactory.getLogger(WechatAcceptThread.class);
	
	public static void start(String qrPath) {
		logger.info("消息发送线程启动");
		IMsgHandlerFace msgHandler = new WechatMsgHandler();
		Wechat wechat = new Wechat(msgHandler, qrPath);
		wechat.start();
	}
}

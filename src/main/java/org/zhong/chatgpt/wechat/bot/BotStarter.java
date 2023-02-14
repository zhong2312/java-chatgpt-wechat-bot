package org.zhong.chatgpt.wechat.bot;

import org.zhong.chatgpt.wechat.bot.config.BotConfig;
import org.zhong.chatgpt.wechat.bot.msgprocess.MsgPreProcessor;
import org.zhong.chatgpt.wechat.bot.msgprocess.OpenAIReplyProcessor;
import org.zhong.chatgpt.wechat.bot.msgprocess.WechatSendProcessor;
import org.zhong.chatgpt.wechat.bot.wechatbot.MsgPreThread;
import org.zhong.chatgpt.wechat.bot.wechatbot.MsgReplyThread;
import org.zhong.chatgpt.wechat.bot.wechatbot.WechatAcceptThread;
import org.zhong.chatgpt.wechat.bot.wechatbot.WechatSendThread;

public class BotStarter {

	public static void main(String[] args) {

		MsgReplyThread.start(new OpenAIReplyProcessor());
		MsgPreThread.start(new MsgPreProcessor());
		WechatSendThread.start(new WechatSendProcessor());
		WechatAcceptThread.start(BotConfig.getQrcodePath());
	}
}

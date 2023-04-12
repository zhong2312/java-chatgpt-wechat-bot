package org.zhong.chatgpt.wechat.bot.model;

import org.zhong.chatgpt.wechat.bot.msgprocess.ACSAcceptor;
import org.zhong.chatgpt.wechat.bot.msgprocess.ACSSendProcessor;
import org.zhong.chatgpt.wechat.bot.msgprocess.ChatGPTReplyProcessor;
import org.zhong.chatgpt.wechat.bot.msgprocess.ConsoleSendProcessor;
import org.zhong.chatgpt.wechat.bot.msgprocess.ConsoleMsgAcceptor;
import org.zhong.chatgpt.wechat.bot.msgprocess.MsgAcceptor;
import org.zhong.chatgpt.wechat.bot.msgprocess.MsgPreProcessor;
import org.zhong.chatgpt.wechat.bot.msgprocess.MsgProcessor;
import org.zhong.chatgpt.wechat.bot.msgprocess.OpenAIReplyProcessor;
import org.zhong.chatgpt.wechat.bot.msgprocess.WechatMsgAcceptor;
import org.zhong.chatgpt.wechat.bot.msgprocess.WechatSendProcessor;
import org.zhong.chatgpt.wechat.bot.wechatbot.MsgPreThread;
import org.zhong.chatgpt.wechat.bot.wechatbot.MsgReplyThread;
import org.zhong.chatgpt.wechat.bot.wechatbot.WechatSendThread;

public class Bot {

	private MsgProcessor msgPreProcessor;
	private MsgProcessor replyProcessor;
	private MsgProcessor sendProcessor;
	private MsgAcceptor msgAcceptor;
	
	public void start() {
		MsgReplyThread.start(replyProcessor);
		MsgPreThread.start(msgPreProcessor);
		WechatSendThread.start(sendProcessor);
		msgAcceptor.start();
	}
	
	public static Bot builder() {
		return new Bot();
	}

	public static Bot buildOpenAIWechatBot() {
		return builder()
					.msgPreProcessor(new MsgPreProcessor())
					.replyProcessor(new OpenAIReplyProcessor())
					.sendProcessor(new WechatSendProcessor())
					.msgAcceptor(new WechatMsgAcceptor());
	}
	
	public static Bot buildChatGPTWechatBot() {
		return builder()
				.msgPreProcessor(new MsgPreProcessor())
				.replyProcessor(new ChatGPTReplyProcessor())
				.sendProcessor(new WechatSendProcessor())
				.msgAcceptor(new WechatMsgAcceptor());
	}
	
	public static Bot buildSocialistAIWechatBot() {
		return builder()
				.msgPreProcessor(new MsgPreProcessor())
				.replyProcessor(new ChatGPTReplyProcessor("我需要你扮演社会主义集大成者来我和对话。你说的每一句话都需要体现出社会主义核心价值观。你把社会主义核心价值观融入到所有学科。"))
				.sendProcessor(new WechatSendProcessor())
				.msgAcceptor(new WechatMsgAcceptor());
	}
	
	public static Bot buildChatGPTConsoleBot() {
		return builder()
				.msgPreProcessor(new MsgPreProcessor())
				.replyProcessor(new ChatGPTReplyProcessor())
				.sendProcessor(new ConsoleSendProcessor())
				.msgAcceptor(new ConsoleMsgAcceptor());
	}
	
	public static Bot buildChatGPTAutoBot() {
		return builder()
				.msgPreProcessor(new MsgPreProcessor())
				.replyProcessor(new ChatGPTReplyProcessor())
				.sendProcessor(new ACSSendProcessor())
				.msgAcceptor(new ACSAcceptor());
	}
	
	public Bot msgPreProcessor(MsgProcessor msgPreProcessor) {
		this.msgPreProcessor = msgPreProcessor;
		return this;
	}

	public Bot replyProcessor(MsgProcessor replyProcessor) {
		this.replyProcessor = replyProcessor;
		return this;
	}

	public Bot sendProcessor(MsgProcessor sendProcessor) {
		this.sendProcessor = sendProcessor;
		return this;
	}
	
	public Bot msgAcceptor(MsgAcceptor msgAcceptor) {
		this.msgAcceptor = msgAcceptor;
		return this;
	}
	
	
}

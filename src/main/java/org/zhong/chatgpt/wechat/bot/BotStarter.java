package org.zhong.chatgpt.wechat.bot;

import org.zhong.chatgpt.wechat.bot.model.Bot;

public class BotStarter {

	public static void main(String[] args) {
		//Bot.buildOpenAIBot().start();
		
		Bot.buildChatGPTWechatBot().start();
	}
}

package org.zhong.chatgpt.wechat.bot.chatgptwechatbot.test;

import org.junit.Test;
import org.zhong.chatgpt.wechat.bot.model.Bot;

public class TestBot {

	
	@Test
	public void test() {
		Bot.buildChatGPTConsoleBot().start();
	}
	
	@Test
	public void testAutoBot() {
		Bot.buildChatGPTAutoBot().start();
	}
}

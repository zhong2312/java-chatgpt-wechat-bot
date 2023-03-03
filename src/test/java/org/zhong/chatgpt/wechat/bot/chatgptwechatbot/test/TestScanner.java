package org.zhong.chatgpt.wechat.bot.chatgptwechatbot.test;

import org.junit.Test;
import org.zhong.chatgpt.wechat.bot.msgprocess.ConsoleMsgAcceptor;

public class TestScanner {

	
	@Test
	public void testScanner() {
		ConsoleMsgAcceptor mockMsgAcceptor = new ConsoleMsgAcceptor();
		mockMsgAcceptor.start();
	}

	public static void main(String[] args) {
		ConsoleMsgAcceptor mockMsgAcceptor = new ConsoleMsgAcceptor();
		mockMsgAcceptor.start();
	}
}

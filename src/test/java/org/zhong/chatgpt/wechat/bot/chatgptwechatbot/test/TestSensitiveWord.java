package org.zhong.chatgpt.wechat.bot.chatgptwechatbot.test;

import org.junit.Test;
import org.zhong.chatgpt.wechat.bot.sensitive.SensitiveWord;

public class TestSensitiveWord {

	
	@Test
	public void test() {
		System.out.println(SensitiveWord.contains("行车自行车阿松大文化大革命啊实打实"));
	}
}

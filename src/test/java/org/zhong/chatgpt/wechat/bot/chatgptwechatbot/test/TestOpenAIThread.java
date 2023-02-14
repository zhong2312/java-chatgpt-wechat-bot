package org.zhong.chatgpt.wechat.bot.chatgptwechatbot.test;

import org.junit.Test;
import org.zhong.chatgpt.wechat.bot.model.BotMsg;
import org.zhong.chatgpt.wechat.bot.model.WehchatMsgQueue;
import org.zhong.chatgpt.wechat.bot.msgprocess.OpenAIReplyProcessor;
import org.zhong.chatgpt.wechat.bot.wechatbot.MsgReplyThread;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;

public class TestOpenAIThread {

	@Test
	public void test() {
		BaseMsg baseMsg = new BaseMsg();
		baseMsg.setFromUserName("@56b78712b25cdd82515e7324696885d4");
		baseMsg.setContent("你好");
		WehchatMsgQueue.pushReplyMsg(new BotMsg(baseMsg));
		
		MsgReplyThread.start(new OpenAIReplyProcessor());
	}
}

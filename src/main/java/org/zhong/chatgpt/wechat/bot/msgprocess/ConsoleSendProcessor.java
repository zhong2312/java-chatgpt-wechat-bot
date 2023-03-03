package org.zhong.chatgpt.wechat.bot.msgprocess;

import org.zhong.chatgpt.wechat.bot.model.BotMsg;

public class ConsoleSendProcessor implements MsgProcessor{

	@Override
	public void process(BotMsg botMsg) {
		System.out.println(botMsg.getReplyMsg());
	}

}

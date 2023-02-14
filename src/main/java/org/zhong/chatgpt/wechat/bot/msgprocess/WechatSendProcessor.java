package org.zhong.chatgpt.wechat.bot.msgprocess;

import org.zhong.chatgpt.wechat.bot.model.BotMsg;

import cn.zhouyafeng.itchat4j.api.MessageTools;

public class WechatSendProcessor implements MsgProcessor{

	@Override
	public void process(BotMsg botMsg) {
		
		MessageTools.sendMsgById(botMsg.getReplyMsg(), botMsg.getBaseMsg().getFromUserName());
	}

}

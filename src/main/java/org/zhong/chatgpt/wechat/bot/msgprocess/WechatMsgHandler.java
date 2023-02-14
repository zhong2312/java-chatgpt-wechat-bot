package org.zhong.chatgpt.wechat.bot.msgprocess;

import org.zhong.chatgpt.wechat.bot.model.BotMsg;
import org.zhong.chatgpt.wechat.bot.model.WehchatMsgQueue;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;

public class WechatMsgHandler implements IMsgHandlerFace {

	@Override
	public String textMsgHandle(BaseMsg msg) {
		WehchatMsgQueue.pushPreMsg(new BotMsg(msg));
		return null;
	}

	@Override
	public String picMsgHandle(BaseMsg msg) {
		WehchatMsgQueue.pushPreMsg(new BotMsg(msg));
		return null;
	}

	@Override
	public String voiceMsgHandle(BaseMsg msg) {
		WehchatMsgQueue.pushPreMsg(new BotMsg(msg));
		return null;
	}

	@Override
	public String viedoMsgHandle(BaseMsg msg) {
		WehchatMsgQueue.pushPreMsg(new BotMsg(msg));
		return null;
	}

	@Override
	public String nameCardMsgHandle(BaseMsg msg) {
		return null;
	}

	@Override
	public void sysMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String verifyAddFriendMsgHandle(BaseMsg msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mediaMsgHandle(BaseMsg msg) {
		WehchatMsgQueue.pushPreMsg(new BotMsg(msg));
		return null;
	}
}

package org.zhong.chatgpt.wechat.bot.msgprocess;

import org.apache.commons.lang3.StringUtils;
import org.zhong.chatgpt.wechat.bot.config.BotConfig;
import org.zhong.chatgpt.wechat.bot.consts.BotConst;
import org.zhong.chatgpt.wechat.bot.model.BotMsg;
import org.zhong.chatgpt.wechat.bot.model.WehchatMsgQueue;
import org.zhong.chatgpt.wechat.bot.sensitive.SensitiveWord;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;

public class MsgPreProcessor implements MsgProcessor{

	TimedCache<String, String> timedCache = CacheUtil.newTimedCache(20*60*1000);
	
	public void process(BotMsg botMsg) {
		BaseMsg baseMsg = botMsg.getBaseMsg();
		
		if((baseMsg.isGroupMsg() && timedCache.get(baseMsg.getGroupUserName()) != null)
				|| (!baseMsg.isGroupMsg() && timedCache.get(baseMsg.getFromUserName()) != null)) {
			return;
		}
		
		if(baseMsg.isGroupMsg()) {//群聊
			
			if(!BotConfig.getGroupWhiteList().contains(baseMsg.getGroupName())) {
				//如果群聊不在白名单
				return;
			}
			
			if(!baseMsg.getContent().contains(BotConfig.getAtBotName())) {
				//如果不是@我的消息
				return;
			}
			
			long count = WehchatMsgQueue.countGroupUserPreMsg(baseMsg.getGroupUserName());
			if(count > 10) {
				timedCache.put(baseMsg.getGroupUserName(), baseMsg.getGroupUserName());
				botMsg.setReplyMsg(BotConst.AT + baseMsg.getGroupUserNickName() + " 你说话太快，接下来的10分钟我不会再处理你的新消息");
				WehchatMsgQueue.pushSendMsg(botMsg);
				return;
			}
		}else {//私聊
			
			if(!BotConfig.getUserWhiteList().isEmpty()
					&& !BotConfig.getUserWhiteList().contains(baseMsg.getFromUserNickName())) {
				return;
			}
			
			long count = WehchatMsgQueue.countUserPreMsg(baseMsg.getFromUserName());
			if(count > 10) {
				timedCache.put(baseMsg.getFromUserName(), baseMsg.getFromUserName());
				botMsg.setReplyMsg("你说话太快，接下来的10分钟我不会再处理你的新消息");
				WehchatMsgQueue.pushSendMsg(botMsg);
				return;
			}
		}
		
		
		if (baseMsg.getType().equals(MsgTypeEnum.TEXT.getType())) {
			
			baseMsg.setContent(baseMsg.getContent().replace(BotConfig.getAtBotName(), ""));
			
			String content = baseMsg.getContent();
			if(StringUtils.isEmpty(content)) {
				//丢弃
				return;
			}
			
			boolean isSensitive = SensitiveWord.contains(content);
			if(isSensitive) {
				if(baseMsg.isGroupMsg()) {
					botMsg.setReplyMsg(BotConst.AT + baseMsg.getGroupUserNickName() + "你说的话太内涵，我无法回答。");
				}else {
					botMsg.setReplyMsg("你说的话太内涵，我无法回答。");
				}
				
				WehchatMsgQueue.pushSendMsg(botMsg);
			}else {
				WehchatMsgQueue.pushReplyMsg(botMsg);
			}
			
		}else {
			botMsg.setReplyMsg("目前我只能针对文本消息进行回答");
			WehchatMsgQueue.pushSendMsg(botMsg);
		}
	}
}

package org.zhong.chatgpt.wechat.bot.model;

import cn.zhouyafeng.itchat4j.beans.BaseMsg;

public class BotMsg{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BaseMsg baseMsg;
	
	/**
	 * 是否完成敏感词检查
	 */
	private boolean isCheck;
	
	/**
	 * 回复信息
	 */
	private String replyMsg;
	
	/**
	 * chatGPT重试次数
	 */
	private int retries;
	
	

	public BotMsg(BaseMsg baseMsg) {
		super();
		this.baseMsg = baseMsg;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public String getReplyMsg() {
		return replyMsg;
	}

	public void setReplyMsg(String replyMsg) {
		this.replyMsg = replyMsg;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public BaseMsg getBaseMsg() {
		return baseMsg;
	}

	public void setBaseMsg(BaseMsg baseMsg) {
		this.baseMsg = baseMsg;
	}
	
	
	
}

package org.zhong.chatgpt.wechat.bot.model;

import java.util.LinkedList;

public class BotMsgLinkedList extends LinkedList<BotMsg>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BotMsg nullPop() {
		return this.isEmpty() ? null : this.pop();
	}
	
	public long countGroupUserMsg(String groupUserName) {
		
		if(this.size() == 0) {
			return 0;
		}
		
		long count = this.stream().filter( (msg) -> {
			if(msg.getBaseMsg().getGroupUserName() != null) {
				return msg.getBaseMsg().getGroupUserName().equals(groupUserName);
			}else {
				return false;
			}
		}).count();
		
		return count;
	}
	
	public long countUserMsg(String userName) {
		
		if(this.size() == 0) {
			return 0;
		}
		
		long count = this.stream().filter( (msg) -> {
			if(msg.getBaseMsg().getFromUserName() != null) {
				return msg.getBaseMsg().getFromUserName().equals(userName);
			}else {
				return false;
			}
		}).count();
		
		return count;
	}
	
	public void removeByUserName() {
		
	}
	
}

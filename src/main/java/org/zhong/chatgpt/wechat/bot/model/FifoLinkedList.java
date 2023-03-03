package org.zhong.chatgpt.wechat.bot.model;

import java.util.LinkedList;

public class FifoLinkedList<E> extends LinkedList<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int limit;

	public FifoLinkedList(int limit) {
		this.limit = limit;
	}

	@Override
	public boolean add(E o) {
		super.add(o);
		while (size() > limit) {
			super.remove();
		}
		return true;
	}
}

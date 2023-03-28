package com.yrd980.qqcommon;

import java.io.Serializable;

/**
 * @author Yrd
 * @version 1.0
 */
@SuppressWarnings({"all"})
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sender;
	private String receiver;
	private String content;
	private String sendTime;

	//非常重要
	private String messageType;

	//进行发文件的拓展成员
	private byte[] fileBytes;
	private int fileLen = 0;
	private String dest ;
	private String src;

	public byte[] getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}

	public int getFileLen() {
		return fileLen;
	}

	public void setFileLen(int fileLen) {
		this.fileLen = fileLen;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}


	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}

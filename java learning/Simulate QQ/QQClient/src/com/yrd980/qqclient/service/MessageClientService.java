package com.yrd980.qqclient.service;

import com.yrd980.qqcommon.Message;
import com.yrd980.qqcommon.MessegeType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @author Yrd
 * @version 1.0
 * 该类提供消息相关的服务方法
 *
 */
@SuppressWarnings({"all"})
public class MessageClientService {

	//私聊

	/**
	 * @param content  内容
	 * @param senderId 发送者
	 * @param getterId 接收者
	 */
	public static void sendMessage(String content, String senderId, String getterId) {
		//构建message对象
		Message message = new Message();
		message.setMessageType(MessegeType.MESSAGE_COMM_MES);
		message.setContent(content);
		message.setSender(senderId);
		message.setReceiver(getterId);
		message.setSendTime(new Date().toString());
		//message.setSendTime(new java.util.Date().toString());
		System.out.println(senderId + "对" + getterId + "说 " + content);

		//给服务器发消息
		try {
			ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.
					                                                getClientConnectServerThread(senderId).getSocket().getOutputStream());
			oos.writeObject(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//群聊

	/**
	 * @param content
	 * @param senderId
	 */
	public static void sendMessageToAll(String content, String senderId) {
		//构建message对象
		Message message = new Message();
		message.setMessageType(MessegeType.MESSAGE_TOALL);//群发消息
		message.setContent(content);
		message.setSender(senderId);
		message.setSendTime(new Date().toString());
		//message.setSendTime(new java.util.Date().toString());
		System.out.println(senderId + " 对大家说 " + content);

		try {
			ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.
					                                                getClientConnectServerThread(senderId).getSocket().getOutputStream());
			oos.writeObject(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

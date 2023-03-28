package com.yrd980.qqserver.service;

import com.yrd980.qqcommon.Message;
import com.yrd980.qqcommon.MessegeType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yrd
 * @version 1.0
 * 该类对应的线程与某个客户端保持通信
 */
@SuppressWarnings({"all"})
public class ServerConnectClientThread extends Thread {
	private Socket socket;
	private String userId;

	public ServerConnectClientThread(Socket socket, String userId) {
		this.socket = socket;
		this.userId = userId;
	}

	public Socket getSocket() {
		return socket;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("服务器端和用户端" + userId + " 保持通信，读取数据。。。");
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				Message message = (Message) ois.readObject();
				//后面会使用message
				//根据message类型做相应的业务


				if (message.getMessageType().equals(MessegeType.MESSAGE_GET_ONLINE_FRIEND)) {
					//用户要在线列表
					System.out.println("用户：" + message.getSender() + " 要在线用户列表");
					String onlineUsers = ManageClientThreads.getOnlineUsers();

					//返回message
					//构建一个Message对象返回给客户端
					Message message1 = new Message();
					message1.setMessageType(MessegeType.MESSAGE_RET_ONLINE_FRIEND);
					message1.setContent(onlineUsers);
					message1.setReceiver(message.getSender());

					//返回给客户端
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(message1);

				} else if (message.getMessageType().equals(MessegeType.MESSAGE_CLIENT_EXIT)) {//客户端退出

					System.out.println("用户：" + message.getSender() + "退出系统");
					//将其从集合中删除
					ManageClientThreads.removeServerConnectClientThread(message.getSender());
					//关闭线程
					socket.close();
					//退出
					break;

				} else if (message.getMessageType().equals(MessegeType.MESSAGE_COMM_MES)) {
					//先根据所要发送对象id获取对应线程
					ServerConnectClientThread scct = ManageClientThreads.
							                                 getServerConnectClientThread(message.getReceiver());
					ObjectOutputStream oos = new ObjectOutputStream(scct.getSocket().getOutputStream());
					//new ObjectOutputStream(ManageClientThreads.getServerConnecttedThread(message.getReciver()).getSocket().getOutputStream).var
					oos.writeObject(message);

					//1.这里首先需要判断getter是否合法，非法用户的话，将message内容更改为提示想要私聊的用户是非法的（不存在）
					//2.如果getter用户是合法用户，则继续判断该用户是否在线，如果在线直接转发，如果不在线则将消息保存至数据库
					//3.这里可以做一个cache来作为一个缓冲结构，防止数据量过大（一大堆人都给这个人发消息），设置可以接受的消息条数
					//4.如果cache中的消息条数超过限制，则将该消息存储到数据库中，待改用户上线后，从cache中将所有消息弹出，
					// 弹出完毕后，去数据库中查询是否仍有留言，并将留言一并弹出即可


				} else if (message.getMessageType().equals(MessegeType.MESSAGE_TOALL)) {
					//需要遍历管理线程的集合得到所有线程然后发消息
					ConcurrentHashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
					Iterator<String> iterator = hm.keySet().iterator();
					while (iterator.hasNext()) {

						//取出在线用户id
						String onlineUserId =  iterator.next();
						if(!onlineUserId.equals(message.getSender())){//排除群发消息者

							//进行转发消息
							ObjectOutputStream oos = new ObjectOutputStream(hm.get(onlineUserId).getSocket().getOutputStream());
							oos.writeObject(message);

						}

					}

				} else if (message.getMessageType().equals(MessegeType.MESSAGE_FILE_MES)) {
					//发文件
					//根据发送者id获得对应线程后将message对象转发
				ServerConnectClientThread scct	= ManageClientThreads.getServerConnectClientThread(message.getReceiver());
					ObjectOutputStream oos = new ObjectOutputStream(scct.getSocket().getOutputStream());
					//转发
					oos.writeObject(message);

				} else {
					System.out.println("其他业务暂时不处理");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		}
	}
}

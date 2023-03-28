package com.yrd980.qqclient.service;

import com.yrd980.qqcommon.Message;
import com.yrd980.qqcommon.MessegeType;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author Yrd
 * @version 1.0
 * 与服务器沟通线程
 */
@SuppressWarnings({"all"})
public class ClientConnectServerThread extends Thread {
	//该线程应该持有Socket
	private Socket socket;

	//构造器可以接受一个socket对象
	public ClientConnectServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		//因为Thread需要在后台和服务器通讯所以用while循环
		while (true) {

			try {
				System.out.println("客户端线程，等待从服务器端发来的消息");
				//对象输入流
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				Message message = (Message) ois.readObject();
				//如果程序没有发message则一直阻塞
				//注意后面我们要使用message
				//判断这个message类型进行对应业务处理
				if (message.getMessageType().equals(MessegeType.MESSAGE_RET_ONLINE_FRIEND)) {
					//取出在线列表信息并显示
					//规定用一个空格分开
					String[] onlineUsers = message.getContent().split(" ");
					System.out.println("\n========当前用户列表如下========");
					for (int i = 0; i < onlineUsers.length; i++) {
						System.out.println("用户：" + onlineUsers[i]);
					}

				} else if (message.getMessageType().equals(MessegeType.MESSAGE_COMM_MES)) {

					//把从服务器接受数据转到控制台
					System.out.println("\n" + message.getSender() + " 对 " + message.getReceiver() + " 说 " + message.getContent());

				} else if (message.getMessageType().equals(MessegeType.MESSAGE_TOALL)) {
					//显示在控制台
					System.out.println("\n" + message.getSender() + " 对大家说 " + message.getContent());
				} else if (message.getMessageType().equals(MessegeType.MESSAGE_FILE_MES)) {
					//如果是文件消息
					System.out.println("\n"+message.getSender()+"给"+message.getReceiver() + "发送"+message.getSrc() + "文件到"+message.getDest() + "目录");
					//取出message的字节数组写入到磁盘
					FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
					fileOutputStream.write(message.getFileBytes());
					fileOutputStream.close();
					System.out.println("保存文件成功");
				} else {
					System.out.println("其他类型message暂不处理");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	//为了方便得到
	public Socket getSocket() {
		return socket;
	}
}

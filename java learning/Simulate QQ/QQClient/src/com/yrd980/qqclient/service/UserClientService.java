package com.yrd980.qqclient.service;

import com.yrd980.qqcommon.Message;
import com.yrd980.qqcommon.MessegeType;
import com.yrd980.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Yrd
 * @version 1.0
 * 该类完成用户注册和登录验证功能
 */
@SuppressWarnings({"all"})
public class UserClientService {

	private User u = new User();//因为我们可能在别的地方使用user信息因此做成成员属性

	private Socket socket;

	//向服务器发送登录信息验证合法性
	public boolean checkUser(String userId, String pwd) {

		boolean b = false;
		//创建user对象
		u.setUserId(userId);
		u.setPwd(pwd);

		//连接到服务端发送对象

		try {
			socket = new Socket(InetAddress.getByName("192.168.0.102"), 9999);
			//准备序列化
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(u);//发送user对象

			//上面是一切的开始

			//读取从服务器回复的message对象
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Message msg = (Message) ois.readObject();

			if (msg.getMessageType().equals(MessegeType.MESSAGE_LOGIN_SUCCEED)) {

				b = true;

				//创建和服务器端保持通信的线程 ->创建一个类 ClientConnectServerThread

				ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
				//启动客户线程
				ccst.start();
				//为了后期的拓展业务建议放在集合里
				ManageClientConnectServerThread.addClientConnectServerThread(userId, ccst);

			} else {
				//如果登录失败就照应上面
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return b;
	}


	//向服务器端请求获得在线用户列表
	public void onlineFriendList() {

		//发送一个消息
		Message message = new Message();
		message.setMessageType(MessegeType.MESSAGE_GET_ONLINE_FRIEND);
		message.setSender(u.getUserId());

		//发送给服务器
		//应该得到该线程对应socket的objectoutputstream对象
		try {
			//从管理线程的集合中得到线程再得到对应socket的数据通道
			ObjectOutputStream oos = new ObjectOutputStream
					                         (ManageClientConnectServerThread.getClientConnectServerThread
							                                                         (u.getUserId()).getSocket().getOutputStream());
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//编写方法退出客户端并给服务器端发消息
	public void logOut() {
		Message message = new Message();
		message.setMessageType(MessegeType.MESSAGE_CLIENT_EXIT);
		message.setSender(u.getUserId());//一定要指定尤其是idea自动提示的

		//发送message
		try {
			//ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			//推荐这样写
			ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
					                                                .getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
			oos.writeObject(message);
			System.out.println("用户："+u.getUserId()+" 退出系统");
			System.exit(0);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

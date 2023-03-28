package com.yrd980.qqserver.service;

import com.yrd980.qqcommon.Message;
import com.yrd980.qqcommon.MessegeType;
import com.yrd980.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yrd
 * @version 1.0
 * 这是服务器监听9999端口等待客户端连接并保持通信
 */
@SuppressWarnings({"all"})
public class QQServer {

	private ServerSocket ss = null;

	//创建一个集合代替数据库
	//这用ConcurrentHashMap比hashmap好因为它处理多线程
	private static ConcurrentHashMap<String,User> validUsers = new ConcurrentHashMap<>();
	//设置离线消息
	private static ConcurrentHashMap<String, ArrayList<Message>> offlineUsers = new ConcurrentHashMap<>();

	static {//用静态代码块初始化 静态代码块>普通代码块>构造器

		validUsers.put("100",new User("100","123456"));
		validUsers.put("200",new User("200","123456"));
		validUsers.put("300",new User("300","123456"));
		validUsers.put("400",new User("400","123456"));
		validUsers.put("500",new User("500","123456"));
	}

	public boolean checkUser(String userId,String pwd){
		//过关斩将
		User user = validUsers.get(userId);
		if (user == null) {
		    return false;
		}
		if(!user.getPwd().equals(pwd)){
			return false;
		}
		return true;
	}


	public QQServer(){
		//端口可以写在一个配置文件
		try {
			//开始推送新闻
			new Thread(new SendNewsToAll()).start();

			System.out.println("服务器在9999端口监听");
			ss = new ServerSocket(9999);


			while (true) {//当与某个客户端连接后会继续监听，因此while循环
				Socket socket = ss.accept();
				//得到socket关联对象输入流
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				//其实这个是第一步
				//得到socket关联对象输出流
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				User u = (User) ois.readObject();
				//创建一个message对象准备回复客户端
				Message message = new Message();
				//这是根据图示所作

				//验证
				if( //u.getUserId().equals("100")&&u.getPwd().equals("123456")
						//通过数据库检验用户合法性
						checkUser(u.getUserId(),u.getPwd())){
					message.setMessageType(MessegeType.MESSAGE_LOGIN_SUCCEED);
					//将message对象回复
					oos.writeObject(message);
					//创建一个线程和客户端保持通信，该线程需要持有socket对象
					ServerConnectClientThread scct = new ServerConnectClientThread(socket, u.getUserId());
					//启动该线程
					scct.start();
					//把该线程对象放入一个集合中管理
					ManageClientThreads.addServerConnectClientThread(u.getUserId(),scct);
				} else {//登录失败

					System.out.println("用户id="+u.getUserId()+" 密码="+u.getPwd()+" 验证失败");
					message.setMessageType(MessegeType.MESSAGE_LOGIN_FAIL);
					oos.writeObject(message);
					//关闭
					socket.close();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//如果服务器退出while就关闭
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

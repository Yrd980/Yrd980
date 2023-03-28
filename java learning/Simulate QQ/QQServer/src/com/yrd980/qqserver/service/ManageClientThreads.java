package com.yrd980.qqserver.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yrd
 * @version 1.0
 * 管理通信线程的集合
 */
@SuppressWarnings({"all"})
public class ManageClientThreads {


	private static ConcurrentHashMap<String,ServerConnectClientThread> hm = new ConcurrentHashMap<>();

	public static ConcurrentHashMap<String, ServerConnectClientThread> getHm() {
		return hm;
	}

	public static void addServerConnectClientThread(String userId,ServerConnectClientThread serverConnectClientThread){
		hm.put(userId, serverConnectClientThread);
	}

	//通过userId可以得到对应线程
	public static ServerConnectClientThread getServerConnectClientThread(String userId){
		return hm.get(userId);
	}

	//编写方法移除
	public static void removeServerConnectClientThread(String userId) {
	    hm.remove(userId);
	}

	//编写方法获得在线用户列表
	public static String getOnlineUsers(){
		Iterator<String> iterator = hm.keySet().iterator();
		String onlineUsers = "";
		while (iterator.hasNext()) {
			onlineUsers += iterator.next()+" ";
		}
		return onlineUsers;
	}
}

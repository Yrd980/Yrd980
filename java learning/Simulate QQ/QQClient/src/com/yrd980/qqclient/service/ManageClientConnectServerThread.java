package com.yrd980.qqclient.service;

import java.util.HashMap;

/**
 * @author Yrd
 * @version 1.0
 * 该类管理客户端连接到服务器端的线程的类
 */
@SuppressWarnings({"all"})
public class ManageClientConnectServerThread {
	//我们把多个线程放入Hashmap集合， key就是id，value就是线程
	private static HashMap<String,ClientConnectServerThread> hm = new HashMap<>();

	public static void addClientConnectServerThread(String userId,ClientConnectServerThread clientConnectServerThread){
		hm.put(userId, clientConnectServerThread);
	}

	//通过userId可以得到对应线程
	public static ClientConnectServerThread getClientConnectServerThread(String userId){
		return hm.get(userId);
	}

}

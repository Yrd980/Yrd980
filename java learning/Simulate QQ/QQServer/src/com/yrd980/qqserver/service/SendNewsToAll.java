package com.yrd980.qqserver.service;

import com.yrd980.qqcommon.Message;
import com.yrd980.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yrd
 * @version 1.0
 */
@SuppressWarnings({"all"})
public class SendNewsToAll implements Runnable {

	@Override
	public void run() {
		//为了多次推送
		while (true) {

			System.out.println("请输入想要的新闻,输入exit退出推送服务");
			String news = Utility.readString(100);
			if(news.equals("exit")){
				break;
			}
			//构建一个消息群发消息
			Message message = new Message();
			message.setSender("服务器");
			message.setContent(news);
			message.setSendTime(new Date().toString());
			System.out.println("服务器推送消息给所有人 说 " + news);

			//遍历所有线程得到socket并发消息
			ConcurrentHashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
			Iterator<String> iterator = hm.keySet().iterator();
			while (iterator.hasNext()) {
				String onlineUserId = iterator.next();
				try {
					ObjectOutputStream oos = new ObjectOutputStream(hm.get(onlineUserId).getSocket().getOutputStream());
					oos.writeObject(message);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
}

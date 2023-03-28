package com.yrd980.qqframe;

import com.yrd980.qqserver.service.QQServer;

/**
 * @author Yrd
 * @version 1.0
 * 该类创建qqserver对象启动后台服务
 */
@SuppressWarnings({"all"})
public class QQFrame {
	public static void main(String[] args) {
		new QQServer();
	}
}

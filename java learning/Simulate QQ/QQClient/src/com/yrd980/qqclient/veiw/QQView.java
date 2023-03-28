package com.yrd980.qqclient.veiw;

import com.yrd980.qqclient.service.FileClientService;
import com.yrd980.qqclient.service.MessageClientService;
import com.yrd980.qqclient.service.UserClientService;
import com.yrd980.qqclient.utils.Utility;

/**
 * @author Yrd
 * @version 1.0
 * 菜单界面
 */
@SuppressWarnings({"all"})
public class QQView {

	private boolean loop = true;//控制是否显示菜单
	private String key = "";//用来接受用户键盘输入

	private UserClientService userClientService = new UserClientService();//对象是用来登录和注册

	private MessageClientService messageClientService = new MessageClientService();//该对象实现聊天

	private FileClientService fileClientService = new FileClientService();//实现文件传输

	public static void main(String[] args) {
		new QQView().mainMenu();
		System.out.println("客户端退出系统。。。。。");
	}

	//显示主菜单
	public void mainMenu() {

		while (loop) {


			System.out.println("===========欢迎登录网络通信系统===========");
			System.out.println("\t\t 1 登录系统");
			System.out.println("\t\t 9 退出系统");
			System.out.print("请输入你的选择：");

			key = Utility.readString(1);

			//根据用户输入来处理不同逻辑
			switch (key) {
				case "1"://登录
					System.out.print("请输入用户号：");
					String userId = Utility.readString(50);
					System.out.print("请输入密  码：");
					String pwd = Utility.readString(50);

					//开始需要服务端验证用户是否合法
					//这里有很多代码所以新建类UserClientService
					//下面if中的方法不得了
					if (userClientService.checkUser(userId, pwd)) {
						System.out.println("===========欢迎" + userId + "===========");
						//进入二级菜单
						while (loop) {
							System.out.println("\n========网络通信系统二级菜单（用户" + userId + ")=======");
							System.out.println("\t\t 1 显示在线用户列表");
							System.out.println("\t\t 2 群发消息");
							System.out.println("\t\t 3 私聊消息");
							System.out.println("\t\t 4 发送文件");
							System.out.println("\t\t 9 退出系统");
							System.out.print("请输入你的选择：");
							key = Utility.readString(1);

							switch (key) {
								case "1":
									//准备写一个方法获得在线用户列表
									userClientService.onlineFriendList();
									break;
								case "2":
									//群聊
									System.out.print("请输入想对大家说的话");
									String s = Utility.readString(100);
									//调用一个方法将消息封装成一个对象发送给服务端
									messageClientService.sendMessageToAll(s,userId);
									break;
								case "3"://私聊信息
									System.out.print("请输入想聊天的用户号（在线）");
									String getId = Utility.readString(50);
									System.out.print("请输入想说的话");
									String content = Utility.readString(100);
									//编写方法将消息发送给服务端
									messageClientService.sendMessage(content,userId,getId);

									break;
								case "4":
									System.out.println("请输入你想把文件发送到的（在线）用户：");
									String getterId = Utility.readString(50);
									System.out.println("请输入发送文件的路径例如d:\\15.png");
									String src = Utility.readString(100);
									System.out.println("请输入接受文件的路径例如d:\\15.png");
									String dest = Utility.readString(100);
									fileClientService.sendFileToOne(src,dest,userId,getterId);
									break;
								case "9":
									loop = false;
									break;

							}

						}
					} else {
						System.out.println("=======登录失败=====");
					}
					break;
				case "9":
					//解决主线程退出服务器线程退出
					//在main线程调用方法给服务器端发送一个退出系统的message对象
					//调用system.exit（0）退出
					userClientService.logOut();
					loop = false;
					break;
			}
		}

	}
}

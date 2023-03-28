package com.yrd980.qqclient.service;

import com.yrd980.qqcommon.Message;
import com.yrd980.qqcommon.MessegeType;

import java.io.*;

/**
 * @author Yrd
 * @version 1.0
 * 该对象完成文件传输服务
 */
@SuppressWarnings({"all"})
public class FileClientService {
	/**
	 *
	 * @param src 源文件地址
	 * @param dest 目的地址
	 * @param sender 发送者
	 * @param getter 接收者
	 */
	public void sendFileToOne(String src,String dest,String sender,String getter){

		//读取src文件到message
		Message message = new Message();
		message.setMessageType(MessegeType.MESSAGE_FILE_MES);
		message.setSrc(src);
		message.setDest(dest);
		message.setSender(sender);
		message.setReceiver(getter);

		//需要将文件读取
		FileInputStream fileInputStream = null;
		byte[] fileBytes = new byte[(int) new File(src).length()];
		try {
			//FileInputStream fileInputStream = new FileInputStream(src);
			fileInputStream.read(fileBytes);
			message.setFileBytes(fileBytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fileInputStream != null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		//提示信息
		System.out.println("\n"+sender+"给"+getter + "发送"+src + "文件到"+dest + "目录");

		//发送
		try {
			ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread
					                                                .getClientConnectServerThread(sender).getSocket().getOutputStream());
			oos.writeObject(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
		}
	}
}

package com.yrd980;

/**
 * @author Yrd
 * @version 1.0
 */
@SuppressWarnings({"all"})
public class test1 {
	String str = new String("hsp");
	final char[] ch = {'j','a','v','a'};

	public void change(String str,char ch[]){
		str = "java";
		ch[0]='h';
	}

	public static void main(String[] args) {
		test1 test1 = new test1();
		test1.change(test1.str, test1.ch);
		System.out.print(test1.str + " and ");
		System.out.println(test1.ch);
	}
}

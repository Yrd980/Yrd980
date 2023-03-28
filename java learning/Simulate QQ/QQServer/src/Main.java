/**
 * @author Yrd
 * @version 1.0
 */
@SuppressWarnings({"all"})
public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world!");
		Person person = new Person();
		B b = new B();
		b.test(person);
		System.out.println(person.age);

	}

}
class Person{
	String name;
	int age=10;
}
class B{
	public void test(Person p){
		//p.age = 100;
		p=null;
	}
}
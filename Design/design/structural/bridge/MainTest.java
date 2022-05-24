/**
 * 省去了创建很多类
 */
public class MainTest {

    public static void main(String[] args) {

        IPhone iPhone = new IPhone();
        iPhone.setSale(new StudentSale("学生",1));


        String phone = iPhone.getPhone();
        System.out.println(phone);

    }
}

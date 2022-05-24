/**
 * 代理的东西不一样，每一种不同的被代理类Person、Dog、Cat,创建不同的静态代理类
 *
 */
public class MainTest {

    public static void main(String[] args) {
        LiMingTiktokProxy proxy = new LiMingTiktokProxy(new LeiTikTok());
        proxy.tiktok();

        //静态代理就是装饰器
        //装饰器模式是代理模式的一种

    }
}

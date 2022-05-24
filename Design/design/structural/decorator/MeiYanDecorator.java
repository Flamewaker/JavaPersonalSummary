/**
 * 美颜装饰器
 *      装饰谁？
 *
 *  装饰器只关系增强这个类的方法。
 */
public class MeiYanDecorator implements TiktokDecorator{

//    private Framework framework;  适配器

    private ManTikTok manTikTok;
    public MeiYanDecorator(ManTikTok manTikTok){
        this.manTikTok = manTikTok;
    }

    @Override
    public void tiktok() {
        //开启美颜
        enable();
        //
//        framework.enableMeiYan();

        //我开始直播
        manTikTok.tiktok();
    }


    /**
     * 定义的增强功能
     */
    @Override
    public void enable() {
        System.out.println("看这个美女.....");
        System.out.println("花花花花花花花花花花花");
    }
}

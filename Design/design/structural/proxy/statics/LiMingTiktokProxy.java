/**
 * 代理一般都是和被代理对象属于同一个接口
 */
public class LiMingTiktokProxy implements ManTikTok {

    private ManTikTok manTikTok; //被代理对象
    public  LiMingTiktokProxy(ManTikTok manTikTok){
        this.manTikTok = manTikTok;
    }


    /**
     * 宋喆（代理）  宝强（被代理对象）
     */
    @Override
    public void tiktok() {
        //增强功能
        System.out.println("渲染直播间....");
        System.out.println("课程只要666，仅此一天....");
        manTikTok.tiktok();

    }
}

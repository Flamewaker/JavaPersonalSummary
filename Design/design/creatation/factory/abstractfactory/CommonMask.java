public class CommonMask  extends AbstractMask{
    public CommonMask(){
        price = 1;
    }
    @Override
    public void protectedMe() {
        System.out.println("普通口罩....简单保护...请及时更换");
    }
}

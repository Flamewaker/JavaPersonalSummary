public class MiniCar extends AbstractCar {

    public MiniCar(){
        this.engine = "四缸水平对置发动机";
    }

    @Override
    public void run() {
        System.out.println(engine+"--> 嘟嘟嘟...");
    }
}

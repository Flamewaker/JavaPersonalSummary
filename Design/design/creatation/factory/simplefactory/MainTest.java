public class MainTest {

    public static void main(String[] args) {

        WuLinSimpleFactory factory = new WuLinSimpleFactory();

        AbstractCar van = factory.newCar("van");
        AbstractCar mini = factory.newCar("mini");
        AbstractCar zz = factory.newCar("zz");
        van.run();
        mini.run();

    }
}

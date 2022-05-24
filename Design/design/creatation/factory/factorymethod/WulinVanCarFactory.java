public class WulinVanCarFactory extends AbstractCarFactory {
    @Override
    public AbstractCar newCar() {
        return new VanCar();
    }
}

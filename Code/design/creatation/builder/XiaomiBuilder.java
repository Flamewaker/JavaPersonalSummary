package com.todd.design.creatation.builder;

public class XiaomiBuilder extends AbstractBuilder{

    public XiaomiBuilder(){
//        phone =
//                new Phone();
        phone = Phone.builder().build();
    }

    /**
     *
     * @param cpu
     */
    @Override
    AbstractBuilder customCpu(String cpu) {
        phone.cpu = cpu;
        return this;
    }

    @Override
    AbstractBuilder customMem(String mem) {
        phone.mem = mem;
        return this;
    }

    @Override
    AbstractBuilder customDisk(String disk) {
        phone.disk = disk;
        return this;
    }

    @Override
    AbstractBuilder customCam(String cam) {
        phone.cam = cam;
        return this;
    }
}

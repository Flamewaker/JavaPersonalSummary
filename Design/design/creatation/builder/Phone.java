import lombok.*;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
@Builder
public class Phone {

    protected String cpu;
    protected String mem;
    protected String disk;
    protected String cam;

    @Override
    public String toString() {
        return "Phone{" +
                "cpu='" + cpu + '\'' +
                ", mem='" + mem + '\'' +
                ", disk='" + disk + '\'' +
                ", cam='" + cam + '\'' +
                '}';
    }

    public String getCpu() {
        return cpu;
    }

    public String getMem() {
        return mem;
    }

    public String getDisk() {
        return disk;
    }

    public String getCam() {
        return cam;
    }
}

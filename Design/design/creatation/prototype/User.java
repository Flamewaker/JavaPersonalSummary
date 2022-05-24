/**
 * 当前对象是可克隆的
 */
public class User implements Cloneable {

    private String username;
    private Integer age;

    public User(){
        System.out.println("User对象创建");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }


    /**
     * 再创建一个人，赋予我的所有属性
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        User user = new User();
        user.setUsername(username);
        user.setAge(age);
        return user;
    }
}

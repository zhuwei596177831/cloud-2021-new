package cpm.example.springcloudstream;

/**
 * @author 朱伟伟
 * @date 2021-03-12 10:42:39
 * @description
 */
public class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}

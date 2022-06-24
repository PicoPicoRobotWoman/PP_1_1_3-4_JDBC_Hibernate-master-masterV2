package jm.task.core.jdbc.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Age")
    private Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User uo = (User)o;
        return Long.compare(this.id, uo.id) == 0 && this.name == uo.name && this.lastName == uo.lastName && Byte.compare(this.age, uo.age) == 0;

    }

    @Override
    public int hashCode() {
        int result = Integer.parseInt(id.toString());
        result = 31 * result + (name != null ? name.hashCode() : 0 );
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0 );
        result = 31 * result + (age != null ? age.hashCode() : 0);
       return result;
    }
}

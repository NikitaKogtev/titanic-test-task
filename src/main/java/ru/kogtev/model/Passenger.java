package ru.kogtev.model;

import jakarta.persistence.*;

@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "survived", nullable = false)
    private Boolean survived = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "pclass", nullable = false, length = 10)
    private PClass pClass;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false, length = 10)
    private Sex sex;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "siblings_spouses_aboard", nullable = false)
    private Integer siblingsSpousesAboard;

    @Column(name = "parents_children_aboard", nullable = false)
    private Integer parentsChildrenAboard;

    @Column(name = "fare", nullable = false)
    private Double fare;

    public Passenger() {

    }

    private Passenger(Builder builder) {
        this.id = builder.id;
        this.survived = builder.survived;
        this.pClass = PClass.valueOf(String.valueOf(builder.pClass));
        this.name = builder.name;
        this.sex = Sex.valueOf(String.valueOf(builder.sex));
        this.age = builder.age;
        this.siblingsSpousesAboard = builder.siblingsSpousesAboard;
        this.parentsChildrenAboard = builder.parentsChildrenAboard;
        this.fare = builder.fare;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getSurvived() {
        return survived;
    }

    public void setSurvived(Boolean survived) {
        this.survived = survived;
    }

    public PClass getPClass() {
        return pClass;
    }

    public void setPClass(PClass pClass) {
        this.pClass = pClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSiblingsSpousesAboard() {
        return siblingsSpousesAboard;
    }

    public void setSiblingsSpousesAboard(Integer siblingsSpousesAboard) {
        this.siblingsSpousesAboard = siblingsSpousesAboard;
    }

    public Integer getParentsChildrenAboard() {
        return parentsChildrenAboard;
    }

    public void setParentsChildrenAboard(Integer parentsChildrenAboard) {
        this.parentsChildrenAboard = parentsChildrenAboard;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }


    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", survived=" + survived +
                ", pClass='" + pClass + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", siblingsSpousesAboard=" + siblingsSpousesAboard +
                ", parentsChildrenAboard=" + parentsChildrenAboard +
                ", fare=" + fare +
                '}';
    }

    public static class Builder {
        private Integer id;
        private Boolean survived;
        private PClass pClass;
        private String name;
        private Sex sex;
        private Integer age;
        private Integer siblingsSpousesAboard;
        private Integer parentsChildrenAboard;
        private Double fare;

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder survived(Boolean survived) {
            this.survived = survived;
            return this;
        }

        public Builder pClass(PClass pClass) {
            this.pClass = pClass;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder sex(Sex sex) {
            this.sex = sex;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder siblingsSpousesAboard(Integer siblingsSpousesAboard) {
            this.siblingsSpousesAboard = siblingsSpousesAboard;
            return this;
        }

        public Builder parentsChildrenAboard(Integer parentsChildrenAboard) {
            this.parentsChildrenAboard = parentsChildrenAboard;
            return this;
        }

        public Builder fare(Double fare) {
            this.fare = fare;
            return this;
        }

        public Passenger build() {
            return new Passenger(this);
        }
    }
}

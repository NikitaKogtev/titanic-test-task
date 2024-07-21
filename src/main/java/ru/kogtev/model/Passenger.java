package ru.kogtev.model;

import jakarta.persistence.*;

/**
 * Представляет пассажира, который находится на борту.
 */
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

    public Passenger(Boolean survived, PClass pClass, String name, Sex sex, Integer age,
                     Integer siblingsSpousesAboard, Integer parentsChildrenAboard, Double fare) {
        this.survived = survived;
        this.pClass = pClass;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.siblingsSpousesAboard = siblingsSpousesAboard;
        this.parentsChildrenAboard = parentsChildrenAboard;
        this.fare = fare;
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
}
package week.second.day.five.read.examples;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = -6558723795337045566L;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private BigDecimal salary;
    private Integer kpi;
    private Integer experience;
    private String level;
    private String position;
    private BigDecimal calculatedSalary;

    public Employee(String firstName, String lastName, String dateOfBirth, BigDecimal salary, Integer kpi, Integer experience, String level, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
        this.kpi = kpi;
        this.experience = experience;
        this.level = level;
        this.position = position;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public int getKpi() {
        return kpi;
    }

    public void setKpi(int kpi) {
        this.kpi = kpi;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getCalculatedSalary() {
        return calculatedSalary;
    }

    public void setCalculatedSalary(BigDecimal calculatedSalary) {
        this.calculatedSalary = calculatedSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(dateOfBirth, employee.dateOfBirth) && Objects.equals(salary, employee.salary) && Objects.equals(kpi, employee.kpi) && Objects.equals(experience, employee.experience) && Objects.equals(level, employee.level) && Objects.equals(position, employee.position) && Objects.equals(calculatedSalary, employee.calculatedSalary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth, salary, kpi, experience, level, position, calculatedSalary);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", salary=" + salary +
                ", kpi=" + kpi +
                ", experience=" + experience +
                ", level='" + level + '\'' +
                ", position='" + position + '\'' +
                ", calculatedSalary=" + calculatedSalary +
                '}';
    }
}

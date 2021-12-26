package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Customer extends User {
    public final String carMake;
    public final String carModel;
    public final String licencePlate;
    public final Date date;
    public final int price;

    public Customer(String fullName, String carMake, String carModel, String licencePlate, Date date, int price) {
        super(Role.CUSTOMER, fullName);
        this.carMake = carMake;
        this.carModel = carModel;
        this.licencePlate = licencePlate;
        this.date = date;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return price == customer.price && Objects.equals(carMake, customer.carMake) && Objects.equals(carModel, customer.carModel) && Objects.equals(date, customer.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carMake, carModel, date, price);
    }

    @Override
    public String toString() {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return "Имя клиента: " + fullName + " Марка автомобиля: " + carMake + " Модель автомобиля: " + carModel + " Гос. номер: " + licencePlate + " Дата визита: " + formatter.format(date) + " Итоговая цена: " + price + "\n";
    }
}

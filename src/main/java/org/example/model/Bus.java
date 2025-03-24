package org.example.model;

public class Bus implements Comparable<Bus>{

    private final int number;       // Номер
    private final String model;     // Модель
    private final double mileage;   // Пробег

    // Приватный конструктор для Builder
    private Bus(Builder builder) {
        this.number = builder.number;
        this.model = builder.model;
        this.mileage = builder.mileage;
    }

    // Геттеры
    public int getNumber() {
        return number;
    }

    public String getModel() {
        return model;
    }

    public double getMileage() {
        return mileage;
    }

    @Override
    public int compareTo(Bus other) {
        int numberComparison = Integer.compare(this.number, other.number);;
        if (numberComparison != 0) {
            return numberComparison;
        }
        int modelComparison = this.model.compareTo(other.model);
        if (modelComparison != 0) {
            return modelComparison;
        }
        return Double.compare(this.mileage, other.mileage);
    }

    // Реализация Builder
    public static class Builder {
        private int number;
        private String model;
        private double mileage;

        public Builder number(int number) {
            this.number = number;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder mileage(double mileage) {
            this.mileage = mileage;
            return this;
        }

        public Bus build() {
            return new Bus(this);
        }
    }

    // Переопределение toString для удобного вывода
    @Override
    public String toString() {
        return "Bus{" +
                "number=" + number +
                ", model='" + model + '\'' +
                ", mileage=" + mileage +
                '}';
    }
}
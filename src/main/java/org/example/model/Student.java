package org.example.model;

public class Student implements Comparable<Student> {
    private final int groupNumber;       // Номер группы
    private final double averageGrade;   // Средний балл
    private final int recordBookNumber;  // Номер зачетной книжки

    // Приватный конструктор для Builder
    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageGrade = builder.averageGrade;
        this.recordBookNumber = builder.recordBookNumber;
    }

    // Геттеры
    public int getGroupNumber() {
        return groupNumber;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public int getRecordBookNumber() {
        return recordBookNumber;
    }

    // Реализация Comparable для сортировки
    @Override
    public int compareTo(Student other) {
        int groupComparison = Integer.compare(this.groupNumber, other.groupNumber);
        if (groupComparison != 0) {
            return groupComparison;
        }
        int gradeComparison = Double.compare(this.averageGrade, other.averageGrade);
        if (gradeComparison != 0) {
            return gradeComparison;
        }
        return Integer.compare(this.recordBookNumber, other.recordBookNumber);
    }

    // Реализация Builder
    public static class Builder {
        private int groupNumber;
        private double averageGrade;
        private int recordBookNumber;

        public Builder groupNumber(int groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public Builder averageGrade(double averageGrade) {
            this.averageGrade = averageGrade;
            return this;
        }

        public Builder recordBookNumber(int recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }

    // Переопределение toString для удобного вывода
    @Override
    public String toString() {
        return "Student{" +
                "groupNumber=" + groupNumber +
                ", averageGrade=" + averageGrade +
                ", recordBookNumber=" + recordBookNumber +
                '}';
    }
}
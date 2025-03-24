package org.example.generator;

import org.example.model.User;
import org.example.model.Student;
import org.example.model.Bus;

import java.util.Random;

// DataGenerators.java
public final class DataGenerators {
    private static final Random RANDOM = new Random();

    private DataGenerators() {}

    public static class BusGenerator {
        private static final String[] MODELS = {"ANKAI", "AYATS", /* остальные модели */};

        public static Bus randomBus() {
            return new Bus.Builder()
                    .number(RANDOM.nextInt(1, 1000))
                    .model(MODELS[RANDOM.nextInt(MODELS.length)])
                    .mileage(RANDOM.nextInt(1000000))
                    .build();
        }
    }

    public static class StudentGenerator {
        public static Student randomStudent() {
            return new Student.Builder()
                    .groupNumber(RANDOM.nextInt(1, 100))
                    .averageGrade(RANDOM.nextDouble(2, 5))
                    .recordBookNumber(RANDOM.nextInt(100000, 1000000))
                    .build();
        }
    }

    public static class UserGenerator {
        private static final String[] NAMES = {"Harry", "James", /* остальные имена */};
        private static final String[] DOMAINS = {"mail.ru", "gmail.com", /* остальные домены */};
        private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+<>?";

        public static User randomUser() {
            String name = NAMES[RANDOM.nextInt(NAMES.length)];
            String password = generateRandomPassword();
            String email = name.toLowerCase() + "@" + DOMAINS[RANDOM.nextInt(DOMAINS.length)];

            return new User.Builder()
                    .name(name)
                    .password(password)
                    .email(email)
                    .build();
        }

        private static String generateRandomPassword() {
            int length = RANDOM.nextInt(6, 12);
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
            }
            return sb.toString();
        }
    }
}
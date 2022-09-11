package homework;

import lombok.*;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class ComplexExamples {

    @Data
    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(0, "Harry"),
            new Person(1, "Harry"),
            new Person(8, "Amelia"),
            new Person(0, "Harry"),
            new Person(4, "Jack"),
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),

    };


    @Data
    @AllArgsConstructor
    public static class SumDTO {
        private Integer first;
        private Integer second;


        @Override
        public String toString() {
            return "{" + first + ", " + second + "}";
        }
    }

    public static void main(String[] args) {
        System.out.println(arrayOfPersonToString(RAW_DATA));
        System.out.println(getTwoWithSum(new Integer[]{3, 4, 2, 7}, 10));
        System.out.println(getTwoWithSumWithSet(new Integer[]{3, 4, 2, 7}, 10));
        System.out.println();
        System.out.println(fuzzySearch("hw", "cartwheelllllaaa"));
        System.out.println(fuzzySearchRegex("hw", "cartwheelllllaaa"));
        System.out.println();
    }

    public static String arrayOfPersonToString(Person[] arr) {
        if (arr == null || arr.length == 0) return "";
        return Arrays.stream(RAW_DATA)
                .distinct()
                .sorted(Comparator.comparingInt(Person::getId))
                .collect(groupingBy(Person::getName, Collectors.reducing(0, (x) -> 1, Integer::sum)))
                .entrySet()
                .stream()
                .collect(StringBuilder::new, (sb, el) -> sb.append("Key:").append(el.getKey()).append('\n').append("Value:").append(el.getValue()).append('\n'), StringBuilder::append)
                .toString();
    }

    public static SumDTO getTwoWithSum(Integer[] arr, Integer sum) {
        if (arr == null || sum == null) return null;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (sum.equals(arr[i] + arr[j])) return new SumDTO(arr[i], arr[j]);
            }
        }
        return null;
    }

    public static SumDTO getTwoWithSumWithSet(Integer[] arr, Integer sum) {
        if (arr == null || sum == null) return null;
        Set<Integer> set=Arrays.stream(arr).collect(Collectors.toSet());
        for (Integer i:arr) {
            if (set.contains(sum-i)) return new SumDTO(i, sum-i);
        }
        return null;
    }

    public static boolean fuzzySearch(String example, String target) {
        if (example == null && target == null) return true;
        if (example == null || target == null) return false;
        int i = 0;
        for (int j = 0; j < target.length() && i < example.length(); j++) {
            if (target.charAt(j) == example.charAt(i)) {
                i++;
            }
        }
        return i == example.length();
    }

    public static boolean fuzzySearchRegex(String example, String target) {
        StringBuilder sb = new StringBuilder(".*");
        for (int j = 0; j < example.length(); j++) {
            sb.append("[").append(example.charAt(j)).append("]").append(".*");
        }
        Pattern pattern = Pattern.compile(sb.toString());
        return pattern.matcher(target).matches();
    }
}

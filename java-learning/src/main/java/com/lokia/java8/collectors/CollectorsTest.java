package com.lokia.java8.collectors;

import com.lokia.java8.bean.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorsTest {

    public static void main(String[] args) {
        List<Student> students = generateStudents();
        Map<String,List<Student>> cityGroupedStudents = students.stream().collect(Collectors.groupingBy(Student::getCity));
        cityGroupedStudents.forEach((key,itemList)->{
            System.out.println("****** "+key+" ******");
            itemList.stream().forEach(item->System.out.println(item));

            System.out.println();
            System.out.println();
        });

    }

    private static List<Student> generateStudents() {

        Student student = new Student("kobe","LA");
        Student student1 = new Student("jason","HZ");
        Student student2 = new Student("shu","HZ");
        Student student3 = new Student("yaoming","SH");
        Student student4 = new Student("king.zhou","SH");
        Student student5 = new Student("bryant","LA");

        return Arrays.asList(student,student1,student2,student3,student4,student5);
    }
}

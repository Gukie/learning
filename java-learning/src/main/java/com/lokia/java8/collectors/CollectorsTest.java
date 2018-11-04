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

        System.out.println("====================================");
        Map<String,Map<String,List<Student>>> sexCityGroup  = students.stream().collect(Collectors.groupingBy(Student::getSex,Collectors.groupingBy(Student::getCity)));
        sexCityGroup.forEach((sex,entryList)->{
            System.out.println("************ "+sex+" ************");
            entryList.forEach((city,itemList)->{
                System.out.println("****** "+city+" ******");
                itemList.stream().forEach(item->System.out.println(item));

                System.out.println();
            });


        });



    }

    private static List<Student> generateStudents() {

        Student student = new Student("kobe","LA","M");
        Student student1 = new Student("jason","HZ","M");
        Student student2 = new Student("shu","HZ","M");
        Student student3 = new Student("yaoming","SH","M");
        Student student4 = new Student("king.zhou","SH","M");
        Student student5 = new Student("bryant","LA","M");

        Student student6 = new Student("lily","LA","F");
        Student student7 = new Student("jolion","HZ","F");
        Student student8 = new Student("shaly","HZ","F");
        Student student9 = new Student("hebe","SH","F");
        Student student10 = new Student("ella","SH","F");

        return Arrays.asList(student,student1,student2,student3,student4,student5,student6,student7,student8,student9,student10);
    }
}

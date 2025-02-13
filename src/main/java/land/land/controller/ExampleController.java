package land.land.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class ExampleController {

    @GetMapping("thymeleaf/example")
    public String thymeleafExample(Model model){
        Person person = new Person();
        person.setId(1L);
        person.setName("홍길동");
        person.setAge(11);
        person.setHobbies(java.util.List.of("운동", "독서"));
        model.addAttribute("person", person);
        model.addAttribute("today", LocalDate.now());
        return "example";
    }

    @Setter
    @Getter
    class Person{
        private Long id;
        private String name;
        private int age;
        private java.util.List<String> hobbies;
    }
}

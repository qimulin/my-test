package zhou.wu.bootes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import zhou.wu.bootes.models.Student;
import zhou.wu.bootes.repositories.StudentRepository;

/**
 * Created by lin.xc on 2019/8/2
 */
@RequestMapping("/student")
@RestController
public class StudentController {
/*
    @Autowired
    StudentRepository studentRepository;

    @RequestMapping(value = "/add_index", method = RequestMethod.POST)
    public ResponseEntity<String> indexDoc(@RequestBody Student student) {
        System.out.println("student===" + student);
        studentRepository.save(student);
        return new ResponseEntity<>("save executed!", HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Iterable> getAll() {
        Iterable<Student> all = studentRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Student> getByName(@PathVariable("name") String name) {
        Student student = studentRepository.findByName(name);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Student> updateStudent(@PathVariable("id") String id,
                                           @RequestBody Student updateStudent) {
        Student student = studentRepository.findStudentById(id);
        if (!StringUtils.isEmpty(updateStudent.getSId())) {
            student.setSId(updateStudent.getSId());
        }
        if (!StringUtils.isEmpty(updateStudent.getSName())) {
            student.setSName(updateStudent.getSName());
        }
        if (!StringUtils.isEmpty(updateStudent.getAddr())) {
            student.setAddr(updateStudent.getAddr());
        }
        studentRepository.save(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteStudent(@PathVariable("id") String id) {
        studentRepository.deleteById(id);
        return new ResponseEntity<>("delete execute!", HttpStatus.OK);
    }*/
}

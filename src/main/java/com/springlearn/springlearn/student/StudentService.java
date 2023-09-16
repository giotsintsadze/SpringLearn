package com.springlearn.springlearn.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) throws IllegalAccessException {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent()) {
            throw new IllegalAccessException("Email Exists");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) throws IllegalAccessException {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalAccessException("Student with id " + studentId + " doesnot exsits");
        }
        studentRepository.deleteById(studentId);

    }

    @Transactional
    public void updateStudent (Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student with id doesnot exsits"));

        if (name != null && !name.isEmpty() && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email Taken");
            }
            student.setEmail(email);
        }
    }
}

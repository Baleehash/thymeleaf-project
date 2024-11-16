package com.hqm.thyme.controllers;

import com.hqm.thyme.forms.User;
import com.hqm.thyme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/submitRegistration")
    public String submitRegistration(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("birthday") String birthday,
            @RequestParam("jobType") String jobType,
            @RequestParam("gender") String gender,
            @RequestParam("lastEducation") String lastEducation,
            @RequestParam(value = "married", required = false) String married,
            @RequestParam(value = "nationality", required = false) String nationality,
            @RequestParam("note") String note,
            Model model) {

        boolean isMarried = (married != null && married.equals("true"));
        boolean isNationality = (nationality != null && nationality.equals("true"));

        // Create User object
        User user = new User();
        user.setName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setBirthday(Date.valueOf(birthday));
        user.setJobType(jobType);
        user.setGender(gender);
        user.setMarried(isMarried);
        user.setNationality(isNationality);
        user.setNote(note);

        // Simpan ke database
        userRepository.save(user);

        // Tambahkan data ke model untuk ditampilkan di halaman sukses
        model.addAttribute("fullName", fullName);
        model.addAttribute("email", email);
        model.addAttribute("phone", phone);
        model.addAttribute("birthday", birthday);
        model.addAttribute("jobType", jobType);
        model.addAttribute("gender", gender);
        model.addAttribute("lastEducation", lastEducation);
        model.addAttribute("married", isMarried);
        model.addAttribute("nationality", isNationality);
        model.addAttribute("note", note);

        return "register_success";
    }
}


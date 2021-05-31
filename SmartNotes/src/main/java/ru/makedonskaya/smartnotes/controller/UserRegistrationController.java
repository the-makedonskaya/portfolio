package ru.makedonskaya.smartnotes.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.makedonskaya.smartnotes.entity.User;
import ru.makedonskaya.smartnotes.model.dto.UserRegistrationDto;
import ru.makedonskaya.smartnotes.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, 
                                      BindingResult result){

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())){
      	  result.rejectValue("confirmPassword", null, "Пароли не совпадают");
        }
        
        if (!userDto.getEmail().equals(userDto.getConfirmEmail())) {
      	result.rejectValue("confirmEmail", null, "Email не совпадает");
        }

        if (result.hasErrors()){
            return "registration";
        }
        
        userService.save(userDto);
        return "redirect:/registration?success";
    }

}

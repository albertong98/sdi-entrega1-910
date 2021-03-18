package com.uniovi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	@Autowired
	private RolesService rolesService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute @Validated User user,BindingResult result) {
		signUpFormValidator.validate(user,result);
		if(result.hasErrors()) return "singup";
		
		user.setRole(rolesService.getRoles()[0]);
		user.setSaldo(100);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(),user.getPasswordConfirm());
		
		return "redirect:home";
	}
	
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		model.addAttribute("user", usersService.getUserByEmail(email));
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping("/user/list")
	public String getList(Model model,String searchText) {
		List<User> users = new ArrayList<User>();

		users = usersService.getUsers();
		
		model.addAttribute("usersList", users);
		return "user/list";
	}

	@RequestMapping("/user/profile")
	public String getDetail(Model model, @PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		model.addAttribute("user", usersService.getUserByEmail(email));
		return "user/profile";
	}
	
	@RequestMapping(value="/user/delete", method=RequestMethod.POST)
	public String deleteUser(Model model,@RequestParam("idUser") List<String> idUsers) {
		if(idUsers != null)
			for(String id : idUsers)
				usersService.deleteUser(this.usersService.getUser(Long.parseLong(id)));

		model.addAttribute("usersList", usersService.getUsers());
		return "redirect:list";
	}
}

package com.santitosvk.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.santitosvk.jpo.Employee;
import com.santitosvk.services.EmployeeService;

@Controller
public class ViewController {

	@Autowired
	private EmployeeService employeeSvc;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("loginPage");

		return model;

	}

	@RequestMapping(value = "/searchResults", method = RequestMethod.POST)
	public ModelAndView searchResults(@ModelAttribute Employee employee) {
		ModelAndView mav = new ModelAndView("results");
		mav.addObject("employee",
				employeeSvc.searchEmployee(employee.getName()));
		return mav;
	}

	@RequestMapping("/")
	public ModelAndView home() {
		return new ModelAndView("search", "employee", new Employee());
	}
	
	@RequestMapping("/search")
	public ModelAndView search() {
		return new ModelAndView("search", "employee", new Employee());
	}

	@RequestMapping("/add")
	public ModelAndView add() {
		return new ModelAndView("add", "employee", new Employee());
	}

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public String add(@Valid Employee employee,
			BindingResult result, Model m) {
		if(result.hasErrors()) {
            return "/add";
        }
		employeeSvc.createEmployee(employee); 
        m.addAttribute("message", "Employee was successfully added.");
        return "/search";
		
		
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editEmployee(@PathVariable Integer id, Model m) {
		ModelAndView mav = new ModelAndView("edit");
		if (!m.containsAttribute("employee")) {
			Employee employee = employeeSvc.findById(id);
			mav.addObject("employee", employee);
	    }
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String editEmployee(@Valid Employee employee,// REMEMBER THAT BINDINGRESULT SHOULD ALWAYS FOLLOW @VALID OR YOU WILL HAVE A BAD TIME (ノ ゜Д゜)ノ ︵ ┻━┻
			BindingResult result, @PathVariable Integer id,
			final RedirectAttributes redirectAttributes,
			Model m) {
		if(result.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employee", result);
			redirectAttributes.addFlashAttribute("employee", employee);
			return "redirect:/edit/" + id;
        }
		employeeSvc.update(employee);
		m.addAttribute("message", "Employee was successfully update.");
        return "/search";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteEmployee(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("redirect:/search");
		employeeSvc.delete(id);
		String message = "The employee was successfully deleted.";
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
		ModelAndView model = new ModelAndView();
		model.setViewName("403");
		return model;

	}
}

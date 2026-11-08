package org.projest.app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import fr.uha.ensisa.gl.repo.RepoFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import fr.uha.ensisa.gl.test.Issue;

@Controller
public class HomeController {
	@Autowired
	public RepoFactory repo;
	@RequestMapping(value="/")
	public String home(){
		return "redirect:/hello";
	}

	@RequestMapping(value="/hello")
	public ModelAndView hello(@RequestParam(required=false, defaultValue="World") String name) {
		ModelAndView ret = new ModelAndView("home");
		// Adds an objet to be used in home.jsp
		ret.addObject("name", name);
		return ret;
	}
	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView ret = new ModelAndView("list");                  // 1
		ret.addObject("issues", repo.getIssueRepo().findAll());                      // 2
		return ret;
	}
	@RequestMapping(value = "/create")
	public String create(@RequestParam(required = true) String title) {     // 3
		Issue newIssue = new Issue();
		newIssue.setName(title);                                                // 4
		repo.getIssueRepo().persist(newIssue);                                  // 5
		return "redirect:/list";                                             // 6
	}
}

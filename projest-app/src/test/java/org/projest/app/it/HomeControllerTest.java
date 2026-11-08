package org.projest.app.it;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.projest.app.controller.HomeController;
import org.springframework.web.servlet.ModelAndView;

import fr.uha.ensisa.gl.repo.IssueRepo;
import fr.uha.ensisa.gl.repo.RepoFactory;
import fr.uha.ensisa.gl.test.Issue;

public class HomeControllerTest {

	@Mock public RepoFactory repo;
	@Mock public IssueRepo repoIssue;

	HomeController sut;

	@BeforeEach
	void prepare() {
		MockitoAnnotations.openMocks(this);
		when(repo.getIssueRepo()).thenReturn(repoIssue);
		sut = new HomeController();
		sut.repo = repo;
	}

	@Test
	@SuppressWarnings("unchecked")
	void emptyList() {
		ModelAndView ret = sut.list();
		Collection<Issue> issues =
			(Collection<Issue>) ret.getModelMap().get("issues");
		assertNotNull(issues);
		assertTrue(issues.isEmpty());
	}

	@Test
	void createIssuePersistsAndRedirects() {
		String view = sut.create("mon titre");

		ArgumentCaptor<Issue> captor = ArgumentCaptor.forClass(Issue.class);
		verify(repoIssue).persist(captor.capture());
		assertEquals("mon titre", captor.getValue().getName());
		assertEquals("redirect:/list", view);
	}
	@Test
	void homeRedirectsToHello() {
		assertEquals("redirect:/hello", sut.home());
	}

	@Test
	void helloPutsNameInModel() {
		ModelAndView ret = sut.hello("Snoopy");
		assertEquals("home", ret.getViewName());
		assertEquals("Snoopy", ret.getModelMap().get("name"));
	}	
}
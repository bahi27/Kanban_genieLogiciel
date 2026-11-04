package fr.uha.ensisa.gl.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IssueTest {

	Issue sut;

	@BeforeEach
	void createIssue() {
		sut = new Issue();
	}

	@Test
	@DisplayName("An issue should have a Name")
	void setName() {
		assertNull(sut.getName());
		String name = "A sample Issue name";
		sut.setName(name);
		assertEquals(name, sut.getName());
	}

	@Test
	@DisplayName("Un constructeur complet garde id et description")
	void constructeurComplet() {
		Issue issue = new Issue(1L, "desc");
		assertEquals(1L, issue.getId());
		assertEquals("desc", issue.getDescription());
	}

	@Test
	@DisplayName("Un constructeur vide laisse id, description et name à leur valeur par défaut")
	void constructeurVide() {
		assertEquals(0L, sut.getId());
		assertNull(sut.getDescription());
		assertNull(sut.getName());
	}
}
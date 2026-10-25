package fr.uha.ensisa.gl.repo;
import fr.uha.ensisa.gl.test.Issue ;
import java.util.Collection;
public interface IssueRepo {
	public void persist(Issue issue) ;
	public void remove(long id) ;
	public Issue find(long id) ; 
	public Collection<Issue> findAll() ;
}

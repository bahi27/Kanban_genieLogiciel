package fr.uha.ensisa.gl.mem;
import fr.uha.ensisa.gl.repo.IssueRepo ;
import fr.uha.ensisa.gl.test.Issue ; 
import java.util.Map;
import java.util.TreeMap ; 
import java.util.Collections;
import java.util.Collection ;
public class IssueRepoMem implements IssueRepo{
	private long nextId = 1; 
	private final Map<Long, Issue>
	store =Collections.synchronizedMap(new TreeMap<Long, Issue>());
	@Override 
	public void persist(Issue issue) {
		if (issue.getId() == 0) {                       // 1 : pas encore d'identifiant
			issue.setId(nextId);                             // 2 : lui en donner un
			nextId++;
		}
		store.put(issue.getId(), issue);
	}
	@Override
	public void remove(long id) {
		store.remove(id) ;
	}
	@Override
	public Issue find(long id) {
		return store.get(id) ;
	}
	@Override
	public Collection<Issue> findAll() {
		return store.values();
	}
		
}

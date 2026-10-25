package fr.uha.ensisa.gl.mem;
import fr.uha.ensisa.gl.repo.IssueRepo;
import fr.uha.ensisa.gl.repo.RepoFactory ;
import fr.uha.ensisa.gl.test.Issue ; 
public class RepoFactoryMem implements RepoFactory {

	public final IssueRepo issueRepo = new IssueRepoMem() ;
	public IssueRepo getIssueRep() {
		return this.issueRepo ;
	}
}

package fr.uha.ensisa.gl.test;

public class Issue {
	private String name ; 
	private long id ; 
	private String description ; 

	public Issue(long id , String description) {
		this.id = id ; 
		this.description = description ; 
	}
	public Issue() {
		
	}
	public long getId() {
		return id ; 
	}
	public String getDes() {
		return description ; 
	}
	public String getName() {
		return this.name ;
	}
	public void setName(String name2) {
		this.name = name2  ;
		
	}
}

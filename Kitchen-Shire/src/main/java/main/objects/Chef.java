package main.objects;

import lombok.Data;

@Data
public class Chef {
	
	private Long id;
	
	private String name;
	
	public Chef() {}
	
	public Chef(Long id, String name) {
		this.id=id;
		this.name=name;
	}
	
	

}

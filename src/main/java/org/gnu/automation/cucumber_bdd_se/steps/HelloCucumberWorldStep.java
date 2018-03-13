package org.gnu.automation.cucumber_bdd_se.steps;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Quando;
import cucumber.api.java.pt.Entao;

public class HelloCucumberWorldStep {

	@Dado("^que o mundo existe$")
	public boolean queOMundoExiste() throws Throwable {
		System.out.println("Dado queOMundoExiste()");
		return true;
	}

	@Quando("^eu pensar$")
	public boolean euPensar() throws Throwable {
		System.out.println("Quando euPensar()");
		return true;
	}

	@Entao("^eu existo$")
	public boolean euExisto() throws Throwable {
		System.out.println("Entao euExisto()");
		return true;
	}
	
}

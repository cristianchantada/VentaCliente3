package ventana2;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
	
	private String name;
	private String nif;
	private List<String> creditCards;
	private String ibanNumber;
	
	public Cliente() {
		creditCards = new ArrayList<>();
	}
	
	public Cliente(String name, String nif, String creditCard, String ibanNumber) {
		this.name = name;
		this.nif = nif;
		creditCards = new ArrayList<>();
		creditCards.add(creditCard);
		this.ibanNumber = ibanNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public List<String> getCreditCards() {
		return creditCards;
	}

	public void addCreditCard(String creditCard) {
		creditCards.add(creditCard);
	}

	public String getIbanNumber() {
		return ibanNumber;
	}

	public void setIbanNumber(String ibanNumber) {
		this.ibanNumber = ibanNumber;
	}
}

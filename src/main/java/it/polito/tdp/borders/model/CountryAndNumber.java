package it.polito.tdp.borders.model;

public class CountryAndNumber implements Comparable<CountryAndNumber> {
//implements Comparable perchè vogliamo ordiarli.
	
	private Country country;
	private Integer number;
	
	public CountryAndNumber(Country country, Integer number) {
		super();
		this.country = country;
		this.number = number;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public int compareTo(CountryAndNumber o) {
		//ordinare i dati in ordine DECRESCENTE per numero
		//agire sull'attributo number
		//per farlo in ordine crescente basta invertire o con this
		return o.getNumber().compareTo(this.getNumber());
		
	}

	//perchè mi viene richiesto di scrivere in un determinato modo questo risultato
	@Override
	public String toString() {
		return country.getStateAbb() + "-" + country.getStateName() + "=" + number;
	}
	
	
}

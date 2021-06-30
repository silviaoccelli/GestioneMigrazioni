package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento> {
//quali tipi di evento ho bisgono
	//evento: un numero di persone arrivano in uno stato
	
	private int t; //tempo t, attributo su cui ordinerò gli eventi 
	//se mettiamo integer per t possiamo mettere nel compareto un compareTo
	private Country country; //riferimento allao stato
	private int n; //quantità di persone arrivate nel tempo t nel country
	
	public Evento(int t, Country country, int n) {
		super();
		this.t = t;
		this.country = country;
		this.n = n;
	}
	
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	@Override
	public int compareTo(Evento o) {
		//perchè c'è una priority queue 
		//attributo su cui vogliamo fare l'ordinamento
		return this.t-o.t; //ritorno un valore a seconda dei valori presenti negli attributi t
	}
	
	
}

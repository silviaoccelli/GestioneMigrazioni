package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	//Modello -> qual è lo stato del sistema ad ogni passo
		//Dipende da come abbiamo fatto il grafo
	private Graph<Country, DefaultEdge> grafo;
	
	//tipi di evento -> coda prioritaria 
	//bisogna mettere la coda prioritaria quando il numero di eventi non è fisso a priori,
	//non c'è una creazione di eventi durante la simulazione
	
	//creare la classe evento
	private PriorityQueue<Evento> queue;
	
	//parametri della simulazione
	private Integer N_MIGRANTI = 1000;
	private Country partenza;
	
	//valori in output
	private int T = - 1; //numero passi simulati
	
	//meglio usare una mappa perchè grazie a una chiave riesco a trovare uno stato
	//l'ordinamento lo farò in seguito
	//perchè durante la simulazione devo modificare la simulazione, le persone si muovono
	//ma possono ritornare nello stato iniziale, il numero di persone stanziali cambia anche durante la simualazione
	private Map<Country, Integer> stanziali; 
	
	//per inizializzare il simulatore
	public void init(Country country, Graph<Country, DefaultEdge> grafo) {
		//impostare i parametri
		this.partenza = country;
		this.grafo = grafo;
		
		//impostare lo stato iniziale
		this.T = 1;
		this.stanziali = new HashMap<>();
		//inizializziamo la mappa con i vertici del grafo 
		//stanziali = 0
	   for(Country c:this.grafo.vertexSet()) {
		   stanziali.put(c, 0);
	   }
	   
	   //creo la coda
	   this.queue = new PriorityQueue<Evento>();
	   
	   //inserisco il primo evento
	   this.queue.add(new Evento(T, partenza, N_MIGRANTI));
	 
	}

	//lacio la simulazione
	public void run() {
		//finchè la coda non si svuota ->
		//prendo un evento per volta e lo eseguo
		
		Evento e;
		while((e = this.queue.poll()) != null) {
			//simulo l'evento e 
			
			this.T = e.getT();
		int nPersone = e.getN();
		Country stato = e.getCountry();
		
		//recuperare i vicini di stato
		//neighbotListOf mi indica i County vicini allo stato
		List<Country> vicini = Graphs.neighborListOf(this.grafo, stato);
		
		//Esempio caso limite
		// nPersone = 10
		//persone che si spostano  = 5
		//se (vicini.size()) sono 7 ->
		//migratiPerStato = 0  (perchè la variabile è intera)
		// le 5 persone diventano stanzili
		
		//persone che finiscono in ogni stato
		int migrantiPerStato = (nPersone/2)/vicini.size();
		
		if(migrantiPerStato > 0) {
			//c'è almeno una persona che si sposta
			//creo un nuovo evento per ogni stato confinante
			for(Country confinante : vicini) {
				queue.add(new Evento(e.getT()+1, confinante, migrantiPerStato));
					
				
			}
		}
		
		int stanziali = nPersone - migrantiPerStato * vicini.size();
		
		//facciamo un aggiornamento di stanziali
		this.stanziali.put(stato, this.stanziali.get(stato) + stanziali);
		
		}
		
		
	}
	
	public Map<Country, Integer> getStanziali(){
		return this.stanziali;
	}
	
	public Integer getT() {
		return this.T;
	}

}


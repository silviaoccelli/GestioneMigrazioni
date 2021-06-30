package it.polito.tdp.borders.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private BordersDAO dao;
	private Map<Integer, Country> idMap;
	private Graph<Country, DefaultEdge> grafo;
	private Simulatore sim;
	
	public Model(){
		dao = new BordersDAO();
		this.sim = new Simulatore();
	}
	
public void creaGrafo(Integer anno) {
	grafo = new SimpleGraph<>(DefaultEdge.class);
	idMap = new HashMap<>();
	dao.getVertici(anno, idMap);
	
	//aggiungo i vertici
	Graphs.addAllVertices(grafo, dao.getVertici(anno, idMap));
	
	//aggiungi gli archi
		
 List<Adiacenza> archi = dao.getAdiacenze(anno);
for(Adiacenza c:archi) {
	grafo.addEdge(this.idMap.get(c.getState1no()), this.idMap.get(c.getState2no()));
}

}

//la lista deve contenere il numero di stati confinanti e lo stato
//vogliamo la lista ordinata, quindi non possiamo usare la mappa
//creare una classe temporanea 
public List<CountryAndNumber> getCountyAndNumbers(){
	List<CountryAndNumber> result = new LinkedList();
	//il numero indica il numero di stati vicini
	for(Country c : this.grafo.vertexSet()) {
		//per ogni stato devo vedere quanti sono i suoi vicini+
		result.add(new CountryAndNumber(c,this.grafo.degreeOf(c)));
		//per un arco non direzionato degreeOf restituisce
		//il numero di archi che ci sono a partire da un vertice,
		//quindi quanti vertici vicini ha
	}
	
	//per ordinare i risultati
	Collections.sort(result);
	return result;
	
}

public void simula(Country partenza) {
	if(grafo!=null) {
	sim.init(partenza, grafo);
	sim.run();
	}
}
  public Integer getT() {
	return sim.getT();
}
  public List<CountryAndNumber> getStaziali() {
	  Map<Country, Integer> stanziali = sim.getStanziali();
	  List<CountryAndNumber> result = new LinkedList<>();
	  
	  //per ogni chiave nella mappa creo un elemento nella lista CountryAndNumber
	  for(Country c : stanziali.keySet()) {
		  if(stanziali.get(c)>0) {
		  CountryAndNumber cn = new CountryAndNumber(c, stanziali.get(c));
		  result.add(cn);
	  }
	  }
	  
	  //ordino la lista
	  Collections.sort(result);
	  return result;
  }

public Set<Country> getCountries() {
	if(this.grafo != null) {
		return this.grafo.vertexSet(); 
	}
	return null;
}
}

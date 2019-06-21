package it.polito.tdp.ufo.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	SightingsDAO dao = new SightingsDAO();
	List<String> stati;
	List<Anno> anni;
	SimpleDirectedGraph<String, DefaultEdge> grafo;
	
	public Model() {
		anni = new LinkedList<Anno>(dao.getAllAnni());
	}
	
	public List<Anno> getAnni(){
		return anni;
	}
	
	public List<String> getStati(int anno){
		stati = new LinkedList<String>(dao.getAllStati(anno));
		return stati;
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		Graphs.addAllVertices(grafo, this.getStati(anno));
		for(String v : grafo.vertexSet()) {
			List<String> collegati = new LinkedList<String>(dao.statiCollegati(v, anno));
			for(String c : collegati)
				grafo.addEdge(v, c);
		}
		
		System.out.println(grafo.vertexSet().size() +" "+ grafo.edgeSet());
	}
	
	public SimpleDirectedGraph<String, DefaultEdge> getGrafo(){
		return grafo;
	}
	
	public List<String> getVicini(String stato){
		List<String> vicini = new LinkedList<String>();
		for(String v : grafo.vertexSet())
			if(grafo.containsEdge(v, stato) || grafo.containsEdge(stato, v))
				if(!vicini.contains(v))
					vicini.add(v);
		return vicini;
	}
	
	public List<String> getRaggiungibili(String stato){
		List<String> ragg = new LinkedList<String>();
		GraphIterator<String, DefaultEdge> dfi = new DepthFirstIterator<String, DefaultEdge>(grafo, stato);
		
		while(dfi.hasNext()) {
			ragg.add(dfi.next());
		}
		
		ragg.remove(stato);
		return ragg;
	}

}

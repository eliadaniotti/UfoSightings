package it.polito.tdp.ufo.model;

public class Anno implements Comparable<Anno> {
	int year;
	int avvistasmenti;
	public Anno(int year) {
		super();
		this.year = year;
		//this.avvistasmenti = avvistasmenti;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getAvvistasmenti() {
		return avvistasmenti;
	}
	public void setAvvistasmenti(int avvistasmenti) {
		this.avvistasmenti = avvistasmenti;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + year;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anno other = (Anno) obj;
		if (year != other.year)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Anno [year=" + year + ", avvistasmenti=" + avvistasmenti + "]";
	}
	@Override
	public int compareTo(Anno arg0) {
		// TODO Auto-generated method stub
		return this.year - arg0.getYear();
	}
	
	
}

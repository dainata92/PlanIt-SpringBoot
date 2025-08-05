package com.descodeuses.planit.test;

class Apprenante {
	private Integer age;

	public Integer getAge() {
		return age;
	}
	public void setAge(int age) {
		if(age < 0) {
            throw new IllegalArgumentException("L'âge ne peut pas être négatif.");
		}
		this.age = age;
	}
	private String prenom;
	
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	private Integer salaire;

	private String nom;

	public String getNom() {
		return nom.toUpperCase();
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
}

class TestJava {
	public static void main(String[] args) {
		Apprenante descodeuses = new Apprenante();
		descodeuses.setPrenom("Sophie");
		descodeuses.setNom("luu");
		descodeuses.setAge(28);

		int somme = 1+1;
		System.out.println("Resultat : " + somme);
		System.out.println(descodeuses.getAge());
		System.out.println(descodeuses.getPrenom());
		System.out.println(descodeuses.getNom());
	}
}

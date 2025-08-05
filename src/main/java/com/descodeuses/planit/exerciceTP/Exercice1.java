package com.descodeuses.planit.exerciceTP;

import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import com.opencsv.CSVWriter;
import java.io.IOException;

class Taches {
    public boolean completed;
    public String[] tache;
    public LocalDate date;

    // constructeur
    public Taches(boolean completed, String[] tache, LocalDate date) {
        this.completed = completed;
        this.tache = tache;
        this.date = date;
    }
}

public class Exercice1 {
    static ArrayList<Taches> taches = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    // on suppose continuer true jusqu'a quand on quitte
    static boolean continuer = true;

    public static void main(String[] args) {
        // ca on affiche dans une boucle
        // tant que continuer reste true
        while (continuer) {
            System.out.println("1.Ajouter une tâche");
            System.out.println("2.Voir les tâches");
            System.out.println("3.Marquer comme faite");
            System.out.println("4.Supprimer une tâche");
            System.out.println("5.Quitter");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            // choissir le numero
            scanner.nextLine();

            switch (choix) {
                // si on choisi 1, la methode ajout va etre appelée
                // pareil pour les autres methodes
                case 1:
                    ajout();
                    break;
                case 2:
                    afficher();
                    break;
                case 3:
                    marquerCommeFaite();
                    break;
                case 4:
                    suppression();
                    break;
                case 5:
                    quitter();
                    break;
            }
        }
    }

    // methode pour ajouter
    public static void ajout() {
        System.out.print("Combien de tâches voulez-vous ajouter ? ");
        int nombreDeTaches = Integer.parseInt(scanner.nextLine());

        for (int i = 1; i <= nombreDeTaches; i++) {
            System.out.print("Entrez la tâche " + i + " : ");
            // inserer les taches
            String tache = scanner.nextLine();
            System.out.println("Entrez la date (format yyyy-MM-dd) : ");
            String data = scanner.nextLine();
            LocalDate date = LocalDate.parse(data);
            // completed ca sera toujours false jusqu'a modification
            Taches t = new Taches(false, new String[] { tache }, date);
            taches.add(t);
        }
    }

    // methode pour afficher
    public static void afficher() {
        System.out.println("Liste des tâches :");
        // le tableau taches va etre parcouru
        for (int i = 0; i < taches.size(); i++) {
            Taches t = taches.get(i);
            // on doit choisir si la tache est completed ou non
            String status = t.completed ? "[X]" : "[ ]";
            System.out.println(i + 1 + ". " + status + " " + t.tache[0] + " - Date : " + t.date);
        }
    }

    // methode pour marquer completed
    public static void marquerCommeFaite() {
        afficher();
        System.out.print("Numéro de la tâche à marquer comme faite : ");
        int index = scanner.nextInt() - 1;

        if (index >= 0 && index < taches.size()) {
            taches.get(index).completed = true;
            System.out.println("Tâche marquée comme faite !");
        }
        afficher();
    }

    // methode pour supprimer
    public static void suppression() {
        afficher();
        System.out.print("Quel numéro de tâche voulez-vous supprimer ? ");
        int numero = scanner.nextInt() - 1;

        if (numero >= 0 && numero < taches.size()) {
            taches.remove(numero);
            System.out.println("Tâche supprimée avec succès !");
        }
        afficher();
    }

    // methode pour quitter le programme
    public static void quitter() {
        System.out.print("Voulez-vous exporter la liste des tâches en CSV ? (oui/non) : ");
        String reponse = scanner.nextLine();

        if (reponse.equalsIgnoreCase("oui")) {
            exporterCSV();
        }

        continuer = false;
        System.out.println("Au revoir !");
    }

    public static void exporterCSV() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("taches.csv"),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.DEFAULT_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] header = { "Tache", "Completed", "Date" };
            writer.writeNext(header);

            for (Taches t : taches) {
                String[] ligne = {
                        t.tache[0],
                        String.valueOf(t.completed),
                        t.date.toString()
                };
                writer.writeNext(ligne);
            }

            writer.close();
            System.out.println("Fichier CSV créé avec succès !");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'exportation CSV : " + e.getMessage());
        }
    }
}

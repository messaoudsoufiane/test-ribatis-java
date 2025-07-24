import java.util.*;

import exception.LivreExisteDejaException;
import exception.LivreIntrouvableException;

public class Bibliotheque {
    private final String nom;
    private final int capaciteMax;
    private final HashMap<String, Livre> collection; // Clé = ISBN (plus fiable que le titre)
    private int compteurEmprunts;

    // Constructeurs , Par default Capacite Maximal egal 1000
    public Bibliotheque(String nom) {
        this(nom, 1000);
    }

    public Bibliotheque(String nom, int capaciteMax) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom ne peut être vide");
        }
        if (capaciteMax <= 0) {
            throw new IllegalArgumentException("Capacité invalide");
        }

        this.nom = nom.strip();
        this.capaciteMax = capaciteMax;
        this.collection = new HashMap<>(capaciteMax);
    }

    // Méthodes principales
    public void ajouterLivre(Livre livre) throws LivreExisteDejaException {
        Objects.requireNonNull(livre, "Le livre ne peut être null");

        if (collection.size() >= capaciteMax) {
            throw new IllegalStateException("Capacité maximale atteinte");
        }

        String isbn = livre.getIsbn();
        if (isbn.isBlank()) {
            throw new IllegalArgumentException("L'ISBN est obligatoire");
        }

        if (collection.containsKey(isbn)) {
            throw new LivreExisteDejaException("ISBN " + isbn + " existe déjà");
        }

        collection.put(isbn, livre);
    }

    public void supprimerLivre(String isbn) throws LivreIntrouvableException {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN invalide");
        }

        Livre livre = collection.get(isbn);
        if (livre == null) {
            throw new LivreIntrouvableException("ISBN " + isbn + " introuvable");
        }

        if (!livre.isDisponible()) {
            throw new IllegalStateException("Le livre est emprunté");
        }

        collection.remove(isbn);
    }

    // Méthodes de recherche
    public Livre rechercherParIsbn(String isbn) throws LivreIntrouvableException {
        Livre livre = collection.get(Objects.requireNonNull(isbn));
        if (livre == null) {
            throw new LivreIntrouvableException("ISBN " + isbn + " introuvable");
        }
        return livre;
    }

    public List<Livre> rechercherParTitre(String titre) {
        List<Livre> resultats = new ArrayList<>();
        String titreLower = titre.toLowerCase();

        for (Livre livre : collection.values()) {
            if (livre.getTitre().toLowerCase().contains(titreLower)) {
                resultats.add(livre);
            }
        }
        return resultats;
    }

    // Gestion des emprunts
    public void emprunterLivre(String isbn) throws LivreIntrouvableException {
        Livre livre = rechercherParIsbn(isbn);
        if (!livre.isDisponible()) {
            throw new IllegalStateException("Livre déjà emprunté");
        }
        livre.setDisponible(false);
        compteurEmprunts++;
    }

    public void retournerLivre(String isbn) throws LivreIntrouvableException {
        Livre livre = rechercherParIsbn(isbn);
        if (livre.isDisponible()) {
            throw new IllegalStateException("Livre non emprunté");
        }
        livre.setDisponible(true);
    }

    // Statistiques
    public void afficherStatistiques() {
        System.out.printf("""
            === Statistiques de %s ===
            Capacité: %d/%d
            Livres disponibles: %d
            Emprunts totaux: %d
            """,
                nom, collection.size(), capaciteMax,
                getNombreLivresDisponibles(), compteurEmprunts);
    }
    public void afficherLivresDisponibles() {
        System.out.println("\n=== Livres disponibles (" + getNombreLivresDisponibles() +
                "/" + getNombreLivres() + ") ===");

        if (collection.isEmpty()) {
            System.out.println("La bibliothèque est vide");
            return;
        }

        boolean aucunDisponible = true;
        for (Livre livre : collection.values()) {
            if (livre.isDisponible()) {
                System.out.println(livre.affichageFormate());
                aucunDisponible = false;
            }
        }

        if (aucunDisponible) {
            System.out.println("Tous les livres sont actuellement empruntés.");
        }
    }

    // Méthodes utilitaires
    public int getNombreLivres() {
        return collection.size();
    }

    public int getNombreLivresDisponibles() {
        return (int) collection.values().stream()
                .filter(Livre::isDisponible)
                .count();
    }

    public Collection<Livre> getTousLivres() {
        return new ArrayList<>(collection.values());
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }
}
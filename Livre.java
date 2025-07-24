import java.util.Objects;

/**
 * Classe représentant un livre dans la bibliothèque
 */
public class Livre {
    // Attributs privés
    private String titre;
    private String auteur;
    private String isbn;
    private int anneePublication;
    private String editeur;
    private String langue;
    private boolean disponible = true; // Par défaut disponible lors de la création

    // Constructeur avec tous les paramètres
    public Livre(String titre, String auteur, String isbn, int anneePublication, String editeur, String langue, boolean disponible) {
        // Validation des paramètres obligatoires
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        if (auteur == null || auteur.trim().isEmpty()) {
            throw new IllegalArgumentException("L'auteur ne peut pas être vide");
        }

        this.titre = titre.trim();
        this.auteur = auteur.trim();
        this.isbn = isbn != null ? isbn.trim() : "";
        this.anneePublication = anneePublication;
        this.editeur = editeur != null ? editeur.trim() : "";
        this.langue = langue != null ? langue.trim() : "Français";
        this.disponible = disponible;
    }

    // Constructeur avec paramètres principaux
    public Livre(String titre, String auteur, String isbn, int anneePublication) {
        // Validation des paramètres obligatoires
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        if (auteur == null || auteur.trim().isEmpty()) {
            throw new IllegalArgumentException("L'auteur ne peut pas être vide");
        }

        this.titre = titre.trim();
        this.auteur = auteur.trim();
        this.isbn = isbn != null ? isbn.trim() : "";
        this.anneePublication = anneePublication;
        this.editeur = "";
        this.langue = "Français";
        this.disponible = true; // Par défaut, le livre est disponible
    }

    // Constructeur avec paramètres principaux (titre et auteur obligatoires)
    public Livre(String titre, String auteur) {
        // Validation des paramètres obligatoires
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        if (auteur == null || auteur.trim().isEmpty()) {
            throw new IllegalArgumentException("L'auteur ne peut pas être vide");
        }

        this.titre = titre.trim();
        this.auteur = auteur.trim();
        this.isbn = "";
        this.anneePublication = 0;
        this.editeur = "";
        this.langue = "Français";
        this.disponible = true;
    }

    // Getters
    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public String getEditeur() {
        return editeur;
    }

    public String getLangue() {
        return langue;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // Setters
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Redéfinition de toString() pour un affichage lisible
    @Override
    public String toString() {
        return "Livre{" +
                "titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", isbn='" + isbn + '\'' +
                ", anneePublication=" + anneePublication +
                ", editeur='" + editeur + '\'' +
                ", langue='" + langue + '\'' +
                ", disponible=" + disponible +
                '}';
    }

    // Redéfinition de equals() - deux livres sont égaux s'ils ont le même titre
    // (selon les spécifications du projet)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Livre livre = (Livre) obj;
        return Objects.equals(titre, livre.titre);
    }

    // Redéfinition de hashCode() - cohérent avec equals()
    @Override
    public int hashCode() {
        return Objects.hash(titre);
    }

    // Méthode utilitaire pour vérifier si le livre a des informations complètes
    public boolean estComplet() {
        return titre != null && !titre.trim().isEmpty() &&
                auteur != null && !auteur.trim().isEmpty() &&
                isbn != null && !isbn.trim().isEmpty() &&
                anneePublication > 0 &&
                editeur != null && !editeur.trim().isEmpty() &&
                langue != null && !langue.trim().isEmpty();
    }

    // Méthode pour obtenir un affichage formaté du livre
    public String affichageFormate() {
        StringBuilder sb = new StringBuilder();
        sb.append("📖 ").append(titre);
        if (auteur != null && !auteur.trim().isEmpty()) {
            sb.append(" - ").append(auteur);
        }
        if (anneePublication > 0) {
            sb.append(" (").append(anneePublication).append(")");
        }
        if (editeur != null && !editeur.trim().isEmpty()) {
            sb.append(" - Éditeur: ").append(editeur);
        }
        if (langue != null && !langue.trim().isEmpty()) {
            sb.append(" - Langue: ").append(langue);
        }
        if (isbn != null && !isbn.trim().isEmpty()) {
            sb.append(" [ISBN: ").append(isbn).append("]");
        }
        sb.append(" - ").append(disponible ? "Disponible" : " Emprunté");
        return sb.toString();
    }

    // Méthodes utilitaires pour la gestion des emprunts
    public void emprunter() {
        this.disponible = false;
    }

    public void retourner() {
        this.disponible = true;
    }
}
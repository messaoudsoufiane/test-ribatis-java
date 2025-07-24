import exception.LivreExisteDejaException;
import exception.LivreIntrouvableException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Bibliotheque biblio = new Bibliotheque("Bibliothèque Complète", 10);

        // Tests unitaires de la classe Livre
        testerConstructeursLivres();
        testerMethodesLivres();

        // Tests d'intégration avec Bibliotheque
        testerIntegrationComplete(biblio);

        // Tests spécifiques de Bibliotheque
        testerOperationsBibliotheque(biblio);
    }

    private static void testerConstructeursLivres() {
        System.out.println("\n=== TEST CONSTRUCTEURS LIVRE ===");

        try {
            // Test constructeur complet
            Livre livreComplet = new Livre("Clean Code", "Robert Martin", "9780132350884", 2008,
                    "Pearson", "Anglais", true);
            System.out.println("Livre complet créé: " + livreComplet.affichageFormate());

            // Test constructeur intermédiaire
            Livre livreIntermediaire = new Livre("Design Patterns", "Erich Gamma", "9780201633610", 1994);
            System.out.println(" Livre intermédiaire créé: " + livreIntermediaire.affichageFormate());

            // Test constructeur minimal
            Livre livreMinimal = new Livre("Refactoring", "Martin Fowler");
            System.out.println(" Livre minimal créé: " + livreMinimal.affichageFormate());

            // Test validation titre vide
            try {
                new Livre("", "Auteur");
                System.out.println(" Erreur: titre vide accepté");
            } catch (IllegalArgumentException e) {
                System.out.println("Exception titre vide capturée");
            }

            // Test validation auteur null
            try {
                new Livre("Titre", null);
                System.out.println(" Erreur: auteur null accepté");
            } catch (IllegalArgumentException e) {
                System.out.println(" Exception auteur null capturée");
            }

        } catch (Exception e) {
            System.out.println(" Erreur inattendue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testerMethodesLivres() {
        System.out.println("\n=== TEST METHODES LIVRE ===");

        Livre livre = new Livre("Effective Java", "Joshua Bloch", "9780134685991", 2018,
                "Addison-Wesley", "Anglais", true);

        // Test getters
        System.out.println("=== TEST GETTERS ===");
        System.out.println("Titre: " + livre.getTitre());
        System.out.println("Auteur: " + livre.getAuteur());
        System.out.println("ISBN: " + livre.getIsbn());
        System.out.println("Année: " + livre.getAnneePublication());
        System.out.println("Éditeur: " + livre.getEditeur());
        System.out.println("Langue: " + livre.getLangue());
        System.out.println("Disponible: " + livre.isDisponible());

        // Test setters
        System.out.println("\n=== TEST SETTERS ===");
        livre.setTitre("Effective Java 3rd Edition");
        livre.setAuteur("Joshua Bloch et al.");
        livre.setIsbn("9780134685992");
        livre.setAnneePublication(2022);
        livre.setEditeur("Pearson");
        livre.setLangue("English");
        livre.setDisponible(false);
        System.out.println("Livre modifié: " + livre.affichageFormate());

        // Test méthodes utilitaires
        System.out.println("\n=== TEST METHODES UTILITAIRES ===");
        System.out.println("estComplet(): " + livre.estComplet());
        System.out.println("equals(): " + livre.equals(new Livre("Effective Java 3rd Edition", "Autre")));
        System.out.println("hashCode(): " + livre.hashCode());

        // Test gestion emprunts
        System.out.println("\n=== TEST GESTION EMPRUNTS ===");
        livre.retourner();
        System.out.println("Après retour, disponibilité de livre : " + livre.isDisponible());
        livre.emprunter();
        System.out.println("Après emprunt de livre la disponibilité est : " + livre.isDisponible());
    }

    private static void testerIntegrationComplete(Bibliotheque biblio) {
        System.out.println("\n=== TEST INTEGRATION COMPLETE ===");

        try {
            // Création de livres avec tous les attributs
            Livre livre1 = new Livre("Clean Architecture", "Robert Martin", "9780134494166", 2017,
                    "Pearson", "Anglais", true);
            Livre livre2 = new Livre("Domain-Driven Design", "Eric Evans", "9780321125217", 2003,
                    "Addison-Wesley", "Anglais", true);

            // Ajout à la bibliothèque
            biblio.ajouterLivre(livre1);
            biblio.ajouterLivre(livre2);
            System.out.println("Livres ajoutés avec tous les attributs");

            // Test affichage
            System.out.println("\n=== AFFICHAGE COMPLET ===");
            biblio.afficherLivresDisponibles();

            // Test recherche avancée
            System.out.println("\n=== RECHERCHE AVANCEE ===");
            List<Livre> resultats = biblio.rechercherParTitre("Design");
            System.out.println("Résultats recherche 'Design': " + resultats.size());

            // Test modification après ajout
            Livre livreTrouve = biblio.rechercherParIsbn("9780134494166");
            livreTrouve.setEditeur("Pearson Education");
            livreTrouve.setLangue("English");
            System.out.println("\nLivre modifié: " + livreTrouve.affichageFormate());

        } catch (Exception e) {
            System.out.println("Erreur d'intégration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testerOperationsBibliotheque(Bibliotheque biblio) {
        System.out.println("\n=== TEST OPERATIONS BIBLIOTHEQUE ===");

        try {
            // Création de livres de test
            Livre livre1 = new Livre("Mastering Docker & Spring Boot", "Karim alaoui", "9781234567890", 2023);
            Livre livre2 = new Livre("Mastering Jenkins CI/CD", "Soufiane oskar", "9780987654321", 2022);
            Livre livre3 = new Livre("Mastering Generative AI", "Salma raoui", "9785432109876", 2024);

            // Ajout normal
            biblio.ajouterLivre(livre1);
            biblio.ajouterLivre(livre2);
            biblio.ajouterLivre(livre3);
            System.out.println("Ajout de 3 livres réussis");

            // Test affichage
            biblio.afficherLivresDisponibles();

            // Test ajout doublon
            try {
                biblio.ajouterLivre(livre1);
                System.out.println(" Erreur: doublon non détecté");
            } catch (LivreExisteDejaException e) {
                System.out.println(" Exception doublon capturée: " + e.getMessage());
            }

            // Test recherche
            System.out.println("\n=== TEST RECHERCHE ===");
            Livre trouve = biblio.rechercherParIsbn("9781234567890");
            System.out.println(" Livre trouvé: " + trouve.affichageFormate());

            // Test suppression
            System.out.println("\n=== TEST SUPPRESSION ===");
            biblio.supprimerLivre("9780987654321");
            System.out.println(" Suppression réussie");

            // Test emprunts
            System.out.println("\n=== TEST EMPRUNTS ===");
            biblio.emprunterLivre("9785432109876");
            System.out.println(" Emprunt réussi");

            // Test statistiques
            System.out.println("\n=== TEST STATISTIQUES ===");
            biblio.afficherStatistiques();

        } catch (Exception e) {
            System.out.println(" Erreur inattendue: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	private static class Marche {
		private Etal[] etals;

		public Marche(int nombreEtals) {
			etals = new Etal[nombreEtals];
			for (int i = 0; i < nombreEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i] != null && !etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		public Etal[] trouverEtal(String produit) {
			int nbEtalAvecProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i] != null && etals[i].contientProduit(produit)) {
					nbEtalAvecProduit++;
				}
			}
			Etal[] tabEtal = new Etal[nbEtalAvecProduit];
			for (int i = 0, j = 0; i < etals.length; i++) {
				if (etals[i] != null && etals[i].contientProduit(produit)) {
					tabEtal[j] = etals[i];
					j++;
				}
			}
			return tabEtal;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (gaulois != null && gaulois.equals(etals[i].getVendeur())) {
					return etals[i];
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				} else {
					nbEtalVide++;
				}
			}
			if (nbEtalVide>0) {
				chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}
	}
	//***************************

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
			throw new VillageSansChefException();
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef ");
			chaine.append(chef.getNom());
			chaine.append(".\n");
		} else {
			chaine.append("Au village du chef ");
			chaine.append(chef.getNom());
			chaine.append( " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- ");
				chaine.append(villageois[i].getNom());
				chaine.append("\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int indice = marche.trouverEtalLibre();
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom());
		chaine.append(" cherche un endroit pour vendre ");
		chaine.append(nbProduit);
		chaine.append(" ");
		chaine.append(produit);
		chaine.append(".\n");
		if (indice == -1) {
			chaine.append("Le vendeur " + vendeur.getNom() + " n'a pas trouvé d'étal libre.\n");
			return chaine.toString();
		}
		marche.utiliserEtal(indice, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (indice+1) + ".\n");
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etalsProduit = marche.trouverEtal(produit);
		StringBuilder chaine = new StringBuilder();
		if (etalsProduit.length == 0) {
			return "Aucun vendeur ne vend " + produit + ".\n";
		}
		if (etalsProduit.length == 1) {
			chaine.append("Seul le vendeur " + etalsProduit[0].getVendeur().getNom() + " vend des " + produit + ".\n");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < etalsProduit.length; i++) {
				chaine.append("- " + etalsProduit[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalALiberer = this.rechercherEtal(vendeur);
		StringBuilder chaine = new StringBuilder();
		if (etalALiberer == null) {
			chaine.append(vendeur.getNom() + " ne tient pas d'étal !\n");
		} else {
			chaine.append(etalALiberer.libererEtal());
		}
		return chaine.toString();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder("Le marché du village \"" + this.getNom() + "\" possède plusieurs étals :\n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
}
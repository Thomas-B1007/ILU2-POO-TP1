package villagegaulois;

import personnages.Gaulois;

public class Marche {
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

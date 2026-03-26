package histoire;

import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String args[]) {
		Etal etal = new Etal();
		etal.libererEtal();
		System.out.println("Fin du test libererEtal");
		try {
			etal.acheterProduit(2, null);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("Il faut acheter une quantité positive de produit !");
		}
		catch (IllegalStateException e) {
			e.printStackTrace();
			System.out.println("L'étal doit être occupé !");
		}
		System.out.println("Fin du test acheterProduit");
	}
}

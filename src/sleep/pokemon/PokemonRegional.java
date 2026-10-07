package sleep.pokemon;

import sleep.bouffe.IngredientPoke;
import sleep.dodos.SleepStyle;
import sleep.zone.Island;
import sleep.dodos.TypesDodo;
import utilitaire.PokeData;
import utilitaire.PokeTypes;
import utilitaire.Region;
import utilitaire.Util;
import utilitaire.article.SideGamePage;

import java.util.ArrayList;

public class PokemonRegional extends Pokemon{
    private final Region m_region;

    /**
     *
     * @param nom : Nom du Pokémon
     * @param region : Région d'où provient la forme
     * @param numDex : Numéro du Pokémon dans le Pokédex National
     * @param type : Type du Pokémon dans sleep
     * @param dodoType : Catégorie de dodo du Pokémon (Ptidodo, Bondodo, ou Grododo)
     * @param specialite : Baies, Ingrédients, ou Compétences
     * @param ingredients : Liste des ingrédients du Pokémon à créer au préalable
     * @param sleepStyles : Liste des dodos du Pokémon pré-remplis
     * @param iles : Iles où le Pokémon peut être trouvé
     * @param frequence : Fréquence de base du Pokémon au format "h:min:sec" ou "min:sec"
     * @param capacite : Capacité maximale du Pokémon (combien d'objets peut-il tenir par défaut)
     * @param competence : Compétence Principale du Pokémon
     * @param ptsAmitie : Combien de Pokébiscuits max faut-il pour devenir ami avec ce Pokémon
     * @param bonbon : Nom de Pokémon utilisé pour les bonbons de celui-ci (utile pour les Pokémon évolués)
     */
    public PokemonRegional(String nom, Region region, int numDex, PokeTypes type, TypesDodo dodoType, Specialites specialite, ArrayList<IngredientPoke> ingredients, ArrayList<SleepStyle> sleepStyles, ArrayList<Island> iles, String frequence, int capacite, Competences competence, int ptsAmitie, String bonbon, Imagery imageryType, String description, boolean isSingle)
    {
        super(nom, numDex, type, dodoType, specialite, ingredients, sleepStyles, iles, frequence, capacite, competence, ptsAmitie, bonbon, imageryType, description, isSingle);
        m_region = region;
    }

    @Override
    protected SideGamePage getSideGamePage() {
        return new SideGamePage(PokeData.getPokemonFromName(getFullName()), m_region);
    }

    @Override
    protected String getNameSection() {
        return (super.getNameSection() + "|forme=" + m_region.getFrName());
    }

    @Override
    public String getRegionalName() {
        return super.getRegionalName() + " " + m_region.getFrAdjective();
    }

    @Override
    protected String getPokemonListName() {
        return super.getPokemonListName() + " forme(" + m_region.getFrName() + ")";
    }

    @Override
    protected String getImageID() {
        return super.getImageID() + " " + m_region.getFrName();
    }
}

package sleep.pokemon;

import utilitaire.ElementNotFoundException;

public enum Competences {
    CHARGE_PUISSANCE_S("Charge de Puissance S", "Charge de Puissance S", "Puissance"),
    STOCKAGE("Stockage (Charge de Puiss. S)", "Charge de Puissance S", "Stockage"),

    CHARGE_PUISSANCE_M("Charge de Puissance M", "Charge de Puissance M", "Puissance"),
    MAUVAIS_REVE("Mauvais Rêve (Ch. de Puiss. M)", "Charge de Puissance M", "Puissance"),

    AIMANT_FRAGMENT_DE_REVE("Aimant à Fragment de Rêve S", "Aimant à Fragment de Rêve S", "Fragment de Rêve"),
    AURASPERE("Aurasphère (Aimant à Fragment de Rêve S)", "Aimant à Fragment de Rêve S", "Fragment de Rêve"),

    ENCOURAGEMENT_ENERGIQUE("Encouragement Énergique S", "Encouragement Énergique S", "Énergie"),
    FROTTE_FRIMOUSSE("Frotte-Frimousse (Encouragement Énergique S)", "Encouragement Énergique S", "Énergie"),
    VIBRA_SOIN("Vibra Soin (Encouragement Énergique S)", "Encouragement Énergique S", "Énergie"),

    CHARGE_ENERGIE("Charge d'Énergie S", "Charge d'Énergie S", "Énergie"),
    RAYON_LUNE("Rayon Lune (Charge d'Énergie S)", "Charge d'Énergie S", "Énergie"),

    ENERGIE_PARTAGEE("Énergie Partagée S", "Énergie Partagée S", "Énergie"),
    PRIERE_LUNAIRE("Prière Lunaire (Én. Partagée S)", "Énergie Partagée S", "Énergie"),
    JUS_DE_BAIE("Jus de Baie (Énergie Partagée S)", "Énergie Partagée S", "Énergie"),

    SOUTIEN_EXTRA("Soutien Extra S", "Soutien Extra S", "Soutien"),

    AIMANT_INGREDIENT("Aimant à Ingrédient S", "Aimant à Ingrédient S", "Ingrédient"),
    CADEAU("Cadeau (Aimant à Ingrédient S)", "Aimant à Ingrédient S", "Ingrédient"),
    PLUS("Plus (Aimant à Ingrédient S)", "Aimant à Ingrédient S", "Ingrédient"),

    GARDE_MANGER("Garde-Manger S", "Garde-Manger S", "Plat"),
    MOINS("Moins (Garde-Manger S)", "Garde-Manger S", "Plat"),

    PLAT_SUPER_BON("Plat Super Bon S", "Plat Super Bon S", "Super Bon"),

    METRONOME("Métronome (Pokémon Sleep)", "Métronome (Pokémon Sleep)", "Métronome"),

    COPIE("Copie (Copiage de Comp.)", "Copiage de Compétence", "Copiage"),
    MORPHING("Morphing (Copiage de Comp.)", "Copiage de Compétence", "Copiage"),

    SUPER_SOUTIEN("Super Soutien", "Super Soutien", ""),
    POLYVALENT("Polyvalent", "Polyvalent", "Polyvalent"),

    BAIES_A_GOGO("Baies à gogo", "Baies à gogo", "Baies"),
    FANTOMASQUE("Fantômasque (Baies à gogo)", "Baies à gogo", "Baies"),
    DRACO_METEORE("Draco-Météore (Baies à gogo)", "Baies à gogo", "Baies"),

    PIOCHE_INGREDIENTS("Pioche à Ingrédient S", "Pioche à Ingrédient S", "Pioche"),
    CHANCEUX("Chanceux (Pioche à Ingrédient S)", "Pioche à Ingrédient S", "Pioche"),
    HYPER_CUTTER("Hyper Cutter (Pioche à Ingrédient S)", "Pioche à Ingrédient S", "Pioche"),

    GONFLETTE("Gonflette (Aide de Cuisine S)", "Aide de Cuisine S", "Cuisine"),

    FEU_ENSORCELE("Feu Ensorcelé (Cordon-bleu S", "Cordon-bleu S", "Cordon-bleu"),

    BAIES_EN_FOLIE("Baies en folie", "Baies en folie", "Baies en folie"),
    FRAPPE_PSY("Frappe Psy (Baies en folie)", "Baies en folie", "Baies en folie");

    private final String m_name;
    private final String m_category;
    private final String m_icon;

    Competences(String nom, String category, String icon)
    {
        m_name = nom;
        m_category = category;
        m_icon = icon;
    }

    public String getName() {
        return m_name;
    }
    public String getCategory() {return m_category;}
    public String getIcon() {return m_icon;}

    public static Competences getFromName(String name)
    {
        name = name.split("\\|")[0];
        for (Competences c : Competences.values()) {
            if (c.m_name.equals(name)) {
                return c;
            }
        }
        throw new ElementNotFoundException(name, "Pokémon ability");
    }
}

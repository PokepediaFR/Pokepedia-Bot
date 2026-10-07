package sleep.pokemon;

import sleep.dodos.SleepStyle;
import sleep.zone.Island;
import sleep.dodos.TypesDodo;
import sleep.UtilSleep;
import sleep.bouffe.IngredientPoke;
import utilitaire.*;
import utilitaire.article.Page;
import utilitaire.article.PageToPublish;
import utilitaire.article.SideGamePage;

import java.util.*;

import static utilitaire.Wiki.POKEPEDIA;

public class Pokemon {
    private final String m_name;
    protected final String m_numDex;
    private final PokeTypes m_type;
    private final TypesDodo m_sleepType;
    private final Specialites m_speciality;
    private final ArrayList<IngredientPoke> m_ingredientList;
    private final ArrayList<SleepStyle> m_sleepList;
    private final ArrayList<Island> m_zones;
    private int m_freqHour;
    private int m_freqMin;
    private int m_freqSec;
    private final int m_storage;
    protected final Competences m_ability;
    private final int m_recruitPoints;
    private final String m_candy;
    protected final Imagery m_imageryType;
    private final String m_description;
    private final boolean m_isSingle;

    protected final String[] m_pages = {
            "Liste des Pokémon de soutien de Pokémon Sleep",
            "Liste des styles de dodo de Pokémon Sleep",
            "Ingrédient (Pokémon Sleep)",
            "Bonbon (Pokémon Sleep)",
            "Liste des Pokémon par famille d'évolution dans Pokémon Sleep"
    };

    /**
     * Constructeur (à peine surchargé) des données d'un Pokémon dans Pokémon sleep sans input supplémentaire
     * @param nom : Nom du Pokémon
     * @param numDex : Numéro du Pokémon dans le Pokédex National
     * @param type : Type du Pokémon dans sleep
     * @param dodoType : Catégorie de sleepStyle du Pokémon (Ptidodo, Bondodo, ou Grododo)
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
    public Pokemon(String nom, int numDex, PokeTypes type, TypesDodo dodoType, Specialites specialite, ArrayList<IngredientPoke> ingredients, ArrayList<SleepStyle> sleepStyles, ArrayList<Island> iles, String frequence, int capacite, Competences competence, int ptsAmitie, String bonbon, Imagery imageryType, String description, boolean isSingle)
    {
        m_name = nom;
        m_numDex = Util.numberToPokepediaDexFormat(numDex);
        m_type = type;
        m_sleepType = dodoType;
        m_speciality = specialite;
        fillFrequency(frequence);
        m_storage = capacite;
        m_ability = competence;
        m_recruitPoints = ptsAmitie;
        m_candy = bonbon;
        m_zones = iles;
        m_sleepList = sleepStyles;
        m_ingredientList = ingredients;
        m_imageryType = imageryType;
        m_description = (description == null) ? "{{?}}" : description;
        m_isSingle = isSingle;
    }

    /**
     * Remplit les différentes valeurs de la fréquence à partir du String passé en paramètre
     * @param freq : String au format "min:sec" ou "h:min:sec"
     */
    private void fillFrequency(String freq)
    {
        String[] details = freq.split(":");
        switch (details.length)
        {
            case 2 :
                m_freqHour = 0;
                m_freqMin = Integer.parseInt(details[0]);
                m_freqSec = Integer.parseInt(details[1]);
                break;
            case 3 :
                m_freqHour = Integer.parseInt(details[0]);
                m_freqMin = Integer.parseInt(details[1]);
                m_freqSec = Integer.parseInt(details[2]);
                break;
            default: {
                throw new IllegalArgumentException("Format de fréquence invalide");
            }
        }
    }

    /**
     * Fonction à appeler pour ajouter le Pokémon aux pages du Wiki UNIQUEMENT s'il n'y a encore rien pour lui
     *
     * @return all Wiki pages that should be modified for this Pokémon alongside the new text
     */
    public ArrayList<PageToPublish> getWikiModifications()
    {
        ArrayList<PageToPublish> wikiPages = new ArrayList<>();
        Page listeSoutien = new Page(m_pages[0], POKEPEDIA);
        Page listeDodo = new Page(m_pages[1], POKEPEDIA);
        Page listeIngredients = new Page(m_pages[2], POKEPEDIA);
        Page listeBonbons = new Page(m_pages[3], POKEPEDIA);
        Page imagery = new Page(getRegionalName() + "/Imagerie", POKEPEDIA);
        Page ability = new Page(m_ability.getCategory(), POKEPEDIA);
        Page main = new Page(getRegionalName(), POKEPEDIA);
        Page secondaryGames = new Page(getRegionalName() + "/Jeux secondaires", POKEPEDIA);
        Page redirection = new Page(getRegionalName() + "/Pokémon Sleep", POKEPEDIA);

        final String SUMMARY = "ajout de " + getFullName();
        wikiPages.add(new PageToPublish(listeSoutien, updateListePokeSoutien(listeSoutien), SUMMARY));
        wikiPages.add(new PageToPublish(listeDodo, updateListeDodo(listeDodo), SUMMARY));
        wikiPages.add(new PageToPublish(imagery, updateImageryPage(imagery), "Ajout Pokémon Sleep"));
        wikiPages.add(new PageToPublish(main, updateMainPokemonPage(main), "Ajout description Pokèmon Sleep"));
        wikiPages.add(new PageToPublish(secondaryGames, makePokemonPage(), "Ajout Pokémon Sleep à compléter"));
        wikiPages.add(new PageToPublish(redirection, "#REDIRECTION [[%s/Jeux secondaires#Pokémon Sleep]]".formatted(getRegionalName()), "Redirection vers Jeux secondaires"));
        wikiPages.addAll(updateZones());
        if(updateAbilityPage(ability) != null) {
            wikiPages.add(new PageToPublish(ability, updateAbilityPage(ability), SUMMARY));
        }

        //Si le pokémon n'est pas la forme de base de sa ligne évolutive, on ne l'ajoute pas à certaines pages.
        if (hasUniqueCandy()) {
            wikiPages.add(new PageToPublish(listeBonbons, updateCandyPage(listeBonbons), SUMMARY));
            wikiPages.add(new PageToPublish(listeIngredients, updateIngredientsPage(listeIngredients), SUMMARY));
        }

        //Si le Pokémon n'a pas de famille evolutive, on peut l'ajouter automatiquement a la page des familles d'évolution
        if (m_isSingle) {
            Page evolution = new Page(m_pages[4], POKEPEDIA);
            wikiPages.add(new PageToPublish(evolution, updateEvolutionPage(evolution), SUMMARY));
        }

        return wikiPages;
    }

    /**
     * S'occupe d'ajouter les lignes d'un Pokémon à la page "Liste des Pokémon de soutien de Pokémon sleep"
     *
     * @return
     */
    protected String updateListePokeSoutien(Page listeSoutien)
    {
        final int LIGNES_INFORMATIONS = 5;

        String content = listeSoutien.getContent();
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));

        //On récupère l'endroit où le Pokémon doit être inséré dans le tableau
        int l = UtilSleep.getInsertionLine(lines, lines.indexOf("! Compétence principale") + 2, Integer.parseInt(m_numDex));

        String[] ajout = getLignePokeRecap();
        lines.addAll(l, List.of(ajout));

        //Reconstruction du texte de la page afin de publier
        return Util.wikicodeReconstruction(lines);
    }

    protected String updateListeDodo(Page listeDodos)
    {
        String content = listeDodos.getContent();
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));

        //Même procédé pour la liste des styles de dodos
        int l = lines.indexOf("! Rareté");
        l += 2;
        String currentLine = lines.get(l);

        int currentNumDex = PokeData.getPokemonFromName(Util.searchValueOf(currentLine, "nom=", "|", false)).getNumDex();

        while(Integer.parseInt(m_numDex) >= currentNumDex)
        {
            l += 2;
            currentLine = lines.get(l);

            //Si jamais le Pokémon doit être inséré à la toute fin du tableau
            if(currentLine.length() < 10)
            {
                break;
            }
            currentNumDex = PokeData.getPokemonFromName(Util.searchValueOf(currentLine, "nom=", "|", false)).getNumDex();
        }

        String lignePoke = getLignePokeDodos();
        lines.add(l, lignePoke);
        lines.add(l+1, "");

        //Reconstruction du texte de la page afin de publier
        return Util.wikicodeReconstruction(lines);
    }

    /**
     * S'occupe de mettre à jour les pages des îles de Pokémon sleep avec les dodos du nouveau Pokémon
     *
     * @return a map containing the list of pages that need to be modified with their new text
     */
    protected ArrayList<PageToPublish> updateZones()
    {
        ArrayList<PageToPublish> wikiPages = new ArrayList<>();

        for (Island ile : m_zones) {
            //Si le pokémon n'est pas dispo sur l'ile, on passe directement à la suivante
            if(!pokeDispoSurIle(ile))
            {
                continue;
            }

            Page pageIle = new Page(ile.getFullName(false), POKEPEDIA);
            String content = pageIle.getContent();
            ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));

            //Recherche de la section Description pour modifier les chiffres
            int l = lines.indexOf("== Description ==");
            l += 3;
            String currentLine = lines.get(l);

            //mise à jour de la ligne contenant le nombre de Pokémon et de dodos disponibles
            currentLine = Util.incrementValueInString(currentLine, 1, 1);
            currentLine = Util.incrementValueInString(currentLine, 2, paliersPourIle(ile).size());
            lines.set(l, currentLine);

            //On continue jusqu'au tableau récapitulatif des paliers de Ronflex
            l = lines.indexOf("| [[Fichier:Sprite Rang Basique Sleep.png|30px]] Basique 1");
            currentLine = lines.get(l);

            //À partir de la liste des paliers qui gagnent un/des sleepStyle(s), on incrémente les valeurs du tableau en conséquence
            ArrayList<String> paliers = paliersPourIle(ile);
            int increment = 0;
            for (int p = 0; p < 35; p++) {
                for(String pal : paliers)
                {
                    if(Util.searchValueOf(currentLine, "]] ", false).equals(pal))
                    {
                        increment++;
                        lines.set(l+2, Util.incrementValueInString(lines.get(l+2), 0, 1));
                    }
                }
                lines.set(l+3, Util.incrementValueInString(lines.get(l+3), 0, increment));

                l += 6;
                currentLine = lines.get(l);
            }

            //Plus qu'à ajouter le Pokémon dans le tableau global
            l = UtilSleep.getInsertionLine(lines, lines.indexOf("! Rang nécessaire") + 2, Integer.parseInt(m_numDex));

            ArrayList<String> ajout = getLignesPokeIle(ile);
            lines.addAll(l, ajout);

            //Reconstruction du texte de la page afin de publier
            String newContenu = Util.wikicodeReconstruction(lines);
            wikiPages.add(new PageToPublish(pageIle, newContenu, "Ajout de " + getFullName()));
        }
        return wikiPages;
    }

    /**
     * Mets à jour la page des ingrédients de Pokémon sleep en ajoutant le Pokémon dans les listes de ses ingrédients
     *
     * @return
     */
    protected String updateIngredientsPage(Page listeIngredients)
    {
        String content = listeIngredients.getContent();
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));

        for(IngredientPoke bouffe : m_ingredientList)
        {
            //On cherche le tableau des Pokémon pour l'ingrédient
            int l = lines.indexOf("| colspan=\"4\" | '''Liste des Pokémon pouvant ramasser le " + bouffe.getNom() + "'''");
            if(l == -1)
            {
                l = lines.indexOf("| colspan=\"4\" | '''Liste des Pokémon pouvant ramasser la " + bouffe.getNom() + "'''");
            }
            if(l == -1)
            {
                l = lines.indexOf("| colspan=\"4\" | '''Liste des Pokémon pouvant ramasser l'" + bouffe.getNom() + "'''");
            }

            l = UtilSleep.getInsertionLine(lines, l + 7, Integer.parseInt(m_numDex));

            String[] ajout = {"|-",
                    "| " + getMiniatureString(),
                    "| " + bouffe.getQttNv1(),
                    "| " + bouffe.getQttNv30(),
                    "| " + bouffe.getQttNv60()};
            lines.addAll(l, List.of(ajout));
        }

        //Reconstruction du texte de la page afin de publier
        String newContenu = Util.wikicodeReconstruction(lines);
        return newContenu;
    }

    private String updateCandyPage(Page sleepCandies)
    {
        String content = sleepCandies.getContent();
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));
        int numDex = Integer.parseInt(m_numDex);

        int gen = PokeData.getPokemonGen(numDex);
        String sup = (gen == 1) ? "{{sup|ère}}" : "<sup>e</sup>";
        int l = lines.indexOf("! style=\"background:#C5BDDC; text-align:center\" | " + gen + sup + " génération") + 3;

        String ligneAct = lines.get(l);
        while (!ligneAct.equals("</gallery>")) {
            //System.out.println(ligneAct);
            String currentPokemon = Util.searchValueOf(ligneAct, "Bonbon ", " Sleep", false);
            int currentNumDex = PokeData.getPokemonFromName(currentPokemon).getNumDex();

            if (currentNumDex > numDex)
            {
                break;
            }

            l++;
            ligneAct = lines.get(l);
        }

        lines.add(l, "Sprite Bonbon " + m_name + " Sleep.png|[[" + m_name + "]]");

        return Util.wikicodeReconstruction(lines);
    }

    protected String updateImageryPage(Page imagery)
    {
        String content = imagery.getContent();
        if(!content.contains("{{#invoke:Imagerie|secondaire")) {
            System.err.println("La page d'imagerie suivante ne suit pas le format attendu : " + imagery.getTitle());
            return content;
        }
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));
        int l = Util.getInsertionLineForSideImagery(lines, "Sleep");

        StringBuilder sleepingSprites = new StringBuilder();
        for (int i = 1; i <= getSleepCount(); i++) {
            sleepingSprites.append(" / Sprite Dodo ").append(i).append(" / Sprite Dodo ").append(i).append(" chromatique");
        }

        String sleepLine = "Sleep // " + m_imageryType.getSprites() + sleepingSprites + " / " + m_imageryType.getMiniatures();
        lines.add(l, sleepLine);

        return Util.wikicodeReconstruction(lines);
    }

    protected String updateAbilityPage(Page ability)
    {
        String content = ability.getContent();
        if(content == null){
            System.err.println("La page de la compétence n'existe pas encore");
            return null;
        }

        if(m_ability == Competences.CHARGE_PUISSANCE_S || m_ability == Competences.AIMANT_FRAGMENT_DE_REVE) {
            System.err.println("La compétence possède une variante aléatoire et est ignorée");
            return content;
        }

        ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));

        int l = lines.indexOf("== Pokémon ayant cette compétence ==") + 2;

        if(l == 1) {
            l = lines.indexOf("== " + m_ability.getName() + " ==");
            if (l == -1) {
                System.err.println("La section suivante n'existe pas dans sa page de compétence : " + ability.getTitle() + "#" + m_ability.getName());
                return content;
            }

            l = Util.nextIndexOf(lines, "=== Pokémon ayant cette compétence ===", l) + 2;
        }

        String currentLine = lines.get(l);
        if(!currentLine.startsWith("{{#invoke:")) {
            System.err.println("La compétence est actuellement considérée comme exclusive");
            return content;
        }

        l++;
        currentLine = lines.get(l);
        int currentNumDex = PokeData.getPokemonFromName(currentLine.split(" f")[0]).getNumDex();
        while(currentNumDex <= Integer.parseInt(m_numDex)) {
            l++;
            currentLine = lines.get(l);
            if (currentLine.equals("}}")) break;
            currentNumDex = PokeData.getPokemonFromName(currentLine.split(" f")[0]).getNumDex();
        }

        lines.add(l, getPokemonListName());
        return Util.wikicodeReconstruction(lines);
    }

    private String updateEvolutionPage(Page evolution)
    {
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(evolution.getContent().split("\n")));
        int l = lines.indexOf("|-") + 1;
        String currentLine = lines.get(l);
        int currentNumDex = Integer.parseInt(Util.searchValueOf(currentLine, "Sprite ", " ", false));

        while(currentNumDex <= Integer.parseInt(m_numDex)) {
            l += 3;
            currentLine = lines.get(l);
            while (!currentLine.startsWith("| colspan=\"5\" class=\"") && !currentLine.equals("|}")) {
                l++;
                currentLine = lines.get(l);
            }

            if (currentLine.equals("|}")) break;

            currentNumDex = Integer.parseInt(Util.searchValueOf(lines.get(l + 2), "Sprite ", " ", false));
        }

        String sexPlus = m_imageryType.equals(Imagery.AGENDER) ? "" : " ♂";
        String addition = """
                |- class="Sans-Famille"
                | colspan="5" class="%s" | Famille de %s
                |- class="Sans-Famille"
                | colspan="5" | [[Fichier:Sprite %s%s Sleep.png|70px]]<br>[[%s/Pokémon Sleep|%s]]""".formatted(
                        m_type.getFrenchName().toLowerCase(), getRegionalName(), m_numDex, sexPlus, getRegionalName(), getRegionalName());

        lines.add(l-1, addition);

        return Util.wikicodeReconstruction(lines);
    }

    protected String[] getLignePokeRecap()
    {
        String r = """
                |-
                | style="text-align:left;" | {{#invoke:Ressources|pokemon|%s jeu(Sleep)}}
                | {{Type|%s|Sleep}}
                | %s
                | [[%s]]""".formatted(getPokemonListName(), m_type.getFrenchName(), m_speciality.getNom(), m_ability.getName());
        return r.split("\n");
    }

    /**
     * Retourne la ligne du second tableau de la page "liste des Pokémon de soutien" pour le Pokémon
     * @return voir ci-dessus
     */
    private String getLignePokeDodos() {
        StringBuilder zones = new StringBuilder();
        for (Island zone : m_zones) {
            zones.append("[[").append(zone.getFullName(true)).append("]]<br>");
        }
        if (zones.isEmpty()) {
            zones.append("{{?}}");
        } else {
            zones.delete(zones.length() - 4, zones.length());
        }

        StringBuilder sleepLine = new StringBuilder("{{Ligne Pokémon Dododex").append(getNameSection())
                .append("|type=").append(m_sleepType.getNom().toLowerCase())
                .append("|zones=").append(zones)
                .append("|dodo1=").append(m_sleepList.getFirst().name());

        for (int i = 1; i < m_sleepList.size(); i++) {
            SleepStyle sleep = m_sleepList.get(i);
            sleepLine.append("|dodo").append(i + 1).append("=").append(sleep.name());

            if (sleep.rarity() != Math.min(i, 4)) sleepLine.append("|rarity").append(i + 1).append("=").append(sleep.rarity());
        }

        if (m_sleepList.size() != 4) {
            sleepLine.append("|dodo=").append(m_sleepList.size());
        }

        sleepLine.append("}}");
        return sleepLine.toString();
    }

    protected String getNameSection()
    {
        return "|nom=" + m_name;
    }

    /**
     * Permets d'obtenir les lignes à ajouter pour le Pokémon dans le tableau des îles
     * @param island : numéro de l'île en considération (1 pour Vertepousse, etc)
     * @return un String à ajouter dans le tableau des Pokémon présents sur l'île
     */
    private ArrayList<String> getLignesPokeIle(Island island) {
        ArrayList<String> r = new ArrayList<>();
        r.add("|-");
        int nbrDodos = paliersPourIle(island).size();
        if (nbrDodos == 1) {
            r.add("| " + getMiniatureString());
            r.add("| class=\"" + m_sleepType.getNom().toLowerCase() + "\" | [[Fichier:Icône Type " +
                    m_sleepType.getNom().toLowerCase() + " Sleep.png|50px]] " + m_sleepType.getNom());
        }
        else {
            r.add("| rowspan=\"" + nbrDodos + "\" | " + getMiniatureString());
            r.add("| rowspan=\"" + nbrDodos + "\" class=\"" + m_sleepType.getNom().toLowerCase() + "\" | [[Fichier:Icône Type " +
                    m_sleepType.getNom().toLowerCase() + " Sleep.png|50px]] " + m_sleepType.getNom());
        }

        //lignes pour chaque sleepStyle
        for(SleepStyle sleepStyle : m_sleepList)
        {
            if(!sleepStyle.isAvailableOnIsland(island)) continue;
            if(r.size() != 3) r.add("|-");
            r.add(UtilSleep.ligneEtoiles(sleepStyle.rarity()));
            r.add("| [[Fichier:Sprite Rang " + sleepStyle.getRankBallOnIsland(island) + " Sleep.png|30px]] " + sleepStyle.getRankOnIsland(island));
        }

        return r;
    }

    private String getMiniatureString()
    {
        return "{{#invoke:Ressources|pokemon|" + getPokemonListName() + " jeu(Sleep)}}";
    }

    /**
     * Mise en forme de la fréquence de base d'un Pokémon pour sa page
     * @return idem
     */
    private String frequenceWiki()
    {
        StringBuilder sb = new StringBuilder();

        boolean hasHours = m_freqHour > 0;
        boolean hasMinutes = m_freqMin > 0;
        boolean hasSeconds = m_freqSec > 0;

        String hText = m_freqHour + " heure" + (m_freqHour > 1 ? "s" : "");
        String mText = m_freqMin + " minute" + (m_freqMin > 1 ? "s" : "");
        String sText = m_freqSec + " seconde" + (m_freqSec > 1 ? "s" : "");

        if (hasHours) sb.append(hText);
        if (hasMinutes) {
            if (hasHours && hasSeconds) sb.append(", ");
            else if (hasHours) sb.append(" et ");
            sb.append(mText);
        }
        if (hasSeconds) {
            if ((hasHours || hasMinutes)) sb.append(" et ");
            sb.append(sText);
        }

        return sb.toString();
    }

    /**
     * Fonction qui permet de déterminer si un Pokémon est discponible sur une ile donnée en regardant si chacun de ses dodos existent sur l'ile
     * @param island : numéro de l'Ile que l'on souhaite vérifer (1 pour Vertepousse, etc)
     * @return true si disponible, false sinon
     */
    private boolean pokeDispoSurIle(Island island)
    {
        for(SleepStyle sleepStyle : m_sleepList)
        {
            if(sleepStyle.isAvailableOnIsland(island)) return true;
        }
        return false;
    }

    /**
     * Permets d'obtenir les paliers nécessaires pour chaque sleepStyle pouvant être obtenus sur une ile donnée (peut aussi être utilisée pour savoir combien de dodos sont sur l'île)
     * @param island : Numéro de l'ile pour laquelle on cherche les paliers (1 pour Vertepousse, etc)
     * @return une liste de String contenant les paliers
     */
    private ArrayList<String> paliersPourIle(Island island)
    {
        ArrayList<String> r = new ArrayList<>();
        for(SleepStyle sleepStyle : m_sleepList)
        {
            if(sleepStyle.isAvailableOnIsland(island))
            {
                r.add(sleepStyle.getRankOnIsland(island));
            }
        }
        return r;
    }

    private String updateMainPokemonPage(Page main) {
        final String[] GAMES_AFTER = {"LPZA", "Pokopia"};
        String mainContent = main.getContent();

        if (!mainContent.contains("=== Descriptions du [[Pokédex]] ===")) {
            System.err.printf("WARNING : la description de %s à besoin d'être géré manuellement\n", main.getTitle());
            return mainContent;
        }

        int descSection = mainContent.indexOf("=== Descriptions du [[Pokédex]] ===");
        int placeToInsert = -1;
        int maxPlace = mainContent.indexOf("\n=", descSection);

        for (String s : GAMES_AFTER) {
            placeToInsert = mainContent.indexOf(";{{Jeu|%s}}".formatted(s), descSection);

            if (placeToInsert != -1 && placeToInsert < maxPlace) break;
        }

        if (placeToInsert == -1 || placeToInsert > maxPlace + 2) {
            placeToInsert = mainContent.indexOf("\n=", descSection);
        }

        return Util.insertIntoString(mainContent, getDescriptionInsert(), placeToInsert);
    }

    public String makePokemonPage() {
        StringBuilder result = new StringBuilder(5000);
        String type = m_type.getFrenchName();
        SideGamePage sidePage = getSideGamePage();

        Page basePage = new Page(getRegionalName(), POKEPEDIA);

        int berryAmount = (m_speciality == Specialites.BAIES || m_speciality == Specialites.TOUTES) ? 2 : 1;

        result.append("{{Édité par robot}}\n[[Fichier:Sprite ").append(getImageID());

        if (m_imageryType.equals(Imagery.SEXUAL_DIMORPHISM)) result.append(" ♂");

        result.append(" Sleep.png|200px|right|thumb|Sprite de ")
            .append(getRegionalName()).append(" dans {{Jeu|Sleep}}.]]\n").append("""
                {{#invoke:Sommaire|table|
                Ingrédients possibles
                Évolution
                Description du Dododex
                Styles de dodo
                }}
                
                """).append("""
                '''%s''' est présent dans {{Jeu|Sleep}} depuis le %s via l'évènement [[{{?}}]]. Il possède %d styles de dodo et apparaît lors de sessions de recherche du type %s.
                
                En tant que Pokémon de soutien, %s arbore le type %s et possède la spécialité « %s ».
                """.formatted(getRegionalName(), UtilSleep.getLastMonday(), m_sleepList.size(), m_sleepType.getNom().toLowerCase(),
                    getRegionalName(), type, m_speciality.getNom())).append("\n<div class=\"liste-tableaux\">\n")
            .append("""
                {| class="tableaustandard %s ficheinfocentrée"
                ! style="text-align:center" colspan="2" | Recherches sur le sommeil
                |-
                ! Nombre de styles de dodo
                | %d
                |-
                ! Points d'amitié
                | %d
                |-
                ! Récompenses d'amitié
                | [[Fichier:Sprite Point de recherche Sleep.png|30px]] Point de recherche × %s<br>[[Fichier:Sprite Fragment de Rêve Sleep.png|30px]] [[Fragment de Rêve]] × %s
                |-
                ! Médailles Amitié
                | N. 10 [[Fichier:Sprite Médaille Amitié Bronze Sleep.png|30px]], N. %d [[Fichier:Sprite Médaille Amitié Argent Sleep.png|30px]], N. %d [[Fichier:Sprite Médaille Amitié Or Sleep.png|30px]]
                |}""".formatted(type.toLowerCase(), m_sleepList.size(), m_recruitPoints,
                    Util.numberDecomposition(m_sleepList.getFirst().exp()), Util.numberDecomposition(m_sleepList.getFirst().shards()),
                    getMedalUnlocks()[1], getMedalUnlocks()[2])).append("\n\n").append("""
                {| class="tableaustandard %s ficheinfocentrée"
                ! style="text-align:center" colspan="2" | Stats de soutien
                |-
                ! [[Type]]
                | {{Type|%s|Sleep}} [[%s (type)|%s]]
                |-
                ! Spécialité
                | %s
                |-
                ! Baie
                | [[Fichier:Sprite Baie %s Sleep.png|30px]] [[Baie %s]] × %d
                |-
                ! Fréquence de base
                | %s
                |-
                ! Capacité de stockage
                | %d
                |-
                ! [[Liste des compétences de Pokémon Sleep#Compétences Principales|Compétence principale]]
                | %s [[%s]]
                |-
                ! Envoi à [[Professeur Néroli|Néroli]]
                | [[Fichier:Sprite Bonbon %s Sleep.png|30px]] [[Bonbon (Pokémon Sleep)|Bonbon %s]] × %d
                |}""".formatted(type.toLowerCase(), type, type, type, m_speciality.getNom(), m_type.getBerry(),
                    m_type.getBerry(), berryAmount, frequenceWiki(), m_storage, getAbilityIcon(), m_ability.getName(), m_candy, m_candy, getSendCandyCount()))
            .append("\n</div>\n\n").append("""
                === [[Ingrédient (Pokémon Sleep)|Ingrédients]] possibles ===
                
                Le tableau ci-dessous indique les différents ingrédients que %s peut récolter. Pour chaque palier de niveau, un seul de ces ingrédients est sélectionné aléatoirement pour un Pokémon de soutien donné."""
                    .formatted(getRegionalName())).append("\n\n").append(getPokemonIngredientsData()).append("\n\n").append("""
                %s=== Description du [[Dododex]] ===
                
                %s
                
                === Styles de dodo ===
                
                """.formatted(getEvolutionData(basePage), m_description)).append(getPokemonSleepData());

        return sidePage.addSection(SideGame.SLEEP, result.toString());
    }

    private int[] getMedalUnlocks() {
        return switch (m_recruitPoints) {
            case 5, 7 -> new int[] {10, 40, 100};
            case 12 -> new int[] {10, 30, 60};
            case 15, 16 -> new int[] {10, 25, 50};
            case 20, 25, 30 -> new int[] {10, 20, 40};
            default -> new int[] {-1, -1, -1};
        };
    }

    private String getPokemonIngredientsData() {
        StringBuilder data = new StringBuilder("""
                {| class="tableaustandard %s centre"
                ! rowspan="2" | Ingrédient
                ! colspan="3" | Niveau
                |-
                ! width="45px" | N. 1
                ! width="45px" | N. 30
                ! width="45px" | N. 60
                """.formatted(m_type.getFrenchName().toLowerCase()));

        for (IngredientPoke ingredient : m_ingredientList) {
            data.append(ingredient.getCondensedData());
        }
        data.append("|}");
        return data.toString();
    }

    private String getPokemonSleepData() {
        StringBuilder data = new StringBuilder("""
                <div class="center">
                {| class="tableaustandard centre %s tableau-overflow" style="max-width:100%c"
                ! colspan="%d" | Styles de dodos de %s
                |-
                ! Rareté
                """.formatted(m_type.getFrenchName().toLowerCase(), '%', m_sleepList.size() + 1 ,getRegionalName()));
        for (SleepStyle sleepStyle : m_sleepList) {
            data.append("|").append(" [[Fichier:Miniature Étoile Sleep.png|25px]]".repeat(sleepStyle.rarity())).append("\n");
        }

        data.append("|-\n! Image\n");
        for (int i = 1; i <= m_sleepList.size(); i++) {
            data.append("| [[Fichier:Sprite %s Dodo %d Sleep.png|150px]]\n".formatted(getImageID(), i));
        }

        data.append("|-\n! Nom\n");
        for (SleepStyle sleepStyle : m_sleepList) {
            data.append("| Dodo ").append(sleepStyle.name()).append("\n");
        }

        data.append("""
                |-
                ! colspan="%d" | Mode normal
                |-
                ! Récompenses
                """.formatted(m_sleepList.size() + 1));
        for (SleepStyle sleepStyle : m_sleepList) {
            data.append(sleepStyle.getRewardsOneCell(m_candy)).append("\n");
        }

        ArrayList<Island> expertAreas = new ArrayList<>();
        for (Island island : m_zones) {
            if (island.isExpert()) {
                expertAreas.add(island);
                continue;
            }

            data.append(getLocationsOnIsland(island));
        }

        if (!expertAreas.isEmpty()) {
            data.append("""
                |-
                ! colspan="%d" | Mode expert
                |-
                ! Récompenses
                """.formatted(m_sleepList.size() + 1));

            data.append(("| style=\"white-space:nowrap; text-align:left\" | [[Fichier:Sprite Point de recherche Sleep.png|30px]] Point de recherche × {{?}}<br>[[Fichier:Sprite Fragment de Rêve Sleep.png|30px]] [[Fragment de Rêve]] × {{?}}<br>[[Fichier:Sprite Bonbon %s Sleep.png|30px]] [[Bonbon (Pokémon Sleep)|Bonbon %s]] × {{?}}\n"
                    .formatted(m_candy, m_candy).repeat(m_sleepList.size())));

            for (Island island : expertAreas) {
                data.append(getLocationsOnIsland(island));
            }
        }

        data.append("|}\n</div>");
        return data.toString();
    }

    private String getEvolutionData(Page basePage) {
        if (m_isSingle) return "";

        String evolutionData = Util.searchValueOf(basePage.getContent(), "=== [[Évolution]] ===\n", "\n==", true);
        if(evolutionData == null || evolutionData.contains("n'a pas d'évolution")) return "";

        if(evolutionData.endsWith("\n")) evolutionData = evolutionData.substring(0, evolutionData.length() - 1);

        evolutionData = evolutionData.replaceAll("\\[\\[niveau]] [0-9]+", "niveau {{?}}").replaceAll("niveau [0-9]+", "niveau {{?}}")
                .replaceAll("\\[*Niveau]* [0-9]+", "Niveau {{?}} + [[Fichier:Sprite Bonbon %s Sleep.png|25px|Bonbon %s|lien=Bonbon (Pokémon Sleep)]] × {{?}}"
                        .formatted(getRegionalName(), getRegionalName()))
                .replace("Tableau d'évolution", "Tableau d'évolution Sleep")
                .replace("] qui lui-", "], qui lui-");

        if(evolutionData.contains("#lst")) {
            String pokeName = Util.searchValueOf(evolutionData, "#lst:", "|", false);
            evolutionData = evolutionData.replaceAll("\\{\\{#lst:.+", "{{#lst:%s/Jeux secondaires|Tableau d'évolution Sleep}}".formatted(pokeName))
                    .replace("[[%s]]".formatted(pokeName), "[[%s/Pokémon Sleep|%s]]".formatted(pokeName, pokeName));
        }
        else {
            for (String line : evolutionData.split("\n")) {
                if (!line.contains("TableauEvolution")) continue;

                String pokeName = line.split("\\|")[line.split("\\|").length - 1].replace("}}", "");
                String thisNumDex = pokeName.equals(getRegionalName()) ? m_numDex : "{{?}}";
                evolutionData = evolutionData.replace("|" + pokeName, "|%s|lien=%s/Pokémon Sleep|image=Sprite %s Sleep.png".formatted(pokeName, pokeName, thisNumDex))
                        .replace("[[" + pokeName + "]]", "[[%s/Pokémon Sleep|%s]]".formatted(pokeName, pokeName));
            }
        }

        return """
        === [[Évolution]] ===
        
        %s
        
        """.formatted(evolutionData);
    }

    private String getLocationsOnIsland(Island island) {
        StringBuilder data =  new StringBuilder("|-\n");
        data.append("! [[%s]]\n".formatted(island.getFullName(true)));
        for (SleepStyle sleepStyle : m_sleepList) {
            if (sleepStyle.isAvailableOnIsland(island)) {
                data.append("| [[Fichier:Sprite Rang %s Sleep.png|30px]] %s\n".formatted(sleepStyle.getRankBallOnIsland(island),
                        sleepStyle.getRankOnIsland(island)));
            } else {
                data.append("| —\n");
            }
        }
        return data.toString();
    }

    protected String getImageID() {
        return m_numDex;
    }

    private String getAbilityIcon() {
        if (m_ability.equals(Competences.SUPER_SOUTIEN)) {
            return "{{Type|%s|Sleep}}".formatted(m_type.getFrenchName());
        } else {
            return "[[Fichier:Icône Compétence %s Sleep.png|30px]]".formatted(m_ability.getIcon());
        }
    }

    private int getSendCandyCount() {
        if (m_name.equals("Branette")) return 7;
        if (m_name.equals("Grodoudou")) return 10;

        return switch (m_recruitPoints) {
            case 5 -> 5;
            case 7 -> 6;
            case 12 -> 7;
            case 15, 20 -> 10;
            case 16 -> 8;
            case 18, 22 -> 11;
            case 25 -> 12;
            case 30 -> 25;
            default -> -1;
        };
    }

    private String getDescriptionInsert() {
        return """
                ;{{Jeu|Sleep}}
                :%s
                """.formatted(m_description);
    }

    protected SideGamePage getSideGamePage() {return new SideGamePage(PokeData.getPokemonFromName(m_name));}

    protected String getPokemonListName()
    {
        return m_name;
    }

    public String getRegionalName()
    {
        return m_name;
    }

    public String getFullName()
    {
        return m_name;
    }

    public String getNumDex()
    {
        return m_numDex;
    }

    public Imagery getImageryType() {
        return m_imageryType;
    }

    public int getSleepCount() {
        return m_sleepList.size();
    }

    public boolean hasUniqueCandy() {
        return m_name.equals(m_candy);
    }
}

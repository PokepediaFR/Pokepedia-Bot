package sleep.event;

import sleep.event.bonus.Bonus;
import sleep.event.bundle.BundlePack;
import sleep.pokemon.SimplifiedPokemon;
import utilitaire.*;
import utilitaire.article.FileToUpload;
import utilitaire.article.Page;
import utilitaire.article.PageToPublish;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public record Event(String name, Date startDate, int weekDuration, List<Bonus> bonuses, List<SimplifiedPokemon> newPokemon,
                    List<SimplifiedPokemon> boostedPokemon, List<Mission> missions, List<BundlePack> bundles, URL linkToEvent,
                    File image, List<SimplifiedPokemon> pokemonOnImage, String spanishName) {

    public String getWikiCode()
    {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return """
        {{Édité par robot}}
        [[Fichier:%s Sleep.png|thumb|right|350px|Image promotionelle pour l'évènement dans {{Jeu|Sleep}}.]]
        '''%s''' est un évènement de {{jeu|Sleep}}.

        == Déroulement ==

        %s est un évènement qui s'est déroulé sur toutes les îles pendant %s. Chaque journée de l'évènement débute à 04:00, heure locale.

        %s%s%s%s== Lien externe ==
        
        * [%s Notice de l'évènement sur le site officiel] '''(fr)'''

        {{Évènements Pokémon Sleep|%d}}

        [[Catégorie:Évènement de Pokémon Sleep]]%s""".formatted(
                name,
                getNameWithArticle(),
                getNameWithArticle(),
                getDateSection(),
                getBonusSection(),
                getFullPokemonSection(),
                getMissionsSection(),
                getBundleSection(),
                linkToEvent,
                year,
                getSpanishLink()
        );
    }

    private String getDateSection() {
        Date endDate = Util.getEndDate(startDate, weekDuration*7 - 1);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(startDate);
        c2.setTime(endDate);
        boolean sameMonth = c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);

        String durationStr = switch (weekDuration) {
            case 1 -> "une";
            case 2 -> "deux";
            case 3 -> "trois";
            default -> "beaucoup";
        };

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM", Locale.FRENCH);

        String semaine = weekDuration == 1 ? " semaine" : " semaines";
        String from = sameMonth ? String.valueOf(c1.get(Calendar.DAY_OF_MONTH)) : formatter.format(startDate);

        return "%s %s, du %s au %s".formatted(durationStr, semaine, from, Util.dateToString(endDate));
    }

    private String getBonusSection() {
        if (bonuses == null || bonuses.isEmpty()) {return "";}
        String preposition = name.matches("(?i)^[aàâäæeéèêëiîïoôöœuùûüyÿ].*") ? "d'" : "de ";

        StringBuilder bonusSection = new StringBuilder("""
                === Bonus d'évènement ===
                
                Comme les autres évènements du jeu, plusieurs bonus d'événement ont été mis en place pendant la durée \
                %s%s. Ainsi :
                """.formatted(preposition, getNameWithArticle()));

        for (Bonus bonus : bonuses) {
            bonusSection.append("\n* ").append(bonus.getBonusString()).append(" ;");
        }
        bonusSection.replace(bonusSection.length()-2, bonusSection.length(), ".\n\n");
        bonusSection.append("""
                Les bonus d'évènement ne s'appliquent uniquement que lors des sessions commencées \
                lors de la période de l'évènement. Ainsi, même si le rapport de sommeil est lu pendant l'évènement, \
                le joueur ne reçoit pas de bonus si la session a été débutée avant le début de celui-ci. Inversement, \
                si le rapport est lu après la fin de l'évènement, mais que la session a été commencée durant celui-ci, \
                alors le joueur reçoit les bonus d'évènements.
                
                Enfin, les bonus d'évènement ne s'appliquent pas lors de la session de sommeil enregistrée pendant le tutoriel du jeu.
                
                """);

        return bonusSection.toString();
    }

    private String getFullPokemonSection() {
        if ((newPokemon == null || newPokemon.isEmpty()) && (boostedPokemon == null || boostedPokemon.isEmpty())) {return "";}
        String section = "== Pokémon à l'affiche ==\n\n";

        if (boostedPokemon == null || boostedPokemon.isEmpty()) {
            String adverb = newPokemon.size() > 1 ? "ont" : "a";
            return section + getNewPokemonString() + " au cours de l'évènement, et " + adverb
                    + " donc plus de chances d'apparaître lors des sessions de sommeil durant cet évènement.\n\n" + getNewPokemonTable() + "\n\n";
        }

        if (newPokemon == null || newPokemon.isEmpty()) {
            return section + boostedPokemon.size() + " anciens Pokémon déjà introduits sont à l'affiche, et ont plus de chances d'apparaître lors des sessions de sommeil durant cet évènement.\n\n"
                    + getReturningPokemonTable() + "\n\n";
        }

        return section + """
                Les Pokémon suivants sont à l'affiche durant l'évènement, et ont plus de chances d'apparaître lors des sessions de sommeil durant cet évènement.
                
                === Nouveaux Pokémon ===
                
                %s au cours de l'évènement.
                
                %s
                
                === Anciens Pokémon ===
                
                Additionnellement, %d anciens Pokémon déjà introduits sont également à l'affiche.
                
                %s
                
                """.formatted(getNewPokemonString(), getNewPokemonTable(), boostedPokemon.size(), getReturningPokemonTable());
    }

    private String getMissionsSection() {
        if (missions == null || missions.isEmpty()) return "";

        StringBuilder section = new StringBuilder("""
                == Missions à durée limitée ==
                
                Les missions suivantes permettent d'obtenir diverses récompenses. Elles peuvent toutes être complétées jusqu'à la fin de l'événement.
                
                {| class="tableaustandard centre"
                ! Mission
                ! Récompense""");
        for (Mission mission : missions) {
            section.append("\n|-\n").append(mission.getWikiCode());
        }
        section.append("\n|}\n\n");

        return section.toString();
    }

    private String getBundleSection() {
        if (bundles == null || bundles.isEmpty()) return "";

        StringBuilder section = new StringBuilder("""
                == Lots à durée limitée ==
                
                Ces lots peuvent être achetés dans la [[Boutique (Pokémon Sleep)|boutique]] uniquement durant la période de l'évènement.
                
                {| class="tableaustandard"
                ! Dates
                ! Lot
                ! Contenu
                ! Prix
                ! Limite<br>d'achat
                """);

        for (BundlePack bundle : bundles) {
            section.append("|-\n").append(bundle.getWikiCode());
        }
        section.append("|}\n\n");

        return section.toString();
    }

    private String getNewPokemonTable() {
        StringBuilder section = new StringBuilder("""
                {| class="tableaustandard sortable" style="text-align:center; white-space:nowrap"
                ! Pokémon
                ! Type
                ! Spécialité
                ! Compétence principale""");

        for (SimplifiedPokemon pokemon : newPokemon) {
            section.append("\n|-\n").append(pokemon.getNewWikiCode());
        }
        section.append("\n|}\n\n");

        String apparition = newPokemon.size() > 1 ? "les nouveaux Pokémon" : newPokemon.getFirst().getFullName();

        section.append("Ci-dessous, les rangs de dodoforce à atteindre avec Ronflex pour faire apparaître ")
                .append(apparition).append("""
                         lors d'une session de sommeil, pendant l'évènement.
                        
                        {| class="tableaustandard"
                        ! Nom
                        ! Type de dodo
                        ! Rareté du Style
                        ! Rang nécessaire
                        |-
                        ! colspan="4" | Pokémon « Plus fréquents »
                        |-""");
        for (SimplifiedPokemon pokemon : newPokemon) {
            section.append("\n").append(pokemon.getSleepData());
        }
        section.replace(section.length()-1, section.length(), "}");

        return section.toString();
    }

    private String getReturningPokemonTable() {
        StringBuilder section = new StringBuilder("""
                {| class="tableaustandard sortable" style="text-align:center; white-space:nowrap"
                ! Pokémon
                ! Type
                ! Spécialité
                ! Type de dodo
                |-
                ! colspan="6" | Pokémon « Un peu plus fréquents »""");

        for (SimplifiedPokemon pokemon : boostedPokemon) {
            section.append("\n|-\n").append(pokemon.getOldWikiCode());
        }
        section.append("\n|}");

        return section.toString();
    }

    private String getNewPokemonString() {
        if (newPokemon.isEmpty()) return "ERROR, NO NEW POKEMON";

        if (newPokemon.size() == 1) return newPokemon.getFirst().getFullName() + " est introduit";

        StringBuilder sb = new StringBuilder(newPokemon.getFirst().getFullName());
        for (int i  = 1; i < newPokemon.size()-1; i++) {
            sb.append(", ").append(newPokemon.get(i).getFullName());
        }
        sb.append(" et ").append(newPokemon.getLast().getFullName()).append(" sont introduits");

        return sb.toString();
    }

    private String getNameWithArticle() {
        return (name.startsWith("Semaine") || name.startsWith("Fête")) ? "La " + name : name;
    }

    private String getSpanishLink() {
        if (spanishName == null || spanishName.isEmpty()) return "";
        return "\n\n[[es:%s]]".formatted(spanishName);
    }

    public PageToPublish updateEventModel() {
        Page eventPage = new Page("Modèle:Évènements Pokémon Sleep", Wiki.POKEPEDIA);

        String content = eventPage.getContent();
        if (content.contains(name)) return null;

        String newContent;
        int year = getYear();

        String oldSection = Util.searchValueOf(content, "padding: 0\" | " + year, "\n</div>\n", true);
        if (oldSection != null) {
            String newSection = oldSection + "\n<div>[[%s]]</div>".formatted(name);
            newContent = content.replace(oldSection, newSection);
        }
        else {
            int insertPlace = content.indexOf("|}</includeonly>");
            String newSection = """
                    {| class="tableaustandard enroulable {{#ifeq: {{{1|}}} | %d | | enroulé}}" style="text-align:center; width: 100%%; margin: 5px auto; max-width: none;"
                    !style="background: #e4f0f7; font-size:90%%; min-width: 75px; padding: 0" | %d
                    |-
                    | style="font-size:85%%;" | [[%s]]
                    |}""".formatted(year, year, name);
            newContent = Util.insertIntoString(content, newSection, insertPlace);
        }

        return new PageToPublish(eventPage, newContent, "Ajout de " + name);
    }

    public PageToPublish updateEvenPage() {
        Page eventPage = new Page("Liste des évènements de Pokémon Sleep", Wiki.POKEPEDIA);

        String content = eventPage.getContent();
        if (content.contains(name)) return null;

        String newContent;
        int year = getYear();
        Date endDate = Util.getEndDate(startDate, weekDuration*7 - 1);

        String oldSection = Util.searchValueOf(content, "durant l'année " + year, "|}", true);
        if (oldSection != null) {

            String newSection = oldSection + """
                |-
                | [[%s]]
                | %s<br>—<br>%s
                | [[Fichier:%s Sleep.png|250px]]
                """.formatted(name, Util.dateToString(startDate), Util.dateToString(endDate), name);

            newContent = content.replace(oldSection, newSection);
        }
        else {
            int insertPlace = content.indexOf("== Voir aussi ==");

            String newSection = """
                == %d ==
                
                Ces événements ont débuté durant l'année %d.
                
                {| class="tableaustandard centre"
                ! Nom
                ! Dates
                ! Image
                |-
                | [[%s]]
                | %s<br>—<br>%s
                | [[Fichier:%s Sleep.png|250px]]
                |}
                
                """.formatted(year, year, name, Util.dateToString(startDate), Util.dateToString(endDate), name);
            newContent = Util.insertIntoString(content, newSection, insertPlace);
        }

        return new PageToPublish(eventPage, newContent, "Ajout de " + name);
    }

    @Deprecated
    public PageToPublish updateShopPage() {
        int year = getYear();
        Page page = new Page("Boutique (Pokémon Sleep)", Wiki.POKEPEDIA);
        String content = page.getContent();

        if (content.contains(bundles.getFirst().bundles().getFirst().name())) return null;

        String newContent;
        String oldSection = Util.searchValueOf(content, "évènementiels en " + year, "\n|}", true);

        if (oldSection != null) {
            StringBuilder newSection = new StringBuilder();
            for (BundlePack pack : bundles) {
                newSection.append("|-\n").append(pack.getWikiCode());
            }
            newContent = content.replace(oldSection, newSection.toString());
        }
        else {
            int insertPlace = content.indexOf("=== Lots à prix réduit ===");
            StringBuilder newSection = new StringBuilder("""
                    {| class="tableaustandard enroulable enroulé"
                    ! colspan="5" | Lots évènementiels en %d
                    |-
                    ! Dates
                    ! Lot
                    ! Contenu
                    ! Prix
                    ! Limite<br>d'achat""".formatted(year));

            for (BundlePack pack : bundles) {
                newSection.append("|-\n").append(pack.getWikiCode());
            }
            newSection.append("|}\n\n");

            newContent = Util.insertIntoString(content, newSection.toString(), insertPlace);
        }

        return new PageToPublish(page, newContent, "Ajout de " + name);
    }

    private int getYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        return cal.get(Calendar.YEAR);
    }

    public List<FileToUpload> getIconsToPublish() {
        List<FileToUpload> iconsToPublish = new ArrayList<>();

        if (image != null) {
            String imageName = "Fichier:" + name + " Sleep.png";
            StringBuilder description = new StringBuilder("""
                    == Description ==
                    Image promotionnelle de l'événement [[%s]] dans {{Jeu|Sleep}}.
                    
                    {{Informations Fichier
                    | Source = [https://www.pokemonsleep.net/fr/news Site officiel de Pokémon Sleep]
                    | Auteur = [[The Pokémon Company]]
                    }}
                    
                    [[Catégorie:Image d'évènement de Pokémon Sleep]]""".formatted(name));
            for (SimplifiedPokemon pokemon : pokemonOnImage) {
                description.append("\n[[Catégorie:Image Pokémon représentant ").append(pokemon.getFullName()).append("]]");
            }
            String summary = "Image promotionelle d'évènement";
            iconsToPublish.add(new FileToUpload(image, imageName, description.toString(), summary));
        }

        for (BundlePack pack : bundles) {
            iconsToPublish.addAll(pack.getIconsToPublish());
        }

        return iconsToPublish;
    }
}

package other;

import sleep.zone.Island;
import utilitaire.*;
import utilitaire.article.Page;
import utilitaire.article.PageToPublish;

import java.util.ArrayList;
import java.util.Arrays;

public class RandomStuff {

    static void main(String[] args) {
        Login.handleLogin(args);

        fixSideGames();
    }

    private static void makeSleepZones() {
        final String[] STUFF_TO_DELETE = {"dex", "bonbon", "nombonbon", "recherche", "fragment"};
        Page page = new Page("Liste des styles de dodo de Pokémon Sleep", Wiki.POKEPEDIA);

        String content = page.getContent().replace("|dodo=4", "");

        ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));

        int l = lines.indexOf("! Rareté") + 2;

        String currentLine = lines.get(l);

        while (currentLine.length() > 10) {
            StringBuilder zones = new StringBuilder("|zones=");
            for (Island zone : Island.values()) {
                if (currentLine.contains(zone.getFullName(true))) zones.append("[[%s]]<br>".formatted(zone.getFullName(true)));
            }

            int place = currentLine.contains("|dodo=") ? currentLine.indexOf("|dodo=") : currentLine.indexOf("}}");
            lines.set(l, currentLine.substring(0, place) + zones.substring(0, zones.length() - 4) + currentLine.substring(place));

            l += 2;
            currentLine = lines.get(l);
        }

        content = Util.wikicodeReconstruction(lines);
        content = content.replaceAll("\\|lieu\\d+=(?:(?!\\|re).)+", "");
        for (String s : STUFF_TO_DELETE) {
            content = content.replaceAll("\\|" + s + "\\d*=[^|}]+", "");
        }

        page.setContent(content, "Nouveau format simplifié");
    }

    private static void addSleepDescriptionsBack() {
        final String[] GAMES_AFTER = {"LPZA", "Pokopia"};

        ArrayList<Page> sleepPages = API.getPageFromCategory("Pokémon apparaissant dans Pokémon Sleep", API.NS_MAIN, Wiki.POKEPEDIA);
        ArrayList<PageToPublish> mainPages = new ArrayList<>();

        for (Page sleepPage : sleepPages) {
            Page mainPage = new Page(sleepPage.getTitle().split("/")[0], Wiki.POKEPEDIA);
            String sleepContent =  sleepPage.getContent();
            String mainContent =  mainPage.getContent();

            if (!mainContent.contains("=== Descriptions du [[Pokédex]] ===") || !sleepContent.contains("=== Description du [[Dododex]] ===")) {
                System.err.printf("WARNING : %s à besoin d'être géré manuellement\n", mainPage.getTitle());
                continue;
            }
            if (mainContent.contains(";{{Jeu|Sleep}}")) {
                System.out.printf("%s déjà fait\n", mainPage.getTitle());
                continue;
            }

            System.out.printf("Gestion de %s\n", mainPage.getTitle());

            String description = ";{{Jeu|Sleep}}\n:" + Util.searchValueOf(sleepContent, "=== Description du [[Dododex]] ===\n\n", false) + "\n";

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

            if (mainPage.getTitle().equals("Banshitrouye")) System.out.println(placeToInsert);

            String newContent = Util.insertIntoString(mainContent, description, placeToInsert).replace("\n\n;{{Jeu|Sleep}}", "\n;{{Jeu|Sleep}}")
                    .replace("\n\n;{{Jeu|Ec}}", "\n;{{Jeu|Ec}}");

            mainPages.add(new PageToPublish(mainPage, newContent, "Ajout description Pokémon Sleep"));
        }

        Util.publishMultipleEdits(mainPages);
    }

    private static void addSpaces() {
        ArrayList<Page> pages = API.getPageFromCategory("Pokémon Sleep", API.NS_MAIN, Wiki.POKEPEDIA);
        ArrayList<PageToPublish> newPages = new ArrayList<>();
        for (Page page : pages) {
            String newContent = page.getContent().replaceAll("×(?=\\d)", "× ");
            if (newContent.equals(page.getContent())) continue;

            newPages.add(new PageToPublish(page, newContent, "Forme : ×1 -> × 1", true));
        }

        Util.publishMultipleEdits(newPages);
    }

    private static void fixSideGames() {
        ArrayList<PageToPublish> fixedPages = new ArrayList<>();

        for (Pokemon pokemon : PokeData.getAllPokemon()) {
            Page pokePage = new Page(pokemon.getFrenchName(), Wiki.POKEPEDIA);

            String content = pokePage.getContent();
            if (content.contains("}}== Apparitions dans les jeux secondaires ==")) {
                fixedPages.add(new PageToPublish(pokePage, content.replace("}}== Apparitions dans les jeux secondaires ==", "}}\n\n== Apparitions dans les jeux secondaires =="), "Fix saut de ligne"));
            }

            if (pokemon.getNumDex() > 35) break;
        }

        Util.publishMultipleEdits(fixedPages);
    }
}
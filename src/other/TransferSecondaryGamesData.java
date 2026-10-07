/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import utilitaire.*;
import utilitaire.article.Page;
import utilitaire.article.PageToPublish;
import utilitaire.article.SideGamePage;

import static utilitaire.Util.*;

void main(String[] args) {
    Login.handleLogin(args);
    ArrayList<String> sleepNames = new ArrayList<>();

    for (Page page : API.getPageFromCategory("Pokémon apparaissant dans Pokémon Sleep", API.NS_MAIN, Wiki.POKEPEDIA)) {
        sleepNames.add(page.getTitle().replace("/Jeux secondaires", ""));
    }

    ArrayList<PageToPublish> pagesReady = new ArrayList<>();

    for (Pokemon pokemon : PokeData.getAllPokemon()) {
        if (pokemon.getNumDex() <= 33) continue;
        if (pokemon.getFrenchName().equals("Pikachu") | pokemon.getFrenchName().equals("Darkrai")) continue;

        boolean hasSleep = sleepNames.contains(pokemon.getFrenchName());
        handlePokemon(pokemon.getFrenchName(), new SideGamePage(pokemon), hasSleep, pagesReady);

        for (Region region : pokemon.getRegionalForms()) {
            String name = pokemon.getFrenchName() + " " + region.getFrAdjective();
            hasSleep = sleepNames.contains(name);
            handlePokemon(name, new SideGamePage(pokemon, region), hasSleep, pagesReady);
        }
    }

    System.out.printf("%d pages to publish%n", pagesReady.size());
    Util.publishMultipleEdits(pagesReady);
}

private void handlePokemon(String name, SideGamePage sideGamePage, boolean hasSleep, ArrayList<PageToPublish> pagesReady) {
    System.out.printf("%S%n", name);
    Page mainPage = new Page(name, Wiki.POKEPEDIA);

    String mainContent = mainPage.getContent().replace("|Unite}}", "|UNITE}}").replace("Pokémon Unite", "Pokémon UNITE")
            .replace("''[[Pokkén Tournament]]''", "[[Pokkén Tournament]]")
            .replace("Apparitions dans {{Jeu|UNITE}}", "Apparition dans {{Jeu|UNITE}}")
            .replace("==== Dans {{Jeu|Pokkén}} ====", "=== Dans {{Jeu|PokkénDX}} ===")
            .replace("== Apparition dans ''[[Pokémon UNITE]]'' ==", "== Apparition dans {{Jeu|UNITE}} ==")
            .replace("== Apparitions dans les autres jeux ==", "== Apparitions dans d'autres jeux ==");

    String newContent = mainContent;
    boolean hasSideGames = hasSleep;

    if (newContent.contains("\n\n\n")) System.err.printf("%s à un triple saut!%n", name);

    String pmdTitle = "== Apparitions dans [[Pokémon Donjon Mystère (série)|Pokémon Donjon Mystère]] ==";
    if (mainContent.contains(pmdTitle)) {
        sideGamePage.addPMD();
        String section = extractSection(mainContent, pmdTitle);
        newContent = newContent.replace(section, "").replace(pmdTitle, "");
        hasSideGames = true;
    }

    if (mainContent.contains("UNITE")) {
        String titleSection = "== Apparition dans {{Jeu|UNITE}} ==";
        String oldSection = extractSection(mainContent, titleSection);
        String newSection = extractUnite(oldSection);
        newContent = newContent.replace(oldSection, "").replace(titleSection, "");
        sideGamePage.addSection(SideGame.UNITE, newSection);
        hasSideGames = true;

        pagesReady.add(new PageToPublish(new Page(name + "/Pokémon UNITE", Wiki.POKEPEDIA),
                "#REDIRECTION [[%s/Jeux secondaires#Pokémon UNITE]]".formatted(name),
                "Redirection vers section Jeu secondaire spécifique"));
    }

    if (mainContent.contains("Pokkén")) {
        boolean isDX = mainContent.contains("PokkénDX");
        String sectionTitle;
        if (isDX) {
            if (mainContent.contains("''[[Pokkén")) {
                sectionTitle = "=== Dans ''[[Pokkén Tournament DX]]'' ===";
            } else {
                sectionTitle = "=== Dans {{Jeu|PokkénDX}} ===";
            }
        } else {
            sectionTitle = mainContent.contains("Jeu|Pokkén") ? "=== Dans {{Jeu|Pokkén}} ===" : "=== Dans [[Pokkén Tournament]] ===";
        }
        String oldSection = extractSection(mainContent, sectionTitle);
        String newSection = extractPokken(oldSection, isDX, name);
        newContent = newContent.replace(oldSection, "").replace(sectionTitle, "");
        sideGamePage.addSection(isDX ? SideGame.POKKENDX : SideGame.POKKEN, newSection);
        hasSideGames = true;

        String addition = isDX ? " DX" : "";
        pagesReady.add(new PageToPublish(new Page(name + "/Pokkén Tournament", Wiki.POKEPEDIA),
                "#REDIRECTION [[%s/Jeux secondaires#Pokkén Tournament%s]]".formatted(name, addition),
                "Redirection vers section Jeu secondaire spécifique"));
    }

    if (hasSideGames) {
        int i = newContent.indexOf("== Apparitions dans d'autres jeux ==");

        if (i == -1) i = newContent.indexOf("== Apparitions dans les dessins animés ==");

        if (i == -1) i = newContent.indexOf("== Apparitions dans le [[Pokémon, la série|dessin animé]] ==");

        if (i == -1) i = newContent.indexOf("== Apparitions dans le [[La série : Pokémon, les horizons|dessin animé]] ==");

        if (i == -1) i = newContent.indexOf("\n\n\n") + 2;

        String sideSection = """
                == Apparitions dans les jeux secondaires ==
                
                {{Article principal|{{PAGENAME}}/Jeux secondaires}}
                
                %s apparaît notamment dans les jeux suivants :
                
                {{#invoke:Sommaire|triAlphabetique|page={{PAGENAME}}/Jeux secondaires}}
                
                """.formatted(name);

        newContent = newContent.substring(0, i) +  sideSection + newContent.substring(i);
    }

    newContent = newContent.replace("== Apparitions dans d'autres jeux ==", "== Apparitions dans les jeux tiers ==");

    while (newContent.contains("\n\n\n")) {
        newContent = newContent.replace("\n\n\n", "\n\n");
    }

    newContent = newContent.replace("== Apparitions dans les jeux tiers ==\n\n== ", "== ");

    if (!newContent.equals(mainContent)) {
        if (hasSideGames) {
            pagesReady.add(new PageToPublish(sideGamePage, sideGamePage.getContent(), "Transfert de données depuis la page principale"));
        }
        pagesReady.add(new PageToPublish(mainPage, newContent, "Restructuration des jeux secondaires", !hasSideGames));
    }
}

private String extractUnite(String section) {
    String role = searchValueOf(section, "rôle2=", false);

    String newIntro = " apparaît dans {{Jeu|UNITE}} en tant que personnage jouable de type %s.".formatted(role);
    return section.replaceAll(" apparaît comme personnage jouable[^.]*\\.", newIntro).replace(".\n{{", ".\n\n{{");
}

private String extractPokken(String section, boolean isDX, String name) {
    String game = isDX ? "PokkénDX" : "Pokkén";

    String newSection = section.replace("Dans ce jeu, ", "").replace("un Pokémon de soutien",
            "un Pokémon de soutien de {{Jeu|%s}},".formatted(game)).replace("Dans ''Pokkén Tournament DX'', ", "")
            .replace("Dans Pokkén Tournament, ", "").replace("Dans {{Jeu|Pokkén|lien=non}}, ", "")
            .replace("\n\n -", "<br>-").replace("\n\n-", "<br>-");

    if (!newSection.contains("Pokémon de soutien")) {
        newSection = "%s est un combattant dans {{Jeu|%s}}.\n\n".formatted(name, game) + newSection;
    }

    return newSection;
}
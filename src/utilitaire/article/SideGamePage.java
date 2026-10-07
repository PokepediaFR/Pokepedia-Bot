/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package utilitaire.article;

import org.jetbrains.annotations.*;
import utilitaire.*;

public class SideGamePage extends Page {
    @NotNull private final Pokemon pokemon;
    @Nullable private final Region region;

    public SideGamePage(@NotNull Pokemon pokemon) {
        this.pokemon = pokemon;
        this.region = null;

        String title = pokemon.getFrenchName() + "/Jeux secondaires";
        super(title, Wiki.POKEPEDIA);
    }

    public SideGamePage(@NotNull Pokemon pokemon, @NotNull Region region) {
        this.pokemon = pokemon;
        this.region = region;

        String title = pokemon.getFrenchName() + " " + region.getFrAdjective() + "/Jeux secondaires";
        super(title, Wiki.POKEPEDIA);
    }

    public String addSection(SideGame game, String text) {
        if (!this.doesPageExists()) return createPage(game, text);

        int insertLocation = findInsertLocation(game);
        String content = getContent() + "\n";
        if (insertLocation == -2) return content;

        String addition = """
                == %s ==
                
                %s
                
                """.formatted(game.fullName, text);

        int categoryLocation = findCategoryLocation(game);

        String category = game.equals(SideGame.PDM) ? "" : "[[Catégorie:Pokémon apparaissant dans %s]]\n".formatted(game.fullName);

        setContent(content.substring(0, insertLocation) + addition + content.substring(insertLocation, categoryLocation)
        + category + content.substring(categoryLocation));

        return getContent();
    }

    public String addPMD() {
        return addSection(SideGame.PDM, "{{Article principal|%s/Pokémon Donjon Mystère}}".formatted(getPokemonName()));
    }

    private String createPage(SideGame game, String text) {
        String category = game.equals(SideGame.PDM) ? "" : "[[Catégorie:Pokémon apparaissant dans %s]]\n".formatted(game.fullName);

        String content = """
                %s
                
                Cette page répertorie les apparitions de '''%s''' dans les [[jeux secondaires]].
                
                {{#invoke:Sommaire|triAlphabetique}}
                
                == %s ==
                
                %s
                
                [[Catégorie:Page de %s]]
                [[Catégorie:Page de jeux secondaires]]%s""".formatted(Util.makeNavigationRibbon(pokemon.getNumDex(), region),
                    getPokemonName(), game.fullName, text, getPokemonName(), category);

        setContent(content);
        return content;
    }

    private int findInsertLocation(SideGame game) {
        String content = getContent();
        int i = -1;
        boolean passed = false;
        for (SideGame sideGame : SideGame.values()) {
            if (sideGame.equals(game)) {
                //Handling the case where there was already a section for that game
                if (content.contains("== %s ==".formatted(game.fullName))) {
                    System.err.printf(
                            "%s already had data for %s, skipping%n", pokemon.getFrenchName(), game.fullName
                    );
                    return -2;
                }
                passed = true;
                continue;
            }

            if (!passed) continue;

            int temp = content.indexOf("== %s ==".formatted(sideGame.fullName));
            if (temp != -1) {
                i = temp;
                break;
            }
        }

        if (i == -1) {
            i = content.indexOf("[[Catégorie:");
        }
        return i;
    }

    private int findCategoryLocation(SideGame game) {
        String content = getContent() + "\n";
        final int init = content.indexOf("[[Catégorie:");
        int i = -1;
        boolean passed = false;

        for (SideGame sideGame : SideGame.values()) {
            if (sideGame.equals(game)) {
                passed = true;
                continue;
            }

            if (!passed) continue;

            int temp = content.indexOf("[[Catégorie:Pokémon apparaissant dans %s]]".formatted(sideGame.fullName), init);
            if (temp != -1) {
                i = temp;
                break;
            }
        }

        if (i == -1) {
            i = content.indexOf("\n", content.lastIndexOf("[[Catégorie:")) + 1;
        }

        return i;
    }

    private String getPokemonName() {
        return pokemon.getFrenchName() + getRegionalAdjective();
    }

    private String getRegionalAdjective() {
        if (this.region == null) return "";
        return " " + region.getFrAdjective();
    }

    public void setContent(String content) {
        this.content = content.replace("\n\n\n", "\n\n");
    }

    public boolean publishEdits(String summary, boolean isMinor) {
        return super.setContent(getContent(), summary, isMinor);
    }
}

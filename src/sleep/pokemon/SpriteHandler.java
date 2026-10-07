package sleep.pokemon;

import utilitaire.API;
import utilitaire.article.Page;
import utilitaire.Wiki;

import java.io.File;

public class SpriteHandler {
    private static final String PATH_TO_ARCHIVE = "/home/samuel/Pokepedia/Pokémon Sleep/exports/images";
    private static final File ARCHIVE_DIRECTORY = new File(PATH_TO_ARCHIVE);
    private static final String DESCRIPTION = "{{#invoke:Description|sprite|source=sleepwiki}}";
    private static final String COMMENT = "Upload automatique d'une image de Pokémon Sleep";

    private final Pokemon m_pokemon;
    private final int m_numDex;

    public SpriteHandler(Pokemon pokemon) {
        m_pokemon = pokemon;
        m_numDex = Integer.parseInt(pokemon.getNumDex());
    }

    public void uploadAll() {
        if (!ARCHIVE_DIRECTORY.exists()) {
            System.err.println("Couldn't open Archive directory, skipping upload");
            return;
        }

        uploadBasics();
        uploadSleeping();
        if (m_pokemon.hasUniqueCandy()) uploadCandy();
    }

    private void uploadBasics() {
        String bonus = m_pokemon.getImageryType().equals(Imagery.AGENDER) ? "" : " ♂";
        File sprite = new File(PATH_TO_ARCHIVE + "/pokemon/portrait/" + m_numDex + ".png");
        uploadSprite(sprite, "Sprite " + m_pokemon.getNumDex() + bonus + " Sleep.png");
        sprite = new File(PATH_TO_ARCHIVE + "/pokemon/portrait/shiny/" + m_numDex + ".png");
        uploadSprite(sprite, "Sprite " + m_pokemon.getNumDex() + bonus + " chromatique Sleep.png");
        sprite = new File(PATH_TO_ARCHIVE + "/pokemon/icons/" + m_numDex + ".png");
        uploadSprite(sprite, "Miniature " + m_pokemon.getNumDex() + bonus + " Sleep.png");
        sprite = new File(PATH_TO_ARCHIVE + "/pokemon/icons/shiny/" + m_numDex + ".png");
        uploadSprite(sprite, "Miniature " + m_pokemon.getNumDex() + bonus + " chromatique Sleep.png");

        if (!m_pokemon.getImageryType().equals(Imagery.AGENDER)) {
            System.err.println("Female sprites will be missing");
        }
    }

    private void uploadCandy() {
        File sprite = new File(PATH_TO_ARCHIVE + "/candy/" + m_numDex + ".png");
        String candyName = "Sprite Bonbon " + m_pokemon.getFullName() + " Sleep.png";
        String candyDescription = "Sprite d'un [[Bonbon (Pokémon Sleep)|Bonbon " + m_pokemon.getFullName() + "]] dans {{jeu|Sleep}}.\n" +
                "\n" +
                "{{Informations Fichier\n" +
                "| Source = [https://pks.raenonx.cc/fr Pokémon Sleep Info Wiki]\n" +
                "| Auteur = [[The Pokémon Company]]\n" +
                "}}\n" +
                "\n" +
                "[[Catégorie:Image d'objet de Pokémon Sleep]]";
        uploadSprite(sprite, candyName, candyDescription);
    }

    private void uploadSleeping() {
        for (int i = 1; i <= m_pokemon.getSleepCount(); i++) {
            String localDirectory = "/sleep/" + i + "/";
            File sprite = new File(PATH_TO_ARCHIVE + localDirectory + m_numDex + ".png");
            uploadSprite(sprite, "Sprite " + m_pokemon.getNumDex() + " Dodo " + i + " Sleep.png");
        }

        for (int i = 1; i <= m_pokemon.getSleepCount(); i++) {
            String localDirectory = "/sleep/" + i  + "/shiny/";
            File sprite = new File(PATH_TO_ARCHIVE + localDirectory + m_numDex + ".png");
            uploadSprite(sprite, "Sprite " + m_pokemon.getNumDex() + " Dodo " + i + " chromatique Sleep.png");
        }
    }

    private void uploadSprite(File file, String fileName) {
        uploadSprite(file, fileName, DESCRIPTION);
    }

    private void uploadSprite(File file, String fileName, String description) {
        if (new Page("Fichier:" + fileName, Wiki.POKEPEDIA).doesPageExists()) {
            System.err.println("File " + fileName + " already exists on Poképedia, skipping");
            return;
        }
        if (file.exists() && API.upload(fileName, file, description, COMMENT)) {
            System.out.println("Successfully uploaded " + fileName);
        } else {
            System.err.println("Failed to upload " + fileName);
        }
    }
}

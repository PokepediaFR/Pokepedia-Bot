package utilitaire;

import org.jetbrains.annotations.Contract;
import utilitaire.article.Page;
import utilitaire.article.PageToPublish;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Util class provides static functions that can have general usage while working on a wiki page.
 *
 * <p>This class isn't made to be instantiated, as it only contains static stuff.</p>
 *
 * @author Samuel Chanal
 */
public class Util {

    private static final int NBR_POKE = 1025;

    private static final ArrayList<String> gamesOrder = new ArrayList(List.of(
            "TCG",
            "TCG2",
            "Pin",
            "PinRS",
            "St1",
            "St2",
            "PBR",
            "PuzC",
            "PuzL",
            "Pokéwalker",
            "Colo",
            "XD",
            "Da",
            "L!",
            "LB",
            "Sh",
            "Sh-v1",
            "CM",
            "CM-v1",
            "PDMRB",
            "PDMTO",
            "PDMC",
            "PDMTOC",
            "PDMPI",
            "PMDM",
            "PDMDX",
            "Ra1",
            "Ra2",
            "Ra3",
            "MPR",
            "PR",
            "SPR",
            "PRU",
            "PRW",
            "PRR",
            "PPk1",
            "PPk2",
            "APCC",
            "Cq",
            "RAdar",
            "P3DP",
            "Pic",
            "Pokkén",
            "PokkénDX",
            "GO",
            "GO-v1",
            "GO-v2",
            "MJ",
            "Pav",
            "DéPi",
            "DéPi2",
            "Q",
            "PM",
            "Smile",
            "NPSnap",
            "UNITE",
            "Sleep",
            "Friends",
            "Pokopia"
    ));

    /**
     * Transform the format of a number to match Poképedia's Pokédex number format.
     * @param number number to be transformed
     * @return a string containing the initial number, preceded by 0s to make it 4 digits
     * @throws IllegalArgumentException if the number is lower than 1 or higher than the current last Pokémon number
     */
    public static String numberToPokepediaDexFormat(int number)
    {
        if(number <= 0 || number > NBR_POKE)
        {
            throw new IllegalArgumentException("The number doesn't correspond to a valid Pokédex number");
        }

        if(number < 10)
        {
            return "000" + number;
        } else if (number < 100) {
            return "00" + number;
        } else if (number < 1000) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    /**
     * Increments a number contained within a sentence String. Currently, does not handle the French number format if it
     * goes above 999
     * @param sentence String containing the number that needs to be incremented
     * @param numberIndex index of the number within the String, starting at 0. Supports French styled numbers
     * @param increment how much should the number be incremented by
     * @return the same string with the incremented number.
     */
    public static String incrementValueInString(String sentence, int numberIndex, int increment) {
        if (increment == 0) {
            return sentence;
        }

        // Regex : nombre entier, éventuellement négatif, avec espaces entre les milliers
        // Exemples valides : "12", "-123", "1 000", "12 345 678"
        Pattern pattern = Pattern.compile("-?\\d{1,3}(?:[ \\u00A0]\\d{3})*");
        Matcher matcher = pattern.matcher(sentence);

        int currentIndex = 0;
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            if (currentIndex == numberIndex) {
                // Supprimer les espaces internes pour parse correctement
                String rawNumber = matcher.group().replaceAll("[ \\u00A0]", "");
                int oldValue = Integer.parseInt(rawNumber);
                int newValue = oldValue + increment;

                // Conversion via ta fonction numberDecomposition (externe)
                String formatted = numberDecomposition(newValue);

                matcher.appendReplacement(result, Matcher.quoteReplacement(formatted));
            } else {
                matcher.appendReplacement(result, matcher.group());
            }
            currentIndex++;
        }

        matcher.appendTail(result);

        if (numberIndex >= currentIndex) {
            throw new IllegalArgumentException(
                    "La phrase ne contient pas autant de nombres (index demandé : " + numberIndex + ")."
            );
        }

        return result.toString();
    }

    /**
     * Increments the "rowspan" value at the start of a String by 1 for wikiMedia tables
     * @param line the line of raw wikicode where the rowspan needs to be incremented
     * @return the same line, but with the rowspan incremented. If it didn't have a rowspan, '| rowspan="2"' will be added
     */
    public static String incrementRowspan(String line)
    {
        int i;
        String result;
        if((i = line.indexOf("rowspan=")) != -1) {
            int previousSpan;
            int previousEnd;
            switch (line.charAt(i + 8)) {
                case '"' -> {
                    previousSpan = Integer.parseInt(line.substring(i + 9, i + 10));
                    previousEnd = i + 11;
                }
                case ' ' -> {
                    previousSpan = Integer.parseInt(line.substring(i + 9, i + 10));
                    previousEnd = i + 10;
                }
                default -> {
                    previousSpan = Integer.parseInt(line.substring(i + 8, i + 9));
                    previousEnd = i + 9;
                }
            }

            result = line.substring(0, i+8) + "\"" + (previousSpan+1) + "\"" + line.substring(previousEnd);
        }
        else
        {
            result = line.charAt(0) + " rowspan=\"2\" |" + line.substring(1);
        }

        return result;
    }

    /**
     * Allows to search the first occurrence of a specific String within an ArrayList after a given element
     * @param lines collection de lignes formant le texte
     * @param searchedElement the researched String (case-sensitive)
     * @param startIndex the index from which the search is started
     * @return the position of the first searchedElement occurrence, or -1 if not found.
     */
    public static int nextIndexOf(ArrayList<String> lines, String searchedElement, int startIndex)
    {
        List<String> sub = lines.subList(startIndex, lines.size());
        int r = sub.indexOf(searchedElement);

        if(r == -1) return -1;
        return r+startIndex;
    }

    /**
     * Adapts numbers to the French number writing for large numbers by adding spaces
     * @param number the number that needs to be formated
     * @return a String of the number with added spaces if needed
     */
    public static String numberDecomposition(int number)
    {
        if(number < 1000) return String.valueOf(number);
        ArrayList<String> temp = new ArrayList<>();
        while(number/1000 >= 1)
        {
            if(number%1000 == 0) {
                temp.addFirst("000");
            } else if (number%1000 < 10) {
                temp.addFirst("00" + number%1000);
            } else if (number%1000 < 100) {
                temp.addFirst("0" + number%1000);
            } else {
                temp.addFirst(String.valueOf(number%1000));
            }
            number /= 1000;
        }

        temp.addFirst(String.valueOf(number));
        StringBuilder r = new StringBuilder();
        for(String t : temp) r.append(t).append(" ");
        return r.substring(0, r.length()-1);
    }

    /**
     * Searches for the rest of a line in a text following a target String
     * @param content the text that needs to be searched
     * @param searchedString the String that is looked for in the text
     * @param nullIsTolerated indicates whether the program should return null or throw an exception if searchedString isn't found
     * @return a substring of content with what is between searchedString and the next line break or the end of content.
     * returns null if nullIsTolerated is true and searchedString isn't found.
     * @throws ElementNotFoundException if nullIsTolerated is false and searchedString isn't found.
     */
    public static String searchValueOf(String content, String searchedString, boolean nullIsTolerated) throws ElementNotFoundException
    {
        try {
            return searchValueOf(content, searchedString, "\n", 0, nullIsTolerated);
        } catch (ElementNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for the rest of a line in a text following a target String after a character certain index
     * @param content the text that needs to be searched
     * @param searchedString the String that is looked for in the text
     * @param from the index from which the search is performed
     * @param nullIsTolerated indicates whether the program should return null or throw an exception if searchedString isn't found
     * @return a substring of content with what is between searchedString and the next line break or the end of content.
     * returns null if nullIsTolerated is true and searchedString isn't found after the given index.
     * @throws ElementNotFoundException if nullIsTolerated is false and searchedString isn't found after the given index.
     */
    public static String searchValueOf(String content, String searchedString, int from, boolean nullIsTolerated) throws ElementNotFoundException
    {
        try {
            return searchValueOf(content, searchedString, "\n", from, nullIsTolerated);
        } catch (ElementNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for a substring of a text between two target Strings
     * @param content the text that needs to be searched
     * @param startString the beginning String that is looked for in the text
     * @param endString the ending String that is looked for after startString in the text
     * @param nullIsTolerated indicates whether the program should return null or throw an exception if startString isn't found
     * @return a substring of content with what is between startString and endString or the end of content.
     * returns null if nullIsTolerated is true and startString isn't found.
     * @throws ElementNotFoundException if nullIsTolerated is false and startString isn't found.
     */
    public static String searchValueOf(String content, String startString, String endString, boolean nullIsTolerated) throws ElementNotFoundException
    {
        try {
            return searchValueOf(content, startString, endString, 0, nullIsTolerated);
        } catch (ElementNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for a substring of a text between two target Strings
     * @param content the text that needs to be searched
     * @param startString the beginning String that is looked for in the text
     * @param endString the ending String that is looked for after startString in the text
     * @param from the index from which the search of startString is performed
     * @param nullIsTolerated indicates whether the program should return null or throw an exception if startString isn't found
     * @return a substring of content with what is between startString and endString or the end of content.
     * returns null if nullIsTolerated is true and startString isn't found.
     * @throws ElementNotFoundException if nullIsTolerated is false and startString isn't found.
     */
    @Contract("_, _, _, _, false -> !null")
    public static String searchValueOf(String content, String startString, String endString, int from, boolean nullIsTolerated) throws ElementNotFoundException
    {
        int startIndex = content.indexOf(startString, from)+startString.length();
        if (startIndex == startString.length()-1) {
            if (nullIsTolerated) {
                return null;
            }
            throw new ElementNotFoundException(startString, "result in text");
        }

        int endIndex = content.indexOf(endString, startIndex);
        if(endIndex == -1) {
            return content.substring(startIndex);
        } else {
            return content.substring(startIndex, endIndex);
        }
    }

    /**
     * Build the wikicode of a page from all of its lines contained in an ArrayList
     * @param lines the ArrayList containing individual every text lines that need to be put together
     * @return a concatenation of every String in the list separated with line breaks
     */
    public static String wikicodeReconstruction(ArrayList<String> lines)
    {
        StringBuilder result = new StringBuilder();
        for(String ligne : lines)
        {
            result.append(ligne).append("\n");
        }
        return result.toString();
    }

    /**
     * Searches for the French description of a given Pokémon for a given Game. The program will crash if the Pokémon doesn't exist
     * @param pokemon the French name of the Pokémon
     * @param game the Pokémon Game the description should be taken from
     * @return a String containing the Pokémon description
     * @throws ElementNotFoundException if a description of this Pokémon isn't found for this game (or might return "mon")
     */
    public static String getFrenchPokemonDescription(String pokemon, Game game) throws ElementNotFoundException {

        Page pokemonPage;
        if(game.getGeneration() < 8) {
            pokemon = pokemon + "/Génération_" + game.getGeneration();
        }
        pokemonPage = new Page(pokemon, Wiki.POKEPEDIA);
        String code = pokemonPage.getContent().replaceAll("<sup>.*</sup>", "");

        try {
            String description = searchValueOf(code, ";{{Jeu|" + game.getFrenchAcronym() + "}}\n:", false);
            if (description.startsWith(" ")) {
                description = description.substring(1);
            }

            return description;
        } catch (ElementNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getInsertionLineForSideImagery(ArrayList<String> lines, String game)
    {
        int l = -1;
        for(String line : lines) {
            if(line.startsWith("{{#invoke:Imagerie|secondaire")) {
                l = lines.indexOf(line) + 1;
                break;
            }
        }

        if(l == -1) {
            throw new ElementNotFoundException("{{#invoke:Imagerie|secondaire", "Searching for the side imagery of the Pokémon");
        }

        int targetGameIndex = gamesOrder.indexOf(game);
        if(targetGameIndex == -1) {
            throw new IllegalArgumentException(game + " is not a valid game");
        }
        int currentGameIndex = gamesOrder.indexOf(lines.get(l).split(" ")[0]);
        while(currentGameIndex < targetGameIndex && !lines.get(l).equals("}}")) {
            l++;
            currentGameIndex = gamesOrder.indexOf(lines.get(l).split(" ")[0]);
        }

        return l;
    }

    public static String insertIntoString(String content, String insertion, int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > content.length()) throw new IndexOutOfBoundsException("index out of \"content\" bounds");

        return content.substring(0, index) + insertion + content.substring(index);
    }

    public static String makeNavigationRibbon(int number) {
        return Util.makeNavigationRibbon(number, null);
    }

    public static String makeNavigationRibbon(int numDex, Region region) {
        String result = "{{Ruban Pokémon\n";
        Pokemon currentPoke = PokeData.getPokemonFromNum(numDex);
        String regionAdjective = region == null ? "" : " " + region.getFrAdjective();

        if(numDex > 1) {
            Pokemon previousPoke = PokeData.getPokemonFromNum(numDex-1);
            result += "| nomPrécédent=" + previousPoke.getFrenchName();
            if(region != null && previousPoke.getRegionalForms().contains(region)) {
                result += regionAdjective + "\n| forme-précédent=" + region.getFrName() + "\n";
            } else {
                result += "\n";
            }
        }

        result += "| numéro=" + numberToPokepediaDexFormat(numDex) + "\n| nom=" + currentPoke.getFrenchName() + regionAdjective + "\n";

        if (numDex == PokeData.getPokemonCount()) {
            result += "}}";
            return result;
        }

        Pokemon nextPoke = PokeData.getPokemonFromNum(numDex + 1);
        result += "| nomSuivant=" + nextPoke.getFrenchName();
        if(region != null && nextPoke.getRegionalForms().contains(region)) {
            result += regionAdjective + "\n| forme-suivant=" + region.getFrName() + "\n}}";
        } else {
            result += "\n}}";
        }
        return result;
    }

    public static void publishMultipleEdits(HashMap<Page, String> wikiPages, String summary, boolean isMinor) {
        Scanner confirm = new Scanner(System.in);
        System.out.println("Upload ready, press Enter to start.");
        confirm.nextLine();

        wikiPages.forEach( (k, v) -> {
            if(k.setContent(v, summary, isMinor))
            {
                System.out.println(k.getTitle() + " was successfully uploaded.");
            }
            else
            {
                System.err.println("Error during upload of " + k.getTitle());
            }
        });
    }

    public static void publishMultipleEdits(List<PageToPublish> pages) {
        Scanner confirm = new Scanner(System.in);
        System.out.println("Upload ready, press Enter to start.");
        confirm.nextLine();

        publishEditsConfirmed(pages);
    }

    public static void publishEditsConfirmed(List<PageToPublish> pages) {
        for(PageToPublish edit : pages) {
            if (edit == null) continue;
            long time = System.currentTimeMillis();

            Page page = edit.getPage();
            if(page.setContent(edit.getNewContent(), edit.getSummary(), edit.isMinor()))
            {
                System.out.println(page.getTitle() + " was successfully uploaded.");
                try {
                    long delay = time + 1000 - System.currentTimeMillis();
                    Thread.sleep(Math.max(1, delay));
                } catch (InterruptedException _) {}
            }
            else
            {
                System.err.println("Error during upload of " + page.getTitle());
                System.out.println(edit.getNewContent());
            }
        }
    }

    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy", Locale.FRENCH);

        String result = formatter.format(date);

        if (result.startsWith("1 ")) return result.replaceFirst("1 ", "1<sup>er</sup> ");

        return result;
    }

    public static Date getEndDate(Date date, int duration) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, duration);

        return cal.getTime();
    }

    public static String insertIntoSideGamesPage(Page page, String game, String content) {

        return "";
    }

    @Deprecated
    public static String extractSection(String content, String sectionName) {
        int level = sectionName.split(" ")[0].length();
        if (!content.contains(sectionName)) throw new ElementNotFoundException(sectionName, "section in the page");

        String result = null;

        for (int i = 1; i <= level; i++) {
            String newResult = searchValueOf(content, sectionName, "\n" + "=".repeat(i) + " ", false).strip();
            if (result == null || newResult.length() < result.length()) result = newResult;
        }

        return result;
    }
}

package utilitaire;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The PokeData class holds data on Pokémon names as well other data such as attacks and abilities, and methods handling them.
 *
 * <p>Later on it should be replaced with JSON(s) so it can be better maintained and doesn't look like... this.</p>
 *
 * @author Mewtwo-Ex
 * @author GaletteLithium
 * @author Samuel Chanal
 */
public class PokeData {

    private static final String[] capacites = {"À la Queue", "Abîme", "Aboiement", "Abri", "Acidarmure", "Acide", "Acrobatie", "Acupression", "Aéro Noir", "Aéro-Lames", "Aéroblast", "Aéropique", "Affûtage", "Aiguisage", "Aile d'Acier", "Air Veinard", "Aire d'Eau", "Aire d'Herbe", "Aire de Feu", "Allègement", "Amnésie", "Ampleur", "Anneau Hydro", "Anti-Air", "Anti-Brume", "Anti-Soin", "Appel Attak", "Appel Défense", "Appel Soins", "Après Vous", "Aqua-Jet", "Ardeur Noire", "Armure", "Aromathérapie", "Assaut Noir", "Assistance", "Assurance", "Astuce Force", "Atout", "Atterrissage", "Attraction (attaque)", "Attrition", "Aurasphère", "Aurore (attaque)", "Avalanche", "Avale", "Babil", "Bâillement", "Bain de Smog", "Balance (attaque)", "Balayage", "Balayette", "Ball'Brume", "Ball'Glace", "Ball'Météo", "Ball'Ombre", "Balle Graine", "Bang Sonique", "Barrage", "Baston", "Bec Vrille", "Bélier", "Bélier Noir", "Berceuse", "Blabla Dodo", "Blizzard", "Blocage Noir", "Bluff", "Bomb'Œuf", "Bomb-Beurk", "Bombaimant", "Bombe Acide", "Boost", "Bouclier", "Bouclier Royal", "Boue-Bombe", "Boul'Armure", "Boule Élek", "Boule Roc", "Bourdon", "Boutefeu", "Brouhaha", "Brouillard (attaque)", "Brume", "Brume Capiteuse", "Brume Noire", "Buée Noire", "Bulldoboule", "Bulles d'O", "Cadeau", "Cage-Éclair", "Calcination", "Câlinerie", "Camaraderie", "Camouflage", "Canicule (attaque)", "Canon Graine", "Carnareket", "Cascade", "Casse-Brique", "Cauchemar", "Célébration", "Champ Brumeux", "Champ Herbu", "Champ Électrifié", "Change Éclair", "Changement Vitesse", "Chant Antique", "Chant Canon", "Charge", "Charge Foudre", "Charge Noire", "Charge-Os", "Chargeur", "Charme (attaque)", "Chatouille", "Choc Mental", "Choc Psy", "Choc Venin", "Chute Glace", "Chute Libre", "Chute Noire", "Châtiment", "Ciel Noir", "Clairvoyance", "Claquoir", "Clonage (attaque)", "Close Combat", "Cogne", "Cognobidon", "Colère", "Combo-Griffe", "Confidence", "Constriction", "Contre", "Conversion", "Conversion2", "Copie", "Copie Type", "Coquilame", "Corps Perdu", "Cotogarde", "Coud'Boue", "Coud'Krâne", "Coup Bas", "Coup d'Boule", "Coup d'Jus", "Coup d'Main", "Coup Double", "Coup Victoire", "Coup-Croix", "Coupe", "Coupe Psycho", "Coupe-Vent", "Cradovague", "Crèvecœur", "Croc de Mort", "Croc Fatal", "Crochet Venin", "Croco Larme", "Crocs Éclair", "Crocs Feu", "Crocs Givre", "Croissance", "Cru-Aile", "Cyclone", "Damoclès", "Danse Draco", "Danse du Feu", "Danse Flamme", "Danse-Fleur", "Danse-Folle", "Danse-Lames", "Danse-Lune", "Danse Pluie", "Danse-Plume", "Dard Mortel", "Dard-Nuée", "Dard-Venin", "Déflagration", "Dégommage", "DélugePlasmique", "Demi-Tour", "Dépit", "Dernier Mot", "Dernier Recours", "Destruction", "Détection", "Détrempage", "Détricanon", "Détritus", "Dévorêve", "Direct Toxik", "Distorsion (attaque)", "Don Naturel", "Double Baffe", "Double-Dard", "Double-Pied", "Doux Baiser", "Doux Parfum", "Draco Ascension", "Draco-Queue", "Draco-Rage", "Dracocharge", "Dracochoc", "Dracogriffe", "Draco Météor", "Dracosouffle", "Dynamopoing", "E-Coque", "Éboulement", "Ébullilave", "Ébullition", "Échange (attaque)", "Échange Psy", "Écho (attaque)", "Éclair", "Éclair Croix", "Éclair Fou", "Éclair Gelé", "Éclair Noir", "Éclat Magique", "Éclate Griffe", "Éclate-Roc", "Éclats Glace", "Éco-Sphère", "Écras'Face", "Écrasement", "Écume", "Effort", "Élécanon", "Électacle", "Électrisation", "Embargo", "Empal'Korne", "Encore", "Encornebois", "Enroulement", "Entrave", "Ère Glaciaire", "Éructation", "Éruption", "Escalade", "Essorage", "Étincelle", "Étonnement", "Étreinte", "Étrennes", "Exploforce", "Explonuit", "Explosion", "Extrasenseur", "Exuviation", "Fatal-Foudre", "Faucheuse", "Faux-Chage", "Façade", "Feinte", "Fertilisation", "Feu d'Enfer", "Feu Ensorcelé", "Feu Follet", "Feu Glacé", "Feu Sacré", "Feuille Magik", "Flair", "Flamme Bleue", "Flamme Croix", "Flammèche", "Flash", "Flatterie", "Fléau", "Flying Press", "Folie Noire", "Force", "Force Ajoutée", "Force Cachée", "Force Chtonienne", "Force Cosmik", "Force Poigne", "Force-Nature", "Forte-Paume", "Fouet Lianes", "Fracass'Tête", "Frappe Atlas", "Frappe Psy", "Frénésie", "Froid Noir", "Frotte-Frimousse", "Frustration", "Fulmifer", "Fulmigraine", "Furie", "Garde Florale", "Garde Large", "Gaz Toxik", "Géo-Contrôle", "Giclédo", "Giga Impact", "Giga-Sangsue", "Glaciation", "Glas de Soin", "Gonflette", "Gravité", "Grêle (attaque)", "Gribouille", "Griffe", "Griffe Acier", "Griffe Ombre", "Grimace", "Grincement", "Grobisou", "Grondement", "Groz'Yeux", "Guillotine", "Gyroballe", "Halloween", "Hantise", "Harcèlement", "Hâte", "Hurle-Temps", "Hurlement", "Hydroblast", "Hydrocanon", "Hydroqueue", "Hypnose", "Imitation", "Implore", "Incendie", "Interversion", "Jackpot", "Jet de Sable", "Jet-Pierres", "Jugement", "Koud'Korne", "Lait à Boire", "Lame d'Air", "Lame de Roc", "Lame Ointe", "Lame Pangéenne", "Lame Sainte", "Lame-Feuille", "Lance-Boue", "Lance-Flammes", "Lance-Soleil", "Lancécrou", "Larcin", "Laser Glace", "Léchouille", "Lévikinésie", "Ligotage", "Lilliput", "Lire-Esprit", "Lumi-Éclat", "Luminocanon", "Lumiqueue", "Lutte", "Lyophilisation", "Mach Punch", "Machination", "Mâchouille", "Magné-Contrôle", "Mains Jointes", "Malédiction", "Maléfice Sylvain", "Mania", "Marto-Poing", "Martobois", "Massd'Os", "Mawashi Geri", "Méga-Sangsue", "Mégacorne", "Mégafouet", "Mégaphone", "Météores", "Métronome (attaque)", "Mimi-Queue", "Mimique", "Miroi-Tir", "Mitra-Poing", "Moi d'Abord", "Morphing", "Morsure", "Mort-Ailes", "Mur de Fer", "Mur Lumière", "Nitrocharge", "Nuée de Poudre", "Nœud Herbe", "Ocroupi", "Octazooka", "Œil Miracle", "Ombre Nocturne", "Ombre Portée", "Onde Boréale", "Onde de Choc (attaque)", "Onde Folie", "Onde Noire", "Onde Originelle", "Onde Vide", "Ondes Étranges", "Orage Adamantin", "Osmerang", "Ouragan", "Papillodanse", "Par Ici", "Para-Spore", "Parabocharge", "Paresse", "Partage Force", "Partage Garde", "Passe-Cadeau", "Passe-Passe", "Patience", "Peignée", "Percée Noire", "Permucœur", "Permuforce", "Permugarde", "Photocopie", "Phytomixeur", "Picanon", "Pico-Défense", "Picore", "Picots", "Picpic", "Pics Toxik", "Pied Brûleur", "Pied Sauté", "Pied Voltige", "Piège de Roc", "Piétisol", "Pilonnage", "Pince-Masse", "Piqué", "Piqûre", "Pisto-Poing", "Pistolet à O", "Piège de Venin", "Plaie-Croix", "Plaquage", "Plénitude", "Plongée", "Plumo-Queue", "Poing Boost", "Poing Comète", "Poing Dard", "Poing de Feu", "Poing Météor", "Poing Ombre", "Poing-Éclair", "Poing-Glace", "Poing-Karaté", "Poison-Croix", "Poliroche", "Possessif", "Poudre Dodo", "Poudre Fureur", "Poudre Toxik", "Poudreuse", "Poursuite", "Pouvoir Antique", "Pouvoir Lunaire", "Prescience", "Presse", "Prévention", "Prélèvem. Destin", "Projection", "Protection", "Provoc", "Psycho Boost", "Psyko", "Psykoud'Boul", "Puissance (attaque)", "Puissance Cachée", "Punition", "Purédpois", "Queue de Fer", "Queue-Poison", "Racines", "Rafale Feu", "Rafale Psy", "Rage Noire", "Râle Mâle", "Rancune", "Rapace", "Rayon Chargé", "Rayon Gemme", "Rayon Lune", "Rayon Signal", "Rayon Simple", "Rebond", "Rebondifeu", "Recyclage", "Reflet", "Reflet Magik", "Regard Médusant", "Regard Noir", "Regard Touchant", "Régénération", "Relais", "Relâche", "Rengorgement", "Renversement", "Repli", "Repos", "Représailles", "Requiem", "Retenue", "Retour", "Retour Noir", "Réveil Forcé", "Revenant", "Riposte", "Roc-Boulet", "Ronflement", "Roue de Feu", "Roulade", "Rugissement", "Rune Protect", "Ruse", "Sabotage", "Sacrifice", "Saisie", "Saumure", "Sécrétion", "Séduction", "Séisme", "Sheauriken", "Siffl'Herbe", "Siphon", "Soin", "Sonicboom", "Soucigraine", "Souffle Glacé", "Souffle Noir", "Souplesse", "Souvenir", "Spatio-Rift", "Spore", "Spore Coton", "Stalagtite", "Stimulant", "Stockage", "Stratopercut", "Strido-Son", "Suc Digestif", "Surchauffe", "Surf", "Surpuissance", "Survinsecte", "Synchropeine", "Synthèse", "Tacle Feu", "Tacle Lourd", "Taillade", "Tatamigaeshi", "Techno Buster", "Télékinésie", "Téléport", "Telluriforce", "Tempête Florale", "Tempête de Sable", "Tempête Verte", "Ten-danse", "Ténacité", "Tête de Fer", "Tir de Boue", "Toile", "Toile Gluante", "Toile Élek", "Tomberoche", "Tonnerre", "Torgnoles", "Tornade", "Tour Rapide", "Tourbi-Sable", "Tourmagik", "Tourmente", "Tourniquet", "Tout ou Rien", "Toxik", "Tranch'Air", "Tranch'Herbe", "Tranche", "Tranche-Nuit", "Trempette", "Tricherie", "Triplattaque", "Triple Pied", "Trou Noir", "Tunnel", "Tunnelier", "Typhon Noir", "Ultimapoing", "Ultimawashi", "Ultralaser", "Ultrason", "Uppercut", "Vague Psy", "Vampibaiser", "Vampigraine", "Vampipoing", "Vampirisme", "Vantardise", "Végé-Attak", "Vendetta", "Vengeance", "Vent Argenté", "Vent Arrière", "Vent Féérique", "Vent Glace", "Vent Mauvais", "Vent Violent", "Verrou Enchanté", "Verrouillage (attaque)", "Vibra Soin", "Vibraqua", "Vibrobscur", "Vigilance", "Vitesse Extrême", "Vive-Attaque", "Vœu", "Vœu Soin", "Voile Miroir", "Voix Enjôleuse", "Vol (attaque)", "Vol Magnétik", "Vol-Vie", "Vortex Magma", "Yama Arashi", "Yoga", "Zénith", "Zone Étrange", "Zone Magique"};

    private static Pokemon[] pokemon = null;

    private static final String[] talents = {"Absentéisme","Absorb Eau","Absorb Volt","Acharné","Adaptabilité","Agitation","Ailes Bourrasque","Air Lock","Alerte Neige","Amour Filial","Analyste","Annule Garde","Anti-Bruit","Anticipation","Armumagma","Armurbaston","Armurouillée","Aroma-Voile","Attention","Aura Féérique","Aura Inversée","Aura Ténébreuse","Baigne Sable","Bajoues","Battant","Benêt","Boom Final","Brasier","Brise Moule","Cacophonie","Calque","Chanceux","Cherche Miel","Chlorophylle","Ciel Gris","Coloforce","Colérique","Contestation","Coque Armure","Corps Ardent","Corps Gel","Corps Maudit","Corps Sain","Crachin","Cran","Cuvette","Cœur de Coq","Cœur Noble","Cœur Soin","Don Floral","Début Calme","Déclic Tactique","Défaitiste","Déguisement","Délestage","Écaille Spéciale","Échauffement","Écran Fumée","Écran Poudre","Engrais","Envelocape","Épine de Fer","Esprit Vital","Essaim (talent)","Farceur","Fermeté","Feuille Garde","Filtre","Flora-Voile","Force Pure","Force Sable","Force Soleil","Fouille","Frein","Fuite (talent)","Garde Amie","Garde Magik","Garde Mystik","Glissade","Gloutonnerie","Gluco-Voile","Glue","Griffe Dure","Heavy Metal","Herbivore","Hydratation","Hyper Cutter","Ignifu-Voile","Ignifuge","Illusion","Impassible","Imposteur","Impudence","Inconscient","Infiltration","Insomnia","Intimidation (talent)","Isograisse","Joli Sourire","Lavabo","Lentiteintée","Light Metal","Lumiattirance","Lunatique","Lévitation","Magicien","Magnépiège","Maladresse","Marque Ombre","Matinal","Mauvais Rêve","Mer Primaire","Minus","Miroir Magik","Mode Transe","Moiteur","Momie","Motorisé","Mue","Multi-Coups","Multi-Type","Multiécaille","Médic Nature","Méga Blaster","Météo","Normalise","Œil Composé","Paratonnerre","Pare-Balles","Peau Céleste","Peau Dure","Peau Féérique","Peau Gelée","Peau Miracle","Peau Sèche","Phobique","Pickpocket","Pied Véloce","Pieds Confus","Piège (talent)","Plus","Poing de Fer","Point Poison","Poisseux","Pose Spore","Pression","Prognathe","Protéen","Prédiction","Puanteur","Querelleur","Rage Brûlure","Rage Poison","Ramassage","Regard Vif","Rideau Neige","Rivalité","Récolte","Régé-Force","Sable Volant","Sans Limite","Simple","Sniper","Soin Poison","Solide Roc","Souffle Delta","Statik","Suintement","Symbiose","Synchro","Sécheresse","Sérénité","Technicien","Tempo Perso","Tension","Terre Finale","Toison Herbue","Toison Épaisse","Torche","Torrent","Toxitouche","Turbo","TurboBrasier","Télécharge","Télépathe","Téméraire","Téra-Voltage","Tête de Roc","Vaccin","Ventouse","Victorieux","Voile Sable"};

    public static void loadPokemon() {
        if(pokemon != null) return;

        try {
            ObjectMapper mapper = new ObjectMapper();
            pokemon = mapper.readValue(new File("ressources/pokémonData.json"), new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println("Error while loading pokémonData.json");
            System.err.println(e.getMessage());
        }
        
        System.out.println("The Pokémon list was successfully loaded");
    }

    /**
     * Renvoie le Pokémon portant le numéro fourni
     * @param num le numéro d'un Pokémon dans le Dex national
     * @return l'instance du Pokemon avec toutes ses infos
     */
    public static Pokemon getPokemonFromNum(int num) throws IndexOutOfBoundsException {
        loadPokemon();

        if ( num > 0 && num <= pokemon.length ) {
            return pokemon[num-1];
        } else {
            throw new IndexOutOfBoundsException("Invalid Pokemon Number: " + num);
        }
    }

    public static Pokemon getPokemonFromName(String name) {
        loadPokemon();

        for (Pokemon mon : pokemon) {
            if(mon.getFrenchName().equals(name)) {
                return mon;
            }
        }
        throw new ElementNotFoundException(name, "Pokémon from french name");
    }

    public static String translateAllPokemonToFrench(String text)
    {
        loadPokemon();

        for(Pokemon mon : pokemon)
        {
            while(text.contains(mon.getEnglishName()))
            {
                text = text.replaceFirst(mon.getEnglishName(), mon.getFrenchName());
            }
        }
        return text;
    }

    public static String getFrenchNameFromEnglish(String englishName, String context)
    {
        loadPokemon();

        String prefix = "";
        if(englishName.startsWith("Mega "))
        {
                prefix = "Méga-";
                englishName = englishName.substring(5);
        }

        for(Pokemon mon : pokemon)
        {
            if(mon.getEnglishName().equals(englishName))
            {
                return prefix + mon.getFrenchName();
            }
        }
        throw new ElementNotFoundException(englishName, context);
    }

    public static ArrayList<Region> getRegionalFormsOfPokemon(String frenchName) {
        return getPokemonFromName(frenchName).getRegionalForms();
    }

    public static int getPokemonCount()
    {
        loadPokemon();

        return pokemon.length;
    }

    public static Pokemon[] getAllPokemon()
    {
        loadPokemon();
        return pokemon;
    }

    /**
     * Renvoie le numéro de la génération auquel un Pokémon appartient
     * @param num le numéro du Pokémon dans le Dex national
     * @return le numéro de la génération concernée, 0 si aucune correspondance n'existe
     */
    public static int getPokemonGen(int num) {
        if(num>=1 && num<=151) return 1;
        if(num>=152 && num<=251) return 2;
        if(num>=252 && num<=386) return 3;
        if(num>=387 && num<=493) return 4;
        if(num>=494 && num<=649) return 5;
        if(num>=650 && num<=721) return 6;
        if(num>=722 && num<=809) return 7;
        if(num>=810 && num<=905) return 8;
        if(num>=906 && num<=1023) return 9;
        return 0;
    }

    public static boolean isShinyLock(int num) {
        return num==494 //Victini
                || num==720 //Hoopa
                || num==721 //Volcanion
                || num==789 //Cosmog
                || num==790 //Cosmovum
                || num==802 //Marshadow
                || num==807 //Zeraora
                || num==888 //Zacian
                || num==889 //Zamazenta
                || num==890 //Éthernatos
                ;
    }

    /**
     * Renvoie la puissance d'une capacité Z à partir de la puissance de sa capacité d'origine
     * @param p la puissance de la capacité d'origine
     * @return la puissance de la capacité Z associée, 0 en cas d'anomalie.
     */
    public static int getZPower(int p) {
        int pz = 0;
        if(0<=p && p<=55) pz = 100;
        else if(60<=p && p<=65) pz = 120;
        else if(70<=p && p<=75) pz = 140;
        else if(80<=p && p<=85) pz = 160;
        else if(90<=p && p<=95) pz = 175;
        else if(p==100) pz = 180;
        else if(p==110) pz = 185;
        else if(120<=p && p<=125) pz = 190;
        else if(p==130) pz = 195;
        else if(140<=p) pz = 200;
        return pz;
    }

    /**
     * Renvoie le nom de la capacité Z associée à un type
     * @param type le type élémentaire
     * @return le nom de la capa
     */
    public static String getZByType(String type) {
        if(type.equalsIgnoreCase("acier")) return "Vrille Maximum";
        if(type.equalsIgnoreCase("dragon")) return "Chaos Draconique";
        if(type.equalsIgnoreCase("électrik")) return "Fulguro-Lance Gigavolt";
        if(type.equalsIgnoreCase("feu")) return "Pyro-Explosion Cataclysmique";
        if(type.equalsIgnoreCase("insecte")) return "Cocon Fatal";
        if(type.equalsIgnoreCase("plante")) return "Pétalexplosion Éblouissante";
        if(type.equalsIgnoreCase("psy")) return "Psycho-Pulvérisation EX";
        if(type.equalsIgnoreCase("sol")) return "Éruption Géo-Sismique";
        if(type.equalsIgnoreCase("ténèbres")) return "Trou Noir des Ombres";
        if(type.equalsIgnoreCase("combat")) return "Combo Hyper-Furie";
        if(type.equalsIgnoreCase("eau")) return "Super Tourbillon Abyssal";
        if(type.equalsIgnoreCase("fée")) return "Impact Choupinova";
        if(type.equalsIgnoreCase("glace")) return "Laser Cryogénique";
        if(type.equalsIgnoreCase("normal")) return "Turbo-Charge Bulldozer";
        if(type.equalsIgnoreCase("poison")) return "Déluge Causti-Toxique";
        if(type.equalsIgnoreCase("roche")) return "Apocalypse Gigalithique";
        if(type.equalsIgnoreCase("spectre")) return "Appel des Ombres Éternelles";
        if(type.equalsIgnoreCase("vol")) return "Piqué Supersonique";
        return null;
    }

    /**
     * Renvoie le nom du cristal Z associée à un type
     * @param type le type élémentaire
     * @return le nom de la capa
     */
    public static String getCristalByType(String type) {
        if(type.equalsIgnoreCase("acier")) return "Métallozélite";
        if(type.equalsIgnoreCase("dragon")) return "Dracozélite";
        if(type.equalsIgnoreCase("électrik")) return "Voltazélite";
        if(type.equalsIgnoreCase("feu")) return "Pyrozélite";
        if(type.equalsIgnoreCase("insecte")) return "Insectozélite";
        if(type.equalsIgnoreCase("plante")) return "Florazélite";
        if(type.equalsIgnoreCase("psy")) return "Psychézélite";
        if(type.equalsIgnoreCase("sol")) return "Terrazélite";
        if(type.equalsIgnoreCase("ténèbres")) return "Ténébrozélite";
        if(type.equalsIgnoreCase("combat")) return "Combazélite";
        if(type.equalsIgnoreCase("eau")) return "Aquazélite";
        if(type.equalsIgnoreCase("fée")) return "Nymphézélite";
        if(type.equalsIgnoreCase("glace")) return "Cryozélite";
        if(type.equalsIgnoreCase("normal")) return "Normazélite";
        if(type.equalsIgnoreCase("poison")) return "Toxizélite";
        if(type.equalsIgnoreCase("roche")) return "Rocazélite";
        if(type.equalsIgnoreCase("spectre")) return "Spectrozélite";
        if(type.equalsIgnoreCase("vol")) return "Aérozélite";
        return null;
    }

    public static String[] getForms(int num) {
        // Zarbi
        if(num==201) {
            return new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","?","!"};
        }
        // Morphéo
        if(num==351) {
            return new String[]{"", "Forme Neige", "Forme Pluie", "Forme Soleil"};
        }
        // Deoxys
        if(num==386) {
            return new String[]{"", "Forme Attaque", "Forme Défense", "Forme Vitesse"};
        }
        // Cheniti et Cheniselle
        if(num==412 || num==413) {
            return new String[]{"Cape Plante", "Cape Déchet", "Cape Sable"};
        }
        // Ceriflor
        if(num==421) {
            return new String[]{"Forme Ouverte", "Forme Fermée"};
        }
        // Sancoki et Tritosor
        if(num==422 || num==423) {
            return new String[]{"Forme Ouest", "Forme Est"};
        }
        // Motisma
        if(num==479) {
            return new String[]{"", "Forme Froid", "Forme Chaleur", "Forme Hélice", "Forme Tonte", "Forme Lavage"};
        }
        // Giratina
        if(num==487) {
            return new String[]{"-a", "-o"};
        }
        // Shaymin
        if(num==492) {
            return new String[]{"-t", "-c"};
        }
        // Arceus
        if(num==493) {
            return new String[]{"", "-Acier","-Combat","-Dragon","-Eau","-Électrique","-Feu","-Glace","-Insecte","-Plante","-Poison","-Psy","-Roche","-Sol","-Spectre","-Ténèbres","-Vol"};
        }
        // Bargantua
        if(num==550) {
            return new String[]{"R", "B"};
        }
        // Vivaldaim et Haydaim
        if(num==585 || num==586) {
            return new String[]{"Printemps", "Été", "Automne", "Hiver"};
        }
        // Génies
        if(num==641 || num==642 || num==645) {
            return new String[]{"", "-t"};
        }
        // Keldeo
        if(num==647) {
            return new String[]{"-n", "-d"};
        }
        // Meloetta
        if(num==648) {
            return new String[]{"-c", "-d"};
        }
        // Genesect
        if(num==649) {
            return new String[]{"", "-c", "-p"};
        }
        // Exagide
        if(num==681) {
            return new String[]{"-p"};
        }

        return null;
    }

    static int getGenerationAttack(String attackName) {
        attackName = attackName.replace(" (capacité)", "");
        Object[][] temporaryDict = new Object[][]{
                {"Abîme", 1}, {"Acidarmure", 1}, {"Acide", 1}, {"Affûtage", 1}, {"Amnésie", 1}, {"Armure", 1}, {"Balayage", 1}, {"Bec Vrille", 1}, {"Bélier", 1}, {"Berceuse", 1}, {"Blizzard", 1}, {"Bombe Œuf", 1}, {"Bouclier", 1}, {"Boul'Armure", 1}, {"Brouillard", 1}, {"Brume", 1}, {"Buée Noire", 1}, {"Bulles d'O", 1}, {"Cage Éclair", 1}, {"Cascade", 1}, {"Charge", 1}, {"Choc Mental", 1}, {"Claquoir", 1}, {"Clonage", 1}, {"Combo-Griffe", 1}, {"Constriction", 1}, {"Conversion", 1}, {"Copie", 1}, {"Coud'Krâne", 1}, {"Coup d'Boule", 1}, {"Coupe", 1}, {"Coupe-Vent", 1}, {"Croc de Mort", 1}, {"Croc Fatal", 1}, {"Croissance", 1}, {"Cru-Ailes", 1}, {"Cyclone", 1}, {"Damoclès", 1}, {"Danse Flammes", 1}, {"Danse Fleurs", 1}, {"Danse Lames", 1}, {"Dard-Nuée", 1}, {"Dard-Venin", 1}, {"Déflagration", 1}, {"Destruction", 1}, {"Détritus", 1}, {"Dévorêve", 1}, {"Double Dard", 1}, {"Double Pied", 1}, {"Draco-Rage", 1}, {"E-Coque", 1}, {"Éboulement", 1}, {"Éclair", 1}, {"Écras'Face", 1}, {"Écrasement", 1}, {"Écume", 1}, {"Empal'Korne", 1}, {"Entrave", 1}, {"Étreinte", 1}, {"Explosion", 1}, {"Fatal-Foudre", 1}, {"Flammèche", 1}, {"Flash", 1}, {"Force", 1}, {"Force Poigne", 1}, {"Fouet Lianes", 1}, {"Frappe Atlas", 1}, {"Frénésie", 1}, {"Furie", 1}, {"Gaz Toxik", 1}, {"Griffe", 1}, {"Grincement", 1}, {"Grobisou", 1}, {"Groz'Yeux", 1}, {"Guillotine", 1}, {"Hâte", 1}, {"Hurlement", 1}, {"Hydrocanon", 1}, {"Hypnose", 1}, {"Jackpot", 1}, {"Jet de Sable", 1}, {"Jet-Pierres", 1}, {"Koud'Korne", 1}, {"Lance-Flammes", 1}, {"Lance-Soleil", 1}, {"Laser Glace", 1}, {"Léchouille", 1}, {"Ligotage", 1}, {"Lilliput", 1}, {"Lutte", 1}, {"Mania", 1}, {"Massd'Os", 1}, {"Mawashi Geri", 1}, {"Méga-Sangsue", 1}, {"Météores", 1}, {"Métronome", 1}, {"Mimi-Queue", 1}, {"Mimique", 1}, {"Morphing", 1}, {"Morsure", 1}, {"Mur Lumière", 1}, {"Ombre Nocturne", 1}, {"Onde Boréale", 1}, {"Onde Folie", 1}, {"Osmerang", 1}, {"Para-Spore", 1}, {"Patience", 1}, {"Picanon", 1}, {"Picpic", 1}, {"Pied Sauté", 1}, {"Pied Voltige", 1}, {"Pilonnage", 1}, {"Pince-Masse", 1}, {"Piqué", 1}, {"Pistolet à O", 1}, {"Plaquage", 1}, {"Poing Comète", 1}, {"Poing Éclair", 1}, {"Poing Feu", 1}, {"Poing Glace", 1}, {"Poing Karaté", 1}, {"Poudre Dodo", 1}, {"Poudre Toxik", 1}, {"Protection", 1}, {"Psyko", 1}, {"Puissance", 1}, {"Purédpois", 1}, {"Rafale Psy", 1}, {"Reflet", 1}, {"Regard Médusant", 1}, {"Repli", 1}, {"Repos", 1}, {"Riposte", 1}, {"Rugissement", 1}, {"Sacrifice", 1}, {"Sécrétion", 1}, {"Séisme", 1}, {"Soin", 1}, {"Sonic Boom", 1}, {"Souplesse", 1}, {"Spore", 1}, {"Surf", 1}, {"Télékinésie", 1}, {"Téléport", 1}, {"Tonnerre", 1}, {"Torgnoles", 1}, {"Tornade", 1}, {"Toxik", 1}, {"Tranch'Herbe", 1}, {"Tranche", 1}, {"Trempette", 1}, {"Triplattaque", 1}, {"Tunnel", 1}, {"Ultimapoing", 1}, {"Ultimawashi", 1}, {"Ultralaser", 1}, {"Ultrason", 1}, {"Uppercut", 1}, {"Vague Psy", 1}, {"Vampigraine", 1}, {"Vampirisme", 1}, {"Vive-Attaque", 1}, {"Vol", 1}, {"Vole-Vie", 1}, {"Yoga", 1},
                {"Abri", 2}, {"Aéroblast", 2}, {"Ailes d'Acier", 2}, {"Ampleur", 2}, {"Attraction", 2}, {"Aurore", 2}, {"Balance", 2}, {"Ball'Ombre", 2}, {"Baston", 2}, {"Blabla Dodo", 2}, {"Bombe Beurk", 2}, {"Boost", 2}, {"Cadeau", 2}, {"Cauchemar", 2}, {"Charge Os", 2}, {"Charme", 2}, {"Clairvoyance", 2}, {"Cognobidon", 2}, {"Colère", 2}, {"Contre", 2}, {"Conversion 2", 2}, {"Corps Perdu", 2}, {"Coud'Boue", 2}, {"Coup Croix", 2}, {"Danse Pluie", 2}, {"Dépit", 2}, {"Détection", 2}, {"Doux Baiser", 2}, {"Doux Parfum", 2}, {"Draco-Souffle", 2}, {"Dynamo-Poing", 2}, {"Éclate-Roc", 2}, {"Élecanon", 2}, {"Encore", 2}, {"Étincelle", 2}, {"Faux-Chage", 2}, {"Feinte", 2}, {"Feu Sacré", 2}, {"Frustration", 2}, {"Giga-Sangsue", 2}, {"Gigotage", 2}, {"Glas de Soin", 2}, {"Gribouille", 2}, {"Griffe Acier", 2}, {"Grimace", 2}, {"Lait à Boire", 2}, {"Larcin", 2}, {"Lien du Destin", 2}, {"Lire-Esprit", 2}, {"Mach Punch", 2}, {"Mâchouille", 2}, {"Malédiction", 2}, {"Mégacorne", 2}, {"Octazooka", 2}, {"Ouragan", 2}, {"Picots", 2}, {"Poudreuse", 2}, {"Poursuite", 2}, {"Pouvoir Antique", 2}, {"Prescience", 2}, {"Puissance Cachée", 2}, {"Queue de Fer", 2}, {"Rayon Lune", 2}, {"Regard Noir", 2}, {"Relais", 2}, {"Requiem", 2}, {"Retour", 2}, {"Ronflement", 2}, {"Roue de Feu", 2}, {"Roulade", 2}, {"Rune Protect", 2}, {"Siphon", 2}, {"Spore Coton", 2}, {"Synthèse", 2}, {"Taillade", 2}, {"Tempête de Sable", 2}, {"Ténacité", 2}, {"Toile", 2}, {"Tour Rapide", 2}, {"Triple Pied", 2}, {"Vantardise", 2}, {"Vent Glace", 2}, {"Verrouillage", 2}, {"Vitesse Extrême", 2}, {"Voile Miroir", 2}, {"Zénith", 2},
                {"Aéro Noir", 3}, {"Aéropique", 3}, {"Ardeur Noire", 3}, {"Aromathérapie", 3}, {"Assaut Noir", 3}, {"Assistance", 3}, {"Avale", 3}, {"Bâillement", 3}, {"Ball'Brume", 3}, {"Ball'Glace", 3}, {"Ball'Météo", 3}, {"Balle Graine", 3}, {"Bélier Noir", 3}, {"Barrage", 3}, {"Blocage Noir", 3}, {"Bluff", 3}, {"Boule Roc", 3}, {"Brouhaha", 3}, {"Brume Noire", 3}, {"Camouflage", 3}, {"Canicule", 3}, {"Casse-Brique", 3}, {"Charge Noire", 3}, {"Chargeur", 3}, {"Chatouille", 3}, {"Chute Noire", 3}, {"Ciel Noir", 3}, {"Cogne", 3}, {"Coup d'Main", 3}, {"Crochet Venin", 3}, {"Croco Larme", 3}, {"Danse Draco", 3}, {"Danse Folle", 3}, {"Danse Plumes", 3}, {"Draco-Griffe", 3}, {"Échange", 3}, {"Éclair Noir", 3}, {"Éclate Griffe", 3}, {"Effort", 3}, {"Électacle", 3}, {"Éruption", 3}, {"Étonnement", 3}, {"Extrasenseur", 3}, {"Façade", 3}, {"Feu Follet", 3}, {"Feuille Magik", 3}, {"Flair", 3}, {"Flatterie", 3}, {"Folie Noire", 3}, {"Force Cachée", 3}, {"Force Cosmique", 3}, {"Force Nature", 3}, {"Froid Noir", 3}, {"Giclédo", 3}, {"Glaciation", 3}, {"Gonflette", 3}, {"Grondement", 3}, {"Grêle", 3}, {"Hydroblast", 3}, {"Imitation", 3}, {"Implore", 3}, {"Lame Feuille", 3}, {"Lance-Boue", 3}, {"Lumi-Éclat", 3}, {"Lumi-Queue", 3}, {"Mégaphone", 3}, {"Mitra-Poing", 3}, {"Mur de Fer", 3}, {"Ocroupi", 3}, {"Onde de Choc", 3}, {"Onde Noire", 3}, {"Par Ici", 3}, {"Paresse", 3}, {"Percée Noire", 3}, {"Pied Brûleur", 3}, {"Plénitude", 3}, {"Plongée", 3}, {"Poing Dard", 3}, {"Poing Météore", 3}, {"Poing Ombre", 3}, {"Possessif", 3}, {"Provoc", 3}, {"Psycho-Boost", 3}, {"Queue-Poison", 3}, {"Racines", 3}, {"Rafale Feu", 3}, {"Rage Noire", 3}, {"Rancune", 3}, {"Rayon Signal", 3}, {"Rebond", 3}, {"Recyclage", 3}, {"Reflet Magik", 3}, {"Régénération", 3}, {"Relâche", 3}, {"Retour Noir", 3}, {"Sabotage", 3}, {"Saisie", 3}, {"Siffl'Herbe", 3}, {"Souffle Noir", 3}, {"Souvenir", 3}, {"Stalactite", 3}, {"Stimulant", 3}, {"Stockage", 3}, {"Stratopercut", 3}, {"Strido-Son", 3}, {"Surchauffe", 3}, {"Surpuissance", 3}, {"Tir de Boue", 3}, {"Tomberoche", 3}, {"Tour de Magie", 3}, {"Tourbi-Sable", 3}, {"Tourmente", 3}, {"Tourniquet", 3}, {"Tranch'Air", 3}, {"Typhon Noir", 3}, {"Vendetta", 3}, {"Végé-Attaque", 3}, {"Vent Argenté", 3}, {"Vibraqua", 3}, {"Vœu Destructeur", 3}, {"Vœu", 3},
                {"Acupression", 4}, {"Air Veinard", 4}, {"Anneau Hydro", 4}, {"Anti-Brume", 4}, {"Anti-Soin", 4}, {"Appel Attaque", 4}, {"Appel Défense", 4}, {"Appel Soins", 4}, {"Aqua-Jet", 4}, {"Assurance", 4}, {"Astuce Force", 4}, {"Atout", 4}, {"Atterrissage", 4}, {"Aurasphère", 4}, {"Avalanche", 4}, {"Babil", 4}, {"Bombe Aimant", 4}, {"Boue-Bombe", 4}, {"Bourdon", 4}, {"Boutefeu", 4}, {"Canon Graine", 4}, {"Close Combat", 4}, {"Coup Bas", 4}, {"Coup d'Jus", 4}, {"Coup Double", 4}, {"Coupe Psycho", 4}, {"Crocs Éclair", 4}, {"Crocs Feu", 4}, {"Crocs Givre", 4}, {"Danse Lune", 4}, {"Dégommage", 4}, {"Demi-Tour", 4}, {"Dernier Recours", 4}, {"Détricanon", 4}, {"Direct Toxik", 4}, {"Distorsion", 4}, {"Don Naturel", 4}, {"Draco-Charge", 4}, {"Draco-Choc", 4}, {"Draco-Météore", 4}, {"Ébullilave", 4}, {"Échange Psy", 4}, {"Éclats Glace", 4}, {"Éco-Sphère", 4}, {"Embargo", 4}, {"Escalade", 4}, {"Essorage", 4}, {"Exploforce", 4}, {"Forte-Paume", 4}, {"Fracass'Tête", 4}, {"Fulmifer", 4}, {"Fulmigraine", 4}, {"Giga Impact", 4}, {"Gravité", 4}, {"Griffe Ombre", 4}, {"Gyroballe", 4}, {"Hurle-Temps", 4}, {"Hydro-Queue", 4}, {"Jugement", 4}, {"Lame d'Air", 4}, {"Lame de Roc", 4}, {"Luminocanon", 4}, {"Machination", 4}, {"Marto-Poing", 4}, {"Martobois", 4}, {"Mégafouet", 4}, {"Miroi-Tir", 4}, {"Moi d'Abord", 4}, {"Nœud Herbe", 4}, {"Œil Miracle", 4}, {"Ombre Portée", 4}, {"Onde Vide", 4}, {"Passe-Passe", 4}, {"Permucœur", 4}, {"Permuforce", 4}, {"Permugarde", 4}, {"Photocopie", 4}, {"Picore", 4}, {"Pics Toxik", 4}, {"Piège de Roc", 4}, {"Piqûre", 4}, {"Pisto-Poing", 4}, {"Plaie Croix", 4}, {"Poison Croix", 4}, {"Poliroche", 4}, {"Presse", 4}, {"Psykoud'Boul", 4}, {"Punition", 4}, {"Rapace", 4}, {"Rayon Chargé", 4}, {"Rayon Gemme", 4}, {"Représailles", 4}, {"Réveil Forcé", 4}, {"Revenant", 4}, {"Roc-Boulet", 4}, {"Ruse", 4}, {"Saumure", 4}, {"Séduction", 4}, {"Soucigraine", 4}, {"Spatio-Rift", 4}, {"Suc Digestif", 4}, {"Telluriforce", 4}, {"Tempête Verte", 4}, {"Tête de Fer", 4}, {"Tranche-Nuit", 4}, {"Trou Noir", 4}, {"Vampi-Poing", 4}, {"Vent Arrière", 4}, {"Vent Mauvais", 4}, {"Vibrobscur", 4}, {"Vœu Soin", 4}, {"Vol Magnétik", 4}, {"Vortex Magma", 4},
                {"À la Queue", 5}, {"Aboiement", 5}, {"Acrobatie", 5}, {"Aiguisage", 5}, {"Aire d'Eau", 5}, {"Aire d'Herbe", 5}, {"Aire de Feu", 5}, {"Allègement", 5}, {"Anti-Air", 5}, {"Après Vous", 5}, {"Attrition", 5}, {"Bain de Smog", 5}, {"Balayette", 5}, {"Bombe Acide", 5}, {"Boule Élek", 5}, {"Bulldoboule", 5}, {"Calcination", 5}, {"Change-Éclair", 5}, {"Change-Vitesse", 5}, {"Chant Antique", 5}, {"Chant Canon", 5}, {"Charge Foudre", 5}, {"Châtiment", 5}, {"Choc Psy", 5}, {"Choc Venin", 5}, {"Chute Glace", 5}, {"Chute Libre", 5}, {"Copie-Type", 5}, {"Coqui-Lame", 5}, {"Cotogarde", 5}, {"Coup Victoire", 5}, {"Cradovague", 5}, {"Crève-Cœur", 5}, {"Danse du Feu", 5}, {"Détrempage", 5}, {"Double Baffe", 5}, {"Draco-Queue", 5}, {"Ébullition", 5}, {"Écho", 5}, {"Éclair Croix", 5}, {"Éclair Fou", 5}, {"Éclair Gelé", 5}, {"Encornebois", 5}, {"Enroulement", 5}, {"Ère Glaciaire", 5}, {"Explonuit", 5}, {"Exuviation", 5}, {"Feu d'Enfer", 5}, {"Feu Glacé", 5}, {"Flamme Bleue", 5}, {"Flamme Croix", 5}, {"Force Ajoutée", 5}, {"Frappe Psy", 5}, {"Garde Large", 5}, {"Incendie", 5}, {"Interversion", 5}, {"Lame Ointe", 5}, {"Lame Sainte", 5}, {"Lancécrou", 5}, {"Lévikinésie", 5}, {"Nitrocharge", 5}, {"Papillodanse", 5}, {"Partage Force", 5}, {"Partage Garde", 5}, {"Passe-Cadeau", 5}, {"Peignée", 5}, {"Phytomixeur", 5}, {"Piétisol", 5}, {"Plumo-Queue", 5}, {"Poudre Fureur", 5}, {"Prévention", 5}, {"Projection", 5}, {"Rayon Simple", 5}, {"Rebondifeu", 5}, {"Rengorgement", 5}, {"Souffle Glacé", 5}, {"Survinsecte", 5}, {"Synchropeine", 5}, {"Tacle Feu", 5}, {"Tacle Lourd", 5}, {"Techno-Buster", 5}, {"Ten-Danse", 5}, {"Toile Élek", 5}, {"Tout ou Rien", 5}, {"Tricherie", 5}, {"Tunnelier", 5}, {"Vengeance", 5}, {"Vent Violent", 5}, {"Vibra Soin", 5}, {"Yama Arashi", 5}, {"Zone Étrange", 5}, {"Zone Magique", 5},
                {"Bang Sonique", 6}, {"Bouclier Royal", 6}, {"Brume Capiteuse", 6}, {"Câlinerie", 6}, {"Camaraderie", 6}, {"Célébration", 6}, {"Champ Brumeux", 6}, {"Champ Électrifié", 6}, {"Champ Herbu", 6}, {"Confidence", 6}, {"Dard Mortel", 6}, {"Déluge Plasmique", 6}, {"Dernier Mot", 6}, {"Draco-Ascension", 6}, {"Éclat Magique", 6}, {"Électrisation", 6}, {"Éructation", 6}, {"Étrennes", 6}, {"Fertilisation", 6}, {"Feu Ensorcelé", 6}, {"Flying Press", 6}, {"Force Chtonienne", 6}, {"Frotte-Frimousse", 6}, {"Furie Dimension", 6}, {"Garde Florale", 6}, {"Géo-Contrôle", 6}, {"Halloween", 6}, {"Hantise", 6}, {"Harcèlement", 6}, {"Jet de Vapeur", 6}, {"Lame Pangéenne", 6}, {"Lumière du Néant", 6}, {"Lyophilisation", 6}, {"Magné-Contrôle", 6}, {"Mains Jointes", 6}, {"Maléfice Sylvain", 6}, {"Mort'Ailes", 6}, {"Nuée de Poudre", 6}, {"Onde Originelle", 6}, {"Ondes Étranges", 6}, {"Orage Adamantin", 6}, {"Parabocharge", 6}, {"Pico-Défense", 6}, {"Piège de Venin", 6}, {"Poing Boost", 6}, {"Pouvoir Lunaire", 6}, {"Râle Mâle", 6}, {"Regard Touchant", 6}, {"Renversement", 6}, {"Retenue", 6}, {"Sheauriken", 6}, {"Tatamigaeshi", 6}, {"Tempête Florale", 6}, {"Toile Gluante", 6}, {"TrouDimensionnel", 6}, {"Vampibaiser", 6}, {"Vent Féérique", 6}, {"Verrou Enchanté", 6}, {"Vigilance", 6}, {"Voix Enjôleuse", 6},
                {"Affilage", 7}, {"Amass'Sable", 7}, {"Ancrage", 7}, {"Apocalypse Gigalithique", 7}, {"Apocalypsis Luminis", 7}, {"Appel des Ombres Éternelles", 7}, {"Aqua-Brèche", 7}, {"Aria de l'Écume", 7}, {"Arrogance", 7}, {"Bec-Canon", 7}, {"Blockhaus", 7}, {"Botte Sucrette", 7}, {"Boule Pollen", 7}, {"Caboche-Kaboum", 7}, {"Canon Floral", 7}, {"Carapiège", 7}, {"Cavalerie Lourde", 7}, {"Centrifugifle", 7}, {"Champ Psychique", 7}, {"Chaos Draconique", 7}, {"Choc Météore", 7}, {"Clepto-Mânes", 7}, {"Cocon Fatal", 7}, {"Colère du Gardien d'Alola", 7}, {"Combo Hyper-Furie", 7}, {"Coup Varia-Type", 7}, {"Danse Éveil", 7}, {"Dark Body Press", 7}, {"Dark Lariat", 7}, {"Déluge Causti-Toxique", 7}, {"Dracacophonie Flamboyante", 7}, {"Draco-Marteau", 7}, {"Écrous d'Poing", 7}, {"Électrikipik", 7}, {"Électro-Surf Survolté", 7}, {"Engrenage", 7}, {"Éruption Géo-Sismique", 7}, {"Escarmouche", 7}, {"Estocorne", 7}, {"Évo-Chardasso", 7}, {"Évo-Congélo", 7}, {"Évo-Dynamo", 7}, {"Évo-Écolo", 7}, {"Évo-Fabulo", 7}, {"Évo-Flambo", 7}, {"Évo-Psycho", 7}, {"Évo-Ténébro", 7}, {"Évo-Thalasso", 7}, {"Exécu-Son", 7}, {"Fauche-Âme des Sept Étoiles", 7}, {"Feuillage", 7}, {"Fil Toxique", 7}, {"Flamme Ultime", 7}, {"Fouet de Feu", 7}, {"Fulguro-Lance Gigavolt", 7}, {"Fureur des Plumes Spectrales", 7}, {"Furie-Bond", 7}, {"Gare au Ronflex", 7}, {"Giga-Tonnerre", 7}, {"Hélio-Choc Dévastateur", 7}, {"Hurlement des Roches-Lames", 7}, {"Impact Choupinova", 7}, {"Ire de la Nature", 7}, {"Lame Solaire", 7}, {"Larme à l'Œil", 7}, {"Laser Cryogénique", 7}, {"Laser Prisme", 7}, {"Marteau de Glace", 7}, {"Myria-Flèches", 6}, {"Myria-Vagues", 6}, {"Neuf pour Un", 7}, {"Os Ombre", 7}, {"Patati-Patattrape", 7}, {"Permuvitesse", 7}, {"Pétalexplosion Éblouissante", 7}, {"Photo-Geyser", 7}, {"Pika-Fracas", 7}, {"Pika-Piqué", 7}, {"Pika-Splash", 7}, {"Pika-Sprint", 7}, {"Pikachute Foudroyante", 7}, {"Piqué Supersonique", 7}, {"Plasma Punch", 7}, {"Projecteur", 7}, {"Psycho-Croc", 7}, {"Psycho-Pulvérisation EX", 7}, {"Purification", 7}, {"Pyro-Explosion Cataclysmique", 7}, {"Rayon Spectral", 7}, {"Rayons Séléno-Explosifs", 7}, {"Sanction Suprême", 7}, {"Soin Floral", 7}, {"Sommation", 7}, {"Super Tourbillon Abyssal", 7}, {"Supernova Originelle", 7}, {"Symphonie des Ondines", 7}, {"Tisse Ombre", 7}, {"Trépignement", 7}, {"Trou Noir des Ombres", 7}, {"Turbo-Charge Bulldozer", 7}, {"Vibrécaille", 7}, {"Vif Roc", 7}, {"Voile Aurore", 7}, {"Vole-Force", 7}, {"Vrille Maximum", 7},
                {"Abattage", 8}, {"Acide Malique", 8}, {"Aegis Maxima", 8}, {"Aéromax", 8}, {"Ailes Psycho", 8}, {"Aquatacle", 8}, {"Assaut Frontal", 8}, {"Ballon Brûlant", 8}, {"Big Splash", 8}, {"Bise Glaciaire", 8}, {"Blocage", 8}, {"Branchicrok", 8}, {"Bulles G-Max", 8}, {"Câlin G-Max", 8}, {"Canon Dynamax", 8}, {"Canonnade G-Max", 8}, {"Cent Rancunes", 8}, {"Champlification", 8}, {"Change-Côté", 8}, {"Choc Émotionnel", 8}, {"Choc G-Max", 8}, {"Coaching", 8}, {"Cœur de Rancœur", 8}, {"Combustion G-Max", 8}, {"Corrosion G-Max", 8}, {"Cortège Funèbre", 8}, {"Coup Final G-Max", 8}, {"Coup Fulgurant", 8}, {"Croque Fort", 8}, {"Cryomax", 8}, {"Cure G-Max", 8}, {"Danse Victoire", 8}, {"Double Volée", 8}, {"Dracacophonie", 8}, {"Draco-Énergie", 8}, {"Draco-Flèches", 8}, {"Dracomax", 8}, {"Eau Revoir", 8}, {"Échange Force", 8}, {"Éclat Spectral", 8}, {"Enchantomax", 8}, {"Enlisement G-Max", 8}, {"Esprit Frappeur", 8}, {"Explo-Brume", 8}, {"Extravaillance", 8}, {"Feu Envieux", 8}, {"Fontaine de Vie", 8}, {"Fonte G-Max", 8}, {"Force G", 8}, {"Force Mystique", 8}, {"Foudre G-Max", 8}, {"Fouet G-Max", 8}, {"Fourbette", 8}, {"Fournaise G-Max", 8}, {"Frappe G-Max", 8}, {"Fulguromax", 8}, {"Fureur Ardente", 8}, {"Gâchette G-Max", 8}, {"Garde-à-Joues", 8}, {"Gardomax", 8}, {"Gaz Corrosif", 8}, {"Gladius Maximus", 8}, {"Gliss'Herbe", 8}, {"Goudronnage", 8}, {"Grand Courroux", 8}, {"Griffes Funestes", 8}, {"Hache de Pierre", 8}, {"Hantise G-Max", 8}, {"Herblast", 8}, {"Hydromax", 8}, {"Illusion G-Max", 8}, {"Insectomax", 8}, {"Joute Astrale", 8}, {"Kokiyarme", 8}, {"Lance de Glace", 8}, {"Laser Infinimax", 8}, {"Laser Météore", 8}, {"Lithomax", 8}, {"Métalaser", 8}, {"Métalliroue", 8}, {"Métallomax", 8}, {"Monte-Tension", 8}, {"Multicoup G-Max", 8}, {"Multitoxik", 8}, {"Mur Fumigène", 8}, {"Nappage", 8}, {"Nectar G-Max", 8}, {"Normalomax", 8}, {"Octoprise", 8}, {"Ondes G-Max", 8}, {"Overdrive", 8}, {"Pactole G-Max", 8}, {"Percée G-Max", 8}, {"Percussion G-Max", 8}, {"Pestilence G-Max", 8}, {"Phytomax", 8}, {"Poing Obscur", 8}, {"Poudre Magique", 8}, {"Prière Lunaire", 8}, {"Prise de Bec", 8}, {"Psychomax", 8}, {"Pugilomax", 8}, {"Pyroball G-Max", 8}, {"Pyromax", 8}, {"Rafale Écailles", 8}, {"Rafale G-Max", 8}, {"Ravage Rampant", 8}, {"Récif G-Max", 8}, {"Récolte G-Max", 8}, {"Regard Glaçant", 8}, {"Résonance G-Max", 8}, {"Roue Libre", 8}, {"Sable Ardent", 8}, {"Selve Salvatrice", 8}, {"Sentence G-Max", 8}, {"Sinistromax", 8}, {"Sismomax", 8}, {"Sort Sinistre", 8}, {"Spectromax", 8}, {"Sprint Bouclier", 8}, {"Tambour Battant", 8}, {"Tapotige", 8}, {"Téphra G-Max", 8}, {"Thérémonie", 8}, {"Tir de Précision", 8}, {"Torpeur G-Max", 8}, {"Torrent de Coups", 8}, {"Toxinomax", 8}, {"Triple Axel", 8}, {"Triple Flèche", 8}, {"Troquenard", 8}, {"Typhon Fulgurant", 8}, {"Typhon Hivernal", 8}, {"Typhon Passionné", 8}, {"Typhon Pyrosable", 8}, {"Ultime Bastion", 8}, {"Usure G-Max", 8}, {"Vagues à Lames", 8}, {"Vapeur Féérique", 8}, {"Vaste Pouvoir", 8}, {"Voltageôle", 8},
                {"Bombe au Sirop", 9}, {"Bond", 9}, {"Canon Blindé", 9}, {"Cataclysme", 9}, {"Chaîne Malsaine", 9}, {"Chant Flamboyant", 9}, {"Charge Glaive", 9}, {"Chute de Neige", 9}, {"Crash Brûlant", 9}, {"Crash Magique", 9}, {"Crash Musclé", 9}, {"Crash Obscur", 9}, {"Crash Toxique", 9}, {"Cri Draconique", 9}, {"Cryo-Pirouette", 9}, {"Danse Aquatique", 9}, {"Désherbaffe", 9}, {"Dissonance Psy", 9}, {"Double Décharge", 9}, {"Double Laser", 9}, {"Douche Froide", 9}, {"Décalquage", 9}, {"Décharnement", 9}, {"Dérapage", 9}, {"Fulgurayon", 9}, {"Génusection", 9}, {"Grand Nettoyage", 9}, {"Habanerage", 9}, {"Hommage Posthume", 9}, {"Hydrovapeur", 9}, {"Hyperceuse", 9}, {"Indignition", 9}, {"Lame en Peine", 9}, {"Lame Psychique", 9}, {"Lame Puissante", 9}, {"Lame Tachyonique", 9}, {"Laser Hasard", 9}, {"Lumino-Impact", 9}, {"Lune Rouge", 9}, {"Magie Florale", 9}, {"Marteau Mastoc", 9}, {"Massue Liane", 9}, {"Mortier Matcha", 9}, {"Neigeux de Mots", 9}, {"Nitro Crash", 9}, {"Piège de Fil", 9}, {"Plat du Jour", 9}, {"Pluie Térastrale", 9}, {"Poing de Colère", 9}, {"Poing Sonique", 9}, {"Pression Extrême", 9}, {"Prio-Parade", 9}, {"Prolifération", 9}, {"Queulonage", 9}, {"Rempart Brûlant", 9}, {"Ruée d'Or", 9}, {"Salaison", 9}, {"Second Souffle", 9}, {"Talon-Marteau", 9}, {"Taurogne", 9}, {"Téra Explosion", 9}, {"Toupie Éclat", 9}, {"Tranch'Aqua", 9}, {"Triple Plongeon", 9}, {"Turbo Volt", 9}, {"Vif Éclair", 9}, {"Vindicte", 9}, {"Voix Envoûtante", 9}, {"Volt Assaut", 9}
        };

        for (int i = 0; i < temporaryDict.length; i++) {
            if (attackName.equals(temporaryDict[i][0])) {
                return (int) temporaryDict[i][1];
            }
        }

        System.err.println("Génération non trouvée pour la capacité " + attackName + ".");
        return 0;
    }

    // Returns a list of attacks absent from a specific pair of games
    static String[] getUnavailableAttacks (String games) {
        switch (games) {
            case ("SL"):
                return new String[] {"Écrous d'Poing", "Évo-Chardasso", "Évo-Congélo", "Évo-Dynamo", "Évo-Écolo", "Évo-Fabulo", "Évo-Flambo", "Évo-Psycho", "Évo-Ténébro", "Évo-Thalasso", "Pika-Fracas", "Pika-Piqué", "Pika-Splash", "Pika-Sprint", "Apocalypsis Luminis", "Caboche-Kaboum", "Dracacophonie Flamboyante", "Hélio-Choc Dévastateur", "Hurlement des Roches-Lames", "Patati-Patattrape", "Photo-Geyser", "Plasma Punch", "Rayons Séléno-Explosifs"};
            case ("USUL"):
                return new String[] {"Écrous d'Poing", "Évo-Chardasso", "Évo-Congélo", "Évo-Dynamo", "Évo-Écolo", "Évo-Fabulo", "Évo-Flambo", "Évo-Psycho", "Évo-Ténébro", "Évo-Thalasso", "Pika-Fracas", "Pika-Piqué", "Pika-Splash", "Pika-Sprint"};
            case ("LGPE"):
                return new String[] {"À la Queue", "Aboiement", "Acrobatie", "Acupression", "Aéroblast", "Aéropique", "Affilage", "Aiguisage", "Ailes d'Acier", "Air Veinard", "Aire d'Eau", "Aire d'Herbe", "Aire de Feu", "Allègement", "Amass'Sable", "Ampleur", "Ancrage", "Anneau Hydro", "Anti-Air", "Anti-Brume", "Anti-Soin", "Apocalypse Gigalithique", "Apocalypsis Luminis", "Appel Attaque", "Appel Défense", "Appel des Ombres Éternelles", "Appel Soins", "Après Vous", "Aqua-Brèche", "Aria de l'Écume", "Aromathérapie", "Arrogance", "Assistance", "Assurance", "Astuce Force", "Atout", "Attraction", "Attrition", "Aurasphère", "Aurore", "Avalanche", "Avale", "Babil", "Balance", "Balayette", "Ball'Brume", "Ball'Glace", "Ball'Météo", "Balle Graine", "Bang Sonique", "Barrage", "Baston", "Bec-Canon", "Blabla Dodo", "Blockhaus", "Bombe Aimant", "Bombe Acide", "Boost", "Botte Sucrette", "Bouclier Royal", "Boue-Bombe", "Boule Élek", "Boule Pollen", "Boule Roc", "Brouhaha", "Brume Capiteuse", "Bulldoboule", "Caboche-Kaboum", "Cadeau", "Calcination", "Camaraderie", "Camouflage", "Canon Floral", "Canon Graine", "Carapiège", "Cauchemar", "Cavalerie Lourde", "Célébration", "Centrifugifle", "Champ Brumeux", "Champ Électrifié", "Champ Herbu", "Champ Psychique", "Change-Éclair", "Chant Antique", "Chant Canon", "Chaos Draconique", "Charge Foudre", "Charge Os", "Chargeur", "Charme", "Châtiment", "Chatouille", "Change-Vitesse", "Choc Météore", "Choc Psy", "Choc Venin", "Chute Glace", "Chute Libre", "Clairvoyance", "Clepto-Mânes", "Close Combat", "Cocon Fatal", "Cogne", "Cognobidon", "Colère du Gardien d'Alola", "Combo Hyper-Furie", "Confidence", "Contre", "Conversion 2", "Copie-Type", "Coqui-Lame", "Corps Perdu", "Cotogarde", "Coud'Boue", "Coup d'Jus", "Coup Double", "Coup Varia-Type", "Coup Victoire", "Coup Croix", "Coupe Psycho", "Cradovague", "Crève-Cœur", "Crochet Venin", "Croco Larme", "Crocs Éclair", "Crocs Feu", "Crocs Givre", "Danse Draco", "Danse du Feu", "Danse Éveil", "Danse Pluie", "Danse Folle", "Danse Lune", "Danse Plumes", "Dard Mortel", "Dark Body Press", "Dark Lariat", "Dégommage", "Déluge Causti-Toxique", "Déluge Plasmique", "Dépit", "Dernier Mot", "Dernier Recours", "Détection", "Détrempage", "Détricanon", "Distorsion", "Don Naturel", "Double Baffe", "Doux Baiser", "Doux Parfum", "Dracacophonie Flamboyante", "Draco-Ascension", "Draco-Météore", "Draco-Marteau", "Draco-Charge", "Draco-Griffe", "Draco-Souffle", "Dynamo-Poing", "Ébullilave", "Échange", "Échange Psy", "Écho", "Éclair Croix", "Éclair Fou", "Éclair Gelé", "Éclate Griffe", "Éclate-Roc", "Éco-Sphère", "Effort", "Élecanon", "Électacle", "Électrikipik", "Électrisation", "Électro-Surf Survolté", "Embargo", "Encornebois", "Engrenage", "Enroulement", "Ère Glaciaire", "Éructation", "Éruption", "Éruption Géo-Sismique", "Escalade", "Escarmouche", "Essorage", "Estocorne", "Étincelle", "Étonnement", "Étrennes", "Exécu-Son", "Exploforce", "Explonuit", "Extrasenseur", "Fauche-Âme des Sept Étoiles", "Faux-Chage", "Feinte", "Fertilisation", "Feu d'Enfer", "Feu Ensorcelé", "Feu Glacé", "Feu Sacré", "Feuillage", "Feuille Magik", "Fil Toxique", "Flair", "Flamme Bleue", "Flamme Croix", "Flamme Ultime", "Flatterie", "Gigotage", "Flying Press", "Force Ajoutée", "Force Cachée", "Force Chtonienne", "Force Cosmique", "Force Nature", "Forte-Paume", "Fouet de Feu", "Fracass'Tête", "Frappe Psy", "Frotte-Frimousse", "Frustration", "Fulguro-Lance Gigavolt", "Fulmifer", "Fulmigraine", "Fureur des Plumes Spectrales", "Furie Dimension", "Furie-Bond", "Garde Florale", "Garde Large", "Gare au Ronflex", "Géo-Contrôle", "Giclédo", "Giga Impact", "Giga-Sangsue", "Giga-Tonnerre", "Glaciation", "Glas de Soin", "Gravité", "Grêle", "Gribouille", "Griffe Acier", "Griffe Ombre", "Grimace", "Grondement", "Gyroballe", "Halloween", "Hantise", "Harcèlement", "Hélio-Choc Dévastateur", "Hurle-Temps", "Hurlement des Roches-Lames", "Hydroblast", "Hydro-Queue", "Imitation", "Impact Choupinova", "Implore", "Incendie", "Interversion", "Ire de la Nature", "Jet de Vapeur", "Jugement", "Lait à Boire", "Lame de Roc", "Lame Ointe", "Lame Pangéenne", "Lame Sainte", "Lame Solaire", "Lame Feuille", "Lance-Boue", "Lancécrou", "Larcin", "Larme à l'Œil", "Laser Cryogénique", "Laser Prisme", "Lévikinésie", "Lire-Esprit", "Lumi-Éclat", "Lumière du Néant", "Lumi-Queue", "Lyophilisation", "Mach Punch", "Magné-Contrôle", "Mains Jointes", "Malédiction", "Maléfice Sylvain", "Marteau de Glace", "Marto-Poing", "Martobois", "Mégaphone", "Miroi-Tir", "Mitra-Poing", "Moi d'Abord", "Mort'Ailes", "Mur de Fer", "Myria-Flèches", "Myria-Vagues", "Neuf pour Un", "Nitrocharge", "Nœud Herbe", "Nuée de Poudre", "Ocroupi", "Octazooka", "Œil Miracle", "Ombre Portée", "Onde de Choc", "Onde Originelle", "Onde Vide", "Ondes Étranges", "Orage Adamantin", "Os Ombre", "Ouragan", "Par Ici", "Parabocharge", "Paresse", "Partage Force", "Partage Garde", "Passe-Cadeau", "Passe-Passe", "Patati-Patattrape", "Peignée", "Permucœur", "Permuforce", "Permugarde", "Permuvitesse", "Pétalexplosion Éblouissante", "Photo-Geyser", "Photocopie", "Phytomixeur", "Pico-Défense", "Picore", "Picots", "Pics Toxik", "Pied Brûleur", "Piège de Venin", "Piétisol", "Pikachute Foudroyante", "Piqué Supersonique", "Piqûre", "Pisto-Poing", "Plasma Punch", "Plongée", "Plumo-Queue", "Poing Boost", "Poing Dard", "Poing Météore", "Poing Ombre", "Poison Croix", "Poliroche", "Possessif", "Poudre Fureur", "Poudreuse", "Poursuite", "Pouvoir Antique", "Lien du Destin", "Prescience", "Presse", "Prévention", "Projecteur", "Projection", "Psycho-Boost", "Psycho-Croc", "Psycho-Pulvérisation EX", "Psykoud'Boul", "Puissance Cachée", "Punition", "Purification", "Pyro-Explosion Cataclysmique", "Queue-Poison", "Racines", "Rafale Feu", "Râle Mâle", "Rancune", "Rapace", "Rayon Chargé", "Rayon Gemme", "Rayon Lune", "Rayon Signal", "Rayon Simple", "Rayon Spectral", "Rayons Séléno-Explosifs", "Rebond", "Rebondifeu", "Recyclage", "Reflet Magik", "Regard Noir", "Regard Touchant", "Régénération", "Relâche", "Relais", "Rengorgement", "Renversement", "Représailles", "Requiem", "Retenue", "Retour", "Réveil Forcé", "Revenant", "Roc-Boulet", "Ronflement", "Roue de Feu", "Roulade", "Rune Protect", "Sabotage", "Saisie", "Sanction Suprême", "Saumure", "Séduction", "Sheauriken", "Siffl'Herbe", "Siphon", "Soin Floral", "Sommation", "Soucigraine", "Souffle Glacé", "Souvenir", "Spatio-Rift", "Spore Coton", "Stalactite", "Stimulant", "Stockage", "Stratopercut", "Strido-Son", "Suc Digestif", "Super Tourbillon Abyssal", "Supernova Originelle", "Surchauffe", "Survinsecte", "Symphonie des Ondines", "Synchropeine", "Synthèse", "Tacle Feu", "Tacle Lourd", "Taillade", "Tatamigaeshi", "Techno-Buster", "Telluriforce", "Tempête de Sable", "Tempête Florale", "Tempête Verte", "Ten-Danse", "Ténacité", "Tête de Fer", "Tir de Boue", "Tisse Ombre", "Toile", "Toile Élek", "Toile Gluante", "Tomberoche", "Tour Rapide", "Tourbi-Sable", "Tour de Magie", "Tourmente", "Tourniquet", "Tout ou Rien", "Tranch'Air", "Tranche-Nuit", "Trépignement", "Triple Pied", "Trou Noir", "Trou Noir des Ombres", "TrouDimensionnel", "Turbo-Charge Bulldozer", "Vampibaiser", "Vampi-Poing", "Vantardise", "Végé-Attaque", "Vendetta", "Vengeance", "Vent Argenté", "Vent Arrière", "Vent Féérique", "Vent Glace", "Vent Mauvais", "Vent Violent", "Verrou Enchanté", "Verrouillage", "Vibra Soin", "Vibraqua", "Vibrécaille", "Vif Roc", "Vigilance", "Vitesse Extrême", "Vœu", "Vœu Destructeur", "Vœu Soin", "Voile Aurore", "Voix Enjôleuse", "Vol Magnétik", "Vole-Force", "Vortex Magma", "Vrille Maximum", "Yama Arashi", "Zénith", "Zone Étrange", "Zone Magique"};
            case ("EB"):
                return new String[] {"Affûtage", "Ailes Psycho", "Air Veinard", "Ampleur", "Anti-Soin", "Apocalypse Gigalithique", "Apocalypsis Luminis", "Appel des Ombres Éternelles", "Appel Soins", "Aquatacle", "Assaut Frontal", "Assistance", "Atout", "Attrition", "Babil", "Ball'Glace", "Bec-Canon", "Bise Glaciaire", "Bombe Aimant", "Bombe Œuf", "Bouclier", "Boue-Bombe", "Bulldoboule", "Camouflage", "Cauchemar", "Chant Antique", "Chaos Draconique", "Chute Libre", "Clairvoyance", "Claquoir", "Cocon Fatal", "Cœur de Rancœur", "Colère du Gardien d'Alola", "Combo Hyper-Furie", "Constriction", "Cortège Funèbre", "Coupe-Vent", "Crève-Cœur", "Croc de Mort", "Danse Éveil", "Danse Victoire", "Dark Body Press", "Déluge Causti-Toxique", "Déluge Plasmique", "Don Naturel", "Double Dard", "Dracacophonie Flamboyante", "Draco-Rage", "Échange Force", "Écume", "Électro-Surf Survolté", "Embargo", "Éruption Géo-Sismique", "Escalade", "Essorage", "Évo-Chardasso", "Évo-Congélo", "Évo-Dynamo", "Évo-Écolo", "Évo-Fabulo", "Évo-Flambo", "Évo-Psycho", "Évo-Ténébro", "Évo-Thalasso", "Extravaillance", "Fauche-Âme des Sept Étoiles", "Feinte", "Fertilisation", "Fil Toxique", "Flair", "Flash", "Force Cachée", "Force Mystique", "Frénésie", "Frustration", "Fulguro-Lance Gigavolt", "Fulmigraine", "Fureur des Plumes Spectrales", "Furie Dimension", "Gare au Ronflex", "Giga-Tonnerre", "Grand Courroux", "Gribouille", "Griffes Funestes", "Hache de Pierre", "Hélio-Choc Dévastateur", "Herblast", "Hurlement des Roches-Lames", "Impact Choupinova", "Jugement", "Lance-Boue", "Laser Cryogénique", "Lévikinésie", "Lumi-Queue", "Lumière du Néant", "Marteau de Glace", "Massd'Os", "Mawashi Geri", "Mimique", "Miroi-Tir", "Moi d'Abord", "Multitoxik", "Mur Fumigène", "Neuf pour Un", "Nuée de Poudre", "Œil Miracle", "Passe-Cadeau", "Patati-Patattrape", "Patience", "Permucœur", "Pétalexplosion Éblouissante", "Picanon", "Pied Sauté", "Pika-Fracas", "Pika-Piqué", "Pika-Splash", "Pika-Sprint", "Pikachute Foudroyante", "Pilonnage", "Piqué Supersonique", "Poing Comète", "Poing Dard", "Poing Karaté", "Poursuite", "Prière Lunaire", "Projecteur", "Psycho-Boost", "Psycho-Pulvérisation EX", "Puissance Cachée", "Punition", "Pyro-Explosion Cataclysmique", "Rayon Signal", "Rayons Séléno-Explosifs", "Rebondifeu", "Régénération", "Retour", "Réveil Forcé", "Saisie", "Séduction", "Siffl'Herbe", "Sonic Boom", "Sprint Bouclier", "Stimulant", "Stratopercut", "Super Tourbillon Abyssal", "Supernova Originelle", "Symphonie des Ondines", "Synchropeine", "Toile", "Torgnoles", "Tourniquet", "Triple Flèche", "Trou Noir", "Trou Noir des Ombres", "TrouDimensionnel", "Turbo-Charge Bulldozer", "Typhon Fulgurant", "Typhon Hivernal", "Typhon Passionné", "Typhon Pyrosable", "Uppercut", "Vague Psy", "Vagues à Lames", "Vent Argenté", "Vent Mauvais", "Vrille Maximum", "Yoga"};
            case ("DEPS"):
                return new String[] {"Abattage", "Acide Malique", "Aegis Maxima", "Aéromax", "Affûtage", "Ailes Psycho", "Air Veinard", "Aire d'Eau", "Aire d'Herbe", "Aire de Feu", "Amass'Sable", "Ampleur", "Ancrage", "Anti-Soin", "Apocalypse Gigalithique", "Apocalypsis Luminis", "Appel des Ombres Éternelles", "Appel Soins", "Aquatacle", "Aria de l'Écume", "Arrogance", "Assaut Frontal", "Assistance", "Atout", "Attrition", "Ball'Glace", "Ballon Brûlant", "Bec-Canon", "Bise Glaciaire", "Blocage", "Blockhaus", "Bombe Aimant", "Bombe Œuf", "Botte Sucrette", "Bouclier", "Bouclier Royal", "Boue-Bombe", "Boule Pollen", "Branchicrok", "Bulldoboule", "Bulles G-Max", "Caboche-Kaboum", "Câlin G-Max", "Camouflage", "Canon Dynamax", "Canon Floral", "Canonnade G-Max", "Carapiège", "Cauchemar", "Célébration", "Cent Rancunes", "Centrifugifle", "Champlification", "Change-Côté", "Chant Antique", "Chaos Draconique", "Charge Foudre", "Chgt Vitesse", "Choc Émotionnel", "Choc G-Max", "Choc Météore", "Chute Libre", "Clairvoyance", "Claquoir", "Clepto-Mânes", "Coaching", "Cocon Fatal", "Cœur de Rancœur", "Colère du Gardien d'Alola", "Combo Hyper-Furie", "Combustion G-Max", "Constriction", "Corrosion G-Max", "Cortège Funèbre", "Coup Final G-Max", "Coup Fulgurant", "Coup Varia-Type", "Coup Victoire", "Coupe-Vent", "Crève-Cœur", "Croc de Mort", "Croque Fort", "Cryomax", "Cure G-Max", "Danse du Feu", "Danse Éveil", "Danse Victoire", "Dark Body Press", "Dark Lariat", "Déluge Causti-Toxique", "Déluge Plasmique", "Don Naturel", "Double Dard", "Double Volée", "Dracacophonie", "Dracacophonie Flamboyante", "Draco-Énergie", "Draco-Flèches", "Draco-Rage", "Dracomax", "Eau Revoir", "Échange Force", "Éclair Croix", "Éclair Gelé", "Éclat Spectral", "Écrous d'Poing", "Écume", "Électrikipik", "Électrisation", "Électro-Surf Survolté", "Embargo", "Enchantomax", "Encornebois", "Engrenage", "Enlisement G-Max", "Ère Glaciaire", "Éruption Géo-Sismique", "Escarmouche", "Esprit Frappeur", "Essorage", "Étrennes", "Évo-Chardasso", "Évo-Congélo", "Évo-Dynamo", "Évo-Écolo", "Évo-Fabulo", "Évo-Flambo", "Évo-Psycho", "Évo-Ténébro", "Évo-Thalasso", "Explo-Brume", "Explonuit", "Extravaillance", "Fauche-Âme des Sept Étoiles", "Feinte", "Fertilisation", "Feu Envieux", "Feu Glacé", "Flair", "Flamme Bleue", "Flamme Croix", "Flying Press", "Fonte G-Max", "Force Cachée", "Force Chtonienne", "Force G", "Force Mystique", "Foudre G-Max", "Fouet de Feu", "Fouet G-Max", "Fourbette", "Fournaise G-Max", "Frappe G-Max", "Frénésie", "Frustration", "Fulguro-Lance Gigavolt", "Fulguromax", "Fureur Ardente", "Fureur des Plumes Spectrales", "Furie Dimension", "Gâchette G-Max", "Garde-à-Joues", "Gardomax", "Gare au Ronflex", "Gaz Corrosif", "Géo-Contrôle", "Giga-Tonnerre", "Gladius Maximus", "Gliss'Herbe", "Goudronnage", "Grand Courroux", "Griffes Funestes", "Hache de Pierre", "Halloween", "Hantise G-Max", "Hélio-Choc Dévastateur", "Herblast", "Hurlement des Roches-Lames", "Hydromax", "Illusion G-Max", "Impact Choupinova", "Incendie", "Insectomax", "Ire de la Nature", "Jet de Vapeur", "Joute Astrale", "Kokiyarme", "Lame Ointe", "Lame Sainte", "Lame Solaire", "Lance de Glace", "Lance-Boue", "Lancécrou", "Laser Cryogénique", "Laser Infinimax", "Laser Météore", "Laser Prisme", "Lévikinésie", "Lithomax", "Lumière du Néant", "Mains Jointes", "Maléfice Sylvain", "Marteau de Glace", "Massd'Os", "Mawashi Geri", "Métalaser", "Métalliroue", "Métallomax", "Mimique", "Miroi-Tir", "Moi d'Abord", "Monte-Tension", "Mort'Ailes", "Multicoup G-Max", "Multitoxik", "Mur Fumigène", "Myria-Flèches", "Myria-Vagues", "Nappage", "Nectar G-Max", "Neuf pour Un", "Normalomax", "Nuée de Poudre", "Octoprise", "Œil Miracle", "Ondes G-Max", "Orage Adamantin", "Os Ombre", "Overdrive", "Pactole G-Max", "Parabocharge", "Passe-Cadeau", "Patati-Patattrape", "Patience", "Peignée", "Percée G-Max", "Percussion G-Max", "Permuvitesse", "Pestilence G-Max", "Pétalexplosion Éblouissante", "Photo-Geyser", "Phytomax", "Picanon", "Pied Sauté", "Pika-Fracas", "Pika-Piqué", "Pika-Splash", "Pika-Sprint", "Pikachute Foudroyante", "Pilonnage", "Piqué Supersonique", "Plasma Punch", "Plumo-Queue", "Poing Comète", "Poing Dard", "Poing Karaté", "Poing Obscur", "Poudre Magique", "Poursuite", "Prière Lunaire", "Prise de Bec", "Projecteur", "Psycho-Croc", "Psycho-Pulvérisation EX", "Psychomax", "Pugilomax", "Punition", "Purification", "Pyro-Explosion Cataclysmique", "Pyroball G-Max", "Pyromax", "Rafale Écailles", "Rafale G-Max", "Ravage Rampant", "Rayon Signal", "Rayon Spectral", "Rayons Séléno-Explosifs", "Rebondifeu", "Récif G-Max", "Récolte G-Max", "Regard Glaçant", "Régénération", "Renversement", "Résonance G-Max", "Retenue", "Retour", "Réveil Forcé", "Roue Libre", "Sable Ardent", "Saisie", "Sanction Suprême", "Séduction", "Selve Salvatrice", "Sentence G-Max", "Sheauriken", "Siffl'Herbe", "Sinistromax", "Sismomax", "Soin Floral", "Sommation", "Sonic Boom", "Sort Sinistre", "Spectromax", "Sprint Bouclier", "Stimulant", "Stratopercut", "Super Tourbillon Abyssal", "Supernova Originelle", "Symphonie des Ondines", "Synchropeine", "Tacle Feu", "Tambour Battant", "Tapotige", "Tatamigaeshi", "Techno-Buster", "Téphra G-Max", "Thérémonie", "Tir de Précision", "Tisse Ombre", "Toile", "Toile Élek", "Torgnoles", "Torpeur G-Max", "Torrent de Coups", "Tourniquet", "Toxinomax", "Triple Axel", "Triple Flèche", "Troquenard", "Trou Noir des Ombres", "TrouDimensionnel", "Turbo-Charge Bulldozer", "Typhon Fulgurant", "Typhon Hivernal", "Typhon Passionné", "Typhon Pyrosable", "Ultime Bastion", "Uppercut", "Usure G-Max", "Vague Psy", "Vagues à Lames", "Vapeur Féérique", "Vaste Pouvoir", "Vent Argenté", "Vent Mauvais", "Verrou Enchanté", "Vibrécaille", "Vif Roc", "Vigilance", "Voltageôle", "Vrille Maximum", "Yoga"};
            case ("LPA"):
                return new String[] {"À la Queue", "Abattage", "Abîme", "Abri", "Acide", "Acide Malique", "Acrobatie", "Acupression", "Aegis Maxima", "Aéroblast", "Aéromax", "Affilage", "Affûtage", "Aiguisage", "Ailes d'Acier", "Air Veinard", "Aire d'Eau", "Aire d'Herbe", "Aire de Feu", "Allègement", "Amass'Sable", "Amnésie", "Ampleur", "Ancrage", "Anneau Hydro", "Anti-Air", "Anti-Brume", "Anti-Soin", "Apocalypse Gigalithique", "Apocalypsis Luminis", "Appel Attaque", "Appel Défense", "Appel des Ombres Éternelles", "Appel Soins", "Après Vous", "Aria de l'Écume", "Armure", "Aromathérapie", "Arrogance", "Assistance", "Assurance", "Astuce Force", "Atout", "Attraction", "Attrition", "Aurore", "Avalanche", "Avale", "Babil", "Bâillement", "Bain de Smog", "Balance", "Balayage", "Balayette", "Ball'Brume", "Ball'Météo", "Balle Graine", "Ballon Brûlant", "Bang Sonique", "Barrage", "Baston", "Bec Vrille", "Bec-Canon", "Bélier", "Berceuse", "Big Splash", "Blabla Dodo", "Blocage", "Blockhaus", "Bluff", "Bombe Aimant", "Bombe Œuf", "Boost", "Botte Sucrette", "Bouclier", "Bouclier Royal", "Boul'Armure", "Boule Élek", "Boule Pollen", "Boule Roc", "Branchicrok", "Brouhaha", "Brouillard", "Brume", "Brume Capiteuse", "Buée Noire", "Bulldoboule", "Bulles d'O", "Bulles G-Max", "Caboche-Kaboum", "Cadeau", "Calcination", "Câlin G-Max", "Camaraderie", "Camouflage", "Canicule", "Canon Dynamax", "Canon Floral", "Canon Graine", "Canonnade G-Max", "Carapiège", "Cascade", "Casse-Brique", "Cauchemar", "Célébration", "Cent Rancunes", "Centrifugifle", "Champ Brumeux", "Champ Électrifié", "Champ Herbu", "Champ Psychique", "Champlification", "Change-Éclair", "Change-Côté", "Chant Antique", "Chant Canon", "Chaos Draconique", "Charge Foudre", "Charge Os", "Chargeur", "Charme", "Chatouille", "Change-Vitesse", "Choc Émotionnel", "Choc G-Max", "Choc Météore", "Choc Psy", "Chute Libre", "Clairvoyance", "Claquoir", "Clepto-Mânes", "Clonage", "Coaching", "Cocon Fatal", "Cogne", "Cognobidon", "Colère du Gardien d'Alola", "Combo Hyper-Furie", "Combo-Griffe", "Combustion G-Max", "Confidence", "Constriction", "Contre", "Conversion", "Conversion 2", "Copie-Type", "Coqui-Lame", "Corps Perdu", "Corrosion G-Max", "Cotogarde", "Coud'Krâne", "Coup Bas", "Coup Croix", "Coup d'Boule", "Coup d'Jus", "Coup d'Main", "Coup Final G-Max", "Coup Fulgurant", "Coup Varia-Type", "Coup Victoire", "Coupe", "Coupe-Vent", "Cradovague", "Crève-Cœur", "Croc de Mort", "Croc Fatal", "Crochet Venin", "Croco Larme", "Croissance", "Croque Fort", "Cru-Ailes", "Cryomax", "Cure G-Max", "Cyclone", "Danse Draco", "Danse du Feu", "Danse Éveil", "Danse Flammes", "Danse Folle", "Danse Lune", "Danse Pluie", "Danse Plumes", "Dard Mortel", "Dark Body Press", "Dark Lariat", "Dégommage", "Déluge Causti-Toxique", "Déluge Plasmique", "Demi-Tour", "Dépit", "Dernier Mot", "Dernier Recours", "Détection", "Détrempage", "Détricanon", "Détritus", "Dévorêve", "Distorsion", "Don Naturel", "Double Baffe", "Double Dard", "Double Pied", "Double Volée", "Doux Baiser", "Doux Parfum", "Dracacophonie", "Dracacophonie Flamboyante", "Draco-Ascension", "Draco-Charge", "Draco-Énergie", "Draco-Flèches", "Draco-Marteau", "Draco-Queue", "Draco-Rage", "Draco-Souffle", "Dracomax", "Dynamo-Poing", "Eau Revoir", "Ébullilave", "Ébullition", "Échange", "Échange Psy", "Écho", "Éclair Croix", "Éclair Gelé", "Éclat Spectral", "Éclate Griffe", "Écras'Face", "Écrasement", "Écrous d'Poing", "Effort", "Élecanon", "Électrikipik", "Électrisation", "Électro-Surf Survolté", "Embargo", "Empal'Korne", "Enchantomax", "Encore", "Encornebois", "Engrenage", "Enlisement G-Max", "Enroulement", "Entrave", "Ère Glaciaire", "Éructation", "Éruption", "Éruption Géo-Sismique", "Escalade", "Escarmouche", "Esprit Frappeur", "Essorage", "Estocorne", "Étreinte", "Étrennes", "Évo-Chardasso", "Évo-Congélo", "Évo-Dynamo", "Évo-Écolo", "Évo-Fabulo", "Évo-Flambo", "Évo-Psycho", "Évo-Ténébro", "Évo-Thalasso", "Exécu-Son", "Explo-Brume", "Exploforce", "Explonuit", "Explosion", "Exuviation", "Façade", "Fauche-Âme des Sept Étoiles", "Feinte", "Fertilisation", "Feu d'Enfer", "Feu Envieux", "Feu Follet", "Feu Glacé", "Feu Sacré", "Fil Toxique", "Flair", "Flamme Bleue", "Flamme Croix", "Flamme Ultime", "Flash", "Flatterie", "Flying Press", "Fontaine de Vie", "Fonte G-Max", "Force", "Force Ajoutée", "Force Cachée", "Force Chtonienne", "Force Cosmique", "Force G", "Force Nature", "Force Poigne", "Forte-Paume", "Foudre G-Max", "Fouet de Feu", "Fouet G-Max", "Fouet Lianes", "Fourbette", "Fournaise G-Max", "Frappe Atlas", "Frappe G-Max", "Frappe Psy", "Frénésie", "Frotte-Frimousse", "Frustration", "Fulguro-Lance Gigavolt", "Fulguromax", "Fulmifer", "Fureur Ardente", "Fureur des Plumes Spectrales", "Furie", "Furie Dimension", "Furie-Bond", "Gâchette G-Max", "Garde Florale", "Garde Large", "Garde-à-Joues", "Gardomax", "Gare au Ronflex", "Gaz Corrosif", "Géo-Contrôle", "Giclédo", "Giga-Sangsue", "Giga-Tonnerre", "Gigotage", "Glaciation", "Gladius Maximus", "Glas de Soin", "Gliss'Herbe", "Goudronnage", "Gravité", "Grêle", "Gribouille", "Griffe", "Griffe Acier", "Grimace", "Grincement", "Grobisou", "Grondement", "Groz'Yeux", "Guillotine", "Gyroballe", "Halloween", "Hantise", "Hantise G-Max", "Harcèlement", "Hâte", "Hélio-Choc Dévastateur", "Hurlement", "Hurlement des Roches-Lames", "Hydroblast", "Hydromax", "Illusion G-Max", "Imitation", "Impact Choupinova", "Implore", "Incendie", "Insectomax", "Interversion", "Ire de la Nature", "Jackpot", "Jet de Sable", "Jet de Vapeur", "Jet-Pierres", "Joute Astrale", "Kokiyarme", "Koud'Korne", "Lait à Boire", "Lame Ointe", "Lame Pangéenne", "Lame Sainte", "Lame Solaire", "Lance de Glace", "Lance-Boue", "Lance-Soleil", "Lancécrou", "Larcin", "Larme à l'Œil", "Laser Cryogénique", "Laser Infinimax", "Laser Météore", "Laser Prisme", "Léchouille", "Lévikinésie", "Lien du Destin", "Ligotage", "Lilliput", "Lire-Esprit", "Lithomax", "Lumi-Éclat", "Lumi-Queue", "Lumière du Néant", "Lyophilisation", "Magné-Contrôle", "Mains Jointes", "Malédiction", "Maléfice Sylvain", "Mania", "Marteau de Glace", "Marto-Poing", "Massd'Os", "Mawashi Geri", "Méga-Sangsue", "Mégafouet", "Mégaphone", "Métalliroue", "Métallomax", "Métronome", "Mimi-Queue", "Mimique", "Miroi-Tir", "Mitra-Poing", "Moi d'Abord", "Monte-Tension", "Morphing", "Mort'Ailes", "Multicoup G-Max", "Mur Lumière", "Myria-Flèches", "Myria-Vagues", "Nappage", "Nectar G-Max", "Neuf pour Un", "Nitrocharge", "Nœud Herbe", "Normalomax", "Nuée de Poudre", "Ocroupi", "Octoprise", "Œil Miracle", "Ombre Nocturne", "Onde Boréale", "Onde de Choc", "Onde Folie", "Onde Originelle", "Onde Vide", "Ondes Étranges", "Ondes G-Max", "Orage Adamantin", "Os Ombre", "Osmerang", "Overdrive", "Pactole G-Max", "Papillodanse", "Par Ici", "Parabocharge", "Paresse", "Partage Force", "Partage Garde", "Passe-Cadeau", "Passe-Passe", "Patati-Patattrape", "Patience", "Peignée", "Percée G-Max", "Percussion G-Max", "Permucœur", "Permuforce", "Permugarde", "Permuvitesse", "Pestilence G-Max", "Pétalexplosion Éblouissante", "Photo-Geyser", "Photocopie", "Phytomax", "Phytomixeur", "Picanon", "Pico-Défense", "Picore", "Picpic", "Pics Toxik", "Pied Brûleur", "Pied Sauté", "Pied Voltige", "Piège de Venin", "Pika-Fracas", "Pika-Piqué", "Pika-Splash", "Pika-Sprint", "Pikachute Foudroyante", "Pilonnage", "Pince-Masse", "Piqué", "Piqué Supersonique", "Piqûre", "Pistolet à O", "Plaquage", "Plasma Punch", "Plongée", "Plumo-Queue", "Poing Boost", "Poing Comète", "Poing Dard", "Poing Karaté", "Poing Météore", "Poing Obscur", "Poing Ombre", "Poliroche", "Possessif", "Poudre Fureur", "Poudre Magique", "Poursuite", "Prescience", "Prévention", "Prise de Bec", "Projecteur", "Projection", "Protection", "Provoc", "Psycho-Boost", "Psycho-Croc", "Psycho-Pulvérisation EX", "Psychomax", "Pugilomax", "Punition", "Purédpois", "Purification", "Pyro-Explosion Cataclysmique", "Pyroball G-Max", "Pyromax", "Queue-Poison", "Racines", "Rafale Écailles", "Rafale Feu", "Rafale G-Max", "Rafale Psy", "Râle Mâle", "Rancune", "Ravage Rampant", "Rayon Lune", "Rayon Signal", "Rayon Simple", "Rayon Spectral", "Rayons Séléno-Explosifs", "Rebond", "Rebondifeu", "Récif G-Max", "Récolte G-Max", "Recyclage", "Reflet", "Reflet Magik", "Regard Glaçant", "Regard Médusant", "Regard Noir", "Régénération", "Relâche", "Relais", "Rengorgement", "Renversement", "Repli", "Représailles", "Requiem", "Résonance G-Max", "Retenue", "Retour", "Réveil Forcé", "Riposte", "Roc-Boulet", "Ronflement", "Roue Libre", "Rugissement", "Rune Protect", "Ruse", "Sable Ardent", "Sabotage", "Sacrifice", "Saisie", "Sanction Suprême", "Saumure", "Sécrétion", "Séduction", "Séisme", "Selve Salvatrice", "Sentence G-Max", "Sheauriken", "Siffl'Herbe", "Sinistromax", "Siphon", "Sismomax", "Soin Floral", "Sommation", "Sonic Boom", "Sort Sinistre", "Soucigraine", "Souffle Glacé", "Souplesse", "Souvenir", "Spectromax", "Spore Coton", "Stalactite", "Stimulant", "Stockage", "Stratopercut", "Strido-Son", "Suc Digestif", "Super Tourbillon Abyssal", "Supernova Originelle", "Surf", "Surpuissance", "Symphonie des Ondines", "Synchropeine", "Synthèse", "Tacle Feu", "Tacle Lourd", "Taillade", "Tambour Battant", "Tapotige", "Tatamigaeshi", "Techno-Buster", "Télékinésie", "Tempête de Sable", "Tempête Florale", "Ten-Danse", "Ténacité", "Téphra G-Max", "Thérémonie", "Tir de Boue", "Tir de Précision", "Tisse Ombre", "Toile", "Toile Élek", "Toile Gluante", "Tomberoche", "Torgnoles", "Torpeur G-Max", "Torrent de Coups", "Tour de Magie", "Tour Rapide", "Tourbi-Sable", "Tourmente", "Tourniquet", "Tout ou Rien", "Toxik", "Toxinomax", "Tranch'Herbe", "Trépignement", "Tricherie", "Triple Axel", "Triple Pied", "Troquenard", "Trou Noir des Ombres", "TrouDimensionnel", "Tunnel", "Tunnelier", "Turbo-Charge Bulldozer", "Ultimapoing", "Ultimawashi", "Ultime Bastion", "Ultrason", "Uppercut", "Usure G-Max", "Vague Psy", "Vampigraine", "Vantardise", "Vapeur Féérique", "Vaste Pouvoir", "Végé-Attaque", "Vendetta", "Vengeance", "Vent Arrière", "Verrou Enchanté", "Verrouillage", "Vibra Soin", "Vibrécaille", "Vif Roc", "Vigilance", "Vitesse Extrême", "Vœu", "Vœu Destructeur", "Vœu Soin", "Voile Aurore", "Voile Miroir", "Voix Enjôleuse", "Vol", "Vol Magnétik", "Vole-Force", "Voltageôle", "Vrille Maximum", "Yama Arashi", "Yoga", "Zénith", "Zone Étrange", "Zone Magique"};
            case ("EV"):
                return new String[] {"Aéromax", "Affilage", "Affûtage", "Air Veinard", "Allègement", "Ampleur", "Ancrage", "Anti-Soin", "Apocalypse Gigalithique", "Apocalypsis Luminis", "Appel des Ombres Éternelles", "Appel Soins", "Aromathérapie", "Assistance", "Atout", "Attrition", "Babil", "Ball'Glace", "Blocage", "Bombe Aimant", "Bombe Œuf", "Bouclier", "Bouclier Royal", "Boue-Bombe", "Branchicrok", "Bulldoboule", "Bulles G-Max", "Caboche-Kaboum", "Câlin G-Max", "Camouflage", "Canonnade G-Max", "Carapiège", "Cauchemar", "Chaos Draconique", "Choc G-Max", "Chute Libre", "Clairvoyance", "Claquoir", "Clepto-Mânes", "Cocon Fatal", "Colère du Gardien d'Alola", "Combo Hyper-Furie", "Combustion G-Max", "Constriction", "Corps Perdu", "Corrosion G-Max", "Coud'Krâne", "Coup Final G-Max", "Coup Varia-Type", "Coup Victoire", "Coupe", "Coupe-Vent", "Crève-Cœur", "Croc de Mort", "Cryomax", "Cure G-Max", "Dark Body Press", "Déluge Causti-Toxique", "Déluge Plasmique", "Don Naturel", "Double Baffe", "Double Dard", "Dracacophonie Flamboyante", "Draco-Rage", "Dracomax", "Échange Force", "Échange Psy", "Écrous d'Poing", "Écume", "Électrisation", "Électro-Surf Survolté", "Embargo", "Enchantomax", "Engrenage", "Enlisement G-Max", "Éruption Géo-Sismique", "Escalade", "Essorage", "Évo-Chardasso", "Évo-Congélo", "Évo-Dynamo", "Évo-Écolo", "Évo-Fabulo", "Évo-Flambo", "Évo-Psycho", "Évo-Ténébro", "Évo-Thalasso", "Fauche-Âme des Sept Étoiles", "Feinte", "Fertilisation", "Flair", "Flamme Ultime", "Flash", "Fonte G-Max", "Force Cachée", "Force Chtonienne", "Force Nature", "Foudre G-Max", "Fouet G-Max", "Fournaise G-Max", "Frappe G-Max", "Frénésie", "Frustration", "Fulguro-Lance Gigavolt", "Fulguromax", "Fureur des Plumes Spectrales", "Gâchette G-Max", "Garde Florale", "Gardomax", "Gare au Ronflex", "Gaz Corrosif", "Géo-Contrôle", "Giga-Tonnerre", "Grêle", "Grobisou", "Halloween", "Hantise G-Max", "Hélio-Choc Dévastateur", "Hurlement des Roches-Lames", "Hydromax", "Illusion G-Max", "Impact Choupinova", "Incendie", "Insectomax", "Ire de la Nature", "Joute Astrale", "Lance-Boue", "Lancécrou", "Laser Cryogénique", "Laser Infinimax", "Lévikinésie", "Lire-Esprit", "Lithomax", "Lumière du Néant", "Mains Jointes", "Massd'Os", "Mawashi Geri", "Métallomax", "Mimique", "Miroi-Tir", "Moi d'Abord", "Mort'Ailes", "Multicoup G-Max", "Myria-Flèches", "Myria-Vagues", "Nectar G-Max", "Neuf pour Un", "Normalomax", "Nuée de Poudre", "Octazooka", "Octoprise", "Œil Miracle", "Ondes G-Max", "Os Ombre", "Osmerang", "Pactole G-Max", "Passe-Cadeau", "Patati-Patattrape", "Patience", "Peignée", "Percée G-Max", "Percussion G-Max", "Pestilence G-Max", "Pétalexplosion Éblouissante", "Phytomax", "Phytomixeur", "Picanon", "Pied Sauté", "Piège de Venin", "Pika-Fracas", "Pika-Piqué", "Pika-Splash", "Pika-Sprint", "Pikachute Foudroyante", "Pilonnage", "Piqué Supersonique", "Plasma Punch", "Poing Boost", "Poing Comète", "Poing Dard", "Poing Karaté", "Poursuite", "Prise de Bec", "Projecteur", "Psycho-Pulvérisation EX", "Psychomax", "Pugilomax", "Puissance Cachée", "Punition", "Purification", "Pyro-Explosion Cataclysmique", "Pyroball G-Max", "Pyromax", "Rafale G-Max", "Rancune", "Rayon Signal", "Rayons Séléno-Explosifs", "Rebondifeu", "Récif G-Max", "Récolte G-Max", "Reflet Magik", "Régénération", "Résonance G-Max", "Retenue", "Retour", "Réveil Forcé", "Sacrifice", "Saisie", "Sanction Suprême", "Séduction", "Sentence G-Max", "Siffl'Herbe", "Sinistromax", "Sismomax", "Sonic Boom", "Spectromax", "Stimulant", "Stratopercut", "Super Tourbillon Abyssal", "Supernova Originelle", "Symphonie des Ondines", "Synchropeine", "Tatamigaeshi", "Techno-Buster", "Télékinésie", "Téphra G-Max", "Toile", "Torgnoles", "Torpeur G-Max", "Tourniquet", "Toxinomax", "Troquenard", "Trou Noir des Ombres", "Turbo-Charge Bulldozer", "Uppercut", "Usure G-Max", "Vague Psy", "Vendetta", "Vent Argenté", "Vent Mauvais", "Vigilance", "Vrille Maximum", "Yama Arashi", "Yoga"};
        }
        return new String[] {};
    }

    static String[] getZAbilities () {
        return new String[] {"Apocalypse Gigalithique", "Apocalypsis Luminis", "Appel des Ombres Éternelles", "Chaos Draconique", "Cocon Fatal", "Colère du Gardien d'Alola", "Combo Hyper-Furie", "Dark Body Press", "Dracacophonie Flamboyante", "Déluge Causti-Toxique", "Électro-Surf Survolté", "Éruption Géo-Sismique", "Fauche-Âme des Sept Étoiles", "Fulguro-Lance Gigavolt", "Fureur des Plumes Spectrales", "Gare au Ronflex", "Giga-Tonnerre", "Hélio-Choc Dévastateur", "Hurlement des Roches-Lames", "Impact Choupinova", "Laser Cryogénique", "Neuf pour Un", "Patati-Patattrape", "Pétalexplosion Éblouissante", "Pikachute Foudroyante", "Piqué Supersonique", "Psycho-Pulvérisation EX", "Pyro-Explosion Cataclysmique", "Rayons Séléno-Explosifs", "Super Tourbillon Abyssal", "Supernova Originelle", "Symphonie des Ondines", "Trou Noir des Ombres", "Turbo-Charge Bulldozer", "Vrille Maximum"};
    }
    static boolean isZAbility (String ability) {
        String[] zAbilities = getZAbilities();
        for (String zAbility : zAbilities) {
            if (ability.equals(zAbility)) {
                return true;
            }
        }
        return false;
    }


    static String[] getDynamaxAbilities () {
        return new String[] {"Aéromax", "Cryomax", "Dracomax", "Enchantomax", "Fulguromax", "Gardomax", "Hydromax", "Insectomax", "Lithomax", "Métallomax", "Normalomax", "Phytomax", "Psychomax", "Pugilomax", "Pyromax", "Sinistromax", "Sismomax", "Spectromax", "Toxinomax"};
    }
    static boolean isDynamaxAbility (String ability) {
        String[] dynamaxAbilities = getDynamaxAbilities();
        for (String dynamaxAbility : dynamaxAbilities) {
            if (ability.equals(dynamaxAbility)) {
                return true;
            }
        }
        return false;
    }

    static String[] getGigantamaxAbilities () {
        return new String[] {"Bulles G-Max", "Canonnade G-Max", "Choc G-Max", "Combustion G-Max", "Corrosion G-Max", "Coup Final G-Max", "Cure G-Max", "Câlin G-Max", "Enlisement G-Max", "Fonte G-Max", "Foudre G-Max", "Fouet G-Max", "Fournaise G-Max", "Frappe G-Max", "Gâchette G-Max", "Hantise G-Max", "Illusion G-Max", "Multicoup G-Max", "Nectar G-Max", "Ondes G-Max", "Pactole G-Max", "Percussion G-Max", "Percée G-Max", "Pestilence G-Max", "Pyroball G-Max", "Rafale G-Max", "Récif G-Max", "Récolte G-Max", "Résonance G-Max", "Sentence G-Max", "Téphra G-Max", "Torpeur G-Max", "Usure G-Max"};
    }
    static boolean isGigantamaxAbility (String ability) {
        String[] gigantamaxAbilities = getGigantamaxAbilities();
        for (String gigantamaxAbility : gigantamaxAbilities) {
            if (ability.equals(gigantamaxAbility)) {
                return true;
            }
        }
        return false;
    }
}
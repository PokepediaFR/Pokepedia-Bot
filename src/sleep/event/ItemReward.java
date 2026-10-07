package sleep.event;

import utilitaire.Util;

public record ItemReward(Item item, int quantity) {
    public enum Item {
        POKE_BISCUIT("Poké Biscuit", "Poké Biscuit]]", true),
        GREAT_BISCUIT("Super Biscuit", "Super Biscuit]]", true),
        ULTRA_BISCUIT("Hyper Biscuit", "Hyper Biscuit]]", true),

        BONBON_TOUBON_S("Bonbon Toubon S", "Bonbon Toubon]] S", true),
        BONBON_TOUBON_M("Bonbon Toubon M", "Bonbon Toubon]] M", true),
        BONBON_TOUBON_L("Bonbon Toubon L", "Bonbon Toubon]] L", true),
        BONBON_TYPE_S("Bonbon de type ??? S", "Bonbon (Pokémon Sleep)#Bonbons spéciaux|Bonbon de type ??? S]] S", false),
        BONBON_TYPE_M("Bonbon de type ??? M", "Bonbon (Pokémon Sleep)#Bonbons spéciaux|Bonbon de type ??? M]] M", false),
        BONBON_TYPE_L("Bonbon de type ??? L", "Bonbon (Pokémon Sleep)#Bonbons spéciaux|Bonbon de type ??? L]] L", false),
        BONBON_POKEMON("Bonbon ???", "Bonbon (Pokémon Sleep)#Bonbon Pokémon|Bonbon ???]]", false),

        TICKET_INGREDIENTS_S("Ticket Ingrédients S", "Ticket Ingrédients]] S", true),
        TICKET_INGREDIENTS_M("Ticket Ingrédients M", "Ticket Ingrédients]] M", true),
        TICKET_INGREDIENTS_L("Ticket Ingrédients L", "Ticket Ingrédients]] L", true),

        AMAS_DE_REVE_S("Amas de Rêve S", "Amas de Rêve]] S", true),
        AMAS_DE_REVE_M("Amas de Rêve M", "Amas de Rêve]] M", true),
        AMAS_DE_REVE_L("Amas de Rêve L", "Amas de Rêve]] L", true),

        ENCENS_AMITIE("Encens Amitié", "Encens (Pokémon Sleep)|Encens Amitié]]", true),
        ENCENS_VEINE("Encens Veine", "Encens (Pokémon Sleep)|Encens Veine]]", true),
        ENCENS_CONCENTRATION("Encens Concentration", "Encens (Pokémon Sleep)|Encens Concentration]]", true),
        ENCENS_CROISSANCE("Encens Croissance", "Encens (Pokémon Sleep)|Encens Croissance]]", true),
        ENCENS_POKEMON("Encens ???", "Encens (Pokémon Sleep)|Encens ???]]", false),

        TICKET_CAMPING("Ticket de Camping", "Ticket de Camping]]", true),

        GRAINE_PRINCIPALE("Graine de Compétence Principale", "Graine de Compétence Principale]]", true),
        GRAINE_SECONDAIRE("Graine de Compétence Secondaire", "Graine de Compétence Secondaire]]", true),
        GRAINE_PRINCIPALE_POKEMON("Graine de Compétence Principale", "Graine de Compétence Principale|Graine de Compétence Principale (???)]]", true),

        FRAGMENT_DE_REVE("Fragment de Rêve", "Fragment de Rêve]]", true),
        ;

        public final String fileName;
        public final String linkName;
        public final boolean isComplete;
        Item(String fileName, String linkName, boolean isComplete) {
            this.fileName = fileName;
            this.linkName = linkName;
            this.isComplete = isComplete;
        }

        @Override
        public String toString() {
            return fileName;
        }
    }

    public String getWikiCode() {
        return "[[Fichier:Sprite %s Sleep.png|30px]] [[%s × %s".formatted(item.fileName, item.linkName, Util.numberDecomposition(quantity));
    }
}

package sleep.event.bonus;

public record ExtraFunctionalityBonus(Functionality functionality) implements Bonus {
    public enum Functionality {
        CANDY_BOOST("Boost Bonbon", "Bonbon (Pokémon Sleep)#Gain d'expérience"),
        MINI_CANDY_BOOST("Mini Boost Bonbon", "Bonbon (Pokémon Sleep)#Gain d'expérience"),
        NIGOMIX("NigoMix 3000 à Bonbons", "Bonbon (Pokémon Sleep)#NigoMix 3000 à Bonbons");

        private final String name;
        private final String link;
        Functionality(String name, String link)
        {
            this.name = name;
            this.link = link;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public String getLink()
        {
            return "[[%s|%s]]".formatted(link, name);
        }
    }

    @Override
    public String getBonusString() {
        return "le %s est disponible".formatted(functionality.getLink());
    }
}

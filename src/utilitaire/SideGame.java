/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package utilitaire;

public enum SideGame {
    SNAP("Pokémon Snap", "Snap"),
    NEW_SNAP("New Pokémon Snap", "NPSnap"),
    TCG("Pokémon Trading Card Game", "TCG"),
    TCG_2("Pokémon Trading Card Game 2", "TCG2"),
    PINBALL("Pokémon Pinball", "Pin"),
    PINBALL_RS("Pokémon Pinball: Rubis & Saphir", "PinRS"),
    STADIUM("Pokémon Stadium", "St1"),
    STADIUM_2("Pokémon Stadium 2", "St2"),
    BATTLE_REVOLUTION("Pokémon Battle Revolution", "PBR"),
    PUZZLE_LEAGUE("Pokémon Puzzle League", "PuzL"),
    PUZZLE_CHALLENGE("Pokémon Puzzle Challenge", "PuzC"),
    COLOSSEUM("Pokémon Colosseum", "Colo"),
    XD("Pokémon XD : Le Souffle des Ténèbres", "XD"),
    DASH("Pokémon Dash", "Da"),
    LINK("Pokémon Link!", "L!"),
    LINK_BATTLE("Pokémon Link: Battle!", "LB"),
    SHUFFLE("Pokémon Shuffle", "Sh"),
    CAFE("Pokémon Café ReMix", "CM"),
    PDM("Pokémon Donjon Mystère", "PDM"),
    PDMRB("Pokémon Donjon Mystère : Équipe de Secours Rouge et Bleue", "PDMRB"),
    PDMTO("Pokémon Donjon Mystère : Explorateurs du Temps et de l'Ombre", "PDMTO"),
    PDMC("Pokémon Donjon Mystère : Explorateurs du Ciel", "PDMC"),
    PDMPI("Pokémon Donjon Mystère : les portes de l'infini", "PDMPI"),
    PMDM("Pokémon Méga Donjon Mystère", "PMDM"),
    PDMDX("Pokémon Donjon Mystère : Équipe de Secours DX", "PDMDX"),
    RANGER("Pokémon Ranger", "Ra1"),
    RANGER_2("Pokémon Ranger : Nuit sur Almia", "Ra2"),
    RANGER_3("Pokémon Ranger : Sillages de Lumière", "Ra3"),
    RANCH("My Pokémon Ranch", "MPR"),
    RUMBLE("Pokémon Rumble", "PR"),
    SUPER_RUMBLE("Super Pokémon Rumble", "SPR"),
    RUMBLE_U("Pokémon Rumble U", "PRU"),
    RUMBLE_WORLD("Pokémon Rumble World", "PRW"),
    RUMBLE_RUSH("Pokémon Rumble Rush", "PRR"),
    POKEPARK("PokéPark Wii : La Grande Aventure de Pikachu", "PPk1"),
    POKEPARK_2("PokéPark 2 : Le Monde des Vœux", "PPk2"),
    CLAVIER("Apprends avec Pokémon : À la Conquête du Clavier", "APCC"),
    CONQUEST("Pokémon Conquest", "Cq"),
    RADAR("RAdar Pokémon", "RAdar"),
    PICROSS("Pokémon Picross", "Pic"),
    POKKEN("Pokkén Tournament", "Pokkén"),
    POKKENDX("Pokkén Tournament DX", "PokkénDX"),
    GO("Pokémon GO", "GO"),
    MAGICARP_JUMP("Pokémon : Magicarpe Jump", "MJ"),
    PAVILLON("Pavillon Pokémon", "Pav"),
    DETECTIVE_PIKACHU("Détective Pikachu", "DéPi"),
    DETECTIVE_PIKACHU_2("Le retour de Détective Pikachu", "DéPi2"),
    QUEST("Pokémon Quest", "Q"),
    MASTERS("Pokémon Masters EX", "PM"),
    SMILE("Pokémon Smile", "Smile"),
    UNITE("Pokémon UNITE", "UNITE"),
    SLEEP("Pokémon Sleep", "Sleep"),
    FRIENDS("Pokémon Friends", "Friends"),
    POKOPIA("Pokémon Pokopia", "Pokopia");

    public final String fullName;
    public final String acronym;

    SideGame(String fullName, String acronym) {
        this.fullName = fullName;
        this.acronym = acronym;
    }

    @Override
    public String toString() {
        return fullName;
    }
}

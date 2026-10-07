/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package sleep.event.view;

import sleep.bouffe.CookingType;
import sleep.event.bonus.*;
import sleep.pokemon.Specialites;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BonusDialog {
    public static List<Bonus> selectBonuses(Component parent) {
        List<Bonus> bonuses = new ArrayList<>();

        JPanel mainPanel = new JPanel(new GridLayout(0, 2));

        JCheckBox chbEF = new JCheckBox("Fonctionnalité bonus");
        JComboBox<ExtraFunctionalityBonus.Functionality> cmbEF = new JComboBox<>(ExtraFunctionalityBonus.Functionality.values());

        JCheckBox chbCT = new JCheckBox("Type de plat défini");
        JComboBox<CookingType> cmbCT = new JComboBox<>(CookingType.values());

        JCheckBox chbCS = new JCheckBox("Multiplicateur cuisine");
        JSpinner spnCS = new JSpinner(new SpinnerNumberModel(150,105,200,5));

        JCheckBox chbI = new JCheckBox("Ingrédient supplémentaire");
        JComboBox<Specialites> cmbI = new JComboBox<>(Specialites.values());

        JCheckBox chbCE = new JCheckBox("Énergie après cuisine");
        JSpinner spnCE = new JSpinner(new SpinnerNumberModel(5,1,20,1));

        JCheckBox chbEXP = new JCheckBox("Multiplicateur EXP");
        JSpinner spnEXP = new JSpinner(new SpinnerNumberModel(150,125,300,25));

        JCheckBox chbH = new JCheckBox("Pokémon affamé");

        JCheckBox chbSL = new JCheckBox("Niveau compétence");
        JSpinner spnSL = new JSpinner(new SpinnerNumberModel(1,1,5,1));

        JCheckBox chbST = new JCheckBox("Activation compétence");
        JSpinner spnST = new JSpinner(new SpinnerNumberModel(150,105,200,5));

        JCheckBox chbRC = new JCheckBox("Multiplicateur bonbons");
        JSpinner spnRC = new JSpinner(new SpinnerNumberModel(150,125,300,25));

        JCheckBox chbRR = new JCheckBox("Multiplicateur Pts. de Rech.");
        JSpinner spnRR = new JSpinner(new SpinnerNumberModel(150,125,300,25));

        JCheckBox chbRS = new JCheckBox("Multiplicateur Frag. de Rêve");
        JSpinner spnRS = new JSpinner(new SpinnerNumberModel(150,125,300,25));

        JCheckBox chbSTQ = new JCheckBox("Quelques Pokémon...");
        JCheckBox chbSTP = new JCheckBox("Plusieurs Pokémon...");

        JCheckBox chbS = new JCheckBox("Boost chromatique");

        mainPanel.add(chbCT);
        mainPanel.add(cmbCT);
        mainPanel.add(chbCS);
        mainPanel.add(spnCS);
        mainPanel.add(chbI);
        mainPanel.add(cmbI);
        mainPanel.add(chbCE);
        mainPanel.add(spnCE);
        mainPanel.add(chbSL);
        mainPanel.add(spnSL);
        mainPanel.add(chbST);
        mainPanel.add(spnST);
        mainPanel.add(chbEXP);
        mainPanel.add(spnEXP);
        mainPanel.add(chbRC);
        mainPanel.add(spnRC);
        mainPanel.add(chbRR);
        mainPanel.add(spnRR);
        mainPanel.add(chbRS);
        mainPanel.add(spnRS);
        mainPanel.add(chbH);
        mainPanel.add(new JLabel());
        mainPanel.add(chbSTQ);
        mainPanel.add(chbSTP);
        mainPanel.add(chbS);
        mainPanel.add(new JLabel());
        mainPanel.add(chbEF);
        mainPanel.add(cmbEF);

        int result = JOptionPane.showConfirmDialog(parent, mainPanel, "Choix des bonus", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            if (chbCT.isSelected()) bonuses.add(new CookingTypeBonus((CookingType) cmbCT.getSelectedItem()));
            if (chbCS.isSelected()) bonuses.add(new CookingStrengthBonus((Integer) spnCS.getValue() - 100));
            if (chbI.isSelected()) bonuses.add(new IngredientBonus((Specialites) cmbI.getSelectedItem()));
            if (chbCE.isSelected()) bonuses.add(new CookingEnergyBonus((Integer) spnCE.getValue()));
            if (chbSL.isSelected()) bonuses.add(new SkillLevelBonus((Integer) spnSL.getValue()));
            if (chbST.isSelected()) bonuses.add(new SkillTrigerBonus((Integer) spnST.getValue() - 100));
            if (chbEXP.isSelected()) bonuses.add(new ExpBonus((Integer) spnEXP.getValue() - 100));
            if (chbRC.isSelected()) bonuses.add(new SleepRewardBonus(SleepRewardBonus.Reward.CANDIES, (Integer) spnRC.getValue() - 100));
            if (chbRS.isSelected()) bonuses.add(new SleepRewardBonus(SleepRewardBonus.Reward.SHARDS, (Integer) spnRS.getValue() - 100));
            if (chbRR.isSelected()) bonuses.add(new SleepRewardBonus(SleepRewardBonus.Reward.RESEARCH_EXP, (Integer) spnRR.getValue() - 100));
            if (chbH.isSelected()) bonuses.add(new HungryBonus());
            if (chbSTQ.isSelected()) bonuses.add(new SleepTypeBonus(SleepTypeBonus.Quantity.QUELQUES));
            if (chbSTP.isSelected()) bonuses.add(new SleepTypeBonus(SleepTypeBonus.Quantity.PLUSIEURS));
            if (chbS.isSelected()) bonuses.add(new ShinyBonus());
            if (chbEF.isSelected()) bonuses.add(new ExtraFunctionalityBonus((ExtraFunctionalityBonus.Functionality) cmbEF.getSelectedItem()));

            return bonuses;
        }
        return null;
    }
}

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class FormCreator {

    private FormCreator(){}

    public static final int ALIGN_RIGHT = 1;
    public static final int ALIGN_LEFT = 2;

    public static final int ALIGN_CENTER = 3;

    public static Box generateField(
            Component component1,
            Component component) {
        return generateField(component1,component,200);
    }

    public static Box generateField(
            Component component1,
            Component component,
            int largeur) {


        Box champs = Box.createHorizontalBox();
        champs.add(Box.createRigidArea(new Dimension(30,20)));
        component1.setPreferredSize(new Dimension(121,30));
        champs.add(component1);
        champs.add(Box.createRigidArea(new Dimension(30,20)));
        component.setPreferredSize(new Dimension(121,30));
        champs.add(component);
        champs.add(Box.createHorizontalGlue());

        return champs;
    }

    public static Box generateRow(
            JComponent component,
            int marginTop,
            int marginRight,
            int marginBottom,
            int marginLeft,
            int alignement
    ) {
        Box conteneurVertical = Box.createVerticalBox();

        //ajout de la marge verticale en hauteur
        conteneurVertical.add(Box.createRigidArea(new Dimension(1,marginTop)));

        Box conteneurHorizontal = Box.createHorizontalBox();
        conteneurVertical.add(conteneurHorizontal);

        //ajout de la marge verticale en bas
        conteneurVertical.add(Box.createRigidArea(new Dimension(1,marginBottom)));

        //ajout de la marge horizontale à gauche
        conteneurHorizontal.add(Box.createRigidArea(new Dimension(marginLeft,1)));

        if(alignement == ALIGN_RIGHT) {
            conteneurHorizontal.add(Box.createHorizontalGlue());
        }

        conteneurHorizontal.add(component);

        //ajout de la marge horizontale à droite
        conteneurHorizontal.add(Box.createRigidArea(new Dimension(marginRight,1)));

        if(alignement == ALIGN_LEFT) {
            conteneurHorizontal.add(Box.createHorizontalGlue());
        }

        return conteneurVertical;
    }

}
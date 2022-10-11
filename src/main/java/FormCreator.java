
import javax.swing.*;
import java.awt.*;

public class FormCreator {

    private FormCreator() {
    }

    public static final int ALIGN_RIGHT = 1;
    public static final int ALIGN_LEFT = 2;


    public static Box generateField(JLabel label, Component component, int defaultMargin) {

        Box champs = Box.createHorizontalBox();

        // champs.setBorder(BorderFactory.createLineBorder(Color.RED));
        champs.add(Box.createRigidArea(new Dimension(defaultMargin, 1)));

        if (label != null) {
            label.setPreferredSize(new Dimension(121, 30));
            champs.add(label);
        }

        champs.add(Box.createRigidArea(new Dimension(defaultMargin, 1)));

        component.setMaximumSize(new Dimension(250, 30));

        champs.add(component);

        champs.add(Box.createRigidArea(new Dimension(defaultMargin, 1)));
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
        conteneurVertical.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        //ajout de la marge verticale en hauteur
        conteneurVertical.add(Box.createRigidArea(new Dimension(1, marginTop)));

        Box conteneurHorizontal = Box.createHorizontalBox();
        conteneurVertical.add(conteneurHorizontal);

        //ajout de la marge verticale en bas
        conteneurVertical.add(Box.createRigidArea(new Dimension(1, marginBottom)));

        //ajout de la marge horizontale à gauche
        conteneurHorizontal.add(Box.createRigidArea(new Dimension(marginLeft, 1)));

        if (alignement == ALIGN_RIGHT) {
            conteneurHorizontal.add(Box.createHorizontalGlue());
        }

        conteneurHorizontal.add(component);

        //ajout de la marge horizontale à droite
        conteneurHorizontal.add(Box.createRigidArea(new Dimension(marginRight, 1)));

        return conteneurVertical;
    }

    public static Box generateCol(
            JComponent component,
            int marginTop,
            int marginRight,
            int marginBottom,
            int marginLeft,
            int alignement
    ) {
        Box conteneurHorisontal = Box.createHorizontalBox();
        conteneurHorisontal.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        //ajout de la marge verticale en hauteur
        conteneurHorisontal.add(Box.createRigidArea(new Dimension(1, marginTop)));

        Box conteneurVertical1 = Box.createVerticalBox();
        conteneurHorisontal.add(conteneurVertical1);
        conteneurVertical1.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));


        //ajout de la marge verticale en bas
        conteneurHorisontal.add(Box.createRigidArea(new Dimension(1, marginBottom)));

        Box conteneurVertical2 = Box.createVerticalBox();
        conteneurHorisontal.add(conteneurVertical2);
        conteneurVertical1.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

        //ajout de la marge horizontale à gauche
        conteneurHorisontal.add(Box.createRigidArea(new Dimension(marginLeft, 1)));

        if (alignement == ALIGN_RIGHT) {
            conteneurHorisontal.add(Box.createHorizontalGlue());
        }

        conteneurVertical1.add(component);
        conteneurVertical2.add(new JLabel("column2"));

        //ajout de la marge horizontale à droite
        conteneurHorisontal.add(Box.createRigidArea(new Dimension(marginRight, 1)));

        return conteneurHorisontal;
    }

}
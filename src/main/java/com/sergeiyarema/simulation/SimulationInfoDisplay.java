package com.sergeiyarema.simulation;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

import static com.sergeiyarema.simulation.Application.WINDOW_HEIGHT;
import static com.sergeiyarema.simulation.Application.WINDOW_WIDTH;

public class SimulationInfoDisplay {
    private Node guiNode;
    private BitmapFont guiFont;
    private BitmapText hudText;

    private SimulationInfoDisplay() {

    }

    public void setText(String str) {
        if (str != null)
            hudText.setText(str);
    }

    public String getText() {
        return hudText.getText();
    }

    private static String generateInfoString(DotParams dotParams) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<W-S> Angle: ").append(dotParams.get(DotParams.START_ANGLE)).append("\n").
                append("<Z-X> Speed: ").append(dotParams.get(DotParams.START_SPEED)).append("\n").
                append("<U-I> Gravity: ").append(dotParams.get(DotParams.GRAVITY)).append("\n").
                append("<F> Fire\n").append("<del> Clear\n").append("<+ -> Zoom\n").append("<arrows> Move\n");

        return strBuilder.toString();
    }

    public void updateInfo(DotParams dotParams) {
        setText(generateInfoString(dotParams));
    }

    public SimulationInfoDisplay(Node guiNode, BitmapFont guiFont) {
        this.guiNode = guiNode;
        this.guiFont = guiFont;

        hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.Blue);                             // font color

        hudText.setLocalTranslation(0.05f * WINDOW_WIDTH,
                0.95f * WINDOW_HEIGHT, 0);
        guiNode.attachChild(hudText);
    }
}

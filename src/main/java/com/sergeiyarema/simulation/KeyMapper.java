package com.sergeiyarema.simulation;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;

import static com.sergeiyarema.simulation.DotParams.GRAVITY;
import static com.sergeiyarema.simulation.DotParams.START_SPEED;

public class KeyMapper {
    // Control commands
    private static final String ZOOM_IN = "ZoomIN";
    private static final String ZOOM_OUT = "ZoomOUT";
    private static final String INCREASE_ANGLE = "IncreaseAngle";
    private static final String DECREASE_ANGLE = "DecreaseAngle";
    private static final String FIRE = "Fire";
    private static final String INCREASE_GRAVITY = "IncreaseGravity";
    private static final String DECREASE_GRAVITY = "DecreaseGravity";
    private static final String INCREASE_SPEED = "IncreaseSpeed";
    private static final String DECREASE_SPEED = "DecreaseSpeed";
    private static final String CAM_RIGHT = "CamRight";
    private static final String CAM_LEFT = "CamLeft";
    private static final String CAM_UP = "CamUp";
    private static final String CAM_DOWN = "CamDown";
    private static final String CLEAR = "Clear";

    private ApplicationControls appControls;
    private InputManager inputManager;

    private static final float CONTROL_SPEED = 1.5f;

    private KeyMapper() {
    }

    public KeyMapper(ApplicationControls applicationControls) {
        appControls = applicationControls;
        inputManager = applicationControls.getInputManager();
        initKeys();
    }

    private void initKeys() {
        addMappings();

        inputManager.addListener(actionListener,
                FIRE,
                CLEAR);
        inputManager.addListener(analogListener,
                ZOOM_IN, ZOOM_OUT,
                CAM_LEFT, CAM_RIGHT, CAM_UP, CAM_DOWN,
                INCREASE_ANGLE, DECREASE_ANGLE,
                INCREASE_GRAVITY, DECREASE_GRAVITY,
                INCREASE_SPEED, DECREASE_SPEED);
    }

    private void addMappings() {
        inputManager.addMapping(ZOOM_IN, new KeyTrigger(KeyInput.KEY_EQUALS));
        inputManager.addMapping(ZOOM_OUT, new KeyTrigger(KeyInput.KEY_MINUS));

        inputManager.addMapping(INCREASE_ANGLE, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(DECREASE_ANGLE, new KeyTrigger(KeyInput.KEY_S));

        inputManager.addMapping(FIRE, new KeyTrigger(KeyInput.KEY_F));

        inputManager.addMapping(INCREASE_GRAVITY, new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping(DECREASE_GRAVITY, new KeyTrigger(KeyInput.KEY_U));

        inputManager.addMapping(INCREASE_SPEED, new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping(DECREASE_SPEED, new KeyTrigger(KeyInput.KEY_Z));

        inputManager.addMapping(CAM_RIGHT, new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping(CAM_LEFT, new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping(CAM_UP, new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping(CAM_DOWN, new KeyTrigger(KeyInput.KEY_DOWN));

        inputManager.addMapping(CLEAR, new KeyTrigger(KeyInput.KEY_DELETE));
    }

    private final AnalogListener analogListener = (name, value, tpf) -> {
        value = value * CONTROL_SPEED;
        float tangelo = 100f * value;
        float gravityLo = 5f * value;
        float speedLo = 10f * value;
        switch (name) {
            case ZOOM_IN:
                appControls.zoom(1.f - 4f * value);
                break;
            case ZOOM_OUT:
                appControls.zoom(1.f + 4f * value);
                break;
            case CAM_RIGHT:
                appControls.horizontalCamMove(5.f * appControls.getCameraSize().getValue() * value);
                break;
            case CAM_LEFT:
                appControls.horizontalCamMove(-5.f * appControls.getCameraSize().getValue() * value);
                break;
            case CAM_UP:
                appControls.verticalCamMove(5.f * appControls.getCameraSize().getValue() * value);
                break;
            case CAM_DOWN:
                appControls.verticalCamMove(-5.f * appControls.getCameraSize().getValue() * value);
                break;
            case INCREASE_ANGLE:
                appControls.changeAngle(tangelo);
                break;
            case DECREASE_ANGLE:
                appControls.changeAngle(-tangelo);
                break;
            case INCREASE_GRAVITY:
                appControls.changeParamByDelta(GRAVITY, gravityLo);
                break;
            case DECREASE_GRAVITY:
                appControls.changeParamByDelta(GRAVITY, -gravityLo);
                break;
            case INCREASE_SPEED:
                appControls.changeParamByDelta(START_SPEED, speedLo);
                break;
            case DECREASE_SPEED:
                appControls.changeParamByDelta(START_SPEED, -speedLo);
                break;
            default:
                break;
        }

        appControls.getDisplay().updateInfo(appControls.getCurrentParams());
    };

    private final ActionListener actionListener = (name, keyPressed, tpf) -> {
        if (!keyPressed) {
            switch (name) {
                case FIRE:
                    appControls.fire();
                    break;
                case CLEAR:
                    appControls.clearTrajectoryTraces();
                    break;
                default:
                    break;
            }
        }

        appControls.getDisplay().updateInfo(appControls.getCurrentParams());
    };
}

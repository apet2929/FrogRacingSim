package com.apet2929.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.Iterator;

public class InputBuffer {
    public static final int BUFFER_FRAMES = 5;

    public enum InputModifier {
        HELD,
        PRESSED,
        RELEASED
    };
    public class InputAction {
        public int value;
        InputModifier modifier;
        public int elapsedFrames;
        InputAction(int value, InputModifier modifier){
            this.value = value;
            this.modifier = modifier;
            this.elapsedFrames = 0;
        }
    }

    public ArrayList<InputAction> buffer;
    public InputBuffer(){
        this.buffer = new ArrayList<>();
    }

    public void update(){
        Iterator<InputAction> iter = buffer.iterator();
        while(iter.hasNext()) {
            InputAction action = iter.next();
            action.elapsedFrames++;
            if(action.elapsedFrames > BUFFER_FRAMES){
                iter.remove();
            }

        }
    }

    public boolean contains(int value, InputModifier modifier) {
        for (InputAction action : buffer) {
            if(action.value == value && action.modifier == modifier) return true;
        }
        return false;
    }

    public boolean isKeyPressedOrHeld(int value) {
        for (InputAction action : buffer) {
            if(action.value == value && (action.modifier == InputModifier.PRESSED || action.modifier == InputModifier.HELD)) return true;
        }
        return false;
    }

    public void put(int value, InputModifier modifier) {
        buffer.add(new InputAction(value, modifier));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("InputBuffer[");
        for(InputAction action : buffer) {
            sb.append(action.modifier.name().charAt(0)).append(action.value).append(", ");
        }
        if(buffer.size() > 0) sb.delete(sb.length()-2, sb.length());
        sb.append("]");
        return sb.toString();
    }
}

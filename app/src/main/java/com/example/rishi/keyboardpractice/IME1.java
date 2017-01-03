package com.example.rishi.keyboardpractice;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import java.io.Console;
import java.util.List;

/**
 * Created by rishi on 2016-12-28.
 */

public class IME1 extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {
    private long startTime = 0;
    private int lastKey = -100;
    private int firstTime = 1;
    private boolean capsLock = false;
    private KeyboardView kv;
    private Keyboard keyboard, shiftKeyboard, noShiftKeyboard;
    boolean flag = false;
    boolean flag2 = false;
    //Vibrator vibe = (Vibrator) this.getSystemService(.VIBRATOR_SERVICE) ;
    private boolean caps = false;
    private Keyboard.Key Key;

    @Override
    public View onCreateInputView() {

        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);

        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        if (firstTime==1){
            caps = !caps;
            keyboard.setShifted(caps);
            kv.invalidateAllKeys();
        }
        return kv;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();

        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE:
               // vibe.vibrate(500);
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                //vibe.vibrate(500);
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                if (capsLock) capsLock = false;
                break;
            case Keyboard.KEYCODE_DONE:
                //vibe.vibrate(500);
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;

            default:
                //vibe.vibrate(500);
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
        }
        if (firstTime==1 && !capsLock){
            caps = !caps;
            keyboard.setShifted(caps);
            kv.invalidateAllKeys();
        }
        else{
            if (keyboard.isShifted() && firstTime==0 && primaryCode!=Keyboard.KEYCODE_SHIFT && !capsLock) {
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
            }
        }
        //List<Keyboard.Key> key = keyboard.getKeys();
        // Keyboard.Key key1 = key.get(31);
       // key1.icon = getResources().getDrawable(R.drawable.pic);
        if (System.currentTimeMillis()-startTime<500 && firstTime==0 && lastKey == primaryCode){
            // CHANGE INPUT: 2 spaces - 1 period, 2 shifts - caps lock
            if (primaryCode == 32){ //if space
                ic.deleteSurroundingText(2,0);
                char code = (char)46;
                ic.commitText(String.valueOf(code),1);
                code = (char)32;
                ic.commitText(String.valueOf(code),1);
            }

            if (primaryCode == Keyboard.KEYCODE_SHIFT){
                capsLock = true;
                caps = true;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
            }
        }
        startTime=System.currentTimeMillis();
        lastKey=primaryCode;
        firstTime=0;
        Log.d("abc","123");
    }
    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {

            Log.d("Test", "Long press!");
            flag = false;
            flag2 = true;
            return true;

        //return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            Log.d("asadf","laksdf");
            event.startTracking();
            if (flag2) {
                flag = false;
            } else {
                flag = true;
                flag2 = false;
            }

        return super.onKeyDown(keyCode, event);

      //  return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {


            event.startTracking();
            if (flag) {
                Log.d("Test", "Short");
            }
            flag = true;
            flag2 = false;



       return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
        Log.d("Swipe","Down");
    }

    @Override
    public void swipeLeft() {
        Log.d("Swipe","Left");
    }

    @Override
    public void swipeRight() {
        Log.d("Swipe","Right");
    }

    @Override
    public void swipeUp() {
        Log.d("Swipe","Up");
        InputConnection ic = getCurrentInputConnection();
        char code = 50;
        if(Character.isLetter(code) && caps){
            code = Character.toUpperCase(code);
        }
        ic.commitText(String.valueOf(code),1);

    }
}

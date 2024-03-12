package com.wky.mmbook.utils;

import android.inputmethodservice.Keyboard;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.inputmethodservice.KeyboardView;

import com.wky.mmbook.R;

public class KeyBoardUtils {
    private final Keyboard k1;//自定义键盘
    private KeyboardView keyboardView;

    private EditText editText;
    public interface OnEnsureListener{
        public void OnEnsure();
    }
    OnEnsureListener onEnsureListener;
    public void setOnEnsureListener(OnEnsureListener onEnsureListener){
        this.onEnsureListener = onEnsureListener;
    }


    public KeyBoardUtils(KeyboardView keyboardView,EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_NULL); //取消系统键盘
        k1 = new Keyboard(this.editText.getContext(), R.xml.key);
        this.keyboardView.setKeyboard(k1);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(listener);

    }

    KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int i) {

        }

        @Override
        public void onRelease(int i) {

        }

        @Override
        public void onKey(int i, int[] ints) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            switch (i){
                case Keyboard.KEYCODE_DELETE:
                    if(editable!=null && editable.length()>0){
                        if(start>0){
                            editable.delete(start-1,start);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_CANCEL:
                    editable.clear();
                    break;
                case Keyboard.KEYCODE_DONE:
                    onEnsureListener.OnEnsure();
                    break;
                default:
                    editable.insert(start,Character.toString((char)i));
                    break;

            }



        }

        @Override
        public void onText(CharSequence charSequence) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    //显示键盘
    public void showKeyboard(){
        int visibility = keyboardView.getVisibility();
        if(visibility == View.INVISIBLE || visibility == View.GONE){
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard(){
        int visibility = keyboardView.getVisibility();
        if(visibility == View.INVISIBLE || visibility == View.VISIBLE){
            keyboardView.setVisibility(View.GONE);
        }
    }

}

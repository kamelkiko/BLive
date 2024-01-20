package com.expert.blive.SpinWheel.SkUtil;

import android.content.Context;
import android.media.MediaPlayer;

import com.expert.blive.R;


public class SoundManagerClass {
    private static MediaPlayer _btnSoundPlay = null;
    private static MediaPlayer _coinCollectSound = null;
    private static MediaPlayer _flip_card = null;
    private static MediaPlayer _loserSoundPlay = null;
    private static MediaPlayer _slotMachinSound = null;
    private static boolean _soundIsPlay = true;
    private static MediaPlayer _sppiner_rotate;
    private static MediaPlayer _startGameSound;
    private static MediaPlayer _winnerSoundPlay;

    public static void initSound(Context context) {
        _btnSoundPlay = MediaPlayer.create(context, (int) R.raw.button_click);
        _winnerSoundPlay = MediaPlayer.create(context, (int) R.raw.winner);
        _loserSoundPlay = MediaPlayer.create(context, (int) R.raw.losser);
        _startGameSound = MediaPlayer.create(context, (int) R.raw.playgame);
        _coinCollectSound = MediaPlayer.create(context, (int) R.raw.magic_collect);
        _sppiner_rotate = MediaPlayer.create(context, (int) R.raw.sppiner_rotate);
        _sppiner_rotate = MediaPlayer.create(context, (int) R.raw.sppiner_rotate);
        _flip_card = MediaPlayer.create(context, (int) R.raw.flip_card);
        _slotMachinSound = MediaPlayer.create(context, (int) R.raw.sm_sound);
    }

    public static void FlipCard(Context context) {
        MediaPlayer mediaPlayer;
        if (_soundIsPlay && (mediaPlayer = _flip_card) != null) {
            mediaPlayer.start();
        }
    }

    public static void ButtonSound(Context context) {
        MediaPlayer mediaPlayer;
        if (_soundIsPlay && (mediaPlayer = _btnSoundPlay) != null) {
            mediaPlayer.start();
        }
    }

    public static void StartPlayGame(Context context) {
        MediaPlayer mediaPlayer;
        if (_soundIsPlay && (mediaPlayer = _startGameSound) != null) {
            mediaPlayer.start();
        }
    }

    public static void StartCoinCollection(Context context) {
        if (_soundIsPlay && _coinCollectSound != null) {
            _startGameSound.start();
        }
    }

    public static void WinnerSound(Context context) {
        MediaPlayer mediaPlayer;
        if (_soundIsPlay && (mediaPlayer = _winnerSoundPlay) != null) {
            mediaPlayer.start();
        }
    }

    public static void SpinSound(Context context) {
        MediaPlayer mediaPlayer;
        if (_soundIsPlay && (mediaPlayer = _sppiner_rotate) != null) {
            mediaPlayer.start();
        }
    }

    public static void LoserSound(Context context) {
        MediaPlayer mediaPlayer;
        if (_soundIsPlay && (mediaPlayer = _loserSoundPlay) != null) {
            mediaPlayer.start();
        }
    }

    public static void SlotMachineSound(Context context) {
        MediaPlayer mediaPlayer;
        if (_soundIsPlay && (mediaPlayer = _slotMachinSound) != null) {
            mediaPlayer.start();
        }
    }

    public static void SlotMachineSoundStop(Context context) {
        MediaPlayer mediaPlayer;
        if (_soundIsPlay && (mediaPlayer = _slotMachinSound) != null) {
            mediaPlayer.pause();
        }
    }

    public static void PlaySround() {
        _soundIsPlay = true;
    }

    public static void StopSround() {
        _soundIsPlay = false;
    }

    public static boolean IsSound() {
        return _soundIsPlay;
    }
}

package com.expert.blive.Agora.agoraLive.utils;

public interface SoundSelected {
    void onLiveSoundSelected(String soundPath,String id,String soundTitle);
    void onSoundStopped(String soundPath,String id);
    void soundProgress(int progress);
}

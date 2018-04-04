package com.wfg.Adapter;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-11-01 13:49
 * @since V1.0.0
 */
public class AdapterDemo {
    public static void main(String[] args){
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.play("mp3", "beyond the s.mp3");
        audioPlayer.play("mp4", "alone.mp4");
        audioPlayer.play("vlc", "far far away.vlc");
        audioPlayer.play("avi", "mind me.avi");
        System.out.println();

    }
}

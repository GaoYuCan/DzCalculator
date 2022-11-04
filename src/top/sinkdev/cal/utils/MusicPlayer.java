package top.sinkdev.cal.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MusicPlayer {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void playMusic(File file) {
        if (file == null || !file.isFile()) {
            return;
        }
        executor.submit(() -> {
            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file)) {
                //获取音频的编码格式
                AudioFormat audioFormat = audioInputStream.getFormat();

                DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
                        audioFormat, AudioSystem.NOT_SPECIFIED);

                SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                sourceDataLine.open(audioFormat);
                sourceDataLine.start();
                int count = -1;
                byte[] buf = new byte[512];
                //播放音频
                while((count = audioInputStream.read(buf,0,buf.length)) != -1){
                    sourceDataLine.write(buf,0,count);
                }
                sourceDataLine.drain();
                sourceDataLine.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

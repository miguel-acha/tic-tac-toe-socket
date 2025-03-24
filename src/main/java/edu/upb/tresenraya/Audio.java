/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities; // Importar SwingUtilities

public class Audio {

    private static final float DEFAULT_VOLUME = 0.5f; // Volumen por defecto (0.0 a 1.0)

    public static synchronized Clip playMusicLoop(String soundFileName, float volume, boolean sonidoSilenciado) {
        Clip clip = null; // Declarar el clip fuera del try
        if (!sonidoSilenciado) { // Verificar si el sonido está silenciado
            try {
                URL soundFileURL = Audio.class.getResource(soundFileName);
                if (soundFileURL == null) {
                    System.err.println("No se pudo encontrar el archivo de música: " + soundFileName);
                    return null;
                }

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileURL);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                // Ajustar el volumen
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                if (gainControl != null) {
                    float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                    gainControl.setValue(dB);
                }

                Clip finalClip = clip; // Crear una variable final para usar en el LineListener
                clip.addLineListener(new LineListener() {
                    @Override
                    public void update(LineEvent event) {
                        if (event.getType() == LineEvent.Type.STOP) {
                            SwingUtilities.invokeLater(() -> {
                                finalClip.setFramePosition(0); // Reiniciar desde el principio
                                finalClip.start(); // Reproducir de nuevo
                            });
                        }
                    }
                });

                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error al reproducir la música: " + e.getMessage());
            }
        }
        return clip; // Devolver el clip
    }

    // Sobrecarga del método playMusicLoop con volumen por defecto
    public static Clip playMusicLoop(String soundFileName, boolean sonidoSilenciado) {
        return playMusicLoop(soundFileName, DEFAULT_VOLUME, sonidoSilenciado);
    }

    public static synchronized void playSound(String soundFileName, float volume, boolean sonidoSilenciado) {
        if (!sonidoSilenciado) { // Verificar si el sonido está silenciado
            new Thread(() -> {
                try {
                    // Obtener el archivo de sonido como recurso
                    URL soundFileURL = Audio.class.getResource(soundFileName);
                    if (soundFileURL == null) {
                        System.err.println("No se pudo encontrar el archivo de sonido: " + soundFileName);
                        return;
                    }

                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFileURL);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);

                    // Ajustar el volumen
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    if (gainControl != null) {
                        float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                        gainControl.setValue(dB);
                    }

                    clip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    System.err.println("Error al reproducir el sonido: " + e.getMessage());
                }
            }).start();
        }
    }

    // Sobrecarga del método playSound con volumen por defecto
    public static void playSound(String soundFileName, boolean sonidoSilenciado) {
        playSound(soundFileName, DEFAULT_VOLUME, sonidoSilenciado);
    }
}

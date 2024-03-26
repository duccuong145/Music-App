package com.example.final1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final1.R;
import com.example.final1.Song;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    TextView txtTitle,txtTimeSong,txtTimeTotal;
    SeekBar skSong;
    ImageButton btnPrev,btnPlay, btnStop,btnNext,btnExit;
    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AnhXa();
        AddSong();

        KhoiTaoMediaPlayer();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position > arraySong.size()-1){
                    position = 0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position < 0 ){
                    position = arraySong.size() - 1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.start);
                KhoiTaoMediaPlayer();
            }


        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.start);
                }else{
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                }
                SetTimeTotal();
                UpdateTimeSong();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }

              Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);
            }
        });

        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });
    }

    private void UpdateTimeSong() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
                skSong.setProgress(mediaPlayer.getCurrentPosition());

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > arraySong.size() - 1) {
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        SetTimeTotal();
                        UpdateTimeSong();


                        showToast("Now playing: " + arraySong.get(position).getTitle());
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }
    private void btnStopOnClick(View v) {
        mediaPlayer.stop();
        mediaPlayer.release();
        btnPlay.setImageResource(R.drawable.start);
        KhoiTaoMediaPlayer();


        showToast("Music stopped");
    }
    private void SetTimeTotal(){
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(dinhDangGio.format(mediaPlayer.getDuration()));
        skSong.setMax(mediaPlayer.getDuration());
    }
    private void KhoiTaoMediaPlayer() {
        mediaPlayer = MediaPlayer.create(HomeActivity.this, arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());


        showToast("Now playing: " + arraySong.get(position).getTitle());
    }

    private void showToast(String message) {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private void AddSong(){
        arraySong = new ArrayList<>();
        arraySong.add(new Song("CHỊU CÁCH MÌNH NÓI THUA - Rhyder",R.raw.chiucachminhthua));
        arraySong.add(new Song("Drama - aespa",R.raw.drama));
        arraySong.add(new Song("Gods - New Jeans",R.raw.gods));
        arraySong.add(new Song("Perfectnight - Le Sserafim",R.raw.perfectnight));

    }
    private void AnhXa(){
        txtTimeSong = (TextView) findViewById(R.id.textViewTimeSong);
        txtTimeTotal = (TextView) findViewById(R.id.textViewTimeTotal);
        txtTitle = (TextView) findViewById(R.id.textviewTitle);
        skSong = (SeekBar) findViewById(R.id.seekBarSong);
        btnNext = (ImageButton) findViewById(R.id.imageButtonNext);
        btnPlay = (ImageButton) findViewById(R.id.imageButtonPlay);
        btnStop = (ImageButton) findViewById(R.id.imageButtonStop);
        btnPrev = (ImageButton) findViewById(R.id.imageButtonPrev);
        btnExit = (ImageButton) findViewById(R.id.imageButtonExit);

    }

}
package br.com.matheusfarias.jokempo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {

    ImageView jogador1;
    ImageView jogador2;
    ImageButton pedra;
    ImageButton papel;
    ImageButton tesoura;
    Animation some;
    Animation aparece;
    int jogada1 = 0;
    int jogada2 = 0;

    MediaPlayer mediaPlayer; //declarando o audio

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.alex_play); //indicano qual o audio

        jogador1 = findViewById(R.id.imgPlayer1);
        jogador2 = findViewById(R.id.imgPlayer2);
        pedra = findViewById(R.id.imgPedra);
        papel = findViewById(R.id.imgPapel);
        tesoura = findViewById(R.id.imgTesoura);

        some = new AlphaAnimation(1,0);
        aparece = new AlphaAnimation(0,1);

        some.setDuration(1500);
        aparece.setDuration(1500);

        some.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                jogador2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jogador2.setVisibility(View.INVISIBLE);
                jogador2.startAnimation(aparece);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        aparece.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                sorteiaJogadaAdversario();
                jogador2.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                verificaJogada();
                jogador2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //função para quando clicar em algum botão
    public void tocouBotao(View view){
        tocaSom();
        jogador1.setScaleX(-1);

        switch(view.getId()){
            case(R.id.imgPedra):
                jogador1.setImageResource(R.drawable.pedra); // vai mudar a img do jogador 1 para a imagem do botão que ele clicou
                jogada1 = 1;
                break;
            case(R.id.imgPapel):
                jogador1.setImageResource(R.drawable.papel);
                jogada1 = 2;
                break;
            case(R.id.imgTesoura):
                jogador1.setImageResource(R.drawable.tesoura);
                jogada1 = 3;
                break;
        }

        jogador2.setImageResource(R.drawable.interrogacao);
        jogador2.startAnimation(some);

    }

    public void sorteiaJogadaAdversario(){
        Random r = new Random(); //define um Random r
        int numeroRandom = r.nextInt(3); //define uma variavel que vai ser o numero randomico inteiro com o limite 3 (0, 1 e 2)
        switch(numeroRandom){
            case 0:
                jogador2.setImageResource(R.drawable.pedra);
                jogada2 = 1;
                break;
            case 1:
                jogador2.setImageResource(R.drawable.papel);
                jogada2 = 2;
                break;
            case 2:
                jogador2.setImageResource(R.drawable.tesoura);
                jogada2 = 3;
                break;
        }
    }
    public void verificaJogada(){
        if (jogada1 == jogada2){
            Toast.makeText(this, "Empate", Toast.LENGTH_SHORT).show();
        }
        if ((jogada1 == 1 && jogada2 == 2) || (jogada1 == 2 && jogada2 == 3) || (jogada1 == 3 && jogada2 == 1)){
            Toast.makeText(this, "Perdeu", Toast.LENGTH_SHORT).show();
        }
        if ((jogada1 == 1 && jogada2 == 3) || (jogada1 == 2 && jogada2 == 1) || (jogada1 == 3 && jogada2 == 2)){
            Toast.makeText(this, "Ganhou", Toast.LENGTH_SHORT).show();
        }

    }

    public void tocaSom(){
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
    }

}

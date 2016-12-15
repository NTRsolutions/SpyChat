package com.incode_it.spychat.effects;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.incode_it.spychat.R;
import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;

public class EffectsView extends FrameLayout {
    public static final int EFFECT_NONE = 0;
    public static final int EFFECT_BALLOON = 1;
    public static final int EFFECT_FIREWORK = 2;
    public static final int EFFECT_LOVE = 3;
    public static final int EFFECT_PARTY = 4;

    private ArrayList<View> emiters = new ArrayList<>();

    private View fireworkEmiter1, fireworkEmiter2, fireworkEmiter3, fireworkEmiter4;

    private ConfettoGenerator balloonGenerator, heartGenerator;

    private ConfettiManager confettiManager;
    private ArrayList<ParticleSystem> particleSystems = new ArrayList<>();

    private ArrayList<Bitmap> fireworkBitmaps = new ArrayList<>();

    private boolean isFireworkAnimating = false;

    public EffectsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        balloonGenerator = ConfettiHandler.getConfettiGenerator(this, getBalloonColors(), R.mipmap.balloon, 500);
        heartGenerator = ConfettiHandler.getConfettiGenerator(this, getHeartColors(), R.drawable.ic_heart, 200);

        int [] colors = getFireworkColors();
        for (int i = 0; i < colors.length; i++) {
            Bitmap bitmap = createBitmap(colors[i], 40, R.mipmap.star_pink);
            fireworkBitmaps.add(bitmap);
        }
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        fireworkEmiter1 = findViewById(R.id.emiter1);
        fireworkEmiter2 = findViewById(R.id.emiter2);
        fireworkEmiter3 = findViewById(R.id.emiter3);
        fireworkEmiter4 = findViewById(R.id.emiter4);

        emiters.add(this);
        emiters.add(fireworkEmiter1);
        emiters.add(fireworkEmiter2);
        emiters.add(fireworkEmiter3);
        emiters.add(fireworkEmiter4);
    }

    public void start(int effect) {
        if (effect == EFFECT_NONE) return;
        switch (effect) {
            case EFFECT_BALLOON: {
                startBalloons();
                break;
            }
            case EFFECT_FIREWORK: {
                startFirework();
                break;
            }
            case EFFECT_LOVE: {
                startLove();
                break;
            }
            case EFFECT_PARTY: {
                startParty();
                break;
            }
        }
    }


    public void startBalloons() {
        cancel();
        confettiManager = ConfettiHandler.getInstance(this)
                .appear(ConfettiHandler.APPEAR_BOTTOM)
                .generator(balloonGenerator)
                .build()
                .setVelocityX(0, 150)
                .setVelocityY(-600, -250)
                .setNumInitialCount(0)
                .setEmissionDuration(3000)
                .setEmissionRate(12)
                .animate();
    }

    public void startFirework() {
        cancel();
        isFireworkAnimating = true;
        /*ConfettiManager confettiManager = ConfettiHandler.getInstance(this)
                .size(20)
                .colors(getFireworkColors())
                .appear(ConfettiHandler.APPEAR_CENTER)
                .image(R.mipmap.star_pink)
                .build()
                .setTTL(1000)
                .setVelocityX(0, 500)
                .setVelocityY(0, 500)
                .enableFadeOut(Utils.getDefaultAlphaInterpolator())
                .setInitialRotation(180, 180)
                .setRotationalAcceleration(360, 180)
                .setTargetRotationalVelocity(360)
                .setNumInitialCount(200)
                .setEmissionDuration(0)
                .animate();*/

        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < fireworkBitmaps.size(); j++) {
                        if (!isFireworkAnimating) return;
                        ParticleSystem ps = new ParticleSystem((Activity) getContext(), 100, fireworkBitmaps.get(j), 800);
                        ps.setScaleRange(0.7f, 1.3f);
                        ps.setSpeedRange(0.15f, 0.3f);
                        ps.setRotationSpeedRange(90, 180);
                        ps.setFadeOut(300, new AccelerateInterpolator());
                        ps.oneShot(emiters.get(finalI), 100);

                        particleSystems.add(ps);
                    }

                }
            }, i*400);
        }
    }

    public void startLove() {
        cancel();
        confettiManager = ConfettiHandler.getInstance(this)
                .appear(ConfettiHandler.APPEAR_TOP)
                .generator(heartGenerator)
                .build()
                .setVelocityX(0, 250)
                .setVelocityY(600, 250)
                .setNumInitialCount(0)
                .setEmissionDuration(3000)
                .setEmissionRate(15)
                .animate();
    }
    public void startParty() {
        cancel();
        confettiManager = CommonConfetti.rainingConfetti(this, getConfettyColors())
                .getConfettiManager()
                .setVelocityX(0, 250)
                .setVelocityY(600, 250)
                .setNumInitialCount(0)
                .setEmissionDuration(3000)
                .setEmissionRate(50)
                .animate();
    }


    public void cancel() {
        isFireworkAnimating = false;
        for (ParticleSystem ps: particleSystems) {
            ps.cancel();
        }
        particleSystems.clear();

        if (confettiManager != null) {
            confettiManager.terminate();
            confettiManager = null;
        }
    }







    private int[] getBalloonColors() {
        final Resources res = getResources();

        int color1 = res.getColor(R.color.balloon_1);
        int color2 = res.getColor(R.color.balloon_2);
        int color3 = res.getColor(R.color.balloon_3);
        int color4 = res.getColor(R.color.balloon_4);
        int color5 = res.getColor(R.color.balloon_5);
        int color6 = res.getColor(R.color.balloon_6);
        int color7 = res.getColor(R.color.balloon_7);
        int[] colors = new int[] { color1, color2, color3, color4, color5, color6, color7 };

        return colors;
    }

    private int[] getHeartColors() {
        final Resources res = getResources();

        int red = res.getColor(R.color.heart_1);
        int[] colors = new int[] { red };

        return colors;
    }

    private int[] getConfettyColors() {
        final Resources res = getResources();

        int red = res.getColor(R.color.confetty_1);
        int blue = res.getColor(R.color.confetty_2);
        int green = res.getColor(R.color.confetty_3);
        int yellow = res.getColor(R.color.confetty_4);
        int[] colors = new int[] { red, blue, green, yellow };

        return colors;
    }

    private int[] getFireworkColors() {
        final Resources res = getResources();

        int color1 = res.getColor(R.color.firework_1);
        //int color2 = res.getColor(R.color.firework_2);
        int color3 = res.getColor(R.color.firework_3);
        int[] colors = new int[] { color1, color3 };

        return colors;
    }

    private Bitmap createBitmap(int color, int size, @DrawableRes int res)
    {
        Drawable drawable = getResources().getDrawable(res);
        DrawableCompat.setTint(drawable, color);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, size, size);
        drawable.draw(canvas);

        return bitmap;
    }
}
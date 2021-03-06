package com.incode_it.spychat.effects;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.incode_it.spychat.R;
import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;

public class VisualsView extends FrameLayout {
    public static final int EFFECT_NONE = 0;
    public static final int EFFECT_BALLOON = 1;
    public static final int EFFECT_FIREWORK = 2;
    public static final int EFFECT_LOVE = 3;
    public static final int EFFECT_PARTY = 4;

    public int currentEffect = EFFECT_NONE;

    private ArrayList<View> emiters = new ArrayList<>();

    private View fireworkEmiter1, fireworkEmiter2, fireworkEmiter3, fireworkEmiter4;

    private ConfettoGenerator balloonGenerator, heartGenerator, confettiGenerator;
    private static ArrayList<Bitmap> balloonBitmaps, heartBitmaps, confettiBitmaps, fireworkBitmaps;

    private ConfettiManager confettiManager;
    private ArrayList<ParticleSystem> particleSystems = new ArrayList<>();


    private boolean isFireworkAnimating = false;

    public VisualsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (balloonBitmaps == null) {
            balloonBitmaps = getBalloonBitmaps();
        }


        if (heartBitmaps == null) {
            heartBitmaps = getHeartBitmaps();
        }

        if (confettiBitmaps == null) {
            confettiBitmaps = getConfetiBitmaps();
        }

        if (fireworkBitmaps == null) {
            fireworkBitmaps = getFireWorkBitmaps();
        }

        balloonGenerator = ConfettiHandler.getConfettiGenerator(balloonBitmaps);
        heartGenerator = ConfettiHandler.getConfettiGenerator(heartBitmaps);
        confettiGenerator = ConfettiHandler.getConfettiGenerator(confettiBitmaps);


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

    public void setCurrentEffectId(int currentEffect) {
        this.currentEffect = currentEffect;
    }

    public void start(int effect) {
        if (effect == EFFECT_NONE) {
            cancel();
            return;
        }
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


    private void startBalloons() {
        cancel();
        currentEffect = EFFECT_BALLOON;
        confettiManager = ConfettiHandler.getInstance(this)
                .appear(ConfettiHandler.APPEAR_BOTTOM_CENTER)
                .generator(balloonGenerator)
                .size(getBiggestSize(balloonBitmaps))
                .build()
                .setVelocityX(0, 250)
                .setVelocityY(-1000, -400)
                .setNumInitialCount(0)
                .setEmissionDuration(2000)
                .setEmissionRate(4)
                .animate();
    }

    public void startFirework() {
        cancel();
        currentEffect = EFFECT_FIREWORK;
        isFireworkAnimating = true;
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
                        ps.oneShot(emiters.get(finalI), 50);

                        particleSystems.add(ps);
                    }

                }
            }, i*400);
        }
    }

    public void startLove() {
        cancel();
        currentEffect = EFFECT_LOVE;
        confettiManager = ConfettiHandler.getInstance(this)
                .appear(ConfettiHandler.APPEAR_TOP)
                .generator(heartGenerator)
                .size(getBiggestSize(heartBitmaps))
                .build()
                .setVelocityX(0, 250)
                .setVelocityY(1000, 400)
                .setNumInitialCount(0)
                .setEmissionDuration(2500)
                .setEmissionRate(6)
                .animate();
    }
    public void startParty() {
        cancel();
        currentEffect = EFFECT_PARTY;
        confettiManager = ConfettiHandler.getInstance(this)
                .appear(ConfettiHandler.APPEAR_TOP)
                .generator(confettiGenerator)
                .size(getBiggestSize(confettiBitmaps))
                .build()
                .setVelocityX(0, 250)
                .setVelocityY(1000, 250)
                .setNumInitialCount(0)
                .setEmissionDuration(2000)
                .setEmissionRate(100)
                .setInitialRotation(180, 90)
                .setRotationalAcceleration(90, 10)
                .setTargetRotationalVelocity(180)
                .animate();
    }


    public void cancel() {
        currentEffect = EFFECT_NONE;
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
        return new int[] { red };
    }

    private int[] getConfettyColors() {
        final Resources res = getResources();

        int red = res.getColor(R.color.confetty_1);
        int blue = res.getColor(R.color.confetty_2);
        int green = res.getColor(R.color.confetty_3);
        int yellow = res.getColor(R.color.confetty_4);
        return new int[] { red, blue, green, yellow };
    }

    private int[] getFireworkColors() {
        final Resources res = getResources();

        int color1 = res.getColor(R.color.firework_1);
        int color2 = res.getColor(R.color.firework_2);
        int color3 = res.getColor(R.color.firework_3);
        int color4 = res.getColor(R.color.firework_4);
        return new int[] { color1, color2, color3, color4 };
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

    private ArrayList<Bitmap> getBalloonBitmaps() {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.balloon_blue_normal));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.balloon_blue_small));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.balloon_green_normal));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.balloon_green_small));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.balloon_red_normal));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.balloon_red_small));

        return bitmaps;
    }

    private ArrayList<Bitmap> getHeartBitmaps() {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.heart_normal));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.heart_small));
        return bitmaps;
    }

    private ArrayList<Bitmap> getConfetiBitmaps() {
        //ArrayList<Bitmap> bitmaps = (ArrayList<Bitmap>) Utils.generateConfettiBitmaps(getConfettyColors(), 50);

        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.confetti1));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.confetti2));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.confetti3));
        //bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.confetti4));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.confetti5));
        return bitmaps;
    }

    private ArrayList<Bitmap> getFireWorkBitmaps() {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.star1));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.star2));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.star3));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.star4));
        return bitmaps;
    }

    private int getBiggestSize(ArrayList<Bitmap> bitmaps) {
        int size = 0;
        for (Bitmap bitmap: bitmaps) {
            int bitmapSize = Math.max(bitmap.getWidth(), bitmap.getHeight());
            if (bitmapSize > size) {
                size = bitmapSize;
            }
        }
        return size;
    }









    static class SavedState extends BaseSavedState {
        int effect;

        /**
         * Constructor called from {@link CompoundButton#onSaveInstanceState()}
         */
        SavedState(Parcelable superState) {
            super(superState);
        }

        /**
         * Constructor called from {@link #CREATOR}
         */
        private SavedState(Parcel in) {
            super(in);
            effect = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(effect);
        }

        @Override
        public String toString() {
            return "CompoundButton.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " effect=" + effect + "}";
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);


        ss.effect = currentEffect;
        Log.d("sfsdfd", "onSaveInstanceState: " + currentEffect);
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;

        super.onRestoreInstanceState(ss.getSuperState());
        currentEffect = ss.effect;
        Log.d("sfsdfd", "onRestoreInstanceState: " + currentEffect);
        requestLayout();
    }
}

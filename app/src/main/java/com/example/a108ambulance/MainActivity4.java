package com.example.a108ambulance;



import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;



public class MainActivity4 extends AppCompatActivity {

    SliderView sliderView;
    int[] images = {R.drawable.img1,R.drawable.img2, R.drawable.img3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        sliderView = findViewById(R.id.image_slider);

        com.example.a108ambulance.SliderAdapter sliderAdapter = new com.example.a108ambulance.SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();


    }

}

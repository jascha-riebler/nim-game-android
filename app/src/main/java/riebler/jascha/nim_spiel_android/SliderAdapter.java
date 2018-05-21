package riebler.jascha.nim_spiel_android;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    private ImageButton mainSelectionButton;
    MainActivity mainActivity;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Arrays
    public int[] slide_images = {

            R.drawable.menu_nimclassic,
            R.drawable.menu_nimfun,
            R.drawable.menu_about
    };

    public String[] slide_headings = {
            "NIM Classic",
            "NIM FUN",
            "ABOUT"
    };

    public String[] slide_descriptions = {
            "-",
            "-",
            "-"
    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageButton slideImageButton = (ImageButton) view.findViewById(R.id.slide_imagebtn);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_description);

        slideImageButton.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descriptions[position]);

        mainSelectionButton = (ImageButton) view.findViewById(R.id.slide_imagebtn);

        mainSelectionButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(context instanceof MainActivity){
                    ((MainActivity)context).SelectionBtn();
                }
            }
        });

        container.addView(view);

        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);

    }


}

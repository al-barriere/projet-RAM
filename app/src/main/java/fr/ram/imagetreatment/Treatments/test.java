package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Treatments.Convolution.GaussianBlur;
import fr.ram.imagetreatment.Treatments.Convolution.Sobel;
import fr.ram.imagetreatment.Treatments.InverseColor;
import fr.ram.imagetreatment.Treatments.Treatment;
import fr.ram.imagetreatment.Util.ColorUtil;

/**
 * Created by Maxime on 13/04/2017.
 */

public class test extends Treatment {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        Bitmap bmpTmp=img.getImageBitmap();
        bmpTmp=bmpTmp.copy(Bitmap.Config.ARGB_8888,true);

        CustomImageView c=img;
        c.setImageBitmap(bmpTmp);

        args = new Bundle();
        args.putInt("maskSize", 3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur._compute(c, args);


        Bitmap bmp = img.getImageBitmap();
        Sobel d=new Sobel();
        d._compute(img,args);
       // Bitmap bmp=img.getImageBitmap();
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];



        InverseColor inverseColor=new InverseColor();
        inverseColor._compute(img,args);


       /* bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for (int i = 0; i < pixels.length; i++) {
            int o = pixels[i];
            int blue = Color.blue(o);
            int red = Color.red(o);
            int green = Color.green(o);

           if(red>200 && green > 200 && blue >200)
           {
               pixels[i] = Color.argb(0,red, green, blue);
           }
           else{
               pixels[i] = Color.argb(255,red, green, blue);
           }


           // pixels[i] = Color.rgb(red, green, blue);


        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());*/


        int pixels_texte[] = new int[bmp.getWidth()*bmp.getHeight()];
        int pixels_fond[] = new int[bmpTmp.getWidth()*bmpTmp.getHeight()];

        bmp.getPixels(pixels_texte, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        bmpTmp.getPixels(pixels_fond, 0, bmpTmp.getWidth(), 0, 0, bmpTmp.getWidth(), bmpTmp.getHeight());
       // if(pixels_fond.length==pixels_texte.length) {
            for (int i = 0; i < pixels_texte.length; i++) {
                int pixelTexte = pixels_texte[i];
                int pixelImage = pixels_fond[i];

                int o = pixelTexte;
                int b = Color.blue(o);
                int r = Color.red(o);
                int g = Color.green(o);

                if(r>127 && g > 127 && b >127)
                {


                    r =  Color.red(pixelImage);
                    g =  Color.green(pixelImage);
                    b =  Color.blue(pixelImage);
                }
                else
                {
                    r = (Color.red(pixelTexte));//+ Color.red(pixelImage)) / 2;
                    g = (Color.green(pixelTexte));//+ Color.green(pixelImage)) / 2;
                    b = (Color.blue(pixelTexte));// + Color.blue(pixelImage)) / 2;

                }
                pixels_texte[i] = Color.rgb(r, g, b);
            }
            bmp.setPixels(pixels_texte, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

            //img.setImageBitmap(bmp);
       // }

        return bmp;
    }
}

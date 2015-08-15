package no.recipeheaven.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CustomBindingAdapter {

   @BindingAdapter("bind:imageUrl")
   public static void loadImage(ImageView imageView, String url) {
      Glide.with(imageView.getContext()).load(url).into(imageView);
   }

   @BindingAdapter({"bind:publishedDate", "bind:updateDate"})
   public static void loadDate(TextView textView, Date published, Date updated) {
       Calendar publishedCalendar = new GregorianCalendar();
       publishedCalendar.setTime(published);

       Calendar updatedCalendar = new GregorianCalendar();
       updatedCalendar.setTime(updated);

       textView.setText(
               published.getTime() == updated.getTime()
                       ? MessageFormat.format(
                       "(Publisert {0}.{1}.{2,number,#})",
                       publishedCalendar.get(Calendar.DAY_OF_MONTH),
                       publishedCalendar.get(Calendar.MONTH),
                       publishedCalendar.get(Calendar.YEAR)
               )

                       : MessageFormat.format(
                       "(Publisert {0}.{1}.{2,number,#}, endret {3}.{4}.{5,number,#})",
                       publishedCalendar.get(Calendar.DAY_OF_MONTH),
                       publishedCalendar.get(Calendar.MONTH),
                       publishedCalendar.get(Calendar.YEAR),
                       updatedCalendar.get(Calendar.DAY_OF_MONTH),
                       updatedCalendar.get(Calendar.MONTH),
                       updatedCalendar.get(Calendar.YEAR)
               )
       );
   }
} 
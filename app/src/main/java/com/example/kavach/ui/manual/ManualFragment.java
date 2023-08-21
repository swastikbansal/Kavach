package com.example.kavach.ui.manual;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.kavach.R;

public class ManualFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manual, container, false);

        // Find the TextView in the layout
        TextView textHead3 = rootView.findViewById(R.id.textHead3);

        // Create a SpannableStringBuilder to hold the formatted text
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        // Add formatted phone number entries
        addPhoneNumber(spannableStringBuilder, "Fire : 101", "101");
        addPhoneNumber(spannableStringBuilder, "Ambulance : 102", "102");
        addPhoneNumber(spannableStringBuilder, "Road Accident Emergency Service : 1073", "1073");
        addPhoneNumber(spannableStringBuilder, "Railway Accident Emergency Service : 1072", "1072");
        addPhoneNumber(spannableStringBuilder, "Police : 100 or 112", "100");
        addPhoneNumber(spannableStringBuilder, "Disaster Management Services : 108", "108");
        addPhoneNumber(spannableStringBuilder, "National Emergency Number : 112", "112");
        addPhoneNumber(spannableStringBuilder, "Women’s Helpline : 1091", "1091");
        addPhoneNumber(spannableStringBuilder, "Women’s Helpline (Domestic Abuse) : 181", "181");
        addPhoneNumber(spannableStringBuilder, "Children In Difficult Situation : 1098", "1098");
        addPhoneNumber(spannableStringBuilder, "Senior Citizen Helpline : 14567", "14567");
        addPhoneNumber(spannableStringBuilder, "Cyber Crime Helpline : 155620", "155620");
        addPhoneNumber(spannableStringBuilder, "Kisan Call Centre : 18001801551", "18001801551");
        addPhoneNumber(spannableStringBuilder, "Aids Helpline : 1097", "1097");
        addPhoneNumber(spannableStringBuilder, "Air Ambulance : 9540161344", "9540161344");
        // ... add more entries here ...

        // Set the formatted text to the TextView
        textHead3.setText(spannableStringBuilder);

        // Make sure the TextView handles links
        textHead3.setMovementMethod(LinkMovementMethod.getInstance());

        return rootView;
    }

    private void addPhoneNumber(SpannableStringBuilder builder, String text, final String phoneNumber) {
        // Create a clickable span for the phone number
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        };

        // Append the formatted text and set the clickable span
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(clickableSpan, text.indexOf(phoneNumber), text.indexOf(phoneNumber) + phoneNumber.length(), 0);
        builder.append(spannableString).append("\n");
    }
}

package umn.ac.id.projectuas_cerdas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AccountFragment extends Fragment {
    TextView tvUsername, tvEmail, tvPhone, tvOccupation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        tvUsername = view.findViewById(R.id.userName);
        tvEmail = view.findViewById(R.id.userEmail);
        tvPhone = view.findViewById(R.id.userPhone);
        tvOccupation = view.findViewById(R.id.userOcupation);

        String username = getArguments().getString("username");
        String email = getArguments().getString("email");
        String phone = getArguments().getString("phone");
        String occupation = getArguments().getString("occupation");
        //String favourite = getArguments().getString("favourite");

        tvUsername.setText(username);
        email = "Email : " + email;
        tvEmail.setText(email);
        phone = "Phone : " + phone;
        //tvUsername.setText(username);
        tvPhone.setText(phone);

        occupation = "Occupation : " + occupation;
        tvOccupation.setText(occupation);


        return view;
    }
}

package umn.ac.id.projectuas_cerdas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AccountFragment extends Fragment {
    TextView tvUsername, tvEmail, tvPhone, tvOccupation;
    Button btnEdit;

    String jenis, index;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        tvUsername = view.findViewById(R.id.userName);
        tvEmail = view.findViewById(R.id.userEmail);
        tvPhone = view.findViewById(R.id.userPhone);
        tvOccupation = view.findViewById(R.id.userOcupation);
        btnEdit = view.findViewById(R.id.editaccBtn);

        String username = getArguments().getString("username");
        String email = getArguments().getString("email");
        String phone = getArguments().getString("phone");
        String occupation = getArguments().getString("occupation");
        jenis = getArguments().getString("jenis");
        index = getArguments().getString("index");
        //String favourite = getArguments().getString("favourite");

        tvUsername.setText(username);
        email = "Email : " + email;
        tvEmail.setText(email);
        phone = "Phone : " + phone;
        //tvUsername.setText(username);
        tvPhone.setText(phone);

        occupation = "Occupation : " + occupation;
        tvOccupation.setText(occupation);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditProfile.class);
                intent.putExtra("name", tvUsername.getText().toString());
                intent.putExtra("email", tvEmail.getText().toString());
                intent.putExtra("phone", tvPhone.getText().toString());
                intent.putExtra("occupation", tvOccupation.getText().toString());
                intent.putExtra("jenis", jenis);
                intent.putExtra("index", index);
                v.getContext().startActivity(intent);
            }
        });

        return view;
    }
}

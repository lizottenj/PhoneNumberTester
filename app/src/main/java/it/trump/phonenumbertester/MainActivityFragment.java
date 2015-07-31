package it.trump.phonenumbertester;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Bind(R.id.countryCode)
    EditText countryCode;

    @Bind(R.id.phoneNumber)
    EditText phoneNumber;

    @Bind(R.id.formattedNumber)
    EditText formattedNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @OnClick(R.id.validateButton)
    public void onClickValidate(Button btn) {

        PhoneNumberUtil util = PhoneNumberUtil.getInstance();

        String countryCodeString = countryCode.getText().toString().trim().replaceAll("\\D", "");
        String regionCodeString = util.getRegionCodeForCountryCode(Integer.parseInt(countryCodeString));

        String phoneNumberString = phoneNumber.getText().toString();

        try {
            Phonenumber.PhoneNumber parsedNumber = util
                    .parse(phoneNumberString, regionCodeString);

            formattedNumber.setText(util.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164));

            if(util.isValidNumberForRegion(parsedNumber, regionCodeString)) {

                formattedNumber.setText(util.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164));

            } else {
                // Invalid number
                showInvalidPhoneNumberDialog(countryCode);
            }

        } catch (NumberParseException e) {
            showInvalidPhoneNumberDialog(countryCode);
        }
    }

    private void showInvalidPhoneNumberDialog(EditText countryCode) {
        Toast.makeText(countryCode.getContext(), "Error", Toast.LENGTH_SHORT).show();
    }

}

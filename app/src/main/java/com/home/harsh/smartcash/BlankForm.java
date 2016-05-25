package com.home.harsh.smartcash;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankForm.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankForm extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
     OtpTimmer otpTimmer;
    FragmentTransaction[] fragTran;
    ImageButton fab;
    EditText cardnum;
    Boolean isMonthValid=false,isYearValid=false;
    private OnFragmentInteractionListener mListener;

    public BlankForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankForm.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankForm newInstance(String param1, String param2) {
        BlankForm fragment = new BlankForm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_blank_form, container, false);
        Bundle args= new Bundle();
        fragmentManager= getFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        otpTimmer= new OtpTimmer();
        args.putString("Adding","true");
        otpTimmer.setArguments(args);
        otpTimmer.setHasOptionsMenu(true);
        fragTran= new FragmentTransaction[1];
        fab = (ImageButton) view.findViewById(R.id.myFab);
        fab.setOnClickListener(this);
        cardnum=(EditText)view.findViewById(R.id.cardnum);
        cardnum.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrecntString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // chech that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrecntString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });
        final EditText name = (EditText) view.findViewById(R.id.dc_holder_name);
        final EditText expiry_month = (EditText) view.findViewById(R.id.dc_expiry_M);
        final EditText expiry_year = (EditText) view.findViewById(R.id.dc_expiry_Y);
        final EditText cvv = (EditText) view.findViewById(R.id.dc_cvv);
        final TextView validMonth= (TextView) view.findViewById(R.id.month_validate);
        expiry_month.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")){

                    int month=Integer.valueOf(s.toString());
                    if(month>=1 && month<=12){
                        validMonth.setText("");
                        isMonthValid=true;
                        String year=expiry_year.getText().toString();
                        if(!year.equals("")){
                            int y=Integer.valueOf(year);
                            if(y>=2016&& y<=2050){

                            }
                            else{
                                validMonth.setText("Enter Valid Year");
                            }
                        }
                    }
                    else{
                        validMonth.setText("Enter Valid Expiry Month");
                        isMonthValid=false;
                        String year=expiry_year.getText().toString();
                        if(!year.equals("")){
                            int y=Integer.valueOf(year);
                            if(y>=2016&& y<=2050){

                            }
                            else{
                                validMonth.setText("Enter Valid Month and Year");
                            }
                        }
                        validMonth.setTextColor(Color.parseColor("#f44336"));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        expiry_year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")){
                    int year= Integer.valueOf(s.toString());
                    if(year>=2016 && year<=2050 ){
                        isYearValid=true;
                    }
                    if(year>=2016 && year<=2050 && isMonthValid){
                        validMonth.setText("");
                        isYearValid=true;
                    }
                    else if(year>=2016 && year<=2050 && !isMonthValid){
                        validMonth.setText("Enter Valid Month");
                        validMonth.setTextColor(Color.parseColor("#f44336"));

                    }
                    else{
                        isYearValid=false;
                        if(isMonthValid){
                            validMonth.setText("Enter Valid Expiry Year");
                            validMonth.setTextColor(Color.parseColor("#f44336"));
                        }
                        else{
                            validMonth.setText("Enter Valid Expiry Month and Year");
                            validMonth.setTextColor(Color.parseColor("#f44336"));
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final Spinner debitSpinner = (Spinner)view.findViewById(R.id.debitCardSpinner);
        final Spinner card = (Spinner) view.findViewById(R.id.db_card_t);

        final List<String> bankName = new ArrayList<String>();
        bankName.add("Please Select a Bank");
        bankName.add("HDFC Bank");
        bankName.add("State Bank of India");
        bankName.add("ICICI Bank");
        bankName.add("AXIS Bank");
        bankName.add("IDBI Bank");
        bankName.add("Bank of Maharashatra");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, bankName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        debitSpinner.setAdapter(dataAdapter);
        debitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity().getApplicationContext(),item,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        final List<String> cardType = new ArrayList<String>();
        cardType.add("Master-card");
        cardType.add("Discover");
        cardType.add("Amex");
        cardType.add("VISA");
        cardType.add("Mastero");
        ArrayAdapter<String> dataAdapterCard = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,cardType);
        dataAdapterCard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        card.setAdapter(dataAdapterCard);
        card.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
              //  Toast.makeText(getActivity().getApplicationContext(),item,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            Toast.makeText(getActivity().getApplicationContext(),"aatach",Toast.LENGTH_SHORT).show();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getActivity().getApplicationContext(),"detach",Toast.LENGTH_SHORT).show();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myFab:
                fragTran[0] = fragmentManager.beginTransaction();
                fragTran[0].replace(R.id.fragment_container,otpTimmer);
                fragTran[0].addToBackStack(null);
                fragTran[0].commit();
                break;

        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

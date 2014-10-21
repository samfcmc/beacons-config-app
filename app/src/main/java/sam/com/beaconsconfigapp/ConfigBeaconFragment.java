package sam.com.beaconsconfigapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kinvey.java.Util;

import sam.com.beaconsconfigapp.models.Beacon;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfigBeaconFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfigBeaconFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ConfigBeaconFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private Button cancelButton;
    private Button doneButton;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText urlEditText;
    private static final String BEACON = "beacon";

    private Beacon beacon;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConfigBeaconFragment.
     */
    public static ConfigBeaconFragment newInstance(Beacon beacon) {
        ConfigBeaconFragment fragment = new ConfigBeaconFragment();
        Bundle args = new Bundle();
        args.putParcelable(BEACON, beacon);
        fragment.setArguments(args);
        return fragment;
    }
    public ConfigBeaconFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        this.beacon = arguments.getParcelable(BEACON);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config_beacon, container, false);
        TextView uuidTextView = (TextView) view.findViewById(R.id.config_beacon_uuid_textView);
        TextView majorTextView = (TextView) view.findViewById(R.id.config_beacon_major_textView);
        TextView minorTextView = (TextView) view.findViewById(R.id.config_beacon_minor_textView);

        String uuid = Utils.byteArrayToString(this.beacon.getUuid());
        String major = Utils.byteArrayToString(this.beacon.getMajor());
        String minor = Utils.byteArrayToString(this.beacon.getMinor());

        uuidTextView.setText(uuid);
        majorTextView.setText(major);
        minorTextView.setText(minor);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.cancelButton = (Button) view.findViewById(R.id.config_beacon_cancel_button);
        this.doneButton = (Button) view.findViewById(R.id.config_beacon_confirm_button);
        this.nameEditText = (EditText) view.findViewById(R.id.config_beacon_name_editText);
        this.urlEditText = (EditText) view.findViewById(R.id.config_beacon_content_editText);
        this.descriptionEditText = (EditText) view.findViewById(R.id.config_beacon_description_editText);

        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        this.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done();
            }
        });
    }

    private void cancel() {
        this.mListener.onConfigBeaconCancel();
    }

    private void done() {
        String name = this.nameEditText.getText().toString();
        String url = this.urlEditText.getText().toString();
        String description = this.descriptionEditText.getText().toString();

        this.mListener.onConfigBeaconDone(this.beacon, name, description, url);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onConfigBeaconCancel();
        public void onConfigBeaconDone(Beacon beacon, String description, String name, String url);
    }

}

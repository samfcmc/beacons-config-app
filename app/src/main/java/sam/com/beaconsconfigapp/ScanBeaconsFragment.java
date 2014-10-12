package sam.com.beaconsconfigapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import sam.com.beaconsconfigapp.adapters.ScanBeaconsListAdapter;
import sam.com.beaconsconfigapp.bluetooth.BeaconCallback;
import sam.com.beaconsconfigapp.bluetooth.BeaconScanner;
import sam.com.beaconsconfigapp.bluetooth.SimBeaconScanner;
import sam.com.beaconsconfigapp.dummy.DummyContent;
import sam.com.beaconsconfigapp.models.Beacon;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class ScanBeaconsFragment extends ListFragment {

    private static final int REQUEST_ENABLE_BT = 0xFF;
    private OnFragmentInteractionListener mListener;

    private ScanBeaconsListAdapter listAdapter;
    private List<Beacon> beacons;
    private BeaconScanner scanner;
    private BluetoothAdapter bluetoothAdapter;

    public static ScanBeaconsFragment newInstance() {
        ScanBeaconsFragment fragment = new ScanBeaconsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ScanBeaconsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.beacons = new ArrayList<Beacon>();
        this.listAdapter = new ScanBeaconsListAdapter(getActivity(), this.beacons);
        setListAdapter(this.listAdapter);

        if(!deviceSupportBLE()) {
            String errorMessage = "";
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            this.mListener.deviceDoesNotSupportBLE();
        }
        initBLE();
        scanBeacons();
    }

    private void initBLE() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        this.bluetoothAdapter = bluetoothManager.getAdapter();

        if(this.bluetoothAdapter == null || !this.bluetoothAdapter.isEnabled()) {
            askToTurnOnBluetooth();
        }

        this.scanner = new SimBeaconScanner(this.bluetoothAdapter);
    }

    private boolean deviceSupportBLE() {
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    private void scanBeacons() {
        this.scanner.startScan(new BeaconCallback<Beacon>() {
            @Override
            public void onBeaconFound(final Beacon beacon) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScanBeaconsFragment.this.beacons.add(beacon);
                        ScanBeaconsFragment.this.listAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void askToTurnOnBluetooth() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, REQUEST_ENABLE_BT);
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


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            Beacon beacon = this.beacons.get(position);
            this.mListener.onBeaconSelected(beacon);
        }
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
        public void onBeaconSelected(Beacon beacon);
        public void deviceDoesNotSupportBLE();
    }

}

package sam.com.beaconsconfigapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import sam.com.beaconsconfigapp.adapters.BeaconsListAdapter;
import sam.com.beaconsconfigapp.storage.WebStorageCallback;
import sam.com.beaconsconfigapp.storage.entities.BeaconEntity;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class MyBeaconsFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;
    private List<BeaconEntity> beacons;
    private BeaconsListAdapter listAdapter;

    private BeaconConfigApplication application;
    private ProgressDialog progressDialog;

    public static MyBeaconsFragment newInstance() {
        MyBeaconsFragment fragment = new MyBeaconsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyBeaconsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.application = (BeaconConfigApplication) getActivity().getApplication();
        this.beacons = new ArrayList<BeaconEntity>();
        this.listAdapter = new BeaconsListAdapter(getActivity(), this.beacons);
        this.progressDialog = new ProgressDialog(getActivity());
        setListAdapter(this.listAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String loadingMessage = getResources().getString(R.string.my_beacons_loading);
        this.progressDialog.setMessage(loadingMessage);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
        this.application.getWebStorage().getBeacons(new WebStorageCallback<BeaconEntity[]>() {
            @Override
            public void onFailure(Throwable throwable) {
                showError();
            }

            @Override
            public void onSuccess(BeaconEntity[] response) {
                updateBeaconsList(response);
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void showError() {
        Toast.makeText(getActivity(), "Cannot get your beacons", Toast.LENGTH_SHORT).show();
        this.progressDialog.dismiss();
    }

    private void updateBeaconsList(BeaconEntity[] beacons) {
        for(BeaconEntity beacon : beacons) {
            this.beacons.add(beacon);
        }
        this.listAdapter.notifyDataSetChanged();
        this.progressDialog.dismiss();
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
            mListener.onFragmentInteraction(beacons.get(position).getUuid());
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
        public void onFragmentInteraction(String id);
    }

}

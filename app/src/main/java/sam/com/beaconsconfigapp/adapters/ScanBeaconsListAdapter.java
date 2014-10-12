package sam.com.beaconsconfigapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sam.com.beaconsconfigapp.R;
import sam.com.beaconsconfigapp.Utils;
import sam.com.beaconsconfigapp.models.Beacon;

/**
 *
 */
public class ScanBeaconsListAdapter extends ArrayAdapter<Beacon> {
    public ScanBeaconsListAdapter(Context context, List<Beacon> objects) {
        super(context, R.layout.scan_beacons_list_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.scan_beacons_list_item, parent, false);
        }

        TextView uuidTextView = (TextView) convertView.findViewById(R.id.scan_beacons_uuid_textView);
        TextView majorTextView = (TextView) convertView.findViewById(R.id.scan_beacons_major_textView);
        TextView minorTextView = (TextView) convertView.findViewById(R.id.scan_beacons_minor_textView);
        TextView powerTextView = (TextView) convertView.findViewById(R.id.scan_beacons_power_textView);

        Beacon beacon = getItem(position);

        String uuid = Utils.byteArrayToString(beacon.getUuid());
        String major = Utils.byteArrayToString(beacon.getMajor());
        String minor = Utils.byteArrayToString(beacon.getMinor());
        String power = Utils.byteToString(beacon.getPower());

        uuidTextView.setText(uuid);
        majorTextView.setText(major);
        minorTextView.setText(minor);
        powerTextView.setText(power);

        return convertView;
    }
}

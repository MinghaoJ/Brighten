package ca.uwaterloo.deltahacks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FeatureViewFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    String[] myDataset = new String[] {"1", "2", "3", "4", "5"};
    List<Listing> listings = new ArrayList<>();
    Listing exampleListing = new Listing("Kitchener Public Library",
            "library",
            new String[] {"community"},
            9, 0,
            17, 30,
            2);

    public FeatureViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feature_view, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        for( int i = 0 ; i < 5 ; i++ ) {
            listings.add(exampleListing);
        }

        // specify an adapter (see also next example)
        mAdapter = new Adapter(listings);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private List<Listing> listings;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
          View mCardView;
            public ViewHolder(View v) {
                super(v);
                mCardView = v;
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public Adapter(List<Listing> listings) {
            this.listings = listings;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_listing, parent, false);
            // set the view's size, margins, paddings and layout parameters
            CardView card = (CardView) v.findViewById(R.id.card_view);
            ImageView img = (ImageView) v.findViewById(R.id.image);

            card.setPreventCornerOverlap(false);
            card.setCardElevation(4);
            //card.setContentPadding(16,16,16,16);
            img.setImageResource(R.drawable.library);

            Adapter.ViewHolder vh = new Adapter.ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            TextView infoText = (TextView) holder.mCardView.findViewById(R.id.info_text);
            infoText.setText(listings.get(position).org);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return listings.size();
        }
    }

    public class Listing {
        String org;
        String cat;
        String[] tags;
        Time startTime;
        Time endTime;
        int distanceKm;

        public class Time {
            int hour;
            int minute;

            public Time(int hour, int minute) {
                if( hour > 24 || hour < 1) {
                    throw new IllegalArgumentException("Invalid hour");
                }
                if( minute > 60 || minute < 0) {
                    throw new IllegalArgumentException("Invalid minute");
                }
                this.hour = hour;
                this.minute = minute;
            }

            @Override
            public String toString() {
                return String.format("%d:%d " + (hour > 12 ? "pm" : "am"),
                        ((hour - 1) % 12) + 1, minute);
            }
        }

        public Listing(String org, String cat, String[] tags, int startHour, int startMinute,
                       int endHour, int endMinute, int distanceKm) {
            this.org = org;
            this.cat = cat;
            this.tags = tags;
            this.startTime = new Time(startHour, startMinute);
            this.endTime = new Time(endHour, endMinute);
            this.distanceKm = distanceKm;
        }
    }

}
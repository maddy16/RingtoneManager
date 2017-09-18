package com.logixity.apps.ringtonesmanager;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class RingtonesFragment extends Fragment implements IFrag {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private AdView mAdView;
    ListView mediaList;
    ArrayList<String> mediaListItems;
    ArrayList<Integer> ringToneIDs;
    static int soundId;
    boolean played = false;
    ImageView currentView;
    int selectedImage;
    private static final String ARG_SECTION_NUMBER = "section_number";
    MediaListAdapter adapter;
    public RingtonesFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RingtonesFragment newInstance(int sectionNumber) {
        RingtonesFragment fragment = new RingtonesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ringtones_tab, container, false);

        mAdView = (AdView) rootView.findViewById(R.id.ringBannerAd);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mediaList = (ListView) rootView.findViewById(R.id.MediaListView);
//        player = new MediaPlayer();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            player = new SoundPool.Builder()
//                    .setMaxStreams(30)
//                    .build();
//        } else {
//            player = new SoundPool(30, AudioManager.STREAM_MUSIC, 1);
//        }
//        mPlayer = new MediaPlayer();
        final Field[] fields = R.raw.class.getFields();
        mediaListItems = new ArrayList<>();
        ringToneIDs = new ArrayList<>();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldName.startsWith("ring_")) {
                fieldName = fieldName.substring(5, 6).toUpperCase() + fieldName.substring(6);
                fieldName = fieldName.replaceAll("_", " ");
                mediaListItems.add(fieldName);
                ringToneIDs.add(getResources().getIdentifier(field.getName(), "raw", getActivity().getPackageName()));
            }
        }
        adapter = new MediaListAdapter(getContext(), R.layout.media_list_single_item, mediaListItems,this,ringToneIDs);
        mediaList.setAdapter(adapter);

        mediaList.setOnItemClickListener(new SystemUtils.ItemClickHandler(ringToneIDs,mediaListItems,getContext()));
        return rootView;
    }

    @Override
    public ImageView getCurrentView() {
        return currentView;
    }

    @Override
    public void setCurrentView(ImageView view) {
        this.currentView = view;
    }

    @Override
    public void setSelectedImage(int i) {
        selectedImage = i;
    }

    @Override
    public int getSelectedImage() {
        return selectedImage;
    }
}

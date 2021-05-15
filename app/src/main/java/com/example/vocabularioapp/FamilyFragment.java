package com.example.vocabularioapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FamilyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FamilyFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudio=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }
            else{
                releaseMP();
            }
        }
    };
    private MediaPlayer.OnCompletionListener mrelease=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMP();
        }
    };
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FamilyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FamilyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FamilyFragment newInstance(String param1, String param2) {
        FamilyFragment fragment = new FamilyFragment();
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
        View rootView=inflater.inflate(R.layout.word_list, container, false);
        mAudioManager =(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("father", "papa",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother", "madre",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son", "hijo",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter", "hija",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother", "hermano mayor",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger brother", "hermano menor",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older sister", "hermana mayor",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger sister", "hermana más joven",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother ", "abuela",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather", "abuelo",R.drawable.family_grandfather,R.raw.family_grandfather));
        WordAdapter wordAdapter=new WordAdapter(getActivity(),words,R.color.category_family);
        ListView listView=(ListView)rootView.findViewById(R.id.list);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMP();
                int result=mAudioManager.requestAudioFocus(mOnAudio,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), words.get(position).getAudioId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mrelease);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMP();
    }

    public void releaseMP(){
        if(mediaPlayer !=null){
            mediaPlayer.release();
        }
        mediaPlayer=null;
        mAudioManager.abandonAudioFocus(mOnAudio);
    }
}
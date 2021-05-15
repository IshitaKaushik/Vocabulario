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
 * Use the {@link PhrasesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhrasesFragment extends Fragment {
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

    public PhrasesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhrasesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhrasesFragment newInstance(String param1, String param2) {
        PhrasesFragment fragment = new PhrasesFragment();
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
    public void onStop() {
        super.onStop();
        releaseMP();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.word_list, container, false);
        mAudioManager =(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> word = new ArrayList<Word>();
        word.add(new Word("Where are you going?", "a dónde vas",R.raw.phrase_where_are_you_going));
        word.add(new Word("What is your name?", "cómo te llamas",R.raw.phrase_what_is_your_name));
        word.add(new Word("My name is...", "mi nombre es",R.raw.phrase_my_name_is));
        word.add(new Word("How are you feeling?", "Cómo te sientes",R.raw.phrase_how_are_you_feeling));
        word.add(new Word("I’m feeling good.", "me siento bien",R.raw.phrase_im_feeling_good));
        word.add(new Word("Are you coming?", "vienes",R.raw.phrase_are_you_coming));
        word.add(new Word("Yes, I’m coming.", "hSí, ya voy.",R.raw.phrase_yes_im_coming));
        word.add(new Word("I’m coming.", "Ya voy",R.raw.phrase_im_coming));
        word.add(new Word("Let’s go.", "Vamos",R.raw.phrase_lets_go));
        word.add(new Word("Come here.", "Ven aca",R.raw.phrase_come_here));
        WordAdapter wordAdapter=new WordAdapter(getActivity(),word,R.color.category_phrases);
        ListView listView=(ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMP();
                int result = mAudioManager.requestAudioFocus(mOnAudio, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), word.get(position).getAudioId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mrelease);
                }
            }
        });
        return rootView;
    }
    public void releaseMP(){
        if(mediaPlayer !=null){
            mediaPlayer.release();
        }
        mediaPlayer=null;
        mAudioManager.abandonAudioFocus(mOnAudio);
    }
}
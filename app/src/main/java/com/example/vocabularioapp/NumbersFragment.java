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
 * Use the {@link NumbersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumbersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    private MediaPlayer.OnCompletionListener mrelease=new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMP();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudio =new AudioManager.OnAudioFocusChangeListener() {

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


    public NumbersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NumbersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumbersFragment newInstance(String param1, String param2) {
        NumbersFragment fragment = new NumbersFragment();
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
        View rootView= inflater.inflate(R.layout.word_list, container, false);
        mAudioManager =(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> al=new ArrayList<>();
        al.add(new Word("one","uno",R.drawable.number_one,R.raw.number_one));
        al.add(new Word("two","dos",R.drawable.number_two,R.raw.number_two));
        al.add(new Word("three","tres",R.drawable.number_three,R.raw.number_three));
        al.add(new Word("four","cuatro",R.drawable.number_four,R.raw.number_four));
        al.add(new Word("five","cinco",R.drawable.number_five,R.raw.number_five));
        al.add(new Word("six","seis",R.drawable.number_six,R.raw.number_six));
        al.add(new Word("seven","siete",R.drawable.number_seven,R.raw.number_seven));
        al.add(new Word("eight","ocho",R.drawable.number_eight,R.raw.number_eight));
        al.add(new Word("nine","nueve",R.drawable.number_nine,R.raw.number_nine));
        al.add(new Word("ten","diez",R.drawable.number_ten,R.raw.number_ten));

        WordAdapter wordAdapter =new WordAdapter(getActivity(),al,R.color.category_numbers);
        ListView listView =(ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMP();
                int result=mAudioManager.requestAudioFocus(mOnAudio,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    mediaPlayer = MediaPlayer.create(getActivity(), al.get(position).getAudioId());

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

    @Override
    public void onStop() {
        super.onStop();
        releaseMP();
    }
}

package net.iesmila.app6_fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import net.iesmila.app6_fragments.dummy.DummyContent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mId;


    private OnFragmentInteractionListener mListener;

    public FragmentDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param paramId Parameter 1.
     * @return A new instance of fragment FragmentDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDetail newInstance(String paramId) {
        FragmentDetail fragment = new FragmentDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, paramId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString(ARG_PARAM1);
        }
    }

    private EditText edtId;
    private EditText edtName;
    private EditText edtContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        edtId = v.findViewById(R.id.edtId);
        edtName = v.findViewById(R.id.edtName);
        edtContent = v.findViewById(R.id.edtContent);

        DummyContent.DummyItem item =  DummyContent.ITEM_MAP.get(mId);

        edtId.setText(item.id);
        edtName.setText(item.content);
        edtContent.setText(item.details);

        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void onClickListener(View v) {
        // aquí desem els canvis
        // ......
        // notifiquem a l'activity que hem canviat el Ítem
        if(mListener != null) {
            mListener.onItemChanged(mId);
        }
    }

    /**
     * "Missatges" que el fragment passa a l'activity on està connectat.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onItemChanged(String id);
        void onItemDeleted(String id);
    }
}

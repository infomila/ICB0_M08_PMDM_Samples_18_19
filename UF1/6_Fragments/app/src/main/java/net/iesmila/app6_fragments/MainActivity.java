package net.iesmila.app6_fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.iesmila.app6_fragments.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements
                                        FragmentItemList.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListItemClick(DummyContent.DummyItem item) {

    }
}

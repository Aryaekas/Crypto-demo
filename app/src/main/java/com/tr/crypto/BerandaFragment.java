package com.tr.crypto;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BerandaFragment extends Fragment {

    ListView listView;
    String mTitle[] = {"BTC","ETH","BNB","XRP","USDT","ADA","DOGE","DOT"};
    String mDesc[] = {"Bitcoin", "Etherium","Binance Coin","XRP","Tether","Cardano","Doge Coin","Polkadot"};
    int images[] = {R.drawable.btc, R.drawable.eth, R.drawable.bnb, R.drawable.xrp, R.drawable.usdt, R.drawable.ada, R.drawable.doge, R.drawable.dot};




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_beranda,container,false);

        ListView listView = (ListView) v.findViewById(R.id.listView);

        myAdapter adapter = new myAdapter(getActivity(), mTitle, mDesc, images);

        listView.setAdapter(adapter);


        return v;
    }

    class myAdapter extends ArrayAdapter<String>{
        Context context;
        String rTitle[];
        String rDesc[];
        int rImgs[];

        myAdapter (Context c, String title[], String desc[], int imgs[]){
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDesc = desc;
            this.rImgs = imgs;
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.images);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView myDesc = row.findViewById(R.id.textView2);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDesc.setText(rDesc[position]);

            return row;
        }
    }

}

package mardiwaluyo.com.mardiwaluyomobile.main.main.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mardiwaluyo.com.mardiwaluyomobile.R;
import mardiwaluyo.com.mardiwaluyomobile.main.main.model.Menu;

/**
 * Created by Wendra on 10/11/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    ArrayList<Menu> mValues;
    Context mContext;
    protected ItemListener mListener;

    public MenuAdapter(Context context, ArrayList<Menu> values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        Menu item;

        public ViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            textView = (TextView) v.findViewById(R.id.textViewMenu);
            imageView = (ImageView) v.findViewById(R.id.imageViewMenu);
          //  relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);

        }

        public void setData(Menu item) {
            this.item = item;

            textView.setText(item.text);
            imageView.setImageResource(item.image);
           // relativeLayout.setBackgroundColor(Color.parseColor(item.color));

        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public  MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(Menu item);
    }
}


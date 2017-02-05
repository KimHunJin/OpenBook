package org.team2.unithon.openbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.team2.unithon.openbook.R;
import org.team2.unithon.openbook.items.MainFragmentItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HunJin on 2017-02-04.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.GenericViewHolder> {
    private static final String TAG = "MainRecyclerViewAdapter";
    private View mView;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    private List<MainFragmentItem> items;

    /**
     * 생성자
     *
     * @param context
     */
    public MainRecyclerViewAdapter(Context context) {
        super();
        this.mContext = context;
        items = new ArrayList<>();
    }

    /**
     * item으로부터 항목을 가져오는 메서드
     *
     * @return
     */
    public List<MainFragmentItem> getItems() {
        return items;
    }

    /**
     * 리스트에 아이템 추가하는 메서드
     *
     * @param item
     */
    public void addData(MainFragmentItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    /**
     * 리스트를 비우는 메서드
     */
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    /**
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = mLayoutInflater.inflate(R.layout.item_main_contents, viewGroup, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.setDataOnView(position);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    abstract class GenericViewHolder extends RecyclerView.ViewHolder {
        public GenericViewHolder(View itemView) {
            super(itemView);
        }


        public abstract void setDataOnView(int position);

    }

    /**
     * ViewHolder Pattern
     * 리스트에 담고자 하는 아이템 리스트
     */
    class ViewHolder extends GenericViewHolder {
        private ImageView imgMainItem;
        private TextView txtMainTitle;
        private TextView txtMainSub;

        /**
         * 생성자
         * @param v
         */
        public ViewHolder(View v) {
            super(v);
            imgMainItem = (ImageView) v.findViewById(R.id.img_main_contents);
            txtMainTitle = (TextView) v.findViewById(R.id.txt_main_title);
            txtMainSub = (TextView)v.findViewById(R.id.txt_main_subTitle);

        }

        /**
         * position으로 가져온 아이템 항목의 값을
         * 컴포넌트에 저장하는 메서드.
         *
         * @param position
         */
        @Override
        public void setDataOnView(int position) {
            MainFragmentItem listItem = items.get(position);

            Log.e(TAG,listItem.getmImgURL());

            Picasso.with(mLayoutInflater.getContext())
                    .load(listItem.getmImgURL())
                    .into(this.imgMainItem);
            // picasso or glide
            txtMainTitle.setText(listItem.getmTitle().toString().trim());
            txtMainSub.setText(listItem.getmSubTitle().toString().trim());
        }
    }

}

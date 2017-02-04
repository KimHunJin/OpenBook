package org.team2.unithon.openbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.team2.unithon.openbook.R;
import org.team2.unithon.openbook.items.MainMapFragmentItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HunJin on 2017-02-05.
 */

public class MainMapRecyclerViewAdapter extends RecyclerView.Adapter<MainMapRecyclerViewAdapter.GenericViewHolder> {

    private static final String TAG = "MainMapRecyclerViewAdpater";
    private View mView;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    private List<MainMapFragmentItem> items;

    /**
     * 생성자
     *
     * @param context
     */
    public MainMapRecyclerViewAdapter(Context context) {
        super();
        this.mContext = context;
        items = new ArrayList<>();
    }


    /**
     * item으로부터 항목을 가져오는 메서드
     *
     * @return
     */
    public List<MainMapFragmentItem> getItems() {
        return items;
    }

    /**
     * 리스트에 아이템 추가하는 메서드
     *
     * @param item
     */
    public void addData(MainMapFragmentItem item) {
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

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = mLayoutInflater.inflate(R.layout.item_main_map_contents, parent, false);
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
        private TextView txtMainMapTitle;
        private TextView txtMainMapLocaion;

        /**
         * 생성자
         *
         * @param v
         */
        public ViewHolder(View v) {
            super(v);
            txtMainMapTitle = (TextView) v.findViewById(R.id.txt_main_map_title);
            txtMainMapLocaion = (TextView) v.findViewById(R.id.txt_main_map_location);

        }

        /**
         * position으로 가져온 아이템 항목의 값을
         * 컴포넌트에 저장하는 메서드.
         *
         * @param position
         */
        @Override
        public void setDataOnView(int position) {
            MainMapFragmentItem listItem = items.get(position);

            txtMainMapTitle.setText(listItem.getmTitle().toString().trim());
            txtMainMapLocaion.setText(listItem.getmLocation().toString().trim());
        }
    }
}

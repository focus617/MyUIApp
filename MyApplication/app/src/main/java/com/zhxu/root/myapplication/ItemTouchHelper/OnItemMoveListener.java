package com.zhxu.root.myapplication.ItemTouchHelper;

import android.support.v7.widget.RecyclerView;

/**
 * Created by zhxu on 11/3/16.
 */

public interface OnItemMoveListener {
    void onItemMove(int fromPosition, int toPosition);
    void onItemSwiped(RecyclerView.ViewHolder viewHolder, int direction);
}

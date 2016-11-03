package com.zhxu.root.myapplication.ItemTouchHelper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.zhxu.root.myapplication.MyRecyclerAdapter;

/**
 * Created by zhxu on 11/2/16.
 */

public class MyRecyclerHelperCallback extends ItemTouchHelper.Callback {
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags, swipeFlags;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager){
            dragFlags =   ItemTouchHelper.UP |
                                    ItemTouchHelper.DOWN |
                                    ItemTouchHelper.LEFT |
                                    ItemTouchHelper.RIGHT;
            // 如果想支持滑动(删除)操作, swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END
            swipeFlags = 0;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            // 如果想支持滑动(删除)操作, swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END
            swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target){
        // 不同Type之间不可移动
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        MyRecyclerAdapter recycleAdapter = (MyRecyclerAdapter)recyclerView.getAdapter();
        if (recycleAdapter.getOnItemClickListener() instanceof OnItemMoveListener) {
            OnItemMoveListener listener = recycleAdapter.getOnItemClickListener();
            listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
        MyRecyclerAdapter.OnRecyclerViewItemClickListener listener =
                ((MyRecyclerAdapter.MyViewHolder)viewHolder).getViewOnItemClickListener();
        if ( listener instanceof OnItemMoveListener) {
             listener.onItemSwiped(viewHolder, direction);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // 不在闲置状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof OnDragVHListener) {
                OnDragVHListener itemViewHolder = (OnDragVHListener) viewHolder;
                itemViewHolder.onItemSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof OnDragVHListener) {
            OnDragVHListener itemViewHolder = (OnDragVHListener) viewHolder;
            itemViewHolder.onItemFinish();
        }
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        // 需要长按拖拽功能  我们手动控制
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        // 需要滑动功能
        return true;
    }
}

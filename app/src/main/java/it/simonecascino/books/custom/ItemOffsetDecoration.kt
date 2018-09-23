package it.simonecascino.books.custom

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

/**
 * Item decorator for managing spaces between items in a recyclerview
 */
class ItemOffsetDecoration(context: Context, @DimenRes itemOffsetId: Int) : RecyclerView.ItemDecoration() {

    private var mItemOffset: Int = 0

    init{

        mItemOffset = context.getResources().getDimensionPixelSize(itemOffsetId)

    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val layoutManager = parent.layoutManager

        if(layoutManager is LinearLayoutManager){

            //bottom spacing is 0 because in my parent RecyclerView I have a padding bottom.
            //for general porpouse, I should check the orientation and presence of padding.
            outRect.set(mItemOffset, mItemOffset, mItemOffset, 0)

        }

        else if(layoutManager is StaggeredGridLayoutManager){

            val lp = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
            val spanIndex = lp.spanIndex;

            if(spanIndex == 1){
                outRect.left = mItemOffset/2
                outRect.right = mItemOffset
            } else{
                outRect.right = mItemOffset/2;
                outRect.left = mItemOffset;
            }

            outRect.top = mItemOffset

        }
    }


}
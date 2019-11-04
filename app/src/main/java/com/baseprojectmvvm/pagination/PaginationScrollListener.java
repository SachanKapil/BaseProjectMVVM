package com.baseprojectmvvm.pagination;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLinearLayoutManager;
    private boolean isLoading;
    private boolean isLastPage;
    private long thresholdItemCount = 3;
    private PaginationListenerCallbacks paginationListenerCallbacks;

    public PaginationScrollListener(LinearLayoutManager layoutManager, PaginationListenerCallbacks callbacks) {
        mLinearLayoutManager = layoutManager;
        paginationListenerCallbacks = callbacks;
    }

    public PaginationScrollListener(GridLayoutManager layoutManager, PaginationListenerCallbacks callbacks) {
        mLinearLayoutManager = layoutManager;
        paginationListenerCallbacks = callbacks;
    }

    public PaginationScrollListener(LinearLayoutManager layoutManager, PaginationListenerCallbacks callbacks, long thresholdItemCount) {
        mLinearLayoutManager = layoutManager;
        paginationListenerCallbacks = callbacks;
        this.thresholdItemCount = thresholdItemCount;

    }

    public void setLastPageStatus(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public void setFetchingStatus(boolean isLoadingDone) {
        this.isLoading = isLoadingDone;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0 && !isLoading && !isLastPage) {
            if(mLinearLayoutManager != null) {
                int visibleItemCount = mLinearLayoutManager.getChildCount();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - thresholdItemCount) {
                    isLoading = true;
                    paginationListenerCallbacks.loadMoreItems();
                }
            }
        }
    }

    public interface PaginationListenerCallbacks {
        void loadMoreItems();
    }
}

package app.cryptochat.com.cryptochat.Tools;

import android.widget.AbsListView;

/**
 * Created by amudarisova on 09.12.16.
 */

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int visibleThreshold = 4; //Минимальное количество пунктов ниже текущей позиции прокрутки
    private int currentPage = 0; //Текущее смещение индекса данных
    private int previousTotalItemCount = 0; //Общее количество элементов в наборе данных после последней загрузки
    private boolean loading = true; //True, если нас еще ждет последний набор данных для загрузки
    private int startingPageIndex = 0; //Задает начальное значение индекса страницы

    public EndlessScrollListener() {
    }

    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) { this.loading = true; }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount ) {
            loading = onLoadMore(currentPage + 1, totalItemCount);
        }
    }

    public abstract boolean onLoadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

}

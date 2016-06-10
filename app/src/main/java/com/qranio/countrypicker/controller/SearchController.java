package com.qranio.countrypicker.controller;

import android.os.Handler;
import android.os.Message;

import com.qranio.countrypicker.interfaces.Filter;
import com.qranio.countrypicker.util.JCollections;

import java.util.ArrayList;
import java.util.List;

public class SearchController<T> {

    private List<T> itensList;
    private List<T> resultList;
    private Filter<T> filter;
    private SearchResultListener searchResultListener;
    private boolean wait;
    private Thread searchThread;
    private SearchRunnable searRunnable;
    private boolean isStarted;

    public SearchController(List<T> itensList, SearchResultListener<T> searchResultListener) {

        wait = true;
        this.itensList = new ArrayList<T>(itensList);
        this.resultList = new ArrayList<T>(0);
        this.searchResultListener = searchResultListener;

        searRunnable = new SearchRunnable();
        searchThread = new Thread(searRunnable);
        searchThread.start();
    }

    public interface SearchResultListener<T> {

        void searchResult(List<T> itens);
    }

    public void clearFilter() {

        isStarted = false;
        resultList.clear();
        resultList.addAll(itensList);
        searRunnable.sendEmptyMessage(SearchRunnable.CODE);
    }

    public void doSearchApplyFilter(Filter<T> filter) {

        isStarted = true;
        this.filter = filter;
        notifyThread();
    }

    public List<T> getItensList() {

        return itensList;
    }

    public boolean isStarted(){

        return isStarted;
    }

    private boolean notifyThread() {

        try {

            synchronized (searchThread) {

                searchThread.notify();
            }

            return true;

        } catch (Exception exception) {

            return false;
        }
    }

    private boolean waitThread() {

        try {

            synchronized (searchThread) {

                searchThread.wait();
            }

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    private class SearchRunnable extends Handler implements Runnable {

        public static final int CODE = 777;

        @Override
        public void run() {

            while (true) {

                if (wait) {

                    if (filter != null && resultList != null) {

                        resultList.clear();
                        resultList.addAll(JCollections.findAllItensFilter(itensList, filter));
                        sendEmptyMessage(CODE);
                    }
                }

                wait = waitThread();
            }
        }

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            if (msg.what == CODE && searchResultListener != null) {

                searchResultListener.searchResult(resultList);
            }
        }
    }
}

package com.example.lebai.qiuda;

import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by lebai on 2015/4/17.
 */
public class SearchResultWrapper {

    private boolean TEST = true;
    private List<ResultListHolder> mResultList;
    private static SearchResultWrapper mInstance = null;

    public SearchResultWrapper() {
    }

    public static SearchResultWrapper getInstance() {
        if (mInstance == null) {
            mInstance = new SearchResultWrapper();
        }
        return mInstance;
    }

    public List<ResultListHolder> getResultList() {
        return mInstance.mResultList;
    }

    private class ResultListHolder {
        public String Address;
        public String Time;
        public Integer Vacancy;
        public Integer Applied;
        public String Description;
    }

}

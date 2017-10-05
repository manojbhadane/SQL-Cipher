package com.manojbhadane.sqlcipher.activity;

/**
 * Created by manoj.bhadane on 05-10-2017.
 */
public class MainActivityPresenterImpl implements MainActivityPresenter
{
    private MainActivityView mainActivityView;

    public MainActivityPresenterImpl(MainActivityView view)
    {
        mainActivityView = view;
    }

    @Override
    public void insetData()
    {
        mainActivityView.insertData();
    }

    @Override
    public void showData()
    {
        mainActivityView.showData();
    }
}

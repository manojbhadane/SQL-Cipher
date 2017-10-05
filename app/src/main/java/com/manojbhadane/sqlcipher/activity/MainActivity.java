package com.manojbhadane.sqlcipher.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.manojbhadane.sqlcipher.R;
import com.manojbhadane.sqlcipher.adapter.SampleAdapter;
import com.manojbhadane.sqlcipher.database.DbHelper;
import com.manojbhadane.sqlcipher.model.DataModel;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MainActivityView
{
    private Button mBtn;
    private EditText mEdtName;
    private RecyclerView mRecyclerview;

    private SampleAdapter mAdapter;
    private ArrayList<DataModel> arrayList;
    private MainActivityPresenter mPresenter;

    private DbHelper dbHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn = (Button) findViewById(R.id.btninsert);
        mEdtName = (EditText) findViewById(R.id.edt);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        arrayList = new ArrayList<>();
        mAdapter = new SampleAdapter(arrayList);

        SQLiteDatabase.loadLibs(getApplicationContext());
        dbHelper = DbHelper.getInstance(this);
        mDb = dbHelper.getWriteable(this);

        mPresenter = new MainActivityPresenterImpl(this);
        mPresenter.showData();
        mBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mPresenter.insetData();
            }
        });
    }

    @Override
    public void insertData()
    {
        if (mEdtName.getText().toString().length() > 0)
        {
            DataModel model = new DataModel();
            model.setId(new Random().nextInt());
            model.setName(mEdtName.getText().toString());
            dbHelper.save(model);
        }
        mPresenter.showData();
    }

    @Override
    public void showData()
    {
        arrayList = dbHelper.listAll(new DataModel());
        mAdapter = new SampleAdapter(arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);
    }
}

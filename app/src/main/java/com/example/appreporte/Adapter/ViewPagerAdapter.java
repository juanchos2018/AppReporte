package com.example.appreporte.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.appreporte.Presenter.IncidenciaPresenter;
import com.example.appreporte.ui.incidencia.IncidenciasFragment;
import com.example.appreporte.ui.misincidencias.MisIncidenciasFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] titles = {"Object", "Array"};
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==1){
        IncidenciasFragment fragment = IncidenciasFragment.newInstance(position + 1);
        new IncidenciaPresenter(context, fragment);
        return fragment;
        }
        IncidenciasFragment fragment = IncidenciasFragment.newInstance(position + 1);
        new IncidenciaPresenter(context, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}

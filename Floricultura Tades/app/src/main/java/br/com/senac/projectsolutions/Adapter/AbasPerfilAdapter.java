package br.com.senac.projectsolutions.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AbasPerfilAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titulos = new ArrayList<>();

    public AbasPerfilAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void adicionar(Fragment fragment, String titulo){
        this.fragments.add(fragment);
        this.titulos.add(titulo);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titulos.get(position);
    }
}

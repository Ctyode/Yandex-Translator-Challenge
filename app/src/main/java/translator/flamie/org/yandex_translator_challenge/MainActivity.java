package translator.flamie.org.yandex_translator_challenge;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import translator.flamie.org.yandex_translator_challenge.fragments.FavoritesFragment;
import translator.flamie.org.yandex_translator_challenge.fragments.HistoryFragment;
import translator.flamie.org.yandex_translator_challenge.fragments.TranslatorFragment;
import translator.flamie.org.yandex_translator_challenge.model.LocalData;

/**
 * Created by flamie on 20.04.17 :3
 */

public class MainActivity extends FragmentActivity {

    private LocalData localData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        localData = LocalData.forContext(context);
        context.setTheme(R.style.AppTheme);
        setContentView(R.layout.main_activity);
        getFragmentManager().findFragmentById(R.layout.translator_fragment);
        final FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame_layout);
        getFragmentManager().beginTransaction().add(contentFrameLayout.getId(), TranslatorFragment.newInstance(localData)).commit();

        BottomNavigationView bottomBar = (BottomNavigationView) findViewById(R.id.bottomBar);

        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                final int tabId = menuItem.getItemId();

                if (tabId == R.id.tab_translator) {
                    FragmentTransaction translatorTransaction = getFragmentManager().beginTransaction();
                    translatorTransaction.replace(R.id.content_frame_layout, TranslatorFragment.newInstance(localData));
                    translatorTransaction.addToBackStack(null);
                    translatorTransaction.commit();
                    System.out.println("Translator");
                }
                if (tabId == R.id.tab_history) {
                    FragmentTransaction historyTransaction = getFragmentManager().beginTransaction();
                    historyTransaction.replace(R.id.content_frame_layout, HistoryFragment.newInstance(localData));
                    historyTransaction.addToBackStack(null);
                    historyTransaction.commit();
                    System.out.println("History");
                }
                if (tabId == R.id.tab_favorites) {
                    FragmentTransaction historyTransaction = getFragmentManager().beginTransaction();
                    historyTransaction.replace(R.id.content_frame_layout, FavoritesFragment.newInstance(localData));
                    historyTransaction.addToBackStack(null);
                    historyTransaction.commit();
                    System.out.println("Favorites");
                }

                return true;
            }
        });
    }
}

package com.leo_art.weatherboy.utils;

import android.content.Context;

import com.leo_art.weatherboy.R;
import com.leo_art.weatherboy.location.LocationEngine;
import com.leo_art.weatherboy.model.Hero;
import com.leo_art.weatherboy.model.Settings;

import io.realm.Realm;

public class DatabaseUtils {

    public static void writeInitialData(Context context) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();

        Hero boy = realm.createObject(Hero.class);
        boy.setId(1);
        boy.setName("Fry");
        boy.setImageTypeCold(R.mipmap.boy);
        boy.setImageTypeCool(R.mipmap.boy);
        boy.setImageTypeHot(R.mipmap.boy);
        boy.setImageTypeNormal(R.mipmap.boy);
        boy.setImageTypeVeryCold(R.mipmap.boy);
        boy.setImageTypeWarm(R.mipmap.boy);
        boy.setIsPaid(false);
        boy.setSex(Hero.MALE);

        Hero girl = realm.createObject(Hero.class);
        girl.setId(2);
        girl.setName("Leela");
        girl.setImageTypeCold(R.mipmap.girl);
        girl.setImageTypeCool(R.mipmap.girl);
        girl.setImageTypeHot(R.mipmap.girl);
        girl.setImageTypeNormal(R.mipmap.girl);
        girl.setImageTypeVeryCold(R.mipmap.girl);
        girl.setImageTypeWarm(R.mipmap.girl);
        girl.setIsPaid(true);
        girl.setPrice(2);
        girl.setSex(Hero.FEMALE);

        Settings settings = realm.createObject(Settings.class);
        settings.setCurrentHeroId(1);
        settings.setCity(LocationEngine.getInstance(context).getCityName());

        realm.commitTransaction();
    }
}

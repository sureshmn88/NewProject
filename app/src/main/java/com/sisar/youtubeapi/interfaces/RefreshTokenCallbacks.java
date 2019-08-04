package com.sisar.youtubeapi.interfaces;


import android.support.annotation.NonNull;

public interface RefreshTokenCallbacks
{
    void onSuccess(@NonNull boolean value);

    void onError(@NonNull Throwable throwable);
}

package com.example.remindME.comunicator;

import com.example.remindME.model.Content;

/*
* Interface ini dibuat untuk tujuan komunikasi antar fragment untuk mengirim data konten
* Diimplementasikan pada main activity
* */
public interface OnAddContentClickListener {
    void onClickListener(Content content);
}

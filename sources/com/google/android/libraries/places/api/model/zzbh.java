package com.google.android.libraries.places.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.libraries.places:places@@3.2.0 */
/* loaded from: classes2.dex */
final class zzbh implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new zzbi((LocalDate) parcel.readParcelable(TimeOfWeek.class.getClassLoader()), (DayOfWeek) parcel.readParcelable(TimeOfWeek.class.getClassLoader()), (LocalTime) parcel.readParcelable(TimeOfWeek.class.getClassLoader()), parcel.readInt() == 1);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbi[i];
    }
}

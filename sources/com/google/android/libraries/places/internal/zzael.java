package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.2.0 */
/* loaded from: classes2.dex */
public final class zzael extends zzago implements zzahx {
    private static final zzael zzb;
    private int zzd;
    private zzafb zze;
    private zzafb zzf;
    private zzafb zzg;

    static {
        zzael zzaelVar = new zzael();
        zzb = zzaelVar;
        zzago.zzI(zzael.class, zzaelVar);
    }

    private zzael() {
    }

    @Override // com.google.android.libraries.places.internal.zzago
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzF(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zzd", "zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzael();
        }
        if (i2 == 4) {
            return new zzaek(null);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}

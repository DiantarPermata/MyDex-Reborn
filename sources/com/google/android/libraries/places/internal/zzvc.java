package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.2.0 */
/* loaded from: classes2.dex */
public final class zzvc extends zzago implements zzahx {
    private static final zzvc zzb;
    private int zzd;
    private int zze;
    private float zzf;

    static {
        zzvc zzvcVar = new zzvc();
        zzb = zzvcVar;
        zzago.zzI(zzvc.class, zzvcVar);
    }

    private zzvc() {
    }

    @Override // com.google.android.libraries.places.internal.zzago
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzF(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001᠌\u0000\u0002ခ\u0001", new Object[]{"zzd", "zze", zzva.zza, "zzf"});
        }
        if (i2 == 3) {
            return new zzvc();
        }
        if (i2 == 4) {
            return new zzvb(null);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}

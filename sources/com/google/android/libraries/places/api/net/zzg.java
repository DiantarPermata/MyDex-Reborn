package com.google.android.libraries.places.api.net;

import com.google.android.gms.tasks.CancellationToken;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.2.0 */
/* loaded from: classes2.dex */
final class zzg extends FetchPlaceRequest {
    private final String zza;
    private final List zzb;
    private final AutocompleteSessionToken zzc;
    private final CancellationToken zzd;

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzg(String str, List list, AutocompleteSessionToken autocompleteSessionToken, CancellationToken cancellationToken, zzf zzfVar) {
        this.zza = str;
        this.zzb = list;
        this.zzc = autocompleteSessionToken;
        this.zzd = cancellationToken;
    }

    public final boolean equals(Object obj) {
        AutocompleteSessionToken autocompleteSessionToken;
        CancellationToken cancellationToken;
        if (obj == this) {
            return true;
        }
        if (obj instanceof FetchPlaceRequest) {
            FetchPlaceRequest fetchPlaceRequest = (FetchPlaceRequest) obj;
            if (this.zza.equals(fetchPlaceRequest.getPlaceId()) && this.zzb.equals(fetchPlaceRequest.getPlaceFields()) && ((autocompleteSessionToken = this.zzc) != null ? autocompleteSessionToken.equals(fetchPlaceRequest.getSessionToken()) : fetchPlaceRequest.getSessionToken() == null) && ((cancellationToken = this.zzd) != null ? cancellationToken.equals(fetchPlaceRequest.getCancellationToken()) : fetchPlaceRequest.getCancellationToken() == null)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.libraries.places.api.net.FetchPlaceRequest, com.google.android.libraries.places.internal.zzhc
    public final CancellationToken getCancellationToken() {
        return this.zzd;
    }

    @Override // com.google.android.libraries.places.api.net.FetchPlaceRequest
    public final List<Place.Field> getPlaceFields() {
        return this.zzb;
    }

    @Override // com.google.android.libraries.places.api.net.FetchPlaceRequest
    public final String getPlaceId() {
        return this.zza;
    }

    @Override // com.google.android.libraries.places.api.net.FetchPlaceRequest
    public final AutocompleteSessionToken getSessionToken() {
        return this.zzc;
    }

    public final int hashCode() {
        int hashCode = ((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb.hashCode();
        AutocompleteSessionToken autocompleteSessionToken = this.zzc;
        int hashCode2 = ((hashCode * 1000003) ^ (autocompleteSessionToken == null ? 0 : autocompleteSessionToken.hashCode())) * 1000003;
        CancellationToken cancellationToken = this.zzd;
        return hashCode2 ^ (cancellationToken != null ? cancellationToken.hashCode() : 0);
    }

    public final String toString() {
        return "FetchPlaceRequest{placeId=" + this.zza + ", placeFields=" + this.zzb.toString() + ", sessionToken=" + String.valueOf(this.zzc) + ", cancellationToken=" + String.valueOf(this.zzd) + "}";
    }
}

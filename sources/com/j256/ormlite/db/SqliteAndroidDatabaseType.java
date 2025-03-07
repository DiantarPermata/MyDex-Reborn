package com.j256.ormlite.db;

import com.j256.ormlite.android.DatabaseTableConfigUtil;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.DateStringType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.sql.SQLException;

/* loaded from: classes2.dex */
public class SqliteAndroidDatabaseType extends BaseSqliteDatabaseType implements DatabaseType {
    @Override // com.j256.ormlite.db.DatabaseType
    public String getDatabaseName() {
        return "Android SQLite";
    }

    @Override // com.j256.ormlite.db.BaseDatabaseType
    protected String getDriverClassName() {
        return null;
    }

    @Override // com.j256.ormlite.db.BaseDatabaseType, com.j256.ormlite.db.DatabaseType
    public boolean isBatchUseTransaction() {
        return true;
    }

    @Override // com.j256.ormlite.db.DatabaseType
    public boolean isDatabaseUrlThisType(String str, String str2) {
        return true;
    }

    @Override // com.j256.ormlite.db.BaseDatabaseType, com.j256.ormlite.db.DatabaseType
    public boolean isNestedSavePointsSupported() {
        return false;
    }

    @Override // com.j256.ormlite.db.BaseDatabaseType, com.j256.ormlite.db.DatabaseType
    public void loadDriver() {
    }

    @Override // com.j256.ormlite.db.BaseDatabaseType
    protected void appendDateType(StringBuilder sb, FieldType fieldType, int i) {
        appendStringType(sb, fieldType, i);
    }

    @Override // com.j256.ormlite.db.BaseDatabaseType
    protected void appendBooleanType(StringBuilder sb, FieldType fieldType, int i) {
        appendShortType(sb, fieldType, i);
    }

    /* renamed from: com.j256.ormlite.db.SqliteAndroidDatabaseType$1, reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$j256$ormlite$field$SqlType;

        static {
            int[] iArr = new int[SqlType.values().length];
            $SwitchMap$com$j256$ormlite$field$SqlType = iArr;
            try {
                iArr[SqlType.DATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    @Override // com.j256.ormlite.db.BaseSqliteDatabaseType, com.j256.ormlite.db.BaseDatabaseType, com.j256.ormlite.db.DatabaseType
    public FieldConverter getFieldConverter(DataPersister dataPersister) {
        if (AnonymousClass1.$SwitchMap$com$j256$ormlite$field$SqlType[dataPersister.getSqlType().ordinal()] == 1) {
            return DateStringType.getSingleton();
        }
        return super.getFieldConverter(dataPersister);
    }

    @Override // com.j256.ormlite.db.BaseDatabaseType, com.j256.ormlite.db.DatabaseType
    public <T> DatabaseTableConfig<T> extractDatabaseTableConfig(ConnectionSource connectionSource, Class<T> cls) throws SQLException {
        return DatabaseTableConfigUtil.fromClass(connectionSource, cls);
    }
}

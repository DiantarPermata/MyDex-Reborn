package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

/* loaded from: classes2.dex */
public class SelectIterator<T, ID> implements CloseableIterator<T> {
    private static final Logger logger = LoggerFactory.getLogger((Class<?>) SelectIterator.class);
    private final Dao<T, ID> classDao;
    private final CompiledStatement compiledStmt;
    private final DatabaseConnection connection;
    private final ConnectionSource connectionSource;
    private final Class<?> dataClass;
    private final DatabaseResults results;
    private final GenericRowMapper<T> rowMapper;
    private final String statement;
    private boolean first = true;
    private boolean closed = false;
    private boolean alreadyMoved = false;
    private T last = null;
    private int rowC = 0;

    public SelectIterator(Class<?> cls, Dao<T, ID> dao, GenericRowMapper<T> genericRowMapper, ConnectionSource connectionSource, DatabaseConnection databaseConnection, CompiledStatement compiledStatement, String str, ObjectCache objectCache) throws SQLException {
        this.dataClass = cls;
        this.classDao = dao;
        this.rowMapper = genericRowMapper;
        this.connectionSource = connectionSource;
        this.connection = databaseConnection;
        this.compiledStmt = compiledStatement;
        this.results = compiledStatement.runQuery(objectCache);
        this.statement = str;
        if (str != null) {
            logger.debug("starting iterator @{} for '{}'", Integer.valueOf(hashCode()), str);
        }
    }

    public boolean hasNextThrow() throws SQLException {
        boolean next;
        if (this.closed) {
            return false;
        }
        if (this.alreadyMoved) {
            return true;
        }
        if (this.first) {
            this.first = false;
            next = this.results.first();
        } else {
            next = this.results.next();
        }
        if (!next) {
            close();
        }
        this.alreadyMoved = true;
        return next;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        try {
            return hasNextThrow();
        } catch (SQLException e) {
            this.last = null;
            closeQuietly();
            throw new IllegalStateException("Errors getting more results of " + this.dataClass, e);
        }
    }

    @Override // com.j256.ormlite.dao.CloseableIterator
    public T first() throws SQLException {
        if (this.closed) {
            return null;
        }
        this.first = false;
        if (this.results.first()) {
            return getCurrent();
        }
        return null;
    }

    @Override // com.j256.ormlite.dao.CloseableIterator
    public T previous() throws SQLException {
        if (this.closed) {
            return null;
        }
        this.first = false;
        if (this.results.previous()) {
            return getCurrent();
        }
        return null;
    }

    @Override // com.j256.ormlite.dao.CloseableIterator
    public T current() throws SQLException {
        if (this.closed) {
            return null;
        }
        if (this.first) {
            return first();
        }
        return getCurrent();
    }

    @Override // com.j256.ormlite.dao.CloseableIterator
    public T nextThrow() throws SQLException {
        boolean next;
        if (this.closed) {
            return null;
        }
        if (!this.alreadyMoved) {
            if (this.first) {
                this.first = false;
                next = this.results.first();
            } else {
                next = this.results.next();
            }
            if (!next) {
                this.first = false;
                return null;
            }
        }
        this.first = false;
        return getCurrent();
    }

    @Override // java.util.Iterator
    public T next() {
        T nextThrow;
        try {
            nextThrow = nextThrow();
        } catch (SQLException e) {
            e = e;
        }
        if (nextThrow != null) {
            return nextThrow;
        }
        e = null;
        this.last = null;
        closeQuietly();
        throw new IllegalStateException("Could not get next result for " + this.dataClass, e);
    }

    @Override // com.j256.ormlite.dao.CloseableIterator
    public T moveRelative(int i) throws SQLException {
        if (this.closed) {
            return null;
        }
        this.first = false;
        if (this.results.moveRelative(i)) {
            return getCurrent();
        }
        return null;
    }

    public void removeThrow() throws SQLException {
        T t = this.last;
        if (t == null) {
            throw new IllegalStateException("No last " + this.dataClass + " object to remove. Must be called after a call to next.");
        }
        Dao<T, ID> dao = this.classDao;
        if (dao == null) {
            throw new IllegalStateException("Cannot remove " + this.dataClass + " object because classDao not initialized");
        }
        try {
            dao.delete((Dao<T, ID>) t);
        } finally {
            this.last = null;
        }
    }

    @Override // java.util.Iterator
    public void remove() {
        try {
            removeThrow();
        } catch (SQLException e) {
            closeQuietly();
            throw new IllegalStateException("Could not delete " + this.dataClass + " object " + this.last, e);
        }
    }

    @Override // com.j256.ormlite.dao.CloseableIterator
    public void close() throws SQLException {
        if (this.closed) {
            return;
        }
        this.compiledStmt.close();
        this.closed = true;
        this.last = null;
        if (this.statement != null) {
            logger.debug("closed iterator @{} after {} rows", Integer.valueOf(hashCode()), Integer.valueOf(this.rowC));
        }
        this.connectionSource.releaseConnection(this.connection);
    }

    @Override // com.j256.ormlite.dao.CloseableIterator
    public void closeQuietly() {
        try {
            close();
        } catch (SQLException unused) {
        }
    }

    @Override // com.j256.ormlite.dao.CloseableIterator
    public DatabaseResults getRawResults() {
        return this.results;
    }

    @Override // com.j256.ormlite.dao.CloseableIterator
    public void moveToNext() {
        this.last = null;
        this.first = false;
        this.alreadyMoved = false;
    }

    private T getCurrent() throws SQLException {
        T mapRow = this.rowMapper.mapRow(this.results);
        this.last = mapRow;
        this.alreadyMoved = false;
        this.rowC++;
        return mapRow;
    }
}

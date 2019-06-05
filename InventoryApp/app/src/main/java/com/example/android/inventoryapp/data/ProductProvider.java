package com.example.android.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ProductContract.ProductEntry;

public class ProductProvider extends ContentProvider {

    private static final int PRODUCTS = 332;

    private static final int PRODUCT = 333;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(ProductContract.CONTENT_AUTHORITY,ProductContract.PATH_PRODUCTS, PRODUCTS);

        uriMatcher.addURI(ProductContract.CONTENT_AUTHORITY, ProductContract.PATH_PRODUCTS + "/#", PRODUCT);
    }

    private InventoryDBHelper dbHelper;
    @Override
    public boolean onCreate() {
        dbHelper = new InventoryDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        int matchResult = uriMatcher.match(uri);
        switch(matchResult) {
            case PRODUCT:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(ProductContract.ProductEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCTS:
                cursor = database.query(ProductEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;

                default:
                    throw new IllegalArgumentException("Cannot query - unknown URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int matchResult = uriMatcher.match(uri);
        switch (matchResult) {
            case PRODUCTS:
                return ProductEntry.CONTENT_LIST_TYPE;
            case PRODUCT:
                return ProductEntry.CONTENT_ITEM_TYPE;
                default:
                    throw new IllegalStateException("Unknown URI "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int matchResult = uriMatcher.match(uri);
        switch (matchResult) {
            case PRODUCTS:
                return  insertProduct(uri, values);
                default:
                    throw new IllegalArgumentException("Insertion not supported for " + uri);
        }
    }
    private Uri insertProduct(Uri uri, ContentValues values) {
        String product_name = values.getAsString(ProductEntry.COLUMN_PRODUCT_NAME);
        if (TextUtils.isEmpty(product_name)) {
            throw new IllegalArgumentException("Product requires a name");
        }
        Integer price = values.getAsInteger(ProductEntry.COLUMN_PRODUCT_PRICE);
        if (price == null || price <= 0 ) {
            throw new IllegalArgumentException("Product requires valid  price");
        }
        Integer quantity = values.getAsInteger(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        if ( quantity != null && quantity < 0) {
            throw new IllegalArgumentException("Product requires valid quantity");
        }
        String supplier_name = values.getAsString(ProductEntry.COLUMN_SUPPLIER_NAME);
        if (TextUtils.isEmpty(supplier_name)) {
            throw new IllegalArgumentException("Product requires a supplier name");
        }
        String phone_number = values.getAsString(ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER);
        if (TextUtils.isEmpty(phone_number) || !ProductEntry.isValidPhoneNumber(phone_number)) {
            throw new IllegalArgumentException("Supplier phone number is invalid");

        }
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long id = database.insert(ProductEntry.TABLE_NAME, null, values);
        if(id == -1) {
            Log.e("lofsds", "Failed to insert row for " + uri);//TODO: dsdcsd
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int rowsDeleted;
        int matchResult = uriMatcher.match(uri);
        switch (matchResult) {
            case PRODUCTS:
                rowsDeleted = database.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCT:
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
                default:
                    throw new IllegalArgumentException("Deletion cannot be performed for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int matchResult = uriMatcher.match(uri);
        switch (matchResult) {
            case PRODUCTS:
                return updateProduct (uri, values, selection, selectionArgs);
            case PRODUCT:
                selection = ProductEntry._ID + "=?" ;
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};
                return updateProduct (uri, values, selection, selectionArgs);
                default:
                    throw new IllegalArgumentException("Update cannot be performed for " + uri);
        }
    }

    private int updateProduct (Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(ProductEntry.COLUMN_PRODUCT_NAME))
        {
            String product_name = values.getAsString(ProductEntry.COLUMN_PRODUCT_NAME);
            if (TextUtils.isEmpty(product_name)) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }
        if (values.containsKey(ProductEntry.COLUMN_PRODUCT_PRICE)) {
            Integer price = values.getAsInteger(ProductEntry.COLUMN_PRODUCT_PRICE);
            if (price == null|| price <= 0 ) {
                throw new IllegalArgumentException("Product requires valid  price");
            }
        }
        if (values.containsKey(ProductEntry.COLUMN_PRODUCT_QUANTITY)) {
            Integer quantity = values.getAsInteger(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            if ( quantity != null && quantity < 0) {
                throw new IllegalArgumentException("Product requires valid quantity");
            }
        }
        if (values.containsKey(ProductEntry.COLUMN_SUPPLIER_NAME)) {
            String supplier_name = values.getAsString(ProductEntry.COLUMN_SUPPLIER_NAME);
            if (TextUtils.isEmpty(supplier_name)) {
                throw new IllegalArgumentException("Product requires a supplier name");
            }
        }
        if (values.containsKey(ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER)) {
            String phone_number = values.getAsString(ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER);
            if (TextUtils.isEmpty(phone_number) || !ProductEntry.isValidPhoneNumber(phone_number)) {
                throw new IllegalArgumentException("Supplier phone number is invalid");
            }
        }
        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rowsUpdated = database.update(ProductEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}

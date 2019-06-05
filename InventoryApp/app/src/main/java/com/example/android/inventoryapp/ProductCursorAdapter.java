package com.example.android.inventoryapp;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ProductContract.ProductEntry;

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate( R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.name_text_view);
        TextView priceTextView = view.findViewById(R.id.price_text_view);
        TextView inStockTextView = view.findViewById(R.id.in_stock_text_view);
        TextView quantityTextView = view.findViewById(R.id.quantity_text_view);

        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        final int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);

        String productName = cursor.getString(nameColumnIndex);
        int productPrice = cursor.getInt(priceColumnIndex);
        final int productQuantity = cursor.getInt(quantityColumnIndex);
        String prPrice = String.valueOf(productPrice) + "$" ;
        nameTextView.setText(productName);
        priceTextView.setText(prPrice);
        if (productQuantity > 0)
        {
            inStockTextView.setText(R.string.in_stock);
            inStockTextView.setTextColor(context.getResources().getColor(R.color.inStock));
        } else
        {
            inStockTextView.setText(R.string.not_in_stock);
            inStockTextView.setTextColor(context.getResources().getColor(R.color.notInStock));
        }
        String quantitySring = String .valueOf(productQuantity);
        quantityTextView.setText(quantitySring);
        Button sellButton = view.findViewById(R.id.sell_button);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = cursor.getInt(idColumnIndex);
                if (productQuantity >0)
                {
                    int newQuantity = productQuantity -1;
                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, newQuantity);
                    context.getContentResolver().update(ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id),
                            values,null,null);
                    Toast.makeText(context, "Sold!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Out of stock!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

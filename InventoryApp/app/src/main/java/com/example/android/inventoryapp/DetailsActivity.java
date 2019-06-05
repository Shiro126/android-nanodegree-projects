package com.example.android.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ProductContract.ProductEntry;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PRODUCT_LOADER = 0;
    private Uri currentProductUri;
    private EditText nameEditText;
    private EditText priceEditText;
    private EditText quantityEditText;
    private EditText supplierNameEditText;
    private EditText supplierPhoneEditText;
    private ImageButton incrementQuantityButton;
    private ImageButton decrementQuantityButton;
    private Button phoneCallButton;
    private boolean productHasChanged = false;
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            productHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        currentProductUri = intent.getData();

        if (currentProductUri == null) {
            setTitle(getString(R.string.details_activity_title_new_product));
            invalidateOptionsMenu();
        } else {

            setTitle(getString(R.string.details_activity_title_edit_product));
            getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
        }
        nameEditText = findViewById(R.id.edit_product_name);
        priceEditText = findViewById(R.id.edit_product_price);
        quantityEditText = findViewById(R.id.edit_quantity);
        supplierNameEditText = findViewById(R.id.edit_supplier_name);
        supplierPhoneEditText = findViewById(R.id.edit_supplier_phone);
        incrementQuantityButton = findViewById(R.id.button_increment_quantity);
        decrementQuantityButton = findViewById(R.id.button_decrement_quantity);
        phoneCallButton = findViewById(R.id.contact_supplier);


        nameEditText.setOnTouchListener(touchListener);
        priceEditText.setOnTouchListener(touchListener);
        quantityEditText.setOnTouchListener(touchListener);
        supplierNameEditText.setOnTouchListener(touchListener);
        supplierPhoneEditText.setOnTouchListener(touchListener);


        incrementQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity;
                String quantStr = quantityEditText.getText().toString().trim();
                if (TextUtils.isEmpty(quantStr)) {
                    quantity = 1;
                } else {
                    quantity = Integer.parseInt(quantStr) + 1;
                }
                productHasChanged = true;
                quantStr = String.valueOf(quantity);
                quantityEditText.setText(quantStr);
            }
        });
        decrementQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity;
                String quantStr = quantityEditText.getText().toString().trim();
                if (TextUtils.isEmpty(quantStr)) {
                    quantity = 0;
                } else {
                    quantity = Integer.parseInt(quantStr);
                    if (quantity == 0) {
                        quantity = 0;
                    } else {
                        quantity--;
                    }
                }
                quantStr = String.valueOf(quantity);
                productHasChanged = true;
                quantityEditText.setText(quantStr);
            }
        });
        phoneCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumberStr = supplierPhoneEditText.getText().toString().trim();
                if (ProductEntry.isValidPhoneNumber(phoneNumberStr)) {
                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumberStr, null));
                    startActivity(phoneIntent);
                }
            }
        });
    }

    private void saveProduct() {
        String nameStr = nameEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();
        String quantityStr = quantityEditText.getText().toString().trim();
        String suppNameStr = supplierNameEditText.getText().toString().trim();
        String suppPhoneStr = supplierPhoneEditText.getText().toString().trim();

        if (currentProductUri == null && TextUtils.isEmpty(nameStr) &&
                TextUtils.isEmpty(quantityStr) && TextUtils.isEmpty(priceStr) &&
                TextUtils.isEmpty(suppNameStr) && TextUtils.isEmpty(suppPhoneStr))
            return;

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, nameStr);
        values.put(ProductEntry.COLUMN_SUPPLIER_NAME, suppNameStr);
        values.put(ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER, suppPhoneStr);
        int price = 0, quantity = 0;
        if (!TextUtils.isEmpty(quantityStr)) {
            quantity = Integer.parseInt(quantityStr);
        }
        if (!TextUtils.isEmpty(priceStr))
            price = Integer.parseInt(priceStr);
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, price);
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);

        if (currentProductUri == null) {
            Uri newUri = null;
            try {
                newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if (newUri == null) {

                Toast.makeText(this, getString(R.string.error_product_save), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.product_saved), Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            int rowsAffected = 0;
            try {
                rowsAffected = getContentResolver().update(currentProductUri, values, null, null);
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if (rowsAffected == 0) {
                Toast.makeText(this, getString(R.string.error_product_update), Toast.LENGTH_SHORT).show();
            } else {
                finish();
                Toast.makeText(this, getString(R.string.product_updated), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_SUPPLIER_NAME,
                ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER};
        return new CursorLoader(this,
                currentProductUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data == null || data.getCount() < 1)
            return;

        if (data.moveToFirst()) {
            int nameColumnIndex = data.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = data.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = data.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            int suppNameColumnIndex = data.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_NAME);
            int suppPhoneColumnIndex = data.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

            String name = data.getString(nameColumnIndex);
            String suppName = data.getString(suppNameColumnIndex);
            String suppPhone = data.getString(suppPhoneColumnIndex);
            int price = data.getInt(priceColumnIndex);
            int quantity = data.getInt(quantityColumnIndex);

            nameEditText.setText(name);
            supplierNameEditText.setText(suppName);
            supplierPhoneEditText.setText(suppPhone);
            priceEditText.setText(Integer.toString(price));
            quantityEditText.setText(Integer.toString(quantity));
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        nameEditText.setText("");
        priceEditText.setText("");
        quantityEditText.setText("");
        supplierNameEditText.setText("");
        supplierPhoneEditText.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_actions, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (currentProductUri == null) {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveProduct();
                return true;
            case R.id.action_delete:
                deleteConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteProduct() {
        if (currentProductUri != null) {
            int rowsDeleted = getContentResolver().delete(currentProductUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.error_product_delete), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.deleted, Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
    private void deleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_question);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProduct();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null)
                {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}

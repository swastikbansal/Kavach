package com.example.kavach;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmergencyContacts extends AppCompatActivity {

    private static final int REQUEST_SELECT_CONTACT = 1;
    private ContactAdapter contactAdapter;
    private ContactDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new ContactDatabaseHelper(this);
        contactAdapter = new ContactAdapter(databaseHelper.getAllContacts());
        recyclerView.setAdapter(contactAdapter);
    }

    public void onSelectContactsClick(View view) {
        // Open the contact list when the button is clicked
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_CONTACT);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactNumber = null;

                // Retrieve the contact number if available
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id},
                        null);

                if (phoneCursor != null && phoneCursor.moveToFirst()) {
                    contactNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phoneCursor.close();
                }

                cursor.close();

                // Add the selected contact to the database
                ContactInfo selectedContact = new ContactInfo(contactName, contactNumber);
                databaseHelper.addContact(selectedContact);
                contactAdapter.updateContacts(databaseHelper.getAllContacts());
                contactAdapter.notifyDataSetChanged();

                Toast.makeText(EmergencyContacts.this, "Contact saved!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // RecyclerView Adapter for displaying selected contacts
    private class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

        private List<ContactInfo> contacts;

        ContactAdapter(List<ContactInfo> contacts) {
            this.contacts = contacts;
        }

        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_item_contact, parent, false);
            return new ContactViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder holder, @SuppressLint("RecyclerView") int position) {
            ContactInfo contactInfo = contacts.get(position);
            holder.contactName.setText(contactInfo.getName());
            holder.contactNumber.setText(contactInfo.getNumber());

            holder.btnRemoveContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeContact(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }

        class ContactViewHolder extends RecyclerView.ViewHolder {
            TextView contactName;
            TextView contactNumber;
            ImageButton btnRemoveContact;

            ContactViewHolder(View itemView) {
                super(itemView);
                contactName = itemView.findViewById(R.id.contact_name);
                contactNumber = itemView.findViewById(R.id.contact_number);
                btnRemoveContact = itemView.findViewById(R.id.btn_remove_contact);
            }
        }

        void updateContacts(List<ContactInfo> newContacts) {
            contacts.clear();
            contacts.addAll(newContacts);
        }

        private void removeContact(int position) {
            if (position >= 0 && position < contacts.size()) {
                ContactInfo removedContact = contacts.remove(position);
                notifyDataSetChanged();
                // Remove the contact from the database
                databaseHelper.removeContact(removedContact);
                Toast.makeText(EmergencyContacts.this, "Contact removed!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

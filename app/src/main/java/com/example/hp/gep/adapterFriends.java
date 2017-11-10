package com.example.hp.gep;

/**
 * Created by HP on 04/11/2017.
 */

        import android.app.Dialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import android.widget.Toast;
        import java.util.Collections;
        import java.util.List;

        import static android.app.Dialog.*;

public class adapterFriends extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    AlertDialog alertDialog;
    private Context context;
    private LayoutInflater inflater;
    List<friendData> data=Collections.emptyList();
   friendData current;
    int currentPos=0;

    // create constructor to initialize context and data sent from MainActivity
    public adapterFriends(Context context, List<friendData> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.friendcontent, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        friendData current=data.get(position);
        myHolder.textFriendName.setText(current.Name);
        myHolder.textgender.setText("Gender: " + current.gender);
        myHolder.textemail.setText(current.email);
        myHolder.textgender.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }



    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textFriendName;
        TextView textgender;
        TextView textemail;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textFriendName= (TextView) itemView.findViewById(R.id.textName);
            textgender = (TextView) itemView.findViewById(R.id.textgender);
            textemail = (TextView) itemView.findViewById(R.id.textemail);

            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {

            Toast.makeText(context, "You clicked an item", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("AlertDialog");
            builder.setMessage("Would you like to ADD him/her As a Friend?");

            // add the buttons
           // builder.setPositiveButton("Send Request", null);
            builder.setPositiveButton("Send Request", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // do something like...
                    SendRequest();
                }
            });

            builder.setNegativeButton("Cancel", null);

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        public void SendRequest(){
            String email =Email.getEmail();
       String Fname = textFriendName.getText().toString();
        String Femail = textemail.getText().toString();
addFriend addFriend=new addFriend(context);
            addFriend.execute(Fname,Femail,email);
            Toast.makeText(context, Fname, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, Femail, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, email, Toast.LENGTH_SHORT).show();

        }

    }

}

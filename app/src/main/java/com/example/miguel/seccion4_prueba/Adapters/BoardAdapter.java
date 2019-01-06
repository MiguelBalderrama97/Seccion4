package com.example.miguel.seccion4_prueba.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miguel.seccion4_prueba.Models.Board;
import com.example.miguel.seccion4_prueba.R;

import java.util.List;

public class BoardAdapter extends BaseAdapter {

    private Context context;
    private List<Board> list;
    private int layout;

    public BoardAdapter(Context context, List<Board> list, int layout){
        this.context = context;
        this.list = list;
        this.layout = layout;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Board getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
            vh = new ViewHolder();
            vh.title = convertView.findViewById(R.id.textViewBoardTitle);
            vh.cretedAt = convertView.findViewById(R.id.textViewBoardDate);
            vh.notes = convertView.findViewById(R.id.textViewBoardNotes);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        Board board = list.get(position);
        vh.title.setText(board.getTitle());

        int numberOfNotes = board.getNotes().size();
        String textForNotes;
        if(numberOfNotes == 1){
            textForNotes = numberOfNotes + " Note";
        }else{
            textForNotes = numberOfNotes + " Notes";
        }
        vh.notes.setText(textForNotes);

        vh.cretedAt.setText(board.getCreatedAt().toString());
    }

    public class ViewHolder{
        TextView title;
        TextView notes;
        TextView cretedAt;
    }
}

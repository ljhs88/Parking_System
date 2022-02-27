package com.xiyou3g.information;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import com.xiyou3g.information.bean.history_list;

import org.w3c.dom.Text;

public class personal_history extends Fragment implements View.OnClickListener {

    private View view;

    private Button back;

    private RecyclerView recyclerView;
    private List<history_list> history_lists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_history, container, false);

        setHistoryList();

        setRecyclerView();

        back = view.findViewById(R.id.back);
        back.setOnClickListener(this);

        return view;
    }

    private void setHistoryList() {
        history_lists = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            history_list history = new history_list();
            history.setEndName("万达国际"+i);
            history.setTime("9.00-10.00");
            history.setDistance("24km");
            history_lists.add(history);
        }
        Log.d("123", "list："+history_lists.toString());
    }

    public void setRecyclerView() {
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        historyAdapter adapter = new historyAdapter(history_lists);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            getActivity().onBackPressed();
        }
    }

    class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder> {

        private List<history_list> history_lists;

        public historyAdapter(List<history_list> history_lists) {
            this.history_lists = history_lists;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            history_list history = history_lists.get(position);
            holder.endName.setText(history.getEndName());
            holder.time.setText(history.getTime());
            holder.distance.setText(history.getDistance());
        }

        @Override
        public int getItemCount() {
            return history_lists.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView endName;
            TextView time;
            TextView distance;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                endName = itemView.findViewById(R.id.endName);
                time = itemView.findViewById(R.id.time);
                distance = itemView.findViewById(R.id.distance);
            }
        }
    }
}

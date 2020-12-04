package com.example.ingredienttracker.ui.main.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.ingredienttracker.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final ArrayList<String> list_titles;
    private final HashMap<String, ArrayList<String>> list_data;

    public CustomExpandableListAdapter(Context context, ArrayList<String> titles, HashMap<String, ArrayList<String>> data) {
        this.context = context;
        this.list_titles = titles;
        this.list_data = data;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.list_data.get(this.list_titles.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expanded_list_item);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.list_data.get(this.list_titles.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.list_titles.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.list_titles.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.expanded_list_title);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}

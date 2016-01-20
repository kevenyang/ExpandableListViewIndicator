package com.example.pc_libo.expandablelistviewindicator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class MainActivity extends Activity{
    private PinnedHeaderExpandableListView explistview;
    private String[][] childrenData = new String[10][10];
    private String[] groupData = new String[10];
    private int expandSelectIndex = -1;//列表的展开项，－1时没有展开的group
    private PinnedHeaderExpandableAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initView();
        initData();
    }

    /**
     * 初始化VIEW
     */
    private void initView() {
        explistview = (PinnedHeaderExpandableListView)findViewById(R.id.explistview);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        for(int i=0;i<10;i++){
            groupData[i] = "分组"+i;
        }

        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                childrenData[i][j] = "好友"+i+"-"+j;
            }
        }
        //设置悬浮头部VIEW
        explistview.setHeaderView(getLayoutInflater().inflate(R.layout.group_head,
                explistview, false));
        adapter = new PinnedHeaderExpandableAdapter(childrenData, groupData, getApplicationContext(),explistview);
        explistview.setAdapter(adapter);

        //设置单个分组展开
        explistview.setOnGroupClickListener(new GroupClickListener());
    }

    class GroupClickListener implements OnGroupClickListener{
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {
            if (expandSelectIndex == -1) {
                // 展开被选的group
                explistview.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                explistview.setSelectedGroup(groupPosition);
                expandSelectIndex = groupPosition;
            } else if (expandSelectIndex == groupPosition) {
                explistview.collapseGroup(expandSelectIndex);
                expandSelectIndex = -1;
            } else {
                explistview.collapseGroup(expandSelectIndex);
                // 展开被选的group
                explistview.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                explistview.setSelectedGroup(groupPosition);
                expandSelectIndex = groupPosition;
            }
            return true;
        }
    }
}
package com.hemou.common.utils;

public class Page {

    private int start;
    private int count;
    private int total;
    private String param;

    public Page(int start, int count) {
        this.start = start;
        this.count = count;
    }

    public Page(int start, int count, int total) {
        this.start = start;
        this.count = count;
        this.total = total;
    }

    public int getTotalPage() {
        int totalPage;
        if (0 == total % count) totalPage = total / count;
        else totalPage = total / count + 1;
        if (0 == totalPage) totalPage = 1;
        return totalPage;
    }

    public int getLastStart() {
        int last;
        if (0 == total % count) last = total - count;
        else last = total - total % count;
        return Math.max(last, 0);
    }

    public boolean isHasNext(){
        return start < getLastStart();
    }

    public boolean isHasPrev(){
        return start != 0;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", param='" + param + '\'' +
                '}';
    }
}

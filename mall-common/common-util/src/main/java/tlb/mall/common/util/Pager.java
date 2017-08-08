package tlb.mall.common.util;

import java.io.Serializable;
import java.util.List;

public class Pager<T> implements Serializable {

    private static final long serialVersionUID = 2621330343032703770L;

    public static final Integer MAX_PAGE_SIZE = 500;// 每页最大记录数限制

    private Boolean usePager = true;// 是否启用分页 默认启用
    private int offset = 0;// 当前偏移量
    private int start = 0;// 当前偏移量
    private int page = 1;// 当前偏移量
    private int limit = 10;// 每页记录数

    private String order;// 排序方式
    private String sort;// 排序字段名

    private Long total;// 总记录数
    private List<T> rows;// 返回结果

    private volatile int draw = 0;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
        this.offset = start;
    }

    public int getDraw() {
        return ++draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit > MAX_PAGE_SIZE) {
            limit = MAX_PAGE_SIZE;
        }
        this.limit = limit;
    }

    public String getSort() {
        StringBuilder result = new StringBuilder();
        if (sort != null && sort.length() > 0) {
            // 将第一个字符处理成大写
            result.append(sort.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < sort.length(); i++) {
                String s = sort.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0)) && Character.isLetter(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        } else {
            return null;
        }
        return result.toString().toLowerCase();
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Boolean getUsePager() {
        return usePager;
    }

    public void setUsePager(Boolean usePager) {
        this.usePager = usePager;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
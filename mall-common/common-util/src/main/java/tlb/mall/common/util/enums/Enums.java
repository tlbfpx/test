package tlb.mall.common.util.enums;

/**
 * 系统全局枚举类
 * 
 */
public class Enums {

    /**
     * 分页枚举
     * 
     */
    public enum Page {

        DEFAULT_PAGE_SIZE(20, "每页大小");

        private int value;
        private String name;

        private Page(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }
    }
    
    /**
     * 编码枚举
     * 
     */
    public enum Charset {
        DEFAULT_CHARSET("UTF-8", "默认编码");

        private String value;
        private String name;

        private Charset(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }
    }
    
    
    
}

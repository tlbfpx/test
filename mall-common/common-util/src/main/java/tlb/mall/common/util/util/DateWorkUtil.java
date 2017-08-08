package tlb.mall.common.util.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>八小时制工作日的日期与时间之间的计算</p>
 * <p>【有效工作时间节点】 09点-12点、13点-18点 ，其他的时间都不算有效工作时间。</p>
 * 
 * <br/>
 * <br/>
 * <p>【提供两种需求的算法实现】：</p>
 *       <p>一、对某一Date类型的日期，加上相应的有效工作小时数，输出最终的结束日期。</p>
 *         比如以下几个例子：<br/>
 *              1、【将日期2017-03-17 02:00:00加上有效工作日7小时，则结束时间为 2017-03-17 17:00:00，有效工作时间从9点开始计算】<br/>
 *              2、【将日期2017-03-17 10:00:00加上有效工作日3小时，则结束时间为 2017-03-17 14:00:00，其中12点到13点是不算工作时间的。】<br/>
 *              3、【将日期2017-03-17 12:00:00加上有效工作日5小时，则结束时间为 2017-03-17 18:00:00，其中如果结束时间是下班时间，则以下班时间为结束日期，不需要放到第二天的9点。】<br/>
 *              4、【将日期2017-03-17 18:00:00加上有效工作日2小时，则结束时间为 2017-03-18 11:00:00，其中如果超过下班时间18点的，则自动放到第二天的9点开始计算】<br/>
 * 
 * <br/>
 * <br/>
 * <br/>
 * 
 *       <p>二、 对两个Date类型的日期，计算两个日期之间的有效工作小时数</p>
 *         比如以下几个例子：<br/>
 *              【两个日期2017-03-17 02:00:00、2017-03-17 09:00:00，则有效工作小时数为0】<br/>
 *              【两个日期2017-03-17 09:00:00、2017-03-17 13:00:00，则有效工作小时数为3，其中12点到13点不算有效工作时间】<br/>
 *              【两个日期2017-03-17 10:00:00、2017-03-17 15:00:00，则有效工作小时数为4，其中12点到13点不算有效工作时间】<br/>
 *              【两个日期2017-03-17 16:00:00、2017-03-17 21:00:00，则有效工作小时数为2，其中18点到21点不算有效工作时间】<br/>
 *              【两个日期2017-03-17 17:00:00、2017-03-18 08:00:00，则有效工作小时数为1】<br/>
 *              【两个日期2017-03-17 17:00:00、2017-03-18 10:00:00，则有效工作小时数为1，双休日不算工作时间】<br/>
 *              【两个日期2017-03-17 17:00:00、2017-03-18 15:00:00，则有效工作小时数为1，双休日不算工作时间】<br/>
 *
 * <br/>
 * <br/>
 * 
 * @see 参考文章 http://blog.csdn.net/caodegao/article/details/6732376
 * 
 * @author zhoubang
 * @date 2017年3月25日 下午18:36:30
 *
 */
public class DateWorkUtil {

    public static void main(String[] args) throws ParseException {
        //测试日期加上有效工作小时数
        Date date = workHourAdd("2017-03-23 01:00:00", 6);
        System.out.println("最终工作日期：" + formatDateToString(date, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
        
        //测试两个日期之间的有效工作小时数
        int hour = workHourNumber(formatStringToDate("2017-03-23 01:00:00", DATE_FORMAT_YYYY_MM_DD_HH_MM_SS), formatStringToDate("2017-03-23 16:00:00", DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
        System.out.println("有效工作小时：" + hour);
    }
    
    
    
    
    
    private static final boolean LOGDEBUG = true;//是否开启日志输出，在开发调试阶段，建议开始
    private static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";//日期格式
    private static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";//日期格式
    private volatile static Date DATE_RESULT = null;//存放最终日期结果
    
    
    /**
     * 以工作日八小时计算，对日期进行小时数累计
     * 
     * @author zhoubang
     * @date 2017年3月16日 上午10:41:25
     * 
     * @param startDate 起始日期字符串
     * @param hourNumber 有效工作小时数
     * @return 返回最终的日期
     * @throws ParseException 
     */
    public static Date workHourAdd(String startDate, int hourNumber) throws ParseException {
        if(hourNumber <= 0){
            return formatStringToDate(startDate, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        }
        DATE_RESULT = null;
        Date startT = formatStringToDate(startDate, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);//开始时间
        Integer countSecond = hourNumber * 3600;//工作时限(秒)

        int forNumber = getLoopNumber(startDate, startT, hourNumber, countSecond);//循环次数:由于小时数不确定因素，所以存在多天情况，这里需要进行循环累计
        
        for (int i = 0; i < forNumber; i++) {
            String today = formatDateToString(startT, DATE_FORMAT_YYYY_MM_DD);//将当前日期转换为字符串类型
            
            Date when1_1 = formatStringToDate(today + " 17:59:59", DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            if (startT.after(when1_1)) {//超过下班时间(18:00:00),加一层循环数，移到明天算
                forNumber = forNumber + 1;
            }
            String str[] = today.split("-");//作为日志输出使用
            int weekDay = getWeekDay(startT);// 今天星期几
            switch (weekDay) {
                case 1: case 7://说明是周六、周日
                    if(LOGDEBUG){
                        System.out.println("今天:" + getWeekDayName(weekDay) + " 休息!");
                    }
                    forNumber = forNumber + 1;//遇到双休日的情况下，需要跳过，循环次数加一
                    startT = nextDate(startT);//获取下一天
                    break;
                case 2: case 3: case 4: case 5: case 6://正常工作日，周一至周五
                    countSecond = obtainDate(startT, countSecond, str, weekDay, forNumber);
                    startT = nextDate(startT);
                    break;
            }
        }
        return DATE_RESULT;
    }

    /**
     * 以工作日八小时计算，算出2个日期之间的有效工作小时数
     * 
     * @author zhoubang
     * @date 2017年3月16日 上午10:42:52
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 返回两个日期之间的有效工作小时数
     * @throws ParseException 
     */
    @SuppressWarnings("deprecation")
    public static int workHourNumber(Date startDate, Date endDate) throws ParseException {
        if(twoDateIsEqual(startDate, endDate)){//年份、月份、日、小时都相等，直接返回0小时
            return 0;
        }
        
        Integer hour = twoDateHourDiff(startDate, endDate);//两个日期之间的实际相差小时数(包含非有效工作时间在内)
        
        int loop = 0;//计数器
        
        Date date = null;
        /**
         * 循环遍历小时差
         */
        for (int i = 1; i <= hour + 1; i++) {
            date = dateHourAdd(startDate, i);//使用方法级别变量，减少变量new的操作，提升性能
            /**
             * 遇到周六、周日、12点至13点、18点至24点、00点至09点之间，则忽略，这些时间段是非有效工作时间段
             */
            if(date.getHours() <= 9 || date.getHours() == 13 || date.getHours() > 18 || date.getHours() == 0 || getWeekDay(date) == 1 || getWeekDay(date) == 7){
                continue;
            }
            loop++;//计数器累计有效工作小时数
            if(twoDateIsEqual(date, endDate)){//如果新日期与最终的日期相等，则直接返回计数器。
                return loop;
            }
        }
        return loop;
    }
    
    
    
    
    
    
    
    
    
    
    /*====================================================辅助方法 start===================================================*/
    /**
     * 【日期加有效工作小时数】获取需要遍历的次数（由于小时数不确定性，所以存在跨天数的情况下，需要循环处理）
     * 
     * @param startDate
     * @param hourNumber
     * @return
     * @throws ParseException
     */
    @SuppressWarnings("deprecation")
    private static int getLoopNumber(String startDate, Date startT, int hourNumber, Integer countSecond) throws ParseException{
        int forNumber = 0;//循环次数:由于小时数不确定因素，所以存在多天情况，这里需要进行循环累计
        String currentDate = formatDateToString(startT, DATE_FORMAT_YYYY_MM_DD);

        /**
         * 日期临界点范围
         */
        Date dateRange1 = getDateRange(currentDate, "18:00:00");
        Date dateRange1_1 = getDateRange(currentDate, "17:59:59");
        Date dateRange2 = getDateRange(currentDate, "12:59:59");
        Date dateRange2_1 = getDateRange(currentDate, "13:00:00");
        Date dateRange3 = getDateRange(currentDate, "12:00:00");
        Date dateRange3_1 = getDateRange(currentDate, "11:59:59");
        Date dateRange4 = getDateRange(currentDate, "08:59:59");
        Date dateRange4_1 = getDateRange(currentDate, "00:00:00");
        
        /**
         * 下午工作时间（有效工作时间），12:59:59 至 18:00:00
         */
        if (startT.after(dateRange2) && startT.before(dateRange1)) {
            int day =(countSecond / 3600 - (18 - startT.getHours())) / 8;//必须的天数
            int hour = countSecond / 3600 - day * 8 - (18 - startT.getHours());//剩余小时数
            forNumber = forNumber + day;//加上必须的天数
            forNumber++;
            if(hour > 0){
                forNumber++;//剩余小时数大于0，也要算一天
            }
        }
        
        /**
         * 上午工作时间（有效工作时间），08:59:59 至 12:00:00
         */
        else if (startT.after(dateRange4) && startT.before(dateRange3)) {//09 > 12
            int day =(countSecond / 3600 - (18 - startT.getHours() - 1)) / 8;//必须的天数
            int hour = countSecond / 3600 - day * 8 - (18 - startT.getHours() - 1);//剩余小时数
            forNumber = forNumber + day;//加上必须的天数
            forNumber++;
            if(hour > 0){
                forNumber++;
            }
        }
        
        /**
         * 凌晨时间（非工作时间），00:00:00 至 08:59:59   或者时分秒正好是 00:00:00
         */
        else if(startT.after(dateRange4_1) && startT.before(dateRange4) || twoDateIsEqual(startT, dateRange4_1)) {// 00 > 09
            int day =countSecond / 3600 / 8;//必须的天数
            int hour = countSecond / 3600 - day * 8;//剩余小时数
            forNumber = forNumber + day;//加上必须的天数
            if(hour > 0){
                forNumber++;
            }
        }
        
        /**
         * 中午吃饭时间（非工作时间），11:59:59 至 13:00:00
         */
        else if(startT.after(dateRange3_1) && startT.before(dateRange2_1)){//12 > 13 ，从13点开始算
            if(startT.getHours() == 12){
                int day =(countSecond / 3600 - (18 - startT.getHours() - 1)) / 8;//必须的天数
                int hour = countSecond / 3600 - day * 8 - (18 - startT.getHours() - 1);//剩余小时数
                forNumber++;
                forNumber = forNumber + day;//加上必须的天数
                if(hour > 0){
                    forNumber++;//剩余小时数大于0，也要算一天
                }
            }else if(startT.getHours() == 13){
                int day =(countSecond / 3600 - (18 - startT.getHours())) / 8;//必须的天数
                int hour = countSecond / 3600 - day * 8 - (18 - startT.getHours());//剩余小时数
                forNumber++;
                forNumber = forNumber + day;//加上必须的天数
                if(hour > 0){
                    forNumber++;//剩余小时数大于0，也要算一天
                }
            }
        }
        
        /**
         * 超过下班时间18:00:00
         */
        else if (startT.after(dateRange1_1)) {//18， 超过下班时间
            if(startT.getHours() == 18){
                int day =(countSecond / 3600 - (18 - startT.getHours())) / 8;//必须的天数
                int hour = countSecond / 3600 - day * 8 - (18 - startT.getHours());//剩余小时数
                forNumber = forNumber + day;//加上必须的天数
                if(hour > 0){
                    forNumber++;//剩余小时数大于0，也要算一天
                }
            }else{
                int day =(countSecond / 3600) / 8;//必须的天数
                int hour = countSecond / 3600 - day * 8;//剩余小时数
                forNumber = forNumber + day;//加上必须的天数
                if(hour > 0){
                    forNumber++;//剩余小时数大于0，也要算一天
                }
            }
        }
        return forNumber;
    }
    
    /**
     * 【日期加有效工作小时数】处理方法
     * 
     * @param date
     * @param time
     * @param str
     * @param weekDay
     * @param forNumber
     * @return
     * @throws ParseException
     */
    @SuppressWarnings("deprecation")
    private static Integer obtainDate(Date date, Integer time, String[] str, int weekDay, int forNumber) throws ParseException {
        String today = formatDateToString(date, DATE_FORMAT_YYYY_MM_DD);
        
        String fistStr = formatDateToString(date, "yyyy-MM-dd 09:00:00");//将日期格式化成String
       
        Integer half = 3 * 3600;//3小时，表示上午工作的小时数： 9点至12点
        Integer all = 8 * 3600;//每天的工作小时数
        
        String offWorkLog = "";//存放下班时间，日志输出，方便调试

        /**
         * 时间临界点范围
         */
        
        Date dateRange1 = getDateRange(today, "18:00:00");
        Date dateRange1_1 = getDateRange(today, "17:59:59");
        Date dateRange2 = getDateRange(today, "12:59:59");
        Date dateRange2_1 = getDateRange(today, "13:00:00");
        Date dateRange3 = getDateRange(today, "12:00:00");
        Date dateRange3_1 = getDateRange(today, "11:59:59");
        Date dateRange4 = getDateRange(today, "08:59:59");
        Date dateRange4_1 = getDateRange(today, "00:00:00");
        
        String strPrintln = "";//日志输出，用于调试， 例如：今天是2017年03月24日 星期五 上班时间:2017-03-24 09:00:00 下班时间:2017-03-24 10:00:00
        
        /**
         * 下午有效工作时间，12:59:59 至 18:00:00
         */
        if (date.after(dateRange2) && date.before(dateRange1)) {
            Integer interval = (int) ((dateRange1.getTime() - date.getTime()) / 1000);//当前下午必须工作时间(13:00:00 - 18:00:00)，秒为单位
            fistStr = formatDateToString(date, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            int temp = time - interval;//去掉下午必须工作时间
            if (temp > 0l) {//如果剩下的时间大于下午上班的时间，就减去时间差，留到明天算
                time = time - interval;
                offWorkLog = formatDateToString(dateRange1, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            } else {//如果时间在下午的范围内,就算当前时间差
                date = new Date(date.getTime() + time * 1000);
                offWorkLog = formatDateToString(date, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
                time = 0;
            }
            
            strPrintln = "今天是" + str[0] + "年" + str[1] + "月" + str[2] + "日 " + getWeekDayName(weekDay) + " 上班时间:" + fistStr + " 下班时间:" + offWorkLog;//日志输出调试
            
            //避免高并发情况下对全局属性的操作出现问题
            synchronized (DATE_FORMAT_YYYY_MM_DD) {
                DATE_RESULT = formatStringToDate(offWorkLog, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);//每次都记录下
            }
        } 
        
        /**
         * 上午有效工作时间，08:59:59 至 12:00:00
         */
        else if (date.after(dateRange4) && date.before(dateRange3)) {
            Integer interval = (int) ((dateRange3.getTime() - date.getTime()) / 1000);//当前上午必须工作时间(09:00:00-12:00:00),秒为单位
            fistStr = formatDateToString(date, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            
            int temp = time - interval;//去掉上午必须工作时间
            
            if ((temp > 0l && temp / 3600 < 6 || temp > 0l) && temp / 3600 < 6) {//如果剩下的时间在下午的范围内,把下午的计算时间为结束时间
                date = new Date(dateRange2_1.getTime() + (time - interval) * 1000);
                offWorkLog = formatDateToString(date, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
                time = 0;
            } else if (temp <= 0l) {//如果没有剩下时间,那就减去最后的时间作为结束时间
                date = new Date(date.getTime() + time * 1000);
                offWorkLog = formatDateToString(date, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
                time = 0;
            } else {//如果剩下的时间大于下午的时间,就减去下午的时间,把下午下班时间为结束时间
                time = time - interval - 3600 * 5;
                offWorkLog = formatDateToString(dateRange1, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            }
            
            strPrintln = "今天是" + str[0] + "年" + str[1] + "月" + str[2] + "日 " + getWeekDayName(weekDay) + " 上班时间:" + fistStr + " 下班时间:" + offWorkLog;//日志输出调试
            
            //避免高并发情况下对全局属性的操作出现问题
            synchronized (DATE_FORMAT_YYYY_MM_DD) {
                DATE_RESULT = formatStringToDate(offWorkLog, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            }
        } 
        
        /**
         * 凌晨非工作时间，00:00:00 至 08:59:59  或者时分秒正好是 00:00:00
         */
        else if (date.after(dateRange4_1) && date.before(dateRange4) || twoDateIsEqual(date, dateRange4_1)) {
            //如果时间是09:00:00前(还不到上班时间),应该拿到09:00:00点这里开始算
            date.setHours(9);//设置从9点开始计算
            Integer interval = (int) ((dateRange3.getTime() - date.getTime()) / 1000);//当前上午必须工作时间(09:00:00-12:00:00)，秒
            int temp = time - interval;//去掉上午必须工作时间
            if (temp <= half && temp > 0l || (temp - 3600) <= half && temp > 0l) {//如果剩下的时间在下午的范围内,把下午的计算时间为结束时间
                date = new Date(dateRange2_1.getTime() + (time - half) * 1000);
                offWorkLog = formatDateToString(date, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
                time = 0;
            } else if (temp <= 0l) {//如果没有剩下时间,那就减去最后的时间作为结束时间
                date = new Date(date.getTime() + time * 1000);
                offWorkLog = formatDateToString(date, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
                time = 0;
            } else {//如果剩下的时间大于下午的时间,就减去下午的时间,把下午下班时间为结束时间
                time = time - all;
                offWorkLog = formatDateToString(dateRange1, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            }
            
            strPrintln = "今天是" + str[0] + "年" + str[1] + "月" + str[2] + "日 " + getWeekDayName(weekDay) + " 上班时间:" + fistStr + " 下班时间:" + offWorkLog;//日志输出调试
            
            //避免高并发情况下对全局属性的操作出现问题
            synchronized (DATE_FORMAT_YYYY_MM_DD) {
                DATE_RESULT = formatStringToDate(offWorkLog, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            }
        } 
        
        /**
         * 午休吃饭时间（非工作时间），12:00:00 至 13:00:00
         */
        else if (date.after(dateRange3_1) && date.before(dateRange2_1)) {//当前时间是中午休息时间(12:00:00-13:00:00),拿到下午算
            fistStr = formatDateToString(dateRange2_1, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            int temp = time - 3600 * 5;//去掉下午必须工作时间
            if (temp > 0l) {//减去下午工作时间后，如果时间还有剩余，说明是超过了下班时间，那就减去时间差,留到明天算
                time = time - 3600 * 5;
                offWorkLog = formatDateToString(dateRange1, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            } else {//如果时间再下午的范围内,就算当前时间差
                if(date.getHours() == 12){//正好是12点
                    date.setHours(13);//从13点开始计算
                }
                date = new Date(date.getTime() + ((3600 * 5 - Math.abs(temp)) * 1000));
                offWorkLog = formatDateToString(date, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
                time = 0;
            }
            
            strPrintln = "今天是" + str[0] + "年" + str[1] + "月" + str[2] + "日 " + getWeekDayName(weekDay) + " 上班时间:" + fistStr + " 下班时间:" + offWorkLog;//日志输出调试
            
            //避免高并发情况下对全局属性的操作出现问题
            synchronized (DATE_FORMAT_YYYY_MM_DD) {
                DATE_RESULT = formatStringToDate(offWorkLog, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            }
        } 
        
        /**
         * 超过下班时间，18:00:00
         */
        else if (date.after(dateRange1_1)) {//下午下班了,拿到明天再算
            forNumber = forNumber + 1;//循环需要加一
            
            strPrintln = "今天是" + str[0] + "年" + str[1] + "月" + str[2] + "日 " + getWeekDayName(weekDay) + " 您指定的时间已经下班了!";//日志输出调试
        }
        if(LOGDEBUG){
            System.out.println(strPrintln);//打印每天的上下班时间
        }
        return time;
    }
    
    /**
     * 获取临界点时间
     * 
     * @param today
     * @param time
     * @return
     * @throws ParseException
     */
    private static Date getDateRange(String today, String time) throws ParseException{
        return formatStringToDate(today + " " + time, DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
    } 
    /*====================================================辅助方法 end===================================================*/
    
    
    
    
    
    
    
    
    
    
    /*============================================所需工具类方法 start=============================================*/
    /**
     * 
     * @description 将日期字符串转换为日期类型
     * 
     * @author zhoubang 
     * @date 2017年3月24日 下午2:06:26 
     * 
     * @param dateStr 需要转换的日期字符串
     * @param format 日期格式
     * @return 转换后的日期Date
     * @throws ParseException 
     */
    private static Date formatStringToDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat(format);
        return inputFormat.parse(dateStr);
    }
    
    /**
     * 
     * @description 将日期类型转换为对应格式的日期字符串
     * 
     * @author zhoubang 
     * @date 2017年3月24日 下午2:10:01 
     * 
     * @param date 需要转换的日期
     * @param format 日期格式
     * @return 转换后的日期字符串
     */
    private static String formatDateToString(Date date, String format) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(format);
        return inputFormat.format(date);
    }
    
    
    /**
     * 
     * @description 判断两个日期是否为同一天 
     * 
     * @author zhoubang 
     * @date 2017年3月24日 下午3:40:47 
     * 
     * @param date1
     * @param date2
     * @return
     */
    private static boolean twoDateIsEqual(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
        boolean isSameHour = isSameDate && cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY);
        return isSameHour;
    }
    
    
    /**
     * 
     * @description 计算两个日期之间相差的小时数
     * 
     * @author zhoubang 
     * @date 2017年3月24日 下午4:50:41 
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 相差小时数
     * @throws ParseException
     */
    private static int twoDateHourDiff(Date startDate, Date endDate) throws ParseException {
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        
        diff = endDate.getTime() - startDate.getTime();//获得两个时间的毫秒时间差异
        day = diff / nd;//计算差多少天
        hour = diff % nd / nh + day * 24;// 计算差多少小时
        return Integer.parseInt(String.valueOf(hour));
    }
    
    
    /**
     * 
     * @description 对日期加小时数
     * 
     * @author zhoubang 
     * @date 2017年3月24日 下午5:01:29 
     * 
     * @param date
     * @param hour
     * @return 返回最新日期
     */
    private static Date dateHourAdd(Date date, int hour){
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY, hour);
        return ca.getTime();
    }
    /**
     * 
     * @description 获取当前日期是星期几
     * 
     * @author zhoubang 
     * @date 2017年3月24日 下午2:11:34 
     * 
     * @param date
     * @return
     */
    private static int getWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }
    
    /**
     * 
     * @description 获取当前日期的下一天
     * 
     * @author zhoubang 
     * @date 2017年3月24日 下午2:14:05 
     * 
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    private static Date nextDate(Date date) {
        date.setHours(9);
        date.setMinutes(0);
        date.setSeconds(0);
        return (new Date(date.getTime() + 24 * 3600 * 1000));
    }
    
    /**
     * 
     * @description 获取工作日对应的星期几
     *              该方法目前只做控制台日志输出调试使用，也方便后期业务的扩展。
     * 
     * @author zhoubang 
     * @date 2017年3月24日 下午2:15:51 
     * 
     * @param weekDay
     * @return
     */
    private static String getWeekDayName(int weekDay) {
        final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        return dayNames[weekDay - 1];
    }
    /*============================================所需工具类方法 end=============================================*/
}

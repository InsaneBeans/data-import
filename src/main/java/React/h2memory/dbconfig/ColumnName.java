package React.h2memory.dbconfig;

/**
 * 字段名生成类
 * 
 * @author huh
 *
 */
public class ColumnName {
	
	public String getColumnName(String sourceName){
		return null;
	}
	
	public String[] getColumnNames(){
		return null;
	}
	
	/**
	 * 判断单个字符的格式
	 * @param c
	 * @return
	 */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
	
	/**
	 * 判断字符串是否是中文
	 * @param str
	 * @return
	 */
	private String isChinese(String str) {
		 char[] ch = str.toCharArray();
		 int totalChar = ch.length;
		 int trueNum = 0;
	     for (int i = 0; i < ch.length; i++) {
	         char c = ch[i];
	         if (isChinese(c)) {
	             System.out.println("the"+i+"is true");
	             trueNum++;
             } else {
            	 System.out.println("the"+i+"is false");
             }
	     }
	     if(trueNum == totalChar){
	    	 return "全都是中文";
	     } else if(trueNum<totalChar&&trueNum!=0){
	    	 return "一部分是中文";
	     } else if(trueNum==0){
	    	 return "全都是英文";
	     } else{
	    	 return "其他";
	     }
    }
	
	public static void main(String[] args){
		String string = "我是abc";
		System.out.println(new ColumnName().isChinese(string));
	}
}

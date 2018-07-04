package com.guo.common.util;

/**
 * 比较版本号工具类
 */
public class CheckVersionUtil {

	/**
	 * 检查版本大小
	 * @param bver   APP版本
	 * @param sver   服务器版本
	 * @return int  -1 当前版本比服务器版本大  0 相等  1 小(升级)  2版本长度不匹配  3 有版本为空 
	 */
	public static int checkVersion(String bver, String sver){
		int result=0;
		
		if(bver.trim().equals("")||sver.trim().equals("")){
			result=3;
			return result;
		}
		
		try{
			String[] barrs=bver.split("\\.");
			String[] sarrs=sver.split("\\.");
			if(barrs.length!=sarrs.length){
				result=2;
			}
			else{
				for(int i=0;i<barrs.length;i++){
					int bi= Integer.valueOf(barrs[i].toString().trim());
					int si= Integer.valueOf(sarrs[i].toString().trim());
					if(bi<si){
						result=1;
						break;
					}
					
					if(bi>si){
						result=-1;
						break;
					}
				}
			}
		}
		catch(Exception e){
		    e.printStackTrace();
			LogUtils.e("CheckVersionUtil", e, "版本检查异常!");
		}
		return result;
	}
}

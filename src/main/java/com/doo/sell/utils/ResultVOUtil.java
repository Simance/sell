package com.doo.sell.utils;

import com.doo.sell.VO.ResultVO;

/**
 * 返回结果工具类
 */
public class ResultVOUtil {

    //成功且有数据返回
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }
    //成功无数据返回
    public static ResultVO success(){
        return success(null);
    }
    //失败
    public static ResultVO error(Integer code, String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

}

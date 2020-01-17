package com.stevejrong.music.factory.common.enums;

import com.stevejrong.music.factory.common.constants.BaseConstants;

public class BaseEnums {

    /**
     * HTTP请求的请求体数据类型
     */
    public enum HttpRequestBodyDataType implements AbstractEnum {
        APPLICATION_JSON {
            @Override
            public String getDesc() {
                return BaseConstants.APPLICATION_JSON;
            }
        },
        APPLICATION_XML {
            @Override
            public String getDesc() {
                return BaseConstants.APPLICATION_XML;
            }
        }
    }
}

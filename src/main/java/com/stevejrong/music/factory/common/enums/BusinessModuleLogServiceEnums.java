package com.stevejrong.music.factory.common.enums;

import com.stevejrong.music.factory.logs.service.impl.AnalysisOriginalMusicFileModuleLogServiceImpl;
import com.stevejrong.music.factory.logs.service.impl.ComplementsMusicInfoModuleLogServiceImpl;
import com.stevejrong.music.factory.module.impl.AnalysisOriginalMusicFileModule;
import com.stevejrong.music.factory.module.impl.ComplementsMusicInfoModule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public enum BusinessModuleLogServiceEnums implements AbstractEnum {
    ANALYSIS_ORIGINAL_MUSIC_FILE_MODULE {
        @Override
        public String getDesc() {
            return null;
        }

        public Object getBusinessModule() {
            return new AnalysisOriginalMusicFileModule();
        }

        public Object getLogServiceImpl() {
            return new AnalysisOriginalMusicFileModuleLogServiceImpl();
        }
    },
    COMPLEMENTS_MUSIC_INFO_MODULE {
        @Override
        public String getDesc() {
            return null;
        }

        public Object getBusinessModule() {
            return new ComplementsMusicInfoModule();
        }

        public Object getLogServiceImpl() {
            return new ComplementsMusicInfoModuleLogServiceImpl();
        }
    };

    public static Class getLogServiceImplClassByBusinessModule(Class businessModuleClass) {
        for (BusinessModuleLogServiceEnums item : BusinessModuleLogServiceEnums.values()) {

            Class clazz = item.getClass();
            Method getBusinessModuleMethod = null, getLogServiceImplMethod = null;

            try {
                getBusinessModuleMethod = clazz.getMethod("getBusinessModule");
                getLogServiceImplMethod = clazz.getMethod("getLogServiceImpl");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            Class businessModule = null, logServiceImpl = null;
            try {
                businessModule = getBusinessModuleMethod.invoke(item).getClass();
                logServiceImpl = getLogServiceImplMethod.invoke(item).getClass();
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            if (businessModuleClass.equals(businessModule)) {
                return logServiceImpl;
            }
        }

        return null;
    }
}

package com.stevejrong.music.factory.common.enums;

import com.stevejrong.music.factory.provider.service.log.impl.AnalyzingInfoForAudioFileModuleLogServiceImpl;
import com.stevejrong.music.factory.provider.service.log.impl.ComplementsInfoForAudioFileModuleLogServiceImpl;
import com.stevejrong.music.factory.provider.service.music.impl.AnalyzingInfoForAudioFileModule;
import com.stevejrong.music.factory.provider.service.music.impl.ComplementsInfoForAudioFileModule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public enum BusinessModuleLogServiceEnums implements AbstractEnum {
    ANALYZING_INFO_FOR_AUDIO_FILE_MODULE {
        @Override
        public String getDesc() {
            return null;
        }

        public Object getMusicFactoryModule() {
            return new AnalyzingInfoForAudioFileModule();
        }

        public Object getLogServiceImpl() {
            return new AnalyzingInfoForAudioFileModuleLogServiceImpl();
        }
    },
    COMPLEMENTS_INFO_FOR_AUDIO_FILE_MODULE {
        @Override
        public String getDesc() {
            return null;
        }

        public Object getMusicFactoryModule() {
            return new ComplementsInfoForAudioFileModule();
        }

        public Object getLogServiceImpl() {
            return new ComplementsInfoForAudioFileModuleLogServiceImpl();
        }
    };

    public static Class getLogServiceImplClassByBusinessModule(Class businessModuleClass) {
        for (BusinessModuleLogServiceEnums item : BusinessModuleLogServiceEnums.values()) {

            Class clazz = item.getClass();
            Method getMusicFactoryModuleMethod = null, getLogServiceImplMethod = null;

            try {
                getMusicFactoryModuleMethod = clazz.getMethod("getMusicFactoryModule");
                getLogServiceImplMethod = clazz.getMethod("getLogServiceImpl");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            Class musicFactoryModule = null, logServiceImpl = null;
            try {
                musicFactoryModule = getMusicFactoryModuleMethod.invoke(item).getClass();
                logServiceImpl = getLogServiceImplMethod.invoke(item).getClass();
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            if (businessModuleClass.equals(musicFactoryModule)) {
                return logServiceImpl;
            }
        }

        return null;
    }
}

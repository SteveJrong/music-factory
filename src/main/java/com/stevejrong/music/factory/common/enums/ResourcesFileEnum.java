package com.stevejrong.music.factory.common.enums;

public enum ResourcesFileEnum implements AbstractEnum {

    DEFAULT_ALBUM_PICTURE {
        @Override
        public String getValue() {
            return "images/default_album_pic.png";
        }
    }
}

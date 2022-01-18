/*
 *             Copyright (C) 2022 Steve Jrong
 * 
 * 	   GitHub Homepage: https://www.github.com/SteveJrong
 *      Gitee Homepage: https://gitee.com/stevejrong1024
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.music.factory.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Steve Jrong
 * create date: 2021-11-19 5:39 PM
 * @since 1.0
 */
public class TestBean2 implements Serializable {
    private static final long serialVersionUID = 5090241533556855311L;

    private String favoriteColor;

    private List<String> favoriteFoods;

    private int favoriteNum;

    private Map<String ,String> favoriteFriendsNameAndAddress;

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public List<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(List<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public int getFavoriteNum() {
        return favoriteNum;
    }

    public void setFavoriteNum(int favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    public Map<String, String> getFavoriteFriendsNameAndAddress() {
        return favoriteFriendsNameAndAddress;
    }

    public void setFavoriteFriendsNameAndAddress(Map<String, String> favoriteFriendsNameAndAddress) {
        this.favoriteFriendsNameAndAddress = favoriteFriendsNameAndAddress;
    }
}
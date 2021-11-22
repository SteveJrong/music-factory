/**
 * Copyright 2021 Steve Jrong
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stevejrong.music.factory.spi.music.bean.partner.kugoumusic;

import com.stevejrong.music.factory.spi.music.bean.partner.AbstractPartnerSongInfoFilterCriteriaBean;

import java.io.Serializable;

/**
 * @author Steve Jrong
 * create date: 2021-11-14 3:46 PM
 * @since 1.0
 */
public class KuGouMusicPartnerSongInfoFilterCriteriaBean extends AbstractPartnerSongInfoFilterCriteriaBean implements Serializable {
    private static final long serialVersionUID = -7564928167972408542L;

    public KuGouMusicPartnerSongInfoFilterCriteriaBean() {
        super();
    }
}